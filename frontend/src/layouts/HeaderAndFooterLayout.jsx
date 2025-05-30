import { Outlet } from 'react-router-dom';
import { Header } from "../components/Header";
import { Footer } from "../components/Footer";

export const HeaderAndFooterLayout = () => (
  <div className='min-h-screen flex flex-col'>
    <Header />
    <main className='flex-1 bg-gray-100 text-black'>
      <Outlet />
    </main>
    <Footer />
  </div>
);
