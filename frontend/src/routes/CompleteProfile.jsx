import { useContext, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { useNavigate, useLocation } from "react-router-dom";
import { config } from "../config";
import { UserContext } from "../contexts/UserContext";
import { useForm } from "@mantine/form";
import { Button, Fieldset, Group, Stack, TextInput, Title } from "@mantine/core";


export const CompleteProfile = () => {
  const { updateUserDetails } = useContext(UserContext);
  const { user, getAccessTokenSilently } = useAuth0();
  const navigate = useNavigate();
  const location = useLocation();

  const originalDestination = location.state?.returnTo || "/";

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const form = useForm({
    mode: 'uncontrolled',
    initialValues: {
      restaurantName: '',
      legalAddress: ''
    }
  })

  const handleSubmit = async (values) => {
    setIsSubmitting(true);
    setError(null);

    try {
      const token = await getAccessTokenSilently();

      console.log({
          ...values,
          email: user.email,
        })

      const response = await fetch(`${config.API_URL}/users`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          ...values,
          email: user.email,
        })
      });

      if (!response.ok) {
        throw new Error('Failed to create user profile');
      }

      updateUserDetails();
      navigate(originalDestination, { replace: true });

    } catch (err) {
      console.error(err);
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Stack>
      <Title>Registro de restaurante</Title>
      <p>Complete el registro de su restaurante para utilizar la plataforma</p>

      {error && <div>Error: {error}</div>}

      <form onSubmit={form.onSubmit(handleSubmit)}>
        <Stack>
        <Fieldset>
          <TextInput
            withAsterisk
            label="Nombre del restaurante"
            placeholder="McDonald's"
            {...form.getInputProps('restaurantName')}
          />
          <TextInput
            withAsterisk
            label="Domicilio legal"
            placeholder="Emilio Civit 256, Mendoza, Argentina"
            {...form.getInputProps('legalAddress')}
          />
        </Fieldset>

        <Group justify="flex-end">
        <Button type="submit" loading={isSubmitting}>
          {isSubmitting ? 'Creando Perfil' : 'Registrar'}
        </Button>
        </Group>
        </Stack>
      </form>
    </Stack>
  );
};