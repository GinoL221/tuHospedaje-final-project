import { useState } from "react";
import logo from "../assets/images/TuHospedaje_Isologotipo.png";
import { Link } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export default function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const { token, user, logout } = useAuth();

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
            {token ? (
              <>
                <li className="user-info">
                  <Link to="/profile">
                    <img
                      src={user.image}
                      alt="Avatar"
                      style={{
                        width: 32,
                        height: 32,
                        borderRadius: "50%",
                        objectFit: "cover",
                        marginRight: "8px",
                      }}
                    />
                    {user.name ? `¡Bienvenido ${user.name}!` : "Perfil"}
                  </Link>
                </li>
                <li>
                  <button onClick={logout} className="btn-secondary">
                    Cerrar sesión
                  </button>
                </li>
              </>
            ) : (
              <>
                <li>
                  <Link to="/login">Iniciar sesión</Link>
                </li>
                <li>
                  <Link to="/register" className="btn-secondary">
                    Registrate
                  </Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </nav>
    </header>
  );
}
