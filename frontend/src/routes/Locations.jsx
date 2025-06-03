import { useState } from "react";
import { LocationList } from "../components/LocationList";
import { Link } from "react-router-dom";
import { Button, Group, Stack, Title } from "@mantine/core";
import { config } from "../config";
import { useAuth0 } from "@auth0/auth0-react";

export const Locations = () => {

  const { isLoading, getAccessTokenSilently } = useAuth0();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const downloadReport = async () => {
    setIsSubmitting(true);

    const token = await getAccessTokenSilently();

    try {
      const response = await fetch(`${config.API_URL}/me/report`, {
        method: "GET",
        headers: {
          authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        }
      });
      
      if (!response.ok) {
        console.error("Failed to fetch PDF");
        return;
      }
      
      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "report.pdf");
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error(error);
    } finally {
      setIsSubmitting(false);      
    }
  };

  return (
    <Stack>
      <Stack>
        <Group>
          <Title>Mis Sucursales</Title>
          <Button onClick={downloadReport} loading={isLoading || isSubmitting}>Generar Reporte</Button>
          <Button component={Link} to="/manage/locations/new">Agregar Sucursal</Button>
        </Group>
        <LocationList isManagementView />
      </Stack>
    </Stack>
  )
};
