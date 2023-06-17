import styles from "./SubmitButton.module.css";

SubmitEvent = ({ text }) => {
  return (
    <div className={styles.center}>
      <button className={styles.btn}>{text}</button>
    </div>
  );
};

export default SubmitEvent;
