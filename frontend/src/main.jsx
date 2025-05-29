import { createRoot } from 'react-dom/client';
import { StrictMode } from 'react';
import { UserProvider } from './contexts/UserContext';
import { ChakraProvider, defaultSystem } from "@chakra-ui/react"
import { ThemeProvider } from "next-themes"
import { BrowserRouter } from 'react-router-dom';
import { Auth0ProviderWithNavigate } from './components/auth0-provider-with-navigate';
import { App } from "./App";

// import './styles/reset.css';
import './styles/index.css';


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <Auth0ProviderWithNavigate>
        <UserProvider>
          <ChakraProvider value={defaultSystem}>
            <ThemeProvider attribute="class" disableTransitionOnChange>
              <App />
            </ThemeProvider>
          </ChakraProvider>
        </UserProvider>
      </Auth0ProviderWithNavigate>
    </BrowserRouter>
  </StrictMode>
)
