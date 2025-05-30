import pedidosNowLogo from '../assets/pedidosnow-logo.svg'
import { Link } from 'react-router-dom';
import { AuthStatus } from './AuthStatus';

export const Header = () => (
  <header className='bg-red-500 flex px-14 py-5 justify-between text-white'>
      <Link className='flex gap-2 items-center text-3xl' to="/">
          <img className='w-10 hover:-rotate-5 transition-all' src={pedidosNowLogo} alt="React logo"/>
          <div className="font-bold italic">PedidosNow</div>
      </Link>
      <AuthStatus />
  </header>
);
