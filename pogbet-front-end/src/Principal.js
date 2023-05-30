import React from 'react';
import './Principal.css';
import Logo from "./img/Logo.svg"
import Crash from "./img/Crash.svg"
import Dice from "./img/Dice.svg"
import Campo from "./img/Campo Minado.svg"
import Joia from "./img/DiaboDoJoia.svg"
import Jooj from "./img/Jooj.svg"
function App() {
  return (
    <div>
      <nav>
        <div>
          <a href="#">Jogos</a>
          <a href="#">Sobre</a>
        </div>
        <div className="logo">
          {<img src ={Logo} alt="Logo do site" title="PogBet"/>}
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
      <div className="parteBaixo">
        
      </div>
      <div className="sobre">
      <img className='jooj' src ={Jooj} alt="imagem do João" title="João Pedro Xavier Martins"/>
        <h1 className='texto'>Aluno: João Pedro Xavier Martins<br></br>
            Perído: 7º <br></br>
            Meios de Contato:@joaop47 </h1>    
            
      </div>
      <div className="sobre1">
      <img className='jooj' src ={Crash} alt="imagem do Crash" title="Crash"/>
            <h1 className='texto'>Aluno: Henrique Navarro Morais<br></br>
            Perído: 7º <br></br>
            Meios de Contato:@Morais </h1>
           
           
      </div>
      <div className="sobre1">
      <img className='jooj' src ={Crash} alt="imagem do Crash" title="Crash"/>
            <h1 className='texto'>Aluno: Breno Henrique Alves Freire Mizael<br></br>
            Perído: 7º <br></br>
            Meios de Contato:@Brenin </h1>
      </div>
       <div className='joia'> <img className='demonioJoia' src ={Joia} alt="imagem do Diabo do Joia" title="Joia"/></div>
    </div>
  );
}

export default App;
