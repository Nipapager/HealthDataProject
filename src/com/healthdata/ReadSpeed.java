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

// Main class to read and process Speed data
public class ReadSpeed {

    // Method to process files and perform the RML mapping for each
    public void processFiles() throws IOException {
        String dataFolder = "schema\\Speed\\"; // Path to the JSON scemas and tamplate rml file
        String templateRmlFileName = dataFolder + "speed_temp.ttl"; // Name of the RML template file
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
                        String number = splitName[1]; // The number of the JSON file
                        String username = splitName[2].split("\\.")[0]; // The user's name
                        String NewRmlFileName = dataFolder + "speed_" + number + "_" + username + ".ttl"; // The new .ttl file with RML rules for each json file.

                        // Parse the JSON file
                        JSONObject json = new JSONObject(new JSONTokener(new FileReader(path.toString())));
                        String speedUnit = json.getJSONObject("body").getJSONObject("speed").getString("unit");  // Get the unit of Speed from JSON file
                        String unitForSpeed;

                        // Map units to specific QUDT ontology unit names
                        switch(speedUnit) {
                            case "m/s":
                                unitForSpeed = "M-PER-SEC";
                                break;
                            case "ft/s":
                                unitForSpeed = "FT-PER-SEC";
                                break;
                            case "km/hr":
                                unitForSpeed = "KiloM-PER-HR";
                                break;
                            default:
                                unitForSpeed = "Unknown";
                        }

                        // Replace input.json, User, and UnitForSpeed that are used on template RML file with actual values for the new RML files
                        String newContent = templateContent.replace("input.json", jsonFileName)
                                .replace("User", username)
                                .replace("UnitForSpeed", unitForSpeed);

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
        String inputFileName = "speed_" + number + "_" + username + ".ttl";
        String outputFileName = "speed_" + number + "_" + username + "_output.ttl";
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