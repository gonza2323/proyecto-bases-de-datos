import pedidosNowLogo from '../assets/pedidosnow-logo.svg'
import { Link } from 'react-router-dom';

function Header() {
  return (
    <header>
        <div className="header-left">
            <Link className='logo-and-title' to="/">
                <img src={pedidosNowLogo} className="logo" alt="React logo" width="50px"/>
                <div className="title">PedidosNow</div>
            </Link>
            <div className='address'>
            Enviar a
            </div>
        </div>
        <input type="search" name="" id="" />
        <Link to="/profile">
            Gonzalo
        </Link>
    </header>
  )
}

export default Header;
