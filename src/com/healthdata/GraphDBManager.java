package com.healthdata;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import java.net.URL;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

// Class for managing repositories on a GraphDB instance.
public class GraphDBManager {
    // URL for the GraphDB server
    private final String serverUrl = "http://localhost:7200";
    // ID for the repository on the GraphDB server
    private String repositoryId;

    // Set the repository ID from the configuration file
    public void setRepositoryIdFromConfig() throws IOException {
        this.repositoryId = getRepoIdFromConfig();
    }

    public String getRepositoryId(){
        return repositoryId;
    }

    // Create a repository and upload all .ttl files with RDF triples to it
    public void createRepositoryAndUploadFiles() throws IOException {
        setRepositoryIdFromConfig();
        createRepository();
        uploadAllTtlFiles();
    }

    // Retrieve the repository ID from the configuration file
    private String getRepoIdFromConfig() throws IOException {
        String configFilePath = "GraphDBconfig\\config.ttl";
        try (BufferedReader br = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Look for the repository ID in the configuration file
                if (line.contains("rep:repositoryID")) {
                    // Extracting repository ID
                    return line.split("\"")[1];
                }
            }
        }
        throw new IOException("Failed to find repositoryID in config file");
    }

    // Check if a repository already exists on the GraphDB server
    private boolean repositoryExists(String repoName) throws IOException {
        URL url = new URL(serverUrl + "/rest/repositories/" + repoName);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.connect();
        int responseCode = httpConn.getResponseCode();
        httpConn.disconnect();
        return (responseCode == HttpURLConnection.HTTP_OK);
    }

    // Create a new repository on the GraphDB server
    private void createRepository() throws IOException {
        if (repositoryExists(repositoryId)) {
            System.out.println("Repository " + repositoryId + " already exists.");
            return;
        }

        // Generate a unique boundary string for the multipart request
        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n"; // Line separator required by multipart/form-data

        URL url = new URL(serverUrl + "/rest/repositories");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        // Open the connection and send the request
        try (
                OutputStream output = httpConn.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true);
        ) {
            // Read the configuration file
            String configFilePath = "GraphDBconfig\\config.ttl";
            File configFile = new File(configFilePath);
            String fileName = configFile.getName();

            // Write the file to the request
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"config\"; filename=\"" + fileName + "\"").append(CRLF);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(configFile.toPath(), output);
            output.flush();
            writer.append(CRLF).flush();
            writer.append("--" + boundary + "--").append(CRLF).flush();
        }

        // Check the server response
        int responseCode = httpConn.getResponseCode();
        if (responseCode < HttpURLConnection.HTTP_OK || responseCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
            System.out.println("Response message: " + httpConn.getResponseMessage());
            throw new IOException("Failed to create repository: " + httpConn.getResponseMessage());
        }

        httpConn.disconnect();
    }

    // Upload all .ttl files with RDF triples from FinalTTL folder to the repository that created
    private void uploadAllTtlFiles() throws IOException {
        String directoryPath = "FinalTTL";
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".ttl"));

        // If the directory contains .ttl files, upload each of them
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    uploadTtlFile(file.getAbsolutePath());
                }
            }
        }
    }

    // Upload a specific .ttl file to the repository
    private void uploadTtlFile(String filePath) throws IOException {
        // Connect to the repository
        HTTPRepository repository = new HTTPRepository(serverUrl, repositoryId);

        File file = new File(filePath);

        try (RepositoryConnection connection = repository.getConnection()) {
            // Define the base URI for the uploaded data
            String baseUri = "http://example.com/hdi/";
            // Add the file to the repository
            connection.add(file, baseUri, Rio.getParserFormatForFileName(file.getName()).orElse(RDFFormat.RDFXML));
        }
    }
}
