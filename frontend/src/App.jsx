import pedidosNowLogo from './assets/pedidosnow-logo.svg'
import RestaurantCardList from './components/RestaurantCardList'
import './App.css'

function App() {
  return (
    <>
      <header>
        <div className="header-left">
          <div className='logo-and-title'>
            <img src={pedidosNowLogo} className="logo" alt="React logo" width="50px"/>
            <div className="title">PedidosNow</div>
          </div>
          <div className='address'>
            Enviar a
          </div>
        </div>
        <input type="search" name="" id="" />
        <div>
          Gonzalo
        </div>
      </header>
      <div className='content'>
        <RestaurantCardList />
      </div>
      <footer>
        Creado por gonza2323
      </footer>
    </>
  )
}

export default App
