import { useState } from "react";
import logo from "../assets/images/TuHospedaje_Isologotipo.png";

export default function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <header>
      <nav className="navbar page-container">
        <div className="logo-container">
          <a href="/" className="logo-link">
            <img src={logo} alt="TuHospedaje Logo" className="logo" />
          </a>
          <p className="tagline">Encuentra tu lugar ideal al mejor precio</p>
        </div>

        <button
          className="hamburger"
          onClick={() => setIsMenuOpen(!isMenuOpen)}
          aria-label="Abrir menú"
        >
          ☰
        </button>

        <div className={`nav-wrapper ${isMenuOpen ? "open" : ""}`}>
          <ul className="nav-links main-links">
            <li>
              <a href="#inicio">Inicio</a>
            </li>
            <li>
              <a href="#habitaciones">Habitaciones</a>
            </li>
            <li>
              <a href="#servicios">Servicios</a>
            </li>
            <li>
              <a href="#contacto">Contacto</a>
            </li>
            <li>
              <button className="btn-primary">Reservar</button>
            </li>
          </ul>
          <ul className="nav-links user-links">
            <li>
              <a href="#iniciar-sesion">Iniciar sesión</a>
            </li>
            <li>
              <a href="#registrarse" className="btn-secondary">
                Crea tu cuenta
              </a>
            </li>
          </ul>
        </div>
      </nav>
    </header>
  );
}
