import React, { useState } from 'react';
import PogBet from "./img/Logo.svg"
import './Cadastro.css';

function Cadastro() {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [dataNascimento, setDataNascimento] = useState('');
  
    const handleSubmit = (event) => {
      event.preventDefault();
      console.log('Nome:', nome);
      console.log('Email:', email);
      console.log('Senha:', senha);
      console.log('Data de Nascimento:', dataNascimento);
    };
  
    return (
      <div className="cadastro-container">
        <img src ={PogBet} alt="Logo do site" title="PogBet"/>
        <h1>Tela de Cadastro</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="label" htmlFor="nome">Nome:</label>
            <input
              type="text"
              id="nome"
              value={nome}
              onChange={(event) => setNome(event.target.value)}
            />
          </div>
          <div className="form-group">
            <label className="label" htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
            />
          </div>
          <div className="form-group">
            <label className="label" htmlFor="dataNascimento">Data de Nascimento:</label>
            <input
              type="date"
              id="dataNascimento"
              value={dataNascimento}
              onChange={(event) => setDataNascimento(event.target.value)}
            />
          </div >
          <div className="form-group">
            <label className="label" htmlFor="senha">Escolha uma Senha:</label>
            <input
              type="password"
              id="senha"
              value={senha}
              onChange={(event) => setSenha(event.target.value)}
            />
          </div>
          
          <button type="submit">Cadastrar</button>
        </form>
      </div>
    );
  }
  
  export default Cadastro;