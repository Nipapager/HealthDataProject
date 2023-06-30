package com.healthdata;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import java.io.*;

public class TTLFilesMerger {
    public static void mergeFiles() throws IOException {
        String directoryPath = "FinalTTL";
        String outputFile = "FinalOntology\\HealthOntology.ttl";
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".ttl"));

        Model model = new LinkedHashModel();

        if (files != null) {
            for (File file : files) {
                try (InputStream input = new FileInputStream(file)) {
                    Model fileModel = Rio.parse(input, "", RDFFormat.TURTLE);
                    model.addAll(fileModel);
                }
            }
        }

        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            Rio.write(model, out, RDFFormat.TURTLE);
        }
    }
}