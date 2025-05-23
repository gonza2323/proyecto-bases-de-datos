import { useAuth0 } from "@auth0/auth0-react";
import { LoginButton } from "./LoginButton";
import { LogoutButton } from "./LogoutButton";
import { SignupButton } from "./SignupButton";
import { Link } from "react-router-dom";

export const AuthStatus = () => {
  const { isLoading, isAuthenticated, user } = useAuth0();

  if (isLoading) return <div>Loading...</div>

  if (!isAuthenticated) return (
    <div>
      <LoginButton />
      <SignupButton />
    </div>
  )

  return (
    <div>
      <Link to="/profile">{user.email}</Link>
      <LogoutButton />
    </div>
  )
};
