import { Card } from "@mantine/core";
import { Button } from "@mantine/core";

export const LocationListElement = ({ location, isManagementView }) => {
  const { name, open, rating } = location;

  return (
    <Card>
      <div>
        <div>{name}</div>
        <div>{open ? 'Abierto' : 'Cerrado'}</div>
        <div>Rating: {rating}/5</div>
        {isManagementView && (
          <>
            <Button>Editar</Button>
            <Button>Borrar</Button>
          </>
        )}
      </div>
    </Card>
  )
};
