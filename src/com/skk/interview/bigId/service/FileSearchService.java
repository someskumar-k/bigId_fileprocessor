package com.skk.interview.bigId.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.skk.interview.bigId.dto.Position;
import com.skk.interview.bigId.util.PatternMatcher;

public class FileSearchService {

    public static List<Map<String, List<Position>>> searchAndMapLocation(Path filePath, int chunkSize, List<String> searchKeys) throws Exception {
        try {
            List<Map<String, List<Position>>> results = new ArrayList<>();
            List<Future<Map<String, List<Position>>>> futures = readAndMatchFile(filePath, chunkSize, searchKeys);
            for (Future<Map<String, List<Position>>> future : futures) {
                try {
                    results.add(future.get());
                } catch (Exception e) {
                    System.err.println("Error processing part: " + e.getMessage());
                }
            }
            return results;
        } catch (Exception exception) {
            throw new Exception("Unable to process the given text file.", exception);
        }
    }

    private static List<Future<Map<String, List<Position>>>> readAndMatchFile(Path filePath, int chunkSize, List<String> searchKeys) throws IOException, InterruptedException {
        int lineCount = 0;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try (Stream<String> linesStream = Files.lines(filePath, StandardCharsets.UTF_8)) {
            List<Future<Map<String, List<Position>>>> futures = new ArrayList<>();
            Iterator<String> linesIterator = linesStream.iterator();
            List<String> lines = new ArrayList<>();
            int lineOffset = 0;

            while (linesIterator.hasNext()) {
                lines.add(linesIterator.next());

                if (lines.size() >= chunkSize) {
                    PatternMatcher matcher = new PatternMatcher(String.join("\n", lines), lineOffset, searchKeys);

                    futures.add(executor.submit(matcher));
                    lines.clear();
                    lineOffset += chunkSize;
                }
            }
            if (!lines.isEmpty()) {
                futures.add(executor.submit(new PatternMatcher(String.join("\n", lines), lineOffset, searchKeys)));
            }
            return futures;
        } finally {
            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        }

    }
}

