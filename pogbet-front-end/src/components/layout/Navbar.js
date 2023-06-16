import Logo from "../../img/Logo.svg";

function Navbar() {
  return (
    <>
      <nav>
        <div>
          <a href="#">Jogos</a>
          <a href="#">Sobre</a>
        </div>
        <div className="logo">
          {<img src={Logo} alt="Logo do site" title="PogBet" />}
        </div>
        <div className="button">
          <button>Login</button>
          <button>Cadastro</button>
        </div>
      </nav>
    </>
  );
}

export default Navbar;
