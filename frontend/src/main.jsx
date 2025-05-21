import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Auth0Provider } from "@auth0/auth0-react";
import { RouterProvider } from "react-router-dom";
import router from "./router"

import './styles/reset.css'
import './styles/index.css'


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Auth0Provider
      domain="dev-76dptwssfg0ud536.us.auth0.com"
      clientId="XzZxpvzbSrdVDRtVb1Wi4NvXCqzgR3UT"
      authorizationParams={{
        redirect_uri: window.location.origin,
      }}
    >
      <RouterProvider router={router} />
    </Auth0Provider>
  </StrictMode>,
)
