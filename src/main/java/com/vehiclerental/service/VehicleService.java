package com.vehiclerental.service;

import com.vehiclerental.FileHandler; //file read/write
import com.vehiclerental.model.Vehicle; //vehicle objects
import java.io.IOException; // catch the file errors
import java.util.ArrayList; // create resizable list
import java.util.List; // declare the list type

    public class VehicleService {

        // path to the text file where vehicle records are stored
        private final String filePath = "src/main/webapp/WEB-INF/data/vehicles.txt";

        // Add Vehicle (CREATE)
        public void addVehicle(Vehicle vehicle) {
            try {

                int newId = FileHandler.getNextId(filePath);
                vehicle.setId(newId);

                // convert vehicle object into a text line format
                String line = vehicle.getId() + "," +
                        vehicle.getBrand() + "," +
                        vehicle.getType() + "," +
                        vehicle.getPricePerDay() + "," +
                        vehicle.isAvailable();

                FileHandler.appendLine(filePath, line);

                // display the error details when file handling
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Get All Vehicles (READ)
        public List<Vehicle> getAllVehicles() {
            List<Vehicle> vehicles = new ArrayList<>();

            try {
                // read all lines from the file
                List<String> lines = FileHandler.readAll(filePath);

                for (String line : lines) {
                    String[] parts = line.split(",");

                    Vehicle v = new Vehicle(
                            Integer.parseInt(parts[0]),  // id
                            parts[1],                   // brand
                            parts[2],                   // type
                            Double.parseDouble(parts[3]), // price per day
                            Boolean.parseBoolean(parts[4]) // available
                    );

                    vehicles.add(v);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return vehicles;
        }

        // Get Vehicle by ID
        public Vehicle getVehicleById(int id) {
            List<Vehicle> vehicles = getAllVehicles();

            for (Vehicle v : vehicles) {
                if (v.getId() == id) {
                    return v;
                }
            }
            return null;
        }

        // Update Vehicle (UPDATE)
        public void updateVehicle(Vehicle updatedVehicle) {
            try {
                List<String> lines = FileHandler.readAll(filePath);
                List<String> newLines = new ArrayList<>();

                for (String line : lines) {
                    String[] parts = line.split(",");
                    int vehicleId = Integer.parseInt(parts[0]);

                    if (vehicleId == updatedVehicle.getId()) {
                        //replace line with new data
                        String newLine = updatedVehicle.getId() + "," +
                                updatedVehicle.getBrand() + "," +
                                updatedVehicle.getType() + "," +
                                updatedVehicle.getPricePerDay() + "," +
                                updatedVehicle.isAvailable();

                        newLines.add(newLine);
                    } else {
                        newLines.add(line);// keep all other lines unchanged
                    }
                }

                FileHandler.writeAll(filePath, newLines); // write everything back

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Delete Vehicle (DELETE)
        public void deleteVehicle(int id) {
            try {
                List<String> lines = FileHandler.readAll(filePath);
                List<String> newLines = new ArrayList<>();

                for (String line : lines) {
                    String[] parts = line.split(",");
                    int vehicleId = Integer.parseInt(parts[0]);

                    if (vehicleId != id) {
                        newLines.add(line); // only keep lines that don't match
                    }
                }

                FileHandler.writeAll(filePath, newLines);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Search Vehicles by Brand or Type
        public List<Vehicle> searchVehicles(String keyword) {
            List<Vehicle> results = new ArrayList<>();
            List<Vehicle> vehicles = getAllVehicles();

            for (Vehicle v : vehicles) {
                if (v.getBrand().toLowerCase().contains(keyword.toLowerCase())
                        || v.getType().toLowerCase().contains(keyword.toLowerCase())) {
                    results.add(v);
                }
            }

            return results;
        }
    }
