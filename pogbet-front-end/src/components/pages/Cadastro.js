import React, { useState } from "react";
import "./Cadastro.css";
import Input from "../form/Input";
import Center from "../layout/Center";
import { useNavigate } from "react-router-dom";

function Cadastro() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [dataNascimento, setDataNascimento] = useState("");
  const [cpf, setCpf] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const usuario = {
      name,
      email,
      senha,
      dataNascimento,
      saldo: 0,
      cpf,
    };
    console.log(usuario);

    fetch("http://localhost:8080/novoUsuario", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(usuario),
    })
      .then((resp) => resp.json())
      .catch((err) => {
        console.log(err);
      });
    navigate("/", {
      state: { message: "Usuário criado com sucesso!" },
    });
  };
  return (
    <Center customClass="column">
      <form onSubmit={handleSubmit}>
        <label>
          Nome:
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </label>

        <label>
          Email:
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </label>

        <label>
          Senha:
          <input
            type="password"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required
          />
        </label>

        <label>
          Data de Nascimento:
          <input
            type="date"
            value={dataNascimento}
            onChange={(e) => setDataNascimento(e.target.value)}
            required
          />
        </label>

        <label>
          CPF:
          <input
            type="text"
            value={cpf}
            onChange={(e) => setCpf(e.target.value)}
            required
          />
        </label>
        <br></br>
        <button type="submit">Cadastrar</button>
      </form>
    </Center>
  );
}

export default Cadastro;
