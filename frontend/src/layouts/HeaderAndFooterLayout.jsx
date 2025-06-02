import { Outlet } from 'react-router-dom';
import { Header } from "../components/Header";
import { Footer } from "../components/Footer";
import { Container } from '@mantine/core';

export const HeaderAndFooterLayout = () => (
  <div className='min-h-screen flex flex-col'>
    <Header />
    <main className='flex-1 bg-gray-100 text-black pt-10 pb-20'>
      <Container>
        <Outlet />
      </Container>
    </main>
    <Footer />
  </div>
);
