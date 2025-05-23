import pedidosNowLogo from '../assets/pedidosnow-logo.svg'
import { Link } from 'react-router-dom';
import { AuthStatus } from './AuthStatus';

export const Header = () => (
  <header>
      <Link className='logo-and-title' to="/">
          <img src={pedidosNowLogo} className="logo" alt="React logo" width="50px"/>
          <div className="title">PedidosNow</div>
      </Link>
      <AuthStatus />
  </header>
);
