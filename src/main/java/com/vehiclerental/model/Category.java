package com.vehiclerental.model;

// Category class
public class Category {

        private int id;
        private String name;
        private String description;

        // Constructor
        public Category(int id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        // Getters
        public int getId(){
            return id;
        }
        public String getName(){
            return name;
        }
        public String getDescription(){
            return description;
        }

        // Setters (used when updating data)
        public void setName(String name){
            this.name = name;
        }
        public void setDescription(String description){
            this.description = description;
        }

        // Convert object → text file format
        public String toFileString() {
            return id + "," + name + "," + description;
        }

        // Convert text line → object
        public static Category fromString(String line) {
            String[] parts = line.split(",");
            return new Category(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2]
            );
        }
}


