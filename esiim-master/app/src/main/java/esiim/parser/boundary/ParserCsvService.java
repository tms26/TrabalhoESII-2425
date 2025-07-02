package esiim.parser.boundary;

import esiim.parser.entity.ParsedData;
import esiim.parser.entity.ParsedEmissionsData;
import esiim.parser.exception.InvalidDataException;

/**
 * Parser CSV service interface.
 */
public interface ParserCsvService {
    public ParsedData parseCsv(String filePath) throws InvalidDataException;
    public ParsedEmissionsData parseEmissionsCsv(String filePath) throws InvalidDataException;
}