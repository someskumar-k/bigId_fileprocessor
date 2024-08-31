package com.skk.interview.bigId.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skk.interview.bigId.dto.Position;

public class ResultAggregatorService {

    public static Map<String, List<Position>> aggregate(List<Map<String, List<Position>>> results) {
        Map<String, List<Position>> aggregatedResults = new HashMap<>();
        for (Map<String, List<Position>> result : results) {
            for (Map.Entry<String, List<Position>> entry : result.entrySet()) {
                String name = entry.getKey();
                List<Position> locations = entry.getValue();
                aggregatedResults.computeIfAbsent(name, k -> new ArrayList<>()).addAll(locations);
            }
        }
        return aggregatedResults;
    }
}