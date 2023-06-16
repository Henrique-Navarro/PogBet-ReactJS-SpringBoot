import React from "react";
import "./Principal.css";
import { Outlet } from "react-router-dom";
import Container from "./components/layout/Container";
import Navbar from "./components/layout/Navbar";

//REMOVE O NAVBAR DAS SEGUINTE PÁGINAS
const hideNavbar = ["/dice", "/crash", "/campominado"].includes(
  window.location.pathname
);

function App() {
  return (
    <>
      {!hideNavbar && <Navbar />}
      <Outlet />
    </>
  );
}

export default App;
