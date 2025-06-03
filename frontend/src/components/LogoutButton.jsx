import { useAuth0 } from "@auth0/auth0-react";
import { Button, Group } from "@mantine/core";

export const LogoutButton = () => {

  return (
    <Button className="rounded-md px-5 py-1 border-2 font-bold hover:cursor-pointer" onClick={handleLogout}>
      Cerrar Sesión
    </Button>
  );
};
