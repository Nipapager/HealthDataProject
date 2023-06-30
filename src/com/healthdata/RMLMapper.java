package com.healthdata;

import be.ugent.idlab.knows.functions.agent.Agent;
import be.ugent.idlab.knows.functions.agent.AgentFactory;
import be.ugent.rml.Executor;
import be.ugent.rml.Utils;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.QuadStore;
import be.ugent.rml.store.QuadStoreFactory;
import be.ugent.rml.store.RDF4JStore;
import be.ugent.rml.term.NamedNode;

import java.io.*;

public class RMLMapper {
    public void runRMLMapping(String pathToMapping, String outputPath) throws IOException {
        try {
            File mappingFile = new File(pathToMapping);

            // Get the mapping string stream
            InputStream mappingStream = new FileInputStream(mappingFile);

            // Load the mapping in a QuadStore
            QuadStore rmlStore = QuadStoreFactory.read(mappingStream);

            // Set up the basepath for the records factory, i.e., the basepath for the (local file) data sources
            RecordsFactory factory = new RecordsFactory(mappingFile.getParent());

            // Set up the functions used during the mapping
            Agent functionAgent = AgentFactory.createFromFnO("fno/functions_idlab.ttl", "fno/functions_idlab_test_classes_java_mapping.ttl");

            // Set up the outputstore (needed when you want to output something else than nquads)
            QuadStore outputStore = new RDF4JStore();

            // Create the Executor
            Executor executor = new Executor(rmlStore, factory, outputStore, Utils.getBaseDirectiveTurtle(mappingStream), functionAgent);

            // Execute the mapping
            QuadStore result = executor.execute(null).get(new NamedNode("rmlmapper://default.store"));

            // Output the result
            BufferedWriter out = new BufferedWriter(new FileWriter(outputPath));
            result.write(out, "turtle");
            out.close();
        } catch (Exception e) {
            System.err.println("An error occurred while executing the RML mapping.");
            e.printStackTrace();
        }
    }
}