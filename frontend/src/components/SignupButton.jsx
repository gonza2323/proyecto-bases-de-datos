import { useAuth0 } from "@auth0/auth0-react";

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
    <button className="rounded-md px-5 py-1 border-2 font-bold hover:cursor-pointer" onClick={handleSignUp}>
      Registrarme
    </button>
  );
};
