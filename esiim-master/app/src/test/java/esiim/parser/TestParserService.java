package esiim.parser;

import esiim.parser.controller.Parser;
import esiim.parser.exception.InvalidDataException;
import esiim.parser.entity.ParsedData;
import esiim.parser.entity.ParsedEmissionsData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Parser} class.
 */
public class TestParserService {
    private Parser parser;

    /**
     * Initial setup before each test, instantiating a new {@link Parser} object.
     */
    @BeforeEach
    void setup() {
        parser = new Parser();
    }

    /**
     * Test to parse a valid CSV file.
     * @throws InvalidDataException if the data is invalid
     */
    @Test
    void parseCsv_ValidCSVFile_ShouldParseSuccessfully() throws InvalidDataException {
        String filePath = "src/test/resources/product/valid_shoe_production.csv";
        ParsedData result = parser.parseCsv(filePath);
        assertNotNull(result);
    }

    /**
     * Test to parse a CSV file with inconsistent spacing.
     */
    @Test
    void parseCsv_MalformattedCSVFileWithInconsistentSpacing_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/malformatted_spacing.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with inconsistent delimiters.
     */
    @Test
    void parseCsv_MalformattedCSVFileWithInconsistentDelimiters_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/malformatted_delimiters.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with high number of rows.
     */
    @Test
    void parseCsv_LargeCSVFileWithHighNumberOfRows_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/large_csv_file.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with empty fields.
     */
    @Test
    void parseCsv_EmptyCSVFile_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/empty.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with incorrect extension.
     */
    @Test
    void parseCsv_CSVFileWithIncorrectExtension_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/incorrect_extension.txt";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with invalid headers.
     */
    @Test
    void parseCsv_StringContainingNullValues_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/null_values.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with empty fields.
     */
    @Test
    void parseCsv_StringContainingEmptyFields_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/empty_fields.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with invalid characters.
     */
    @Test
    void parseCsv_StringContainingInvalidCharacters_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/invalid_characters.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with letters in numeric fields.
     */
    @Test
    void parseCsv_StringContainingLettersInNumericFields_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/letters_in_numeric_fields.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with zero or negative values.
     */
    @Test
    void parseCsv_StringContainingNumericFieldsWithZeroOrNegativeValues_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/zero_or_negative_values.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with invalid headers.
     */
    @Test
    void parseCsv_CSVFileWithInvalidHeaders_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/invalid_headers.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a CSV file with letters in numeric fields.
     */
    @Test
    void parseCsv_CSVFileNotFound_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/product/non_existent_file.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseCsv(filePath));
    }

    /**
     * Test to parse a valid emissions CSV file.
     * @throws InvalidDataException if the data is invalid
     */
    @Test
    void parseEmissionsCsv_ValidCSVFile_ShouldParseSuccessfully() throws InvalidDataException {
        String filePath = "src/test/resources/emissions/valid_emissions.csv";
        ParsedEmissionsData result = parser.parseEmissionsCsv(filePath);
        assertNotNull(result);
    }

    /**
     * Test to parse a emissions CSV file with inconsistent spacing.
     */
    @Test
    void parseEmissionsCsv_MalformattedCSVFileWithInconsistentSpacing_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/malformatted_spacing.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with inconsistent delimiters.
     */
    @Test
    void parseEmissionsCsv_MalformattedCSVFileWithInconsistentDelimiters_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/malformatted_delimiters.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with high number of rows.
     */
    @Test
    void parseEmissionsCsv_LargeCSVFileWithHighNumberOfRows_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/large_csv_file.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with empty fields.
     */
    @Test
    void parseEmissionsCsv_EmptyCSVFile_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/empty.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with incorrect extension.
     */
    @Test
    void parseEmissionsCsv_CSVFileWithIncorrectExtension_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/incorrect_extension.txt";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with invalid headers.
     */
    @Test
    void parseEmissionsCsv_StringContainingNullValues_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/null_values.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with empty fields.
     */
    @Test
    void parseEmissionsCsv_StringContainingEmptyFields_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/empty_fields.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with invalid characters.
     */
    @Test
    void parseEmissionsCsv_StringContainingInvalidCharacters_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/invalid_characters.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with letters in numeric fields.
     */
    @Test
    void parseEmissionsCsv_StringContainingLettersInNumericFields_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/letters_in_numeric_fields.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with zero or negative values.
     */
    @Test
    void parseEmissionsCsv_StringContainingNumericFieldsWithZeroOrNegativeValues_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/zero_or_negative_values.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with invalid headers.
     */
    @Test
    void parseEmissionsCsv_CSVFileWithInvalidHeaders_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/invalid_headers.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }

    /**
     * Test to parse a emissions CSV file with letters in numeric fields.
     */
    @Test
    void parseEmissionsCsv_CSVFileNotFound_ShouldThrowInvalidDataException() {
        String filePath = "src/test/resources/emissions/non_existent_file.csv";
        assertThrows(InvalidDataException.class, () -> parser.parseEmissionsCsv(filePath));
    }
}