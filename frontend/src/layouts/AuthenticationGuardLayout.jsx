import { useAuth0 } from "@auth0/auth0-react";
import { Outlet, useLocation } from "react-router-dom";
import { useEffect } from "react";

export const AuthenticationGuardLayout = () => {
  const { isAuthenticated, isLoading, loginWithRedirect } = useAuth0();
  const location = useLocation();

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      loginWithRedirect({
        appState: { returnTo: location.pathname + location.search },
      });
    }
  }, [isLoading, isAuthenticated, loginWithRedirect, location]);

  if (isLoading) return null;
  if (!isAuthenticated) return null; // redirecting, so don’t render anything

  return <Outlet />;
};
