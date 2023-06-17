import ApostaCard from "../layout/ApostaCard";
import { useEffect, useState } from "react";
import Center from "../layout/Center";

function HistoricoApostas() {
  const [apostas, setApostas] = useState([]);

  useEffect(() => {
    setTimeout(() => {
      fetch("http://localhost:8080/aposta/list", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((resp) => resp.json())
        .then((data) => {
          setApostas(data);
          console.log(data);
          //setRemoveLoading(true);
        })
        .catch((err) => console.log(err));
    }, 1000);
  }, []);

  function removeAposta(id) {
    console.log("Id removido:" + id);
    fetch(`http://localhost:8080/aposta?id=${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(() => {
        setApostas(apostas.filter((aposta) => aposta.id !== id));
        //setProjectMessage("Projeto Removido com Sucesso!");
      })
      .catch((err) => console.log(err));
  }

  return (
    <>
      <Center customClass="column">
        {apostas.length > 0 &&
          apostas.map((aposta) => (
            <ApostaCard
              categoria={aposta.categoria}
              valor={aposta.valor}
              data={aposta.data}
              ganhou={aposta.ganhou}
            />
          ))}
      </Center>
    </>
  );
}

export default HistoricoApostas;
