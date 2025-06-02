import { useAuth0 } from "@auth0/auth0-react";
import { Card, Checkbox, Group, Modal, Text, Title } from "@mantine/core";
import { Button } from "@mantine/core";
import { useRef, useState } from "react";
import { Link } from "react-router-dom";
import { config } from "../config";

export const LocationListElement = ({ location, isManagementView, onDelete }) => {
  const { name, isOpen, rating, id } = location;
  const { isLoading, getAccessTokenSilently } = useAuth0();
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const isDeleted = useRef(false);

  const submitDelete = async () => {
    try {
      setIsSubmitting(true);
      const token = await getAccessTokenSilently();

      const response = await fetch(`${config.API_URL}/me/locations/${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('Failed to submit');
      }

      isDeleted.current = true;
      setDeleteDialogOpen(false);

    } catch (err) {
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  const handleDelete = () => {
    if (!isLoading)
      submitDelete();
  }

  const toggleIsOpen = async () => {
    try {
      setIsSubmitting(true);
      const token = await getAccessTokenSilently();

      const response = await fetch(`${config.API_URL}/me/locations/${id}/open`, {
        method: 'PATCH',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(!isOpen)
      });

      if (!response.ok) {
        throw new Error('Failed to submit');
      }

      location.isOpen = !isOpen;

    } catch (err) {
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  let linkToLocationPage = `locations/${id}`;
  if (isManagementView)
    linkToLocationPage = `/manage/locations/${id}`;

  return (
    <Card>
      <div>
        <Link to={linkToLocationPage}>
          <Title>{name}</Title>
          <div>{isOpen ? 'Abierto' : 'Cerrado'}</div>
          <div>{rating !== null ? `Rating: ${rating}/5` : 'Sin calificaciones'}</div>
        </Link>
        {isManagementView && (
          <Group justify="flex-end">
            <Button disabled={isSubmitting} onClick={toggleIsOpen}>{isOpen ? 'Cerrar' : 'Abrir'}</Button>
            <Button component={Link} to={`/manage/locations/${id}/edit`}>Editar</Button>
            <Button onClick={() => setDeleteDialogOpen(true)}>Borrar</Button>
          </Group>
        )}
      </div>
      <Modal
        opened={deleteDialogOpen}
        onClose={() => setDeleteDialogOpen(false)}
        onExitTransitionEnd={() => isDeleted.current && onDelete(id) }
        title="Borrar Sucursal"
        centered
      >
        <Text>{name}</Text>
        <Text>Está seguro de qué desea eliminar esta sucursal? Se eliminar también el menú de la misma</Text>
        <Button onClick={() => setDeleteDialogOpen(false)}>Cancelar</Button>
        <Button disabled={isSubmitting} onClick={handleDelete}>Borrar</Button>
      </Modal>
    </Card>
  )
};
