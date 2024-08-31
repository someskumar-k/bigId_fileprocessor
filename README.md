# 📜 README: Large Text Searcher Project

## Project Overview
This Java-based project is designed to search for specific strings (e.g., the 50 most common English first names) in a large text file. It uses a multi-threaded approach to efficiently process and search within a massive file by dividing it into manageable chunks. The results are then aggregated and displayed.

## Features
- **Efficient File Downloading**: Downloads a large text file if it doesn't already exist locally.
- **Chunked File Processing**: Reads and processes the file in chunks to optimize memory usage.
- **Concurrent Search**: Utilizes multiple threads to search for strings concurrently within each chunk.
- **Result Aggregation**: Combines the results from all threads to produce a final output.

## Project Structure
```plaintext
com.skk.interview.bigId
├── Main.java                     // Entry point for the application
├── dto
│   └── Position.java             // DTO representing a string's position in the text
├── service
│   ├── FileSearchService.java    // Service to handle file reading and string searching
│   └── ResultAggregatorService.java // Service to aggregate search results
└── util
    └── PatternMatcher.java       // Callable task to search a text chunk for specific strings
```

Design Outlook
--------------

### Main Class (`Main.java`)

-   **Purpose**: Entry point that orchestrates the file download, chunk processing, and result aggregation.
-   **Key Functions**:
    -   `downloadFile()`: Downloads the sample input file.
    -   `printResults()`: Outputs the aggregated results.

### File Search Service (`FileSearchService.java`)

-   **Purpose**: Handles reading the file in chunks and initiating the search tasks using multiple threads.
-   **Key Functions**:
    -   `searchAndMapLocation()`: Manages the search process across file chunks.
    -   `readAndMatchFile()`: Reads the file line by line and submits chunks to `PatternMatcher` tasks.

### Pattern Matcher (`PatternMatcher.java`)

-   **Purpose**: Performs the actual search within a chunk of text. Implements `Callable` to run as a separate thread.
-   **Key Functions**:
    -   `call()`: Searches for each name in the provided chunk and records their positions.

### Result Aggregator Service (`ResultAggregatorService.java`)

-   **Purpose**: Combines results from all `PatternMatcher` instances to produce the final output.
-   **Key Functions**:
    -   `aggregate()`: Merges multiple maps of string positions into a single map.

### Position DTO (`Position.java`)

-   **Purpose**: Represents the position of a found string within the text.
-   **Key Fields**:
    -   `lineOffset`: The line number in the text.
    -   `charOffset`: The character offset within the line.
