export const login = async (credentials) => {
  const response = await fetch("http://localhost:8081/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credentials),
  });

  if (!response.ok) {
    throw new Error("Login fallido");
  }

  const data = await response.json();
  return { data };
};
