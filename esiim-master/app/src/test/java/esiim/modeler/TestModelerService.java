package esiim.modeler;

import esiim.modeler.controller.Modeler;
import esiim.modeler.exception.InvalidParseDataException;
import esiim.modeler.entity.Product;
import esiim.parser.entity.ParsedData;
import esiim.modeler.exception.InvalidParsedEmissionsDataException;
import esiim.modeler.entity.Emission;
import esiim.parser.entity.ParsedEmissionsData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for the {@link Modeler} class.
 */
public class TestModelerService {
    private Modeler modeler;

    /**
     * Initial setup before each test, instantiating a new {@link Modeler} object.
     */
    @BeforeEach
    void setup() {
        modeler = new Modeler();
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with valid parsed data.
     */
    @Test
    void modelProduct_ValidParsedData_ShouldReturnProduct() throws InvalidParseDataException {
        String productString = "Name,Country,Weight,Unit,Process Type,ProductFlow Name,Flow Name,Unit,Category,Quantity,Tag,Type,Origin Country\n" +
                               "Running Shoes,Portugal,0.8,KG,RawMaterialProduction,Polyester Production,Petroleum,Liters,RawMaterial,0.3,Virgin,Input,Turkey";
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(productString);

        Product result = modeler.modelProduct(parsedData);
        assertNotNull(result);
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with valid parsed data.
     */
    @Test
    void modelProduct_NullParsedData_ShouldThrowInvalidParseDataException() {
        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(null));
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with empty or null product string.
     */
    @Test
    void modelProduct_EmptyOrNullParsedDataProductString_ShouldThrowInvalidParseDataException() {
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(null);
        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));

        parsedData.setProductString("");
        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with invalid product string.
     */
    @Test
    void modelProduct_InvalidProductUnitValue_ShouldThrowInvalidParseDataException() {
        String productString = "Name,Country,Weight,Unit,Process Type,ProductFlow Name,Flow Name,Unit,Category,Quantity,Tag,Type,Origin Country\n" +
                               "Running Shoes,Portugal,0.8,INVALID_UNIT,RawMaterialProduction,Polyester Production,Petroleum,Liters,RawMaterial,0.3,Virgin,Input,Turkey";
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(productString);

        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with invalid product string.
     */
    @Test
    void modelProduct_InvalidFlowUnitValue_ShouldThrowInvalidParseDataException() {
        String productString = "Name,Country,Weight,Unit,Process Type,ProductFlow Name,Flow Name,Unit,Category,Quantity,Tag,Type,Origin Country\n" +
                               "Running Shoes,Portugal,0.8,KG,RawMaterialProduction,Polyester Production,Petroleum,INVALID_UNIT,RawMaterial,0.3,Virgin,Input,Turkey";
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(productString);

        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with invalid product string.
     */
    @Test
    void modelProduct_InvalidProcessTypeValue_ShouldThrowInvalidParseDataException() {
        String productString = "Name,Country,Weight,Unit,Process Type,ProductFlow Name,Flow Name,Unit,Category,Quantity,Tag,Type,Origin Country\n" +
                               "Running Shoes,Portugal,0.8,KG,INVALID_PROCESS_TYPE,Polyester Production,Petroleum,Liters,RawMaterial,0.3,Virgin,Input,Turkey";
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(productString);

        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with invalid product string.
     */
    @Test
    void modelProduct_InvalidFlowCategoryValue_ShouldThrowInvalidParseDataException() {
        String productString = "Name,Country,Weight,Unit,Process Type,ProductFlow Name,Flow Name,Unit,Category,Quantity,Tag,Type,Origin Country\n" +
                               "Running Shoes,Portugal,0.8,KG,RawMaterialProduction,Polyester Production,Petroleum,Liters,INVALID_CATEGORY,0.3,Virgin,Input,Turkey";
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(productString);

        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with invalid product string.
     */
    @Test
    void modelProduct_InvalidFlowTagValue_ShouldThrowInvalidParseDataException() {
        String productString = "Name,Country,Weight,Unit,Process Type,ProductFlow Name,Flow Name,Unit,Category,Quantity,Tag,Type,Origin Country\n" +
                               "Running Shoes,Portugal,0.8,KG,RawMaterialProduction,Polyester Production,Petroleum,Liters,RawMaterial,0.3,INVALID_TAG,Input,Turkey";
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(productString);

        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with invalid product string.
     */
    @Test
    void modelProduct_InvalidFlowTypeValue_ShouldThrowInvalidParseDataException() {
        String productString = "Name,Country,Weight,Unit,Process Type,ProductFlow Name,Flow Name,Unit,Category,Quantity,Tag,Type,Origin Country\n" +
                               "Running Shoes,Portugal,0.8,KG,RawMaterialProduction,Polyester Production,Petroleum,Liters,RawMaterial,0.3,Virgin,INVALID_TYPE,Turkey";
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(productString);

        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));
    }

    /**
     * Test the {@link Modeler#modelProduct(ParsedData)} method with invalid product string.
     */
    @Test
    void modelProduct_InvalidUnitForCategory_ShouldThrowInvalidParseDataException() {
        String productString = "Name,Country,Weight,Unit,Process Type,ProductFlow Name,Flow Name,Unit,Category,Quantity,Tag,Type,Origin Country\n" +
                               "Running Shoes,Portugal,0.8,KG,RawMaterialProduction,Polyester Production,Petroleum,Joules,RawMaterial,0.3,Virgin,Input,Turkey";
        ParsedData parsedData = new ParsedData();
        parsedData.setProductString(productString);

        assertThrows(InvalidParseDataException.class, () -> modeler.modelProduct(parsedData));
    }

    /**
     * Test the {@link Modeler#modelEmission(ParsedEmissionsData)} method with valid parsed emissions data.
     */
    @Test
    void modelEmission_ValidParsedEmissionsData_ShouldReturnListOfEmissions() throws InvalidParsedEmissionsDataException {
        String emissionsString = "Name,Category,Unit,Quantity,EmissionFactor\n" +
                                 "CO2,AirEmission,KgCO2,100,1.5";
        ParsedEmissionsData parsedEmissionsData = new ParsedEmissionsData();
        parsedEmissionsData.setEmissionsString(emissionsString);

        List<Emission> result = modeler.modelEmission(parsedEmissionsData);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Test the {@link Modeler#modelEmission(ParsedEmissionsData)} method with null parsed emissions data.
     */
    @Test
    void modelEmission_NullParsedEmissionsData_ShouldThrowInvalidParsedEmissionsDataException() {
        assertThrows(InvalidParsedEmissionsDataException.class, () -> modeler.modelEmission(null));
    }

    /**
     * Test the {@link Modeler#modelEmission(ParsedEmissionsData)} method with empty or null emissions string.
     */
    @Test
    void modelEmission_EmptyOrNullParsedEmissionsDataEmissionsString_ShouldThrowInvalidParsedEmissionsDataException() {
        ParsedEmissionsData parsedEmissionsData = new ParsedEmissionsData();
        parsedEmissionsData.setEmissionsString(null);
        assertThrows(InvalidParsedEmissionsDataException.class, () -> modeler.modelEmission(parsedEmissionsData));

        parsedEmissionsData.setEmissionsString("");
        assertThrows(InvalidParsedEmissionsDataException.class, () -> modeler.modelEmission(parsedEmissionsData));
    }

    /**
     * Test the {@link Modeler#modelEmission(ParsedEmissionsData)} method with invalid emissions unit value.
     */
    @Test
    void modelEmission_InvalidEmissionsUnitValue_ShouldThrowInvalidParsedEmissionsDataException() {
        String emissionsString = "Name,Category,Unit,Quantity,EmissionFactor\n" +
                                 "CO2,AirEmission,INVALID_UNIT,100,1.5";
        ParsedEmissionsData parsedEmissionsData = new ParsedEmissionsData();
        parsedEmissionsData.setEmissionsString(emissionsString);

        assertThrows(InvalidParsedEmissionsDataException.class, () -> modeler.modelEmission(parsedEmissionsData));
    }

    /**
     * Test the {@link Modeler#modelEmission(ParsedEmissionsData)} method with invalid emissions category value.
     */
    @Test
    void modelEmission_InvalidEmissionsCategoryValue_ShouldThrowInvalidParsedEmissionsDataException() {
        String emissionsString = "Name,Category,Unit,Quantity,EmissionFactor\n" +
                                 "CO2,INVALID_CATEGORY,KgCO2,100,1.5";
        ParsedEmissionsData parsedEmissionsData = new ParsedEmissionsData();
        parsedEmissionsData.setEmissionsString(emissionsString);

        assertThrows(InvalidParsedEmissionsDataException.class, () -> modeler.modelEmission(parsedEmissionsData));
    }

    /**
     * Test the {@link Modeler#modelEmission(ParsedEmissionsData)} method with invalid unit for category.
     */
    @Test
    void modelEmission_InvalidUnitForCategory_ShouldThrowInvalidParsedEmissionsDataException() {
        String emissionsString = "Name,Category,Unit,Quantity,EmissionFactor\n" +
                                 "CO2,AirEmission,Liters,100,1.5";
        ParsedEmissionsData parsedEmissionsData = new ParsedEmissionsData();
        parsedEmissionsData.setEmissionsString(emissionsString);

        assertThrows(InvalidParsedEmissionsDataException.class, () -> modeler.modelEmission(parsedEmissionsData));
    }
}