import { useAuth0 } from "@auth0/auth0-react";
import { Button } from "@mantine/core";

export const LoginButton = () => {
  const { loginWithRedirect } = useAuth0();

  const handleLogin = async () => {
    await loginWithRedirect({
      appState: {
        returnTo: "/manage",
      },
    });
  };

  return (
    <Button className="rounded-md px-5 py-1 border-2 font-bold hover:cursor-pointer" onClick={handleLogin}>
      Iniciar Sesión
    </Button>
  );
};
