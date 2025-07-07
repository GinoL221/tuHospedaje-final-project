import apiClient from "./apiClient";

export const login = (credentials) => {
  return apiClient.post("/auth/login", credentials);
};

export const register = (data) => {
  return apiClient.post("/auth/register", data);
};
