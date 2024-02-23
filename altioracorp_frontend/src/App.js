import { Container, Nav, Navbar } from "react-bootstrap";
import { Route, Routes } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { Article } from "./components/article";
import Client from "./components/client";
import Order from "./components/order";

function App() {
  return (
    <>
      <Navbar bg="light" expand="lg">
        <Container>
          <Navbar.Brand href="#home">AltioraCorp - Evaluación</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="/">INICIO</Nav.Link>
              <Nav.Link href="/clients">CLIENTES</Nav.Link>
              <Nav.Link href="/articles">ARTÍCULOS</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <Container className="mt-4">
        <Routes>
          <Route path="/" element={<Order />} />
          <Route path="/clients" element={<Client />} />
          <Route path="/articles" element={<Article />} />
        </Routes>
        <ToastContainer />
      </Container>
    </>
  );
}

export default App;
