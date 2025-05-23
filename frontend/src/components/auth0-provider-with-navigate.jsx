import { Auth0Provider } from "@auth0/auth0-react";
import { useNavigate } from "react-router-dom";
import { config } from "../config"


export const Auth0ProviderWithNavigate = ({ children }) => {
  const navigate = useNavigate();

  const onRedirectCallback = (appState) => {
    navigate(appState?.returnTo || window.location.pathname);
  };

  if (!(config.AUTH0_DOMAIN && config.AUTH0_CLIENT_ID &&
        config.AUTH0_CALLBACK_URL && config.AUTH0_AUDIENCE)) {
    console.error("Could not load auth0 config");
    return null;
  }

  return (
    <Auth0Provider
      domain={config.AUTH0_DOMAIN}
      clientId={config.AUTH0_CLIENT_ID}
      authorizationParams={{
        audience: config.AUTH0_AUDIENCE,
        redirect_uri: config.AUTH0_CALLBACK_URL,
      }}
      onRedirectCallback={onRedirectCallback}
    >
      {children}
    </Auth0Provider>
  );
};