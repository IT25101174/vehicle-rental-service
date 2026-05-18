package com.vehiclerental.service;

import com.vehiclerental.model.Booking;
import com.vehiclerental.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final String FILE_PATH = "data/bookings.txt";

    // CREATE
    public void addBooking(Booking booking) throws IOException {
        int newId = FileHandler.getNextId(FILE_PATH);
        booking.setId(newId);
        
        String line = booking.getId() + "," +
                booking.getUserId() + "," +
                booking.getVehicleId() + "," +
                booking.getStartDate() + "," +
                booking.getEndDate() + "," +
                booking.getStatus();

        FileHandler.appendLine(FILE_PATH, line);
    }

    // READ ALL
    public List<Booking> getAllBookings() throws IOException {
        List<String> lines = FileHandler.readAll(FILE_PATH);
        List<Booking> list = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");

            Booking b = new Booking(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    parts[3],
                    parts[4],
                    parts[5]
            );

            list.add(b);
        }
        return list;
    }

    // READ BY ID
    public Booking getBookingById(int id) throws IOException {
        List<Booking> all = getAllBookings();
        for (Booking b : all) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    // READ BY USER
    public List<Booking> getBookingsByUser(int userId) throws IOException {
        List<Booking> all = getAllBookings();
        List<Booking> result = new ArrayList<>();

        for (Booking b : all) {
            if (b.getUserId() == userId) {
                result.add(b);
            }
        }
        return result;
    }

    // DELETE (Cancel)
    public void deleteBooking(int id) throws IOException {
        List<String> lines = FileHandler.readAll(FILE_PATH);
        List<String> updated = new ArrayList<>();

        for (String line : lines) {
            if (!line.startsWith(id + ",")) {
                updated.add(line);
            }
        }

        FileHandler.writeAll(FILE_PATH, updated);
    }

    // UPDATE
    public void updateBooking(Booking updatedBooking) throws IOException {
        List<String> lines = FileHandler.readAll(FILE_PATH);
        List<String> updated = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith(updatedBooking.getId() + ",")) {
                String newLine = updatedBooking.getId() + "," +
                        updatedBooking.getUserId() + "," +
                        updatedBooking.getVehicleId() + "," +
                        updatedBooking.getStartDate() + "," +
                        updatedBooking.getEndDate() + "," +
                        updatedBooking.getStatus();

                updated.add(newLine);
            } else {
                updated.add(line);
            }
        }

        FileHandler.writeAll(FILE_PATH, updated);
    }

    // AVAILABILITY CHECK
    public boolean isVehicleAvailable(int vehicleId, String start, String end) throws IOException {
        List<Booking> bookings = getAllBookings();

        for (Booking b : bookings) {
            if (b.getVehicleId() == vehicleId && b.getStatus().equals("active")) {

                if (start.compareTo(b.getEndDate()) <= 0 &&
                        end.compareTo(b.getStartDate()) >= 0) {
                    return false;
                }
            }
        }
        return true;
    }
}