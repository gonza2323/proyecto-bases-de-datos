import { useState } from 'react'
import pedidosNowLogo from './assets/pedidosnow-logo.svg'
import './App.css'

function App() {
  // const [count, setCount] = useState(0)

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
        {/* <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button> */}
      </div>
      <footer>
        Creado por gonza2323
      </footer>
    </>
  )
}

export default App
