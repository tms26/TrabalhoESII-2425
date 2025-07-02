package esiim.modeler.boundary;

import java.util.List;

import esiim.modeler.entity.Emission;
import esiim.parser.entity.ParsedEmissionsData;
import esiim.modeler.exception.InvalidParsedEmissionsDataException;

/**
 * Modeler emission service interface.
 */
public interface ModelerEmissionService{
    public List<Emission> modelEmission(ParsedEmissionsData parsedEmissionsData) throws InvalidParsedEmissionsDataException;
}
