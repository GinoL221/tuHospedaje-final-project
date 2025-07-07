export async function register(userData, token) {
  const headers = { "Content-Type": "application/json" };
  if (token && userData.role === "ADMIN") {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const response = await fetch("http://localhost:8081/auth/register", {
    method: "POST",
    headers,
    body: JSON.stringify(userData),
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || "Error al registrar");
  }
  return response.json();
}
