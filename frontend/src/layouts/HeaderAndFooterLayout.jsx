import { Outlet } from 'react-router-dom';
import { Header } from "../components/Header";
import { Footer } from "../components/Footer";

export const HeaderAndFooterLayout = () => (
  <>
    <Header />
    <main>
      <Outlet />
    </main>
    <Footer />
  </>
);
