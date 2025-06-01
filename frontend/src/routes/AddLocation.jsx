import { Button, TextInput, Fieldset, Title, Stack } from "@mantine/core"
import { useForm } from "@mantine/form"
import { useState } from "react";
import { config } from "../config";
import { useAuth0 } from "@auth0/auth0-react";
import { useNavigate } from "react-router-dom";

export const AddLocation = ({variant="create"}) => {

  const { getAccessTokenSilently } = useAuth0();
  const [isSubmitting, setIsSubmitting] = useState(false);
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

  const handleSubmit = async (values) => {
    setIsSubmitting(true);
    try {
      const token = await getAccessTokenSilently();

      const response = await fetch(`${config.API_URL}/me/locations`, {
        method: 'POST',
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
      <Title>Agregar Nueva Sucursal</Title>
      <form onSubmit={form.onSubmit(handleSubmit)}>
        <Fieldset>
          <TextInput
            withAsterisk
            label="Nombre"
            {...form.getInputProps('name')}
          />
          <TextInput
            withAsterisk
            label="Provincia"
            placeholder="Mendoza"
            {...form.getInputProps('address.province')}
          />
          <TextInput
            withAsterisk
            label="Municipio"
            placeholder="Ciudad de Mendoza"
            {...form.getInputProps('address.municipio')}
          />
          <TextInput
            withAsterisk
            label="Localidad"
            placeholder="1A. Sección"
            {...form.getInputProps('address.localidad')}
          />
          <TextInput
            withAsterisk
            label="Calle"
            placeholder="Av. San Martín"
            {...form.getInputProps('address.street')}
          />
          <TextInput
            withAsterisk
            label="Número"
            placeholder="520"
            {...form.getInputProps('address.number')}
          />
          <TextInput
            label="Piso"
            placeholder=""
            {...form.getInputProps('address.floor')}
          />
          <TextInput
            label="Departamento"
            placeholder=""
            {...form.getInputProps('address.apartment')}
          />
          <TextInput
            withAsterisk
            label="Teléfono"
            placeholder="261-264-7733"
            {...form.getInputProps('address.phoneNumber')}
          />
          <TextInput
            label="Observación"
            placeholder=""
            {...form.getInputProps('address.observation')}
          />
        </Fieldset>
        
        <Button type="Submit" loading={isSubmitting}>Agregar</Button>
      </form>
    </Stack>
  )
}
