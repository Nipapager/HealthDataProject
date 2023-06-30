package com.healthdata;

// import necessary libraries
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF4J;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

// Class for validating RDF Triples using SHACL shapes
public class ShaclValidation {

    // URL for the GraphDB server
    private static final String GRAPHDB_SERVER = "http://localhost:7200/";

    // Main method for performing the validation
    public static void validate(String REPOSITORY_ID) {
        // Create a new RepositoryManager at the provided address
        RemoteRepositoryManager manager = new RemoteRepositoryManager(GRAPHDB_SERVER);
        manager.init();

        // Get the repository by its id
        Repository repository = manager.getRepository(REPOSITORY_ID);

        // Open a connection to the repository
        try (RepositoryConnection conn = repository.getConnection()) {
            // Load and add the SHACL file to the repository
            Model shaclRules = loadShaclFile("SHACLValidation\\shacl.ttl");
            // Specify the SHACL graph
            IRI shaclGraph = SimpleValueFactory.getInstance().createIRI(RDF4J.SHACL_SHAPE_GRAPH.stringValue());
            // Add the SHACL rules to the repository
            conn.add(shaclRules, shaclGraph);
        }
        // Shut down the RepositoryManager after use
        manager.shutDown();
    }

    // Method for loading SHACL files
    private static Model loadShaclFile(String path) {
        Model model = null;

        // Load the file and parse it
        try {
            InputStream inputStream = new FileInputStream(path);
            RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
            model = new org.eclipse.rdf4j.model.impl.LinkedHashModel();
            RDFHandler handler = new StatementCollector(model);
            // Parse the SHACL rules file into a model
            rdfParser.setRDFHandler(handler);
            rdfParser.parse(inputStream, RDF4J.SHACL_SHAPE_GRAPH.stringValue());
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            e.printStackTrace();
        } catch (Exception e) {
            // Handle general exceptions
            e.printStackTrace();
        }

        // Return the parsed model
        return model;
    }
}