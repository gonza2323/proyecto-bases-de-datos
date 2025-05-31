import { Card } from "@mantine/core";
import { Button } from "@mantine/core";
import { Link } from "react-router-dom";

export const LocationListElement = ({ location, isManagementView }) => {
  const { name, open, rating, id } = location;

  return (
    <Card>
      <div>
        <div>{name}</div>
        <div>{open ? 'Abierto' : 'Cerrado'}</div>
        <div>Rating: {rating}/5</div>
        {isManagementView && (
          <>
            <Button component={Link} to={`/manage/locations/${id}/edit`}>Editar</Button>
            <Button>Borrar</Button>
          </>
        )}
      </div>
    </Card>
  )
};
