import Joia from "../../img/DiaboDoJoia.svg";
import Jooj from "../../img/Jooj.svg";
import Crash from "../../img/Crash.svg";

function Contato() {
  return (
    <>
      <div className="parteBaixo">
        <div className="sobre">
          <img
            className="jooj"
            src={Jooj}
            alt="imagem do João"
            title="João Pedro Xavier Martins"
          />
          <h1 className="texto">
            Aluno: João Pedro Xavier Martins<br></br>
            Perído: 7º <br></br>
            Meios de Contato:@joaop47{" "}
          </h1>
        </div>
        <div className="sobre1">
          <img
            className="jooj"
            src={Crash}
            alt="imagem do Crash"
            title="Crash"
          />
          <h1 className="texto">
            Aluno: Henrique Navarro Morais<br></br>
            Perído: 7º <br></br>
            Meios de Contato:@Morais{" "}
          </h1>
        </div>
        <div className="sobre1">
          <img
            className="jooj"
            src={Crash}
            alt="imagem do Crash"
            title="Crash"
          />
          <h1 className="texto">
            Aluno: Breno Henrique Alves Freire Mizael<br></br>
            Perído: 7º <br></br>
            Meios de Contato:@Brenin{" "}
          </h1>
        </div>
        <div className="joia">
          {" "}
          <img
            className="demonioJoia"
            src={Joia}
            alt="imagem do Diabo do Joia"
            title="Joia"
          />
        </div>
      </div>
    </>
  );
}

export default Contato;
