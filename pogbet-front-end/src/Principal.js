import React from 'react';
import './Principal.css'
import PogBet from "./img/Logo.svg"

class Navbar extends React.Component {
  render() {
    return (
      <nav>
        <diV className="navbar-container">
           <div className="logo-container">
            <img src ={PogBet} alt="Logo do site" title="PogBet"/></div>
        < ul className="menu">
          <li>
          <span className="texto">Jogos</span>
          </li>
          <li>
          <span className="texto">Criadores</span>
          </li>
          <li>
          <span className="texto">Login</span>
          </li>
        </ul></diV>
        
      </nav>
    );
  }
}

export default Navbar;
