import apiClient from "../api/apiClient";

export async function fetchUsers() {
  const response = await apiClient.get("/usuarios");
  return response.data;
}

export async function fetchUserById(userId) {
  const response = await apiClient.get(`/usuarios/${userId}`);
  return response.data;
}

export async function updateUser(userId, userData) {
  const response = await apiClient.put(`/usuarios/${userId}`, userData);
  return response.data;
}

export async function deleteUser(userId) {
  const response = await apiClient.delete(`/usuarios/${userId}`);
  return response.data;
}
