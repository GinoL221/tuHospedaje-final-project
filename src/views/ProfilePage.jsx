import React from "react";
import { useAuth } from "../hooks/useAuth";
import "../assets/css/profile.css";

export default function ProfilePage() {
  const { user, token, logout } = useAuth();

  if (!token) {
    return <p>No estás autenticado. Por favor, inicia sesión.</p>;
  }

  return (
    <div className="profile-container">
      <h1>Perfil de Usuario</h1>
      <img src={user.image} alt="Avatar" className="profile-avatar" />
      <div className="profile-info">
        <p>
          <strong>Nombre:</strong> {user.name}
        </p>
        <p>
          <strong>Apellido:</strong> {user.lastName}
        </p>
        <p>
          <strong>Email:</strong> {user.email}
        </p>
        {user?.role === "ADMIN" && (
          <p>
            <strong>Rol: </strong>Administrador
          </p>
        )}
      </div>
      <div className="profile-actions">
        <button onClick={logout} className="logout-btn">
          Cerrar sesión
        </button>
      </div>
    </div>
  );
}
