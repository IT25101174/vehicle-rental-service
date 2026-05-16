package com.vehiclerental;

import java.io.*;
import java.util.*;

public class FileHandler {
    // Read all lines from a file
    public static List<String> readAll(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return lines; // return empty if file doesnt exist yet
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) lines.add(line);
        }
        reader.close();
        return lines;
    }

    // Write all lines to a file (overwrites existing content)
    public static void writeAll(String filePath, List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    // Append one new line to end of file (used for CREATE)
    public static void appendLine(String filePath, String line) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(line);
        writer.newLine();
        writer.close();
    }

    // Get next available ID
    public static int getNextId(String filePath) throws IOException {
        List<String> lines = readAll(filePath);
        if (lines.isEmpty()) return 1;
        String lastLine = lines.get(lines.size() - 1);
        String[] parts = lastLine.split(",");
        return Integer.parseInt(parts[0]) + 1;
    }
}