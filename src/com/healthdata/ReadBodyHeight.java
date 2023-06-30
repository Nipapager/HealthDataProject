package com.healthdata;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// Main class to read and process Body Height data
public class ReadBodyHeight {

    // Method to process files and perform the RML mapping for each
    public void processFiles() throws IOException {
        String dataFolder = "schema\\Body Height\\"; // Path to the JSON scemas and tamplate rml file
        String templateRmlFileName = dataFolder + "body_height_temp.ttl"; // Name of the RML template file
        String templateContent = readFile(templateRmlFileName); // Content of the RML template file

        // Get the list of all files in the dataFolder directory
        Files.list(Paths.get(dataFolder))
        // Filter for only JSON schema files
        .filter(path -> path.toString().endsWith(".json"))
        // Process each file
        .forEach(path -> {
            try {
                String jsonFileName = path.getFileName().toString(); // Get file name
                String[] splitName = jsonFileName.split("_"); // Split file name to get the user's name and the number of each JSON file
                String number = splitName[2]; // The number of the JSON file
                String username = splitName[3].split("\\.")[0]; // The user's name
                String NewRmlFileName = dataFolder + "body_height_" + number + "_" + username + ".ttl"; // The new .ttl file with RML rules for each json file.

                // Parse the JSON file
                JSONObject json = new JSONObject(new JSONTokener(new FileReader(path.toString())));
                String heightUnit = json.getJSONObject("body").getJSONObject("body_height").getString("unit"); // Get the unit of height from JSON file
                String unitForBodyHeight;

                // Map units to specific QUDT ontology unit names
                switch(heightUnit) {
                    case "m":
                        unitForBodyHeight = "M";
                        break;
                    case "cm":
                        unitForBodyHeight = "CentiM";
                        break;
                    case "ft":
                        unitForBodyHeight = "FT";
                        break;
                    case "in":
                        unitForBodyHeight = "IN";
                        break;
                    default:
                        unitForBodyHeight = "Unknown";
                }

                // Replace input.json, User, and UnitForBodyHeight that are used on template RML file with actual values for the new RML files
                String newContent = templateContent.replace("input.json", jsonFileName)
                        .replace("User", username)
                        .replace("UnitForBodyHeight", unitForBodyHeight);

                // Write the output to the new file
                writeFile(NewRmlFileName, newContent);
                // Process the new ttl file with RML Mapper
                runRMLMapper(dataFolder, username, number);

            } catch (IOException | InterruptedException e) {
                System.err.println("Error while processing file: " + path);
                e.printStackTrace();
            }
        });
    }

    // Method to run the RML Mapper
    void runRMLMapper(String path, String username, String number) throws IOException, InterruptedException {
        String inputFileName = "body_height_" + number + "_" + username + ".ttl";
        String outputFileName = "body_height_" + number + "_" + username + "_output.ttl";
        String inputPath = path + inputFileName;
        String outputPath = "FinalTTL\\" + outputFileName; // Path to the folder of final .ttl files with RDF triples
        RMLMapper rmlMapper = new RMLMapper();
        rmlMapper.runRMLMapping(inputPath, outputPath); // Call to the RMLMapper's method to run the mapping
    }

    // Method to read a file and return its content as a string
    private String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator()); // Append each line to the content
            }
        }
        return content.toString(); // Return the content as a string
    }

    // Method to write the given content to a file
    private void writeFile(String fileName, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(content); // Write the content to the file
        }
    }
}