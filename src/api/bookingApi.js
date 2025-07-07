import apiClient from "./apiClient";

export const getBookings = () => apiClient.get("/bookings");

export const getBookingById = (id) => apiClient.get(`/bookings/${id}`);

export const createBooking = (bookingData) =>
  apiClient.post("/bookings", bookingData);

export const updateBooking = (id, bookingData) =>
  apiClient.put(`/bookings/${id}`, bookingData);

export const deleteBooking = (id) => apiClient.delete(`/bookings/${id}`);
