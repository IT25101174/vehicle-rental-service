/*package com.vehiclerental.servlet;

import com.vehiclerental.model.Category;
import com.vehiclerental.util.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Service layer → handles business logic
public class CategoryService {

    // File path where data is stored
    private final String path = "data/categories.txt";

    // CREATE → add new category
    public void addCategory(String name, String desc) throws IOException {
        int id = FileHandler.getNextId(path); // generate ID

        Category c = new Category(id, name, desc);

        // save to file
        FileHandler.appendLine(path, c.toFileString());
    }
    // READ → get all categories
    public List<Category> getAll() throws IOException {

        List<String> lines = FileHandler.readAll(path);
        List<Category> list = new ArrayList<>();

        // convert each line → Category object
        for (String line : lines) {
            list.add(Category.fromString(line));
        }

        return list;
    }

    // UPDATE → update category
    public void update(int id, String name, String desc) throws IOException {

        List<String> lines = FileHandler.readAll(path);
        List<String> updated = new ArrayList<>();

        for (String line : lines) {

            Category c = Category.fromString(line);

            if (c.getId() == id) {
                c.setName(name);
                c.setDescription(desc);
                updated.add(c.toFileString());
            } else {
                updated.add(line);
            }
        }

        FileHandler.writeAll(path, updated);
    }

    // DELETE → delete category
    public void delete(int id) throws IOException {

        List<String> lines = FileHandler.readAll(path);
        List<String> updated = new ArrayList<>();

        for (String line : lines) {

            Category c = Category.fromString(line);

            if (c.getId() != id) {
                updated.add(line);
            }
        }

        FileHandler.writeAll(path, updated);
    }
}
*/
