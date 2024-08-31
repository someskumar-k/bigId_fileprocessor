package com.skk.interview.bigId.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.skk.interview.bigId.dto.Position;

public class PatternMatcher implements Callable<Map<String, List<Position>>>{
    private final String textChunk;
    private final int lineOffset;
    private final List<String> namesToFind;

    public PatternMatcher(String textChunk, int lineOffset, List<String> namesToFind) {
        this.textChunk = textChunk;
        this.lineOffset = lineOffset;
        this.namesToFind = namesToFind;
    }

    @Override
    public Map<String, List<Position>> call() {
        Map<String, List<Position>> results = new HashMap<>();
        String[] lines = textChunk.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            for (String name : namesToFind) {
                int index = line.indexOf(name);
                while (index >= 0) {
                    int charOffset = index + (i > 0 ? textChunk.substring(0, textChunk.indexOf(line)).length() : 0);
                    results.computeIfAbsent(name, k -> new ArrayList<>())
                            .add(new Position(lineOffset + i, charOffset));
                    index = line.indexOf(name, index + 1);
                }
            }
        }
        return results;
    }
}
