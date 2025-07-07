import { useState } from "react";
import { useAuth } from "../hooks/useAuth";
import { register as registerAPI } from "../api/auth";

export default function RegisterForm() {
  const { user, token } = useAuth();

  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    role: "USER",
  });

  const [imageFile, setImageFile] = useState(null);
  const [error, setError] = useState("");

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleRoleChange = (e) => {
    setForm({ ...form, role: e.target.checked ? "ADMIN" : "USER" });
  };

  const handleImageChange = (e) => {
    setImageFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const formData = new FormData();
      formData.append("firstName", form.firstName);
      formData.append("lastName", form.lastName);
      formData.append("email", form.email);
      formData.append("password", form.password);
      formData.append("role", form.role);
      if (imageFile) formData.append("image", imageFile);

      await registerAPI(formData, token);

      alert("Registro exitoso. Ahora podés iniciar sesión.");
    } catch (error) {
      setError(error.message || "Error al registrar");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        name="firstName"
        placeholder="Nombre"
        onChange={handleChange}
        required
      />
      <input
        name="lastName"
        placeholder="Apellido"
        onChange={handleChange}
        required
      />
      <input
        name="email"
        type="email"
        placeholder="Email"
        onChange={handleChange}
        required
      />
      <input
        name="password"
        type="password"
        placeholder="Contraseña"
        onChange={handleChange}
        required
      />

      <label>
        Foto de perfil:
        <input type="file" accept="image/*" onChange={handleImageChange} />
      </label>

      {user?.role === "ADMIN" && (
        <label>
          <input type="checkbox" name="isAdmin" onChange={handleRoleChange} />
          Registrar como administrador
        </label>
      )}

      <button type="submit">Registrarse</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </form>
  );
}
