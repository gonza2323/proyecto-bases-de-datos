import { useAuth0 } from "@auth0/auth0-react";
import { Button } from "@mantine/core";

export const SignupButton = () => {
  const { loginWithRedirect } = useAuth0();

  const handleSignUp = async () => {
    await loginWithRedirect({
      appState: {
        returnTo: "/complete-profile",
      },
      authorizationParams: {
        screen_hint: "signup",
      },
    });
  };

  return (
    <Button className="rounded-md px-5 py-1 border-2 font-bold hover:cursor-pointer" onClick={handleSignUp}>
      Registrarme
    </Button>
  );
};
