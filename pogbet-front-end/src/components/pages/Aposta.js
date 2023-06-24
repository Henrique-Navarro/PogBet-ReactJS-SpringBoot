import styles from "./Aposta.module.css";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import Center from "../layout/Center";
import { useNavigate } from "react-router-dom";

const Aposta = () => {
  const { id } = useParams();
  const [aposta, setAposta] = useState([]);
  const [showApostaForm, setShowApostaForm] = useState(false);
  const [categoria, setCategoria] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    fetch("http://localhost:8080/aposta", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(aposta),
    })
      .then((resp) => resp.json())
      .catch((err) => {
        console.log(err);
      });
    navigate("/aposta/list", {
      state: { message: "Aposta atualizada com sucesso!" },
    });
  };

  useEffect(() => {
    fetch(`http://localhost:8080/aposta/${id}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((resp) => resp.json())
      .then((data) => {
        setAposta(data);
        //setRemoveLoading(true);
      })
      .catch((err) => console.log(err));
  }, [id]);

  function toggleApostaForm() {
    setShowApostaForm(!showApostaForm);
  }

  return (
    <div className={styles.project_details}>
      <Center customClass="column">
        <div className={styles.details_container}>
          <h1>Categoria: {aposta.categoria}</h1>
          <button className={styles.btn} onClick={toggleApostaForm}>
            {!showApostaForm ? "Editar Aposta" : "Fechar"}
          </button>
          {!showApostaForm ? (
            <div className={styles.project_info}>
              <p>
                <span>Categoria: {aposta.categoria}</span>
              </p>
              <p>
                <span>Data: {aposta.data}</span>
              </p>
              <p>
                <span>Total Valor: R${aposta.valor},00</span>
              </p>
              <p>
                <span>ID Aposta: {aposta.id}</span>
              </p>
            </div>
          ) : (
            <div className={styles.project_info}>
              <form onSubmit={handleSubmit}>
                <label>
                  Nova Categoria:
                  <input
                    type="text"
                    value={aposta.categoria}
                    onChange={(e) =>
                      setAposta({ ...aposta, categoria: e.target.value })
                    }
                    required
                  />
                </label>
                <button type="submit">Concluir Edição</button>
              </form>
            </div>
          )}
        </div>
      </Center>
    </div>
  );
};

export default Aposta;
