import { useAuth0 } from "@auth0/auth0-react";
import { useEffect } from "react";

export const AuthenticationGuard = ({ children }) => {
  const { isAuthenticated, isLoading, loginWithRedirect } = useAuth0();

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      loginWithRedirect();
    }
  }, [isLoading, isAuthenticated, loginWithRedirect]);

  if (isLoading) return null;
  if (!isAuthenticated) return null;

  return children;
};
