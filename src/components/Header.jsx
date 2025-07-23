import { useState, useEffect } from "react";
import logo from "../assets/images/TuHospedaje_Isologotipo.png";
import { Link } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export default function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const { token, user, logout } = useAuth();

  const handleClickOutside = (e) => {
    if (!e.target.closest(".user-dropdown")) {
      setIsMenuOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener("click", handleClickOutside);
    return () => document.removeEventListener("click", handleClickOutside);
  }, []);

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
              <li className="user-info user-dropdown">
                <button
                  className="user-button"
                  onClick={() => setIsMenuOpen(!isMenuOpen)}
                >
                  {user.name ? `¡Bienvenido ${user.name}!` : "Perfil"}
                  <img src={user.image} alt="Avatar" className="user-avatar" />
                </button>

                {isMenuOpen && (
                  <div className="dropdown-menu">
                    <li>
                      <Link to="/profile" onClick={() => setIsMenuOpen(false)}>
                        Perfil
                      </Link>
                    </li>
                    <li>
                      <button
                        onClick={() => {
                          logout();
                          setIsMenuOpen(false);
                        }}
                        className="btn-secondary"
                      >
                        Cerrar sesión
                      </button>
                    </li>
                  </div>
                )}
              </li>
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
