import { Outlet } from 'react-router-dom';
import { Auth0Provider } from "@auth0/auth0-react";
import { Header } from "../components/Header";
import { Footer } from "../components/Footer";

export const RootLayout = () => (
  <>
    <Header />
    <main>
      <Outlet />
    </main>
    <Footer />
  </>
);
