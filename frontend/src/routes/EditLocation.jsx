import { Button, TextInput, Fieldset, Title, Stack, Group } from "@mantine/core"
import { useForm } from "@mantine/form"
import { useEffect, useState } from "react";
import { config } from "../config";
import { useAuth0 } from "@auth0/auth0-react";
import { Link, useNavigate, useParams } from "react-router-dom";

export const EditLocation = () => {

  const { locationId } = useParams();
  const { getAccessTokenSilently, isLoading } = useAuth0();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isLoadingValues, setIsLoadingValues] = useState(false);
  const navigate = useNavigate();

  const form = useForm({
    mode: 'uncontrolled',
    initialValues: {
      name: '',
      address: {
        province: '',
        municipio: '',
        localidad: '',
        street: '',
        number: '',
        floor: '',
        apartment: '',
        phoneNumber: '',
        observation: '',
        latitude: 0,
        longitude: 0,
      },
    }
  })

  useEffect(() => {
    const fetchInitialValues = async () => {
      try {
        setIsLoadingValues(true);
        const token = await getAccessTokenSilently();

        const response = await fetch(`${config.API_URL}/me/locations/${locationId}`, {
          headers: {
            authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          }
        });

        if (!response.ok) throw new Error(response.statusText);

        const values = await response.json();
        form.setValues(values);
      } catch (error) {
        console.error(error.message || 'An unknown error occurred');
      } finally {
        setIsLoadingValues(false);
      }
    }

    if (!isLoading)
      fetchInitialValues();

  }, [isLoading]);

  const handleSubmit = async (values) => {
    setIsSubmitting(true);
    try {
      const token = await getAccessTokenSilently();

      const response = await fetch(`${config.API_URL}/me/locations/${locationId}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });

      if (!response.ok) {
        throw new Error('Failed to submit');
      }

      navigate('/manage/locations');

      const body = await response.json;

    } catch (err) {
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Stack>
      <Title>Editar Sucursal</Title>
      <form onSubmit={form.onSubmit(handleSubmit)}>
        <Stack>
          <Fieldset>
            <TextInput
              withAsterisk
              label="Nombre"
              disabled={isLoadingValues}
              {...form.getInputProps('name')}
            />
            <TextInput
              withAsterisk
              label="Provincia"
              placeholder="Mendoza"
              disabled={isLoadingValues}
              {...form.getInputProps('address.province')}
            />
            <TextInput
              disabled={isLoadingValues}
              withAsterisk
              label="Municipio"
              placeholder="Ciudad de Mendoza"
              {...form.getInputProps('address.municipio')}
            />
            <TextInput
              disabled={isLoadingValues}
              withAsterisk
              label="Localidad"
              placeholder="1A. Sección"
              {...form.getInputProps('address.localidad')}
            />
            <TextInput
              disabled={isLoadingValues}
              withAsterisk
              label="Calle"
              placeholder="Av. San Martín"
              {...form.getInputProps('address.street')}
            />
            <TextInput
              disabled={isLoadingValues}
              withAsterisk
              label="Número"
              placeholder="520"
              {...form.getInputProps('address.number')}
            />
            <TextInput
              disabled={isLoadingValues}
              label="Piso"
              placeholder=""
              {...form.getInputProps('address.floor')}
            />
            <TextInput
              disabled={isLoadingValues}
              label="Departamento"
              placeholder=""
              {...form.getInputProps('address.apartment')}
            />
            <TextInput
              disabled={isLoadingValues}
              withAsterisk
              label="Teléfono"
              placeholder="261-264-7733"
              {...form.getInputProps('address.phoneNumber')}
            />
            <TextInput
              disabled={isLoadingValues}
              label="Observación"
              placeholder=""
              {...form.getInputProps('address.observation')}
            />
          </Fieldset>

          <Group justify="flex-end">
            <Button component={Link} to="/manage/locations">Cancelar</Button>
            <Button type="Submit" disabled={isLoadingValues} loading={isSubmitting}>Aceptar</Button>
          </Group>
        </Stack>
      </form>
    </Stack>
  )
}
