import React from 'react';
import './Principal.css';
import Logo from "./img/Logo.svg"
import Crash from "./img/Crash.svg"
import Dice from "./img/Dice.svg"
import Campo from "./img/Campo Minado.svg"
function App() {
  return (
    <div>
      <nav>
        <div className="logo">
          {<img src ={Logo} alt="Logo do site" title="PogBet"/>}
        </div>
        <div>
          <a href="#">Jogos</a>
          <a href="#">Sobre</a>
        </div>
        <div className="button">
          <button>Login</button>
          <button>Cadastro</button>
        </div>
      </nav>

      <div className="image-section">
        <img src ={Crash} alt="imagem do Crash" title="Crash"/>
        <img src ={Dice} alt="imagem do Dice" title="Dice"/>
        <img src ={Campo} alt="imagem do Campo Minado" title="Campo Minado"/>
      </div>
    </div>
  );
}

export default App;
