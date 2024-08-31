package com.skk.interview.bigId;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.skk.interview.bigId.dto.Position;
import com.skk.interview.bigId.service.FileSearchService;
import com.skk.interview.bigId.service.ResultAggregatorService;

public class Main {
    public static void main(String[] args) {
        List<String> keys = Arrays.asList(
                "James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles",
                "Joseph", "Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald", "George",
                "Kenneth", "Steven", "Edward", "Brian", "Ronald", "Anthony", "Kevin", "Jason",
                "Matthew", "Gary", "Timothy", "Jose", "Larry", "Jeffrey", "Frank", "Scott",
                "Eric", "Stephen", "Andrew", "Raymond", "Gregory", "Joshua", "Jerry", "Dennis",
                "Walter", "Patrick", "Peter", "Harold", "Douglas", "Henry", "Carl", "Arthur",
                "Ryan", "Roger"
        );
        final int lines_size = 1000;
        Path filePath = Paths.get("bigId_sample_input.txt");
        try {
            downloadFile(filePath);
            System.out.println("Processing text file..");
            Long startTime = System.currentTimeMillis();
            List<Map<String, List<Position>>> chuncks = new FileSearchService().searchAndMapLocation(filePath, lines_size, keys);
            System.out.println("Aggregating results..");
            Long aggTime = System.currentTimeMillis();
            Map<String, List<Position>> results = ResultAggregatorService.aggregate(chuncks);
            System.out.println("Print results");
            printResults(results);
            Long endTime = System.currentTimeMillis();
            System.out.println("-----------------------------------------\n" +
                    "Time taken for file processing: "+(aggTime-startTime)+"ms\n"+
                    "Time taken for result aggregation: "+(endTime-aggTime)+"ms\n"+
                    "Total time taken: "+(endTime-startTime)+"ms\n"
                    );


        } catch (Exception exception) {
            System.out.println(exception);
        }

    }

    private static void printResults(Map<String, List<Position>> result) {
        for (Map.Entry<String, List<Position>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
    }

    private static void downloadFile(final Path fileName) throws IOException {
        try {
            if (!Files.exists(fileName)) {
                System.out.println("Sample input file downloading.");
                Files.createFile(fileName);
                URL url = new URL("http://norvig.com/big.txt");
                try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                     FileOutputStream fos = new FileOutputStream(fileName.toFile())) {
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }
                System.out.println("File downloaded.");
            }
        } catch (final IOException exception) {
            throw new IOException("Error in downloading the input text file.", exception);
        }

    }
}
