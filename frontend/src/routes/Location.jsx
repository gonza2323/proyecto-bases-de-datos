import { Container, Stack, Title, Text, Button, Card, Group, Modal } from "@mantine/core"
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { config } from "../config";
import { useAuth0 } from "@auth0/auth0-react";


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

  const createItem = async () => {
    console.log("Created new item");
    closeDialog();
  }

  const updateItem = async () => {
    console.log(`Updated item ${dialogState.menuItem.name}`);
    closeDialog();
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
          <Text>Rating: {location.rating}/5</Text>
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
          <Button onClick={handleCreateButton}>Agregar ítem al menú</Button>
          <MenuItemDialog
            opened={dialogState.type === 'create' || dialogState.type === 'update'}
            menuItem={dialogState.menuItem}
            type={dialogState.type}
            onClose={closeDialog}
            onConfirmCreate={createItem}
            onConfirmUpdate={updateItem}
            submitting={isSubmitting}
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
  return (
    <Card>
      <Title>{menuItem.name}</Title>
      <Text>{menuItem.category}</Text>
      <Text>{menuItem.description}</Text>
      <Text>${menuItem.price}</Text>

      {isManagementView && (
        <Group>
          <Button onClick={() => onUpdate(menuItem)}>Editar</Button>
          <Button onClick={() => onDelete(menuItem)}>Borrar</Button>
        </Group>
      )}
      {!isManagementView && <Button>Comprar</Button>}
    </Card>
  )
}

const MenuItemDialog = ({menuItem, type, onConfirmCreate, onConfirmUpdate, submitting, ...props}) => {
  return (
    <Modal
      centered
      title={type === "create" ? "Agregar nuevo ítem al menú" : `Editar ${menuItem?.name}`}
      {...props}
    >
      <Button onClick={props.onClose}>Cancelar</Button>
      <Button
        disabled={submitting}
        onClick={type === "create" ? onConfirmCreate : onConfirmUpdate}
      >
        Confirmar
      </Button>
    </Modal>
  )
}

const ConfirmDeleteItemDialog = ({menuItem, onConfirm, submitting, ...props}) => {
  return (
    <Modal
      centered
      title='Borrar ítem del menú'
      {...props}
    >
      <Text>Está seguro de qué desea borrar el ítem "{menuItem?.name}"?</Text>
      <Button onClick={props.onClose}>Cancelar</Button>
      <Button
        disabled={submitting}
        onClick={onConfirm}
      >
        Confirmar
      </Button>
    </Modal>
  )
}
