import { Container, Stack, Title, Text, Button, Card, Group, Modal, TextInput, NumberInput, NativeSelect, Alert } from "@mantine/core"
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { config } from "../config";
import { useAuth0 } from "@auth0/auth0-react";
import { useForm } from "@mantine/form";


export const Location = ({ isManagementView }) => {

  const { isLoading, getAccessTokenSilently } = useAuth0();
  const { locationId } = useParams();
  const [error, setError] = useState(null);
  const [location, setLocation] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [dialogState, setDialogState] = useState({
    type: null,
    menuItem: null,
  });

  const fetchData = async () => {
    try {
      const response = await fetch(`${config.API_URL}/locations/${locationId}`)
      if (!response.ok) throw new Error(response.statusText);
      const json = await response.json();
      setLocation(json);
    } catch (error) {
      console.error(error.message);
      setError(error.message || 'An unknown error occurred');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if (!isLoading)
      fetchData();
  }, [isLoading]);

  const handleDeleteButton = (menuItem) => {
    setDialogState({
      type: "delete",
      menuItem,
    })
  }

  const handleUpdateButton = (menuItem) => {
    setDialogState({
      type: "update",
      menuItem,
    })
  }

  const handleCreateButton = () => {
    setDialogState({
      type: "create",
      menuItem: null,
    })
  }

  const closeDialog = () => {
    setDialogState({
      type: null,
      menuItem: null,
    })
  }

  const createItem = async (values) => {
    try {
      setIsSubmitting(true);
      const token = await getAccessTokenSilently();

      const fixedValues = {
        ...values,
        categoryId: Number.parseInt(values.categoryId)
      };

      const response = await fetch(`${config.API_URL}/me/locations/${locationId}/menu`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(fixedValues)
      });
      if (!response.ok) throw new Error('Failed to submit')

      const newMenuItem = await response.json();

      setLocation(data => ({
        ...data,
        menuItems: [...data.menuItems, newMenuItem]
      }))

      closeDialog();
    } catch (err) {
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  const updateItem = async (values) => {
    try {
      setIsSubmitting(true);
      const token = await getAccessTokenSilently();

      const fixedValues = {
        ...values,
        categoryId: Number.parseInt(values.categoryId)
      };

      const response = await fetch(`${config.API_URL}/me/menu/${dialogState.menuItem.id}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(fixedValues)
      });
      if (!response.ok) throw new Error('Failed to submit')
      
      const newItem = await response.json();
      
      setLocation(data => ({
        ...data,
        menuItems: data.menuItems.map( i => i.id === newItem.id ? newItem : i)
      }))

      closeDialog();
    } catch (err) {
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  const deleteItem = async () => {
    try {
      setIsSubmitting(true);
      const token = await getAccessTokenSilently();

      const response = await fetch(`${config.API_URL}/me/menu/${dialogState.menuItem.id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) throw new Error('Failed to submit')

      setLocation(data => ({
        ...data,
        menuItems: data.menuItems.filter(i => i.id !== dialogState.menuItem.id)
      }))

      closeDialog();
    } catch (err) {
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  if (loading) return null;

  return (
    <Container>
      <Stack>
        {error && `ERROR: ${error}`}
        {!error && <>
          <Title>{location.name}</Title>
          <Text>{location.open ? 'Abierto' : 'Cerrado'}</Text>
          <Text>{location.rating !== null ? `Rating: ${location.rating}/5` : 'Sin calificaciones'}</Text>
          {location.menuItems.length === 0 && "Este restaurante no tiene un menú cargado"}
          {location.menuItems.length !== 0 && <>
            <Stack>
              {location.menuItems.map(i => <MenuItem
                key={i.id}
                menuItem={i}
                isManagementView={isManagementView}
                onDelete={handleDeleteButton}
                onUpdate={handleUpdateButton}
              />)}
            </Stack>
          </>}
        </>}
        {isManagementView && <>
          <Group justify="flex-end">
            <Button onClick={handleCreateButton}>Agregar ítem al menú</Button>
          </Group>
          <MenuItemDialog
            opened={dialogState.type === 'create' || dialogState.type === 'update'}
            menuItem={dialogState.menuItem}
            type={dialogState.type}
            onClose={closeDialog}
            onConfirmCreate={createItem}
            onConfirmUpdate={updateItem}
            submitting={isSubmitting}
            categories={location.categories.map(c => ({ label: c.name, value: c.id }))}
          />
          <ConfirmDeleteItemDialog
            opened={dialogState.type === 'delete'}
            menuItem={dialogState.menuItem}
            onClose={closeDialog}
            onConfirm={deleteItem}
            submitting={isSubmitting}
          />
        </>}
      </Stack>
    </Container>
  )
}


const MenuItem = ({ menuItem, isManagementView, onDelete, onUpdate }) => {

  const [isSubmitting, setIsSubmitting] = useState(false);
  const { getAccessTokenSilently } = useAuth0();
  
  const toggleIsAvailable = async () => {
    try {
      setIsSubmitting(true);
      const token = await getAccessTokenSilently();

      const response = await fetch(`${config.API_URL}/me/menu/${menuItem.id}/available`, {
        method: 'PATCH',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(!menuItem.available)
      });

      if (!response.ok) {
        throw new Error('Failed to submit');
      }

      menuItem.available = !menuItem.available;

    } catch (err) {
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <Card>
      <Group justify="space-between" align="stretch">
        <Stack gap="xs">
          <Group>
            <Title fz="h2">{menuItem.name}</Title>
            {!menuItem.available && <Alert>NO DISPONIBLE</Alert>}
          </Group>
          <Text>{menuItem.category.name}</Text>
          <Text>{menuItem.description}</Text>
        </Stack>
        <Stack align="end" justify="space-between">
          <Text size="xl">${menuItem.price}</Text>
          {!isManagementView && <Button disabled={!menuItem.available}>Comprar</Button>}
        </Stack>
      </Group>

      {isManagementView && (
        <Group justify="flex-end">
          <Button
            onClick={toggleIsAvailable}
            loading={isSubmitting}
          >
            {menuItem.available ? "Marcar como no disponible" : "Marcar como disponible"}
          </Button>
          <Button onClick={() => onUpdate(menuItem)}>Editar</Button>
          <Button onClick={() => onDelete(menuItem)}>Borrar</Button>
        </Group>
      )}
    </Card>
  )
}

const MenuItemDialog = ({ menuItem, type, categories, onConfirmCreate, onConfirmUpdate, submitting, ...props }) => {

  const form = useForm({
    mode: 'uncontrolled',
    initialValues: {
      name: '',
      description: '',
      price: 0,
      categoryId: 0,
      imageUrl: ''
    }
  })

  useEffect(() => {
    if (type === 'create') {
      form.setValues({
        name: '',
        description: '',
        price: 0,
        categoryId: 0,
        imageUrl: ''
      })
    } else {
      form.setValues({
        name: menuItem?.name,
        description: menuItem?.description,
        price: menuItem?.price,
        categoryId: menuItem?.category?.id,
        imageUrl: ''
      })
    }
  }, [type])

  return (
    <Modal
      centered
      title={type === "create" ? "Agregar nuevo ítem al menú" : `Editar ${menuItem?.name}`}
      {...props}
    >
      <form onSubmit={form.onSubmit((type === "create" ? onConfirmCreate : onConfirmUpdate))}>
        <Stack>
          <TextInput
            withAsterisk
            label="Nombre"
            placeholder="BigMac"
            {...form.getInputProps('name')}
          />
          <TextInput
            withAsterisk
            label="Descripción"
            placeholder="Hamburguesa con queso, lechuga y pepino"
            {...form.getInputProps('description')}
          />
          <NumberInput
            withAsterisk
            label="Precio"
            placeholder="$1.500,00"
            {...form.getInputProps('price')}
          />
          <NativeSelect
            withAsterisk
            label="Categoría"
            data={categories}
            {...form.getInputProps('categoryId')}
          />

          <Group justify="flex-end">
            <Button onClick={props.onClose}>Cancelar</Button>
            <Button
              type="submit"
              loading={submitting}
            >
              Confirmar
            </Button>
          </Group>
        </Stack>
      </form>
    </Modal>
  )
}

const ConfirmDeleteItemDialog = ({ menuItem, onConfirm, submitting, ...props }) => {
  return (
    <Modal
      centered
      title='Borrar ítem del menú'
      {...props}
    >
      <Stack>
        <Text>Está seguro de qué desea borrar el ítem "{menuItem?.name}"?</Text>
        <Group justify="flex-end">
          <Button onClick={props.onClose}>Cancelar</Button>
          <Button
            loading={submitting}
            onClick={onConfirm}
          >
            Confirmar
          </Button>
        </Group>
      </Stack>
    </Modal>
  )
}
