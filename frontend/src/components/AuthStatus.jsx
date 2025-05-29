import { useAuth0 } from "@auth0/auth0-react";
import { LoginButton } from "./LoginButton";
import { LogoutButton } from "./LogoutButton";
import { SignupButton } from "./SignupButton";
import { Link } from "react-router-dom";
import { useContext } from "react";
import { UserContext } from "../contexts/UserContext";

export const AuthStatus = () => {
  const { isLoading, isAuthenticated, user, error } = useContext(UserContext);

  if (isLoading) return <div>Loading...</div>

  if (!isAuthenticated) return (
    <div className="header-right">
      <LoginButton />
      <SignupButton />
    </div>
  )

  return (
    <div className="header-right">
      <Link to="/manage">{user?.name || user?.email || 'error'}</Link>
      <LogoutButton />
    </div>
  )
};
