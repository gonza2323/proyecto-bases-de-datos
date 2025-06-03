import { useAuth0 } from "@auth0/auth0-react";
import { Button, Group } from "@mantine/core";

export const LogoutButton = () => {
  const { logout } = useAuth0();

  const handleLogout = () => {
    logout({
      logoutParams: {
        returnTo: window.location.origin,
      },
    });
  };

  return (
    <Button className="rounded-md px-5 py-1 border-2 font-bold hover:cursor-pointer" onClick={handleLogout}>
      Cerrar Sesión
    </Button>
  );
};
