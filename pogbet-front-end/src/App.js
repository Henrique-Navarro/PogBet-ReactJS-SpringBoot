import React from "react";
import "./Principal.css";
import { Outlet } from "react-router-dom";
import Container from "./components/layout/Container";
import Navbar from "./components/layout/Navbar";

function App() {
  return (
    <div>
      <Navbar />
      <Container>
        <Outlet />
      </Container>
    </div>
  );
}

export default App;
