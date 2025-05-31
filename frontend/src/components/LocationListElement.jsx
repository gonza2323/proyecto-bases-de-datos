import { useAuth0 } from "@auth0/auth0-react";
import { Card, Modal, Text } from "@mantine/core";
import { Button } from "@mantine/core";
import { useRef, useState } from "react";
import { Link } from "react-router-dom";
import { config } from "../config";

export const LocationListElement = ({ location, isManagementView, onDelete }) => {
  const { name, open, rating, id } = location;
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

  return (
    <Card>
      <div>
        <div>{name}</div>
        <div>{open ? 'Abierto' : 'Cerrado'}</div>
        <div>Rating: {rating}/5</div>
        {isManagementView && (
          <>
            <Button component={Link} to={`/manage/locations/${id}/edit`}>Editar</Button>
            <Button onClick={() => setDeleteDialogOpen(true)}>Borrar</Button>
          </>
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
