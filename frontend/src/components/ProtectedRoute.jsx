import { useAuth0 } from "@auth0/auth0-react";
import { Navigate, useLocation } from "react-router-dom";

export default function ProtectedRoute({ children }) {
  const { isAuthenticated, isLoading, loginWithRedirect } = useAuth0();
  const location = useLocation();

  if (isLoading) return <div>Loading...</div>;

  if (!isAuthenticated) {
    console.log("Not authenticated");
    loginWithRedirect({
      appState: { returnTo: location.pathname },
    });
    return null;
  }

  console.log("Authenticated");
  return children;
}