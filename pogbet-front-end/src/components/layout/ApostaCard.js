import styles from "./ApostaCard.module.css";
import { BsPencil, BsFillTrashFill } from "react-icons/bs";
import { Link } from "react-router-dom";
let vitoria = "";

function ApostaCard({ id, categoria,  data, ganhou,valor, handleRemove }) {
  const remove = (e) => {
    e.preventDefault();
    handleRemove(id);
  };

  return (
    <>
      <div className={styles.project_card}>
        <h4>{categoria}</h4>
        <p className={styles.category_text}>
          <span className={`${styles[vitoria]}`}></span>
          {valor}
        </p>
        <p className={styles.category_text}>
          <span className={`${styles[vitoria]}`}></span>
          {data}
        </p>
        <p className={styles.category_text}>
          <span className={`${styles[ganhou.toString().toLowerCase()]}`}></span>
          {ganhou && <p>Ganhou</p>}
          {!ganhou && <p>Perdeu</p>}
        </p>

        <div className={styles.project_card_actions}>
          <Link to={`/aposta/${id}`}>
            <BsPencil />
            Editar
          </Link>
          <button onClick={remove}>
            <BsFillTrashFill />
            Remover
          </button>
        </div>
      </div>
    </>
  );
}

export default ApostaCard;
