import apiClient from "../api/apiClient";

export async function fetchLodgings() {
  const response = await apiClient.get("/alojamientos");
  return response.data;
}

export async function createLodging(data) {
  const response = await apiClient.post("/alojamientos", data);
  return response.data;
}

export async function fetchLodgingById(id) {
  const response = await apiClient.get(`/alojamientos/${id}`);
  return response.data;
}

export async function updateLodging(id, data) {
  const response = await apiClient.put(`/alojamientos/${id}`, data);
  return response.data;
}

export async function deleteLodging(id) {
  const response = await apiClient.delete(`/alojamientos/${id}`);
  return response.data;
}
