import { Link } from "react-router-dom";
import Logo from "../../img/Logo.svg";

function Navbar() {
  return (
    <nav>
      <div>
        <Link to="/aposta/list">Apostas</Link>
        <Link to="/sobre">Sobre</Link>
        <Link to="/contato">Contato</Link>
      </div>

      <Link to="/">
        <div className="logo">
          {<img src={Logo} alt="Logo do site" title="PogBet" />}
        </div>
      </Link>

      <div>
        <Link to="/login">Login</Link>
        <Link to="/cadastro">Cadastro</Link>
      </div>
    </nav>
  );
}

export default Navbar;
