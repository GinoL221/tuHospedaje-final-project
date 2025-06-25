import logo from "../assets/images/TuHospedaje_Logotipo.png";
import twitterLogo from "../assets/images/logo-twitterx.png";
import facebookLogo from "../assets/images/logo-facebook.png";
import instagramLogo from "../assets/images/logo-instagram.png";

export default function Footer() {
  return (
    <footer className="footer-bg">
      <div className="page-container">
        <section className="footer-left">
          <img src={logo} alt="TuHospedaje Logo" className="footer-logo" />
          <p>© 2025 TuHospedaje. Todos los derechos reservados.</p>
        </section>
        <section className="footer-right">
          <a href="https://facebook.com" target="_blank" rel="noreferrer">
            <img src={facebookLogo} alt="Facebook" />
          </a>
          <a href="https://instagram.com" target="_blank" rel="noreferrer">
            <img src={instagramLogo} alt="Instagram" />
          </a>
          <a href="https://twitter.com" target="_blank" rel="noreferrer">
            <img src={twitterLogo} alt="Twitter" />
          </a>
        </section>
      </div>
    </footer>
  );
}
