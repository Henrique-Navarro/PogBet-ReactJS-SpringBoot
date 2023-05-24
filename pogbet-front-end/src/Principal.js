import React from 'react';
import './Principal.css'

class Navbar extends React.Component {
  render() {
    return (
      <nav>
        < ul className="lista">
          <li>
          <span className="texto">Jogos</span>
          </li>
          <li>
          <span className="texto">Criadores</span>
          </li>
          <li>
          <span className="texto">Login</span>
          </li>
        </ul>
      </nav>
    );
  }
}

export default Navbar;
