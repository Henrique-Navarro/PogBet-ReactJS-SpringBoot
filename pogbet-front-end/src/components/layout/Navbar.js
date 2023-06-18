import { Link } from "react-router-dom";
import Logo from "../../img/Logo.svg";

function Navbar() {
  return (
    <nav>
      <div>
        <Link className="aposta" to="/aposta/list">Apostas</Link>
        <Link className="sobrenav" to="/sobre">Sobre</Link>
        <Link className="contato" to="/contato">Contato</Link>
      </div>

      <Link to="/">
        <div className="logo">
          {<img src={Logo} alt="Logo do site" title="PogBet" />}
        </div>
      </Link>

      <div>
        <Link className="login" to="/login">Login</Link>
        <Link className = "cadastro" to="/cadastro">Cadastro</Link>
      </div>
    </nav>
  );
}

export default Navbar;
