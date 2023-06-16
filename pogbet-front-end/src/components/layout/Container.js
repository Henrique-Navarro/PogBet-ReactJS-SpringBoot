import Crash from "../../img/Crash.svg";
import Dice from "../../img/Dice.svg";
import Campo from "../../img/Campo Minado.svg";
import { Link } from "react-router-dom";

function Container() {
  return (
    <>
      <div className="image-section">
        <Link to="crash">
          <img src={Crash} alt="imagem do Crash" title="Crash" />
        </Link>
        <Link to="dice">
          <img src={Dice} alt="imagem do Dice" title="Dice" />
        </Link>
        <Link to="campominado">
          <img src={Campo} alt="imagem do Campo Minado" title="Campo Minado" />
        </Link>
      </div>
      <div>
        <h1>Faça Login</h1>
        <Link to="login">Faça Login</Link>
      </div>
    </>
  );
}

export default Container;
