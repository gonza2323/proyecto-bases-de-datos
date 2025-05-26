import { createRoot } from 'react-dom/client';
import { StrictMode } from 'react';
import { ChakraProvider } from '@chakra-ui/react';
import { system } from '@chakra-ui/react/preset';
import { BrowserRouter } from 'react-router-dom';
import { Auth0ProviderWithNavigate } from './components/auth0-provider-with-navigate';
import { App } from "./App";

import './styles/reset.css';
import './styles/index.css';


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ChakraProvider value={system}> 
      <BrowserRouter>
        <Auth0ProviderWithNavigate>
          <App />
        </Auth0ProviderWithNavigate>
      </BrowserRouter>
    </ChakraProvider>
  </StrictMode>
)
