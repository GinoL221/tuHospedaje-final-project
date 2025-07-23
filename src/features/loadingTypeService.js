import apiClient from "../api/apiClient";

export async function fetchLodgingTypes() {
  const response = await apiClient.get("/tipos-de-alojamientos");
  return response.data;
}
