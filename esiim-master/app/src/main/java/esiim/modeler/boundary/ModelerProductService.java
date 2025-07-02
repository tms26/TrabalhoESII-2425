package esiim.modeler.boundary;

import esiim.modeler.entity.Product;
import esiim.modeler.exception.InvalidParseDataException;
import esiim.parser.entity.ParsedData;

/**
 * Modeler product service interface.
 */
public interface ModelerProductService {
    public Product modelProduct(ParsedData parsedData) throws InvalidParseDataException;
}