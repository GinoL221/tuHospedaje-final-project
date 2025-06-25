export default function HotelCard({ image, name, location, price, rating }) {
  return (
    <div className="hotel-card">
      <img src={image} alt={name} className="hotel-image" />
      <div className="hotel-info">
        <h3>{name}</h3>
        <p className="location">{location}</p>
        <p className="price">Desde ${price} / noche</p>
        <p className="rating">⭐ {rating}/5</p>
        <button className="btn-primary">Ver más</button>
      </div>
    </div>
  );
}
