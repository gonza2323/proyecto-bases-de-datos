import pedidosNowLogo from '../assets/pedidosnow-logo.svg'
import { Link } from 'react-router-dom';
import { AuthStatus } from './AuthStatus';
import { useMediaQuery } from '@mantine/hooks';

export const Header = () => {
  const isMobile = useMediaQuery('(max-width: 768px)');
  

  if (isMobile) return (
    <header className='bg-red-500 flex px-4 py-5 justify-between gap-5 text-white'>
      <Link className='flex gap-2 items-center text-3xl' to="/">
      <img className='w-10 hover:-rotate-5 transition-all' src={pedidosNowLogo} alt="React logo" />
      </Link>
      <AuthStatus />
    </header>
  )

  return (
    <header className='bg-red-500 flex px-14 py-5 justify-between text-white'>
      <Link className='flex gap-2 items-center text-3xl' to="/">
        <img className='w-10 hover:-rotate-5 transition-all' src={pedidosNowLogo} alt="React logo" />
        <div className="font-bold italic">PedidosNow</div>
      </Link>
      <AuthStatus />
    </header>
  )
};
