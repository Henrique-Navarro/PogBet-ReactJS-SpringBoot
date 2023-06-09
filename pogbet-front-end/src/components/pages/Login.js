import React, { useState } from "react";
import "./Login.css";
import PogBet from "../../img/Logo.svg";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("Nome de Usuario: ", username);
    console.log("Senha: ", password);
  };

  return (
    <div className="login-container">
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label  htmlFor="Nome de usuario">Nome de Usuário</label>
          <input className="nomeuse"
            type="text"
            id="Nome de usuario"
            value={username}
            onChange={handleUsernameChange}
            required
          />
          <label htmlFor="password">Senha</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={handlePasswordChange}
            required
          />
        </div>
        <button className ="buttonlogin" type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
