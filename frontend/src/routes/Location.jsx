import { Container, Stack, Title, Text, Button, Card, Group } from "@mantine/core"
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { config } from "../config";


export const Location = ({ isManagementView }) => {

  const { locationId } = useParams();
  const [error, setError] = useState(null);
  const [location, setLocation] = useState(null);
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`${config.API_URL}/locations/${locationId}`)

        if (!response.ok) throw new Error(response.statusText);

        const json = await response.json();
        console.log(json)

        setLocation(json);
      } catch (error) {
        console.error(error.message);
        setError(error.message || 'An unknown error occurred');
      } finally {
        setLoading(false);
      }
    }

    fetchData();
  }, []);

  if (isLoading) return null;

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
              {location.menuItems.map(i => <MenuItem key={i.id} menuItem={i} isManagementView={isManagementView} />)}
            </Stack>
          </>}
        </>}
        {isManagementView && <Button>Add Menu Item</Button>}
      </Stack>
    </Container>
  )
}


const MenuItem = ({ menuItem, isManagementView }) => {
  return (
    <Card>
      <Title>{menuItem.name}</Title>
      <Text>{menuItem.category}</Text>
      <Text>{menuItem.description}</Text>
      <Text>${menuItem.price}</Text>
      
      {isManagementView && (
        <Group>
          <Button>Editar</Button>
          <Button>Borrar</Button>
        </Group>
      )}
      {!isManagementView && <Button>Comprar</Button>}
    </Card>
  )
}