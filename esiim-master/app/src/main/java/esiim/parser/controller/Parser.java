package esiim.parser.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import esiim.parser.entity.*;
import esiim.parser.exception.InvalidDataException;
import esiim.parser.boundary.ParserService;

/**
 * Parser class to parse CSV files.
 */
public class Parser implements ParserService {
    private static final int MAX_LINES = 50;
    private ParsedData parsedData;
    private ParsedEmissionsData parsedEmissionsData;

    /**
     * Parses the CSV file.
     *
     * @param filePath the file path
     * @return the parsed data
     * @throws InvalidDataException if the data is invalid
     */
    public ParsedData parseCsv(String filePath) throws InvalidDataException {
        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new InvalidDataException("Invalid file type. Only .csv files are supported.");
        }

        String data;
        try {
            data = readFile(filePath);
        } catch (Exception e) {
            throw new InvalidDataException("Error reading the CSV file: " + e.getMessage(), e);
        }

        if (data.trim().isEmpty()) {
            throw new InvalidDataException("CSV file is empty.");
        }

        String[] lines = data.split("\n");
        if (lines.length > MAX_LINES) {
            throw new InvalidDataException("CSV file is too large.");
        }

        boolean isFirstLine = true;
        int weightIndex = -1;
        int quantityIndex = -1;
        for (String line : lines) {
            if (line.contains("  ")) {
                throw new InvalidDataException("CSV file has inconsistent spacing.");
            }

            if (line.contains(";")) {
                throw new InvalidDataException("CSV file has inconsistent delimiters.");
            }

            if (line.contains("null")) {
                throw new InvalidDataException("CSV file contains null values.");
            }

            String[] fields = line.split(",");
            for (String field : fields) {
                if (field.trim().isEmpty()) {
                    throw new InvalidDataException("CSV file contains empty fields.");
                }
            }

            if (!Pattern.matches("[\\w\\s,.-]+", line)) {
                throw new InvalidDataException("CSV file contains invalid characters.");
            }

            if (isFirstLine) {
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].trim().equalsIgnoreCase("Weight")) {
                        weightIndex = i;
                    } else if (fields[i].trim().equalsIgnoreCase("Quantity")) {
                        quantityIndex = i;
                    }
                }
                if (!isValidProductHeaders(fields)) {
                    throw new InvalidDataException("CSV file has invalid headers.");
                }

                isFirstLine = false;
                continue;
            }

            for (int i = 0; i < fields.length; i++) {
                String field = fields[i];
                if (i == weightIndex || i == quantityIndex) {
                    if (field.matches(".*[a-zA-Z].*")) {
                        throw new InvalidDataException("CSV file contains letters in numeric fields.");
                    }
                    if (field.matches("-?\\d*\\.?\\d+") && Double.parseDouble(field) <= 0) {
                        throw new InvalidDataException("CSV file contains numeric fields with 0 or negative values.");
                    }
                }
            }
        }

        parsedData = new ParsedData();
        parsedData.setProductString(data);
        return parsedData;
    }

    /**
     * Parses the CSV emissions file.
     *
     * @param filePath the file path
     * @return the parsed emissions data
     * @throws InvalidDataException if the data is invalid
     */
    public ParsedEmissionsData parseEmissionsCsv(String filePath) throws InvalidDataException {
        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new InvalidDataException("Invalid file type. Only .csv files are supported.");
        }

        String data;
        try {
            data = readFile(filePath);
        } catch (Exception e) {
            throw new InvalidDataException("Error reading the CSV file: " + e.getMessage(), e);
        }

        if (data.trim().isEmpty()) {
            throw new InvalidDataException("CSV file is empty.");
        }

        String[] lines = data.split("\n");
        if (lines.length > MAX_LINES) {
            throw new InvalidDataException("CSV file is too large.");
        }

        boolean isFirstLine = true;
        int quantityIndex = -1;
        int emissionFactorIndex = -1;
        for (String line : lines) {
            if (line.contains("  ")) {
                throw new InvalidDataException("CSV file has inconsistent spacing.");
            }

            if (line.contains(";")) {
                throw new InvalidDataException("CSV file has inconsistent delimiters.");
            }

            if (line.contains("null")) {
                throw new InvalidDataException("CSV file contains null values.");
            }

            String[] fields = line.split(",");
            for (String field : fields) {
                if (field.trim().isEmpty()) {
                    throw new InvalidDataException("CSV file contains empty fields.");
                }
            }

            if (!Pattern.matches("[\\w\\s,.-]+", line)) {
                throw new InvalidDataException("CSV file contains invalid characters.");
            }

            if (isFirstLine) {
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].trim().equalsIgnoreCase("EmissionFactor")) {
                        emissionFactorIndex = i;
                    } else if (fields[i].trim().equalsIgnoreCase("Quantity")) {
                        quantityIndex = i;
                    }
                }
                if (!isValidEmissionsHeaders(fields)) {
                    throw new InvalidDataException("CSV file has invalid headers.");
                }

                isFirstLine = false;
                continue;
            }

            for (int i = 0; i < fields.length; i++) {
                String field = fields[i];
                if (i == quantityIndex || i == emissionFactorIndex) {
                    if (field.matches(".*[a-zA-Z].*")) {
                        throw new InvalidDataException("CSV file contains letters in numeric fields.");
                    }
                    if (field.matches("-?\\d*\\.?\\d+") && Double.parseDouble(field) <= 0) {
                        throw new InvalidDataException("CSV file contains numeric fields with 0 or negative values.");
                    }
                }
            }
        }

        parsedEmissionsData = new ParsedEmissionsData();
        parsedEmissionsData.setEmissionsString(data);
        return parsedEmissionsData;
    }

    /**
     * Reads the file.
     *
     * @param filePath the file path
     * @return the file data
     */
    private String readFile(String filePath) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * Checks if the headers are valid.
     *
     * @param headers the headers
     * @return true if the headers are valid, false otherwise
     */
    private boolean isValidProductHeaders(String[] headers) {
        String[] expectedHeaders = { "Name", "Country", "Weight", "Unit", "Process Type", "ProductFlow Name",
                "Flow Name", "Unit", "Category", "Quantity", "Tag", "Type", "Origin Country" };
        if (headers.length != expectedHeaders.length) {
            return false;
        }
        for (int i = 0; i < headers.length; i++) {
            if (!headers[i].trim().equalsIgnoreCase(expectedHeaders[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the headers are valid.
     *
     * @param headers the headers
     * @return true if the headers are valid, false otherwise
     */
    private boolean isValidEmissionsHeaders(String[] headers) {
        String[] expectedHeaders = { "Name", "Category", "Unit", "Quantity", "EmissionFactor" };
        if (headers.length != expectedHeaders.length) {
            return false;
        }
        for (int i = 0; i < headers.length; i++) {
            if (!headers[i].trim().equalsIgnoreCase(expectedHeaders[i])) {
                return false;
            }
        }
        return true;
    }
}