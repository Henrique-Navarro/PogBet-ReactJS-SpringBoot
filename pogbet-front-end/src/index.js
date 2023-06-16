import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Container from "./components/layout/Container";
import ListaApostas from "./components/pages/HistoricoApostas";
import Sobre from "./components/pages/Sobre";
import Login from "./components/pages/Login";
import Cadastro from "./components/pages/Cadastro";
import Crash from "./components/games/Crash";
import Dice from "./components/games/Dice";
import CampoMinado from "./components/games/CampoMinado";
import Contato from "./components/pages/Contato";

//FAZ O MAPEAMENTO DAS SEGUINTE ROTAS
const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        element: <Container />,
      },
      {
        path: "aposta/list",
        element: <ListaApostas />,
      },
      {
        path: "sobre",
        element: <Sobre />,
      },
      {
        path: "contato",
        element: <Contato />,
      },
      {
        path: "login",
        element: <Login />,
      },
      {
        path: "cadastro",
        element: <Cadastro />,
      },
      {
        path: "crash",
        element: <Crash />,
      },
      {
        path: "dice",
        element: <Dice />,
      },
      {
        path: "campominado",
        element: <CampoMinado />,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
