import React, { createContext, useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import apiClient from "../api/apiClient";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [token, setToken] = useState(null);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const savedToken = localStorage.getItem("token");
    if (savedToken) {
      setToken(savedToken);
      const decoded = jwtDecode(savedToken);
      setUser({
        name: decoded.firstName,
        lastName: decoded.lastName,
        role: decoded.role,
        email: decoded.sub,
        image: decoded.image,
      });
    }
    setLoading(false);
  }, []);

  const login = async ({ email, password }) => {
    const response = await apiClient.post("/auth/login", { email, password });
    const data = response.data;
    const decoded = jwtDecode(data.token);

    setToken(data.token);
    setUser({
      name: decoded.firstName,
      lasName: decoded.lasName,
      role: decoded.role,
      email: decoded.sub,
      image: decoded.image,
    });

    localStorage.setItem("token", data.token);
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem("token");
  };

  return (
    <AuthContext.Provider value={{ token, user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

export { AuthContext };
