import { createRoot } from 'react-dom/client';
import { StrictMode } from 'react';
import { UserProvider } from './contexts/UserContext';
import { BrowserRouter } from 'react-router-dom';
import { Auth0ProviderWithNavigate } from './components/auth0-provider-with-navigate';
import '@mantine/core/styles.css';
import { App } from "./App";

import './styles/index.css';
import { createTheme, MantineProvider } from '@mantine/core';


const theme = createTheme({
  primaryColor: 'red'
});

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <Auth0ProviderWithNavigate>
        <UserProvider>
          <MantineProvider withGlobalStyles withNormalizeCSS theme={theme}>
            <App />
          </MantineProvider>
        </UserProvider>
      </Auth0ProviderWithNavigate>
    </BrowserRouter>
  </StrictMode>
)
