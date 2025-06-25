import { useState } from "react";
import "../assets/css/home.css";
import "../assets/css/hotelCard.css";
import "../assets/css/searchCard.css";
import "../assets/css/accomodationCard.css";

import HotelCard from "../components/HotelCard";
import hotel1 from "../assets/images/hotel1.jpg";
import hotel2 from "../assets/images/hotel2.jpg";
import hotel3 from "../assets/images/hotel3.jpg";
import hotel4 from "../assets/images/hotel4.jpg";
import hotel5 from "../assets/images/hotel5.jpg";
import hotel6 from "../assets/images/hotel6.jpg";

import hotelImage from "../assets/images/tipo-hotel.jpg";
import hostelImage from "../assets/images/tipo-hostel.jpg";
import cabinImage from "../assets/images/tipo-cabana.jpg";
import apartmentImage from "../assets/images/tipo-departamento.jpg";

import arrowRight from "../assets/images/arrow_right.png";
import arrowLeft from "../assets/images/arrow_left.png";

export default function Home() {
  const hotels = [
    {
      image: hotel1,
      name: "Hotel Costa del Sol",
      location: "Mar del Plata, Argentina",
      price: 15200,
      rating: 4.5,
    },
    {
      image: hotel2,
      name: "Andes Lodge",
      location: "Mendoza, Argentina",
      price: 17800,
      rating: 4.7,
    },
    {
      image: hotel3,
      name: "EcoHotel El Bosque",
      location: "Villa La Angostura, Neuquén",
      price: 21200,
      rating: 4.8,
    },
    {
      image: hotel4,
      name: "Hotel Playa Serena",
      location: "Las Grutas, Río Negro",
      price: 13400,
      rating: 4.2,
    },
    {
      image: hotel5,
      name: "Patagonia Suites",
      location: "El Calafate, Santa Cruz",
      price: 25900,
      rating: 4.9,
    },
    {
      image: hotel6,
      name: "Hotel Mirador Urbano",
      location: "Córdoba Capital",
      price: 11800,
      rating: 4.3,
    },
  ];

  const maxVisible = 4;
  const [startIndex, setStartIndex] = useState(0);

  const next = () => {
    setStartIndex((prevIndex) => (prevIndex + 1) % hotels.length);
  };

  const prev = () => {
    setStartIndex(
      (prevIndex) => (prevIndex - 1 + hotels.length) % hotels.length
    );
  };

  const visibleHotels = [];

  for (let i = 0; i < maxVisible; i++) {
    const index = (startIndex + i) % hotels.length;
    visibleHotels.push(hotels[index]);
  }

  return (
    <main className="home page-container">
      <section className="search">
        <div className="search-card">
          <h2>Buscar hospedaje</h2>
          <form>
            <input type="text" placeholder="Destino" />
            <input type="date" />
            <input type="date" />
            <select>
              <option value="">Huéspedes</option>
              <option value="1">1 huésped</option>
              <option value="2">2 huéspedes</option>
              <option value="3">3 huéspedes</option>
              <option value="4+">4 o más</option>
            </select>
            <button type="submit" className="btn-search">
              Buscar
            </button>
          </form>
        </div>
      </section>

      <section className="accommodation-types">
        <h2>Tipos de alojamientos</h2>
        <div className="accommodation-card-list">
          <div
            className="accommodation-card"
            style={{ backgroundImage: `url(${hotelImage})` }}
          >
            <span>Hotel</span>
          </div>
          <div
            className="accommodation-card"
            style={{ backgroundImage: `url(${hostelImage})` }}
          >
            <span>Hostel</span>
          </div>
          <div
            className="accommodation-card"
            style={{ backgroundImage: `url(${cabinImage})` }}
          >
            <span>Cabaña</span>
          </div>
          <div
            className="accommodation-card"
            style={{ backgroundImage: `url(${apartmentImage})` }}
          >
            <span>Departamento</span>
          </div>
        </div>
      </section>

      <section className="promotions">
        <h2>Recomendaciones</h2>
        <div className="carousel-container">
          <button
            className="scroll-btn left"
            onClick={prev}
            aria-label="Anterior"
          >
            <img src={arrowLeft} alt="Flecha izquierda" />
          </button>
          <div className="hotel-list">
            {visibleHotels.map((hotel, index) => (
              <HotelCard
                key={startIndex + index}
                image={hotel.image}
                name={hotel.name}
                location={hotel.location}
                price={hotel.price}
                rating={hotel.rating}
              />
            ))}
          </div>
          <button
            className="scroll-btn right"
            onClick={next}
            aria-label="Siguiente"
          >
            <img src={arrowRight} alt="Flecha derecha" />
          </button>
        </div>
      </section>
    </main>
  );
}
