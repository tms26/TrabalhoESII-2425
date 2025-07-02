package esiim.calculator;

import esiim.calculator.controller.Calculator;
import esiim.calculator.exception.InvalidCalculationException;
import esiim.modeler.entity.Emission;
import esiim.modeler.entity.Flow;
import esiim.modeler.entity.Process;
import esiim.modeler.entity.Product;
import esiim.modeler.entity.ProductFlow;
import esiim.modeler.entity.ProductSystem;
import esiim.modeler.entity.enums.Category;
import esiim.modeler.entity.enums.FlowType;
import esiim.modeler.entity.enums.ProcessType;
import esiim.modeler.entity.enums.Unit;
import esiim.modeler.exception.InvalidParseDataException;
import esiim.modeler.exception.InvalidParsedEmissionsDataException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the {@link Calculator} class.
 */
public class TestCalculatorService {
    private Calculator calculator;

    /**
     * Initial setup before each test, instantiating a new {@link Calculator} object.
     */
    @BeforeEach
    void setup() {
        calculator = new Calculator();
    }

    /**
     * Helper method to create a valid product.
     * @return a valid product
     */
    private Product createValidProduct() {
        List<Flow> flows = new ArrayList<>();
        flows.add(new Flow("Petroleum", Unit.Liters, Category.RawMaterial, 0.3, FlowType.Input, "Turkey"));
        List<ProductFlow> productFlows = new ArrayList<>();
        productFlows.add(new ProductFlow("Polyester Production", flows));
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(ProcessType.RawMaterialProduction, productFlows));
        return new Product("Running Shoes", "Portugal", 0.8, Unit.KG, new ProductSystem(processes));
    }

    /**
     * Helper method to create a valid product with a non-input flow.
     * @return a valid product with a non-input flow
     */
    private Product createValidProductWithNonInputFlow() {
        List<Flow> flows = new ArrayList<>();
        flows.add(new Flow("Petroleum", Unit.Liters, Category.RawMaterial, 0.3, FlowType.Output, "Turkey"));
        List<ProductFlow> productFlows = new ArrayList<>();
        productFlows.add(new ProductFlow("Polyester Production", flows));
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(ProcessType.RawMaterialProduction, productFlows));
        return new Product("Running Shoes", "Portugal", 0.8, Unit.KG, new ProductSystem(processes));
    }

    /**
     * Helper method to create valid emissions.
     * @return a list of valid emissions
     */ 
    private List<Emission> createValidEmissions() {
        List<Emission> emissions = new ArrayList<>();
        emissions.add(new Emission("Petroleum", Category.RawMaterial, Unit.Liters, 0.3, 1.5));
        return emissions;
    }

    /**
     * Test method for the {@link Calculator#calculatePcf(Product, List)} method.
     * This test case is for when the product and emissions are valid, the method should calculate the pcf successfully.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_ValidProductAndEmissions_ShouldCalculateSuccessfully() throws InvalidCalculationException {
        Product product = createValidProduct();
        List<Emission> emissions = createValidEmissions();

        calculator.calculatePcf(product, emissions);

        assertNotNull(product.getPcf());
        assertTrue(product.getPcf() > 0);
    }

    /**
     * Test methods for the {@link Calculator#calculatePcf(Product, List)} method with invalid input.
     * This test case is for when the product is null, the method should throw an exception.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_NullProduct_ShouldThrowInvalidCalculationException() {
        List<Emission> emissions = createValidEmissions();
        assertThrows(InvalidCalculationException.class, () -> calculator.calculatePcf(null, emissions));
    }

    /**
     * Test methods for the {@link Calculator#calculatePcf(Product, List)} method with invalid input.
     * This test case is for when the emissions list is null, the product should be skipped.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_NullEmissions_ShouldThrowInvalidCalculationException() {
        Product product = createValidProduct();
        assertThrows(InvalidCalculationException.class, () -> calculator.calculatePcf(product, null));
    }

    /**
     * Test methods for the {@link Calculator#calculatePcf(Product, List)} method with invalid input.
     * This test case is for when the emissions list is empty, the product should be skipped.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_FlowWithoutMatchingEmissions_ShouldThrowInvalidCalculationException() {
        Product product = createValidProduct();
        List<Emission> emissions = new ArrayList<>();

        assertThrows(InvalidCalculationException.class, () -> calculator.calculatePcf(product, emissions));
    }

    /**
     * Test methods for the {@link Calculator#calculatePcf(Product, List)} method with invalid input.
     * This test case is for when the product system does not contain any processes, the product should be skipped.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_ProductSystemWithoutProcesses_ShouldThrowInvalidCalculationException() {
        Product product = new Product("Test Product", "Country", 1.0, Unit.KG, new ProductSystem(new ArrayList<>()));
        List<Emission> emissions = createValidEmissions();

        assertThrows(InvalidCalculationException.class, () -> calculator.calculatePcf(product, emissions));
    }

    /**
     * Test methods for the {@link Calculator#calculatePcf(Product, List)} method with invalid input.
     * This test case is for when the process does not contain any product flows, the process should be skipped.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_ProcessesWithoutProductFlows_ShouldThrowInvalidCalculationException() {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(ProcessType.RawMaterialProduction, new ArrayList<>()));
        Product product = new Product("Test Product", "Country", 1.0, Unit.KG, new ProductSystem(processes));
        List<Emission> emissions = createValidEmissions();

        assertThrows(InvalidCalculationException.class, () -> calculator.calculatePcf(product, emissions));
    }

    /**
     * Test methods for the {@link Calculator#calculatePcf(Product, List)} method with invalid input.
     * This test case is for when the product flows do not contain any flows, the process should be skipped.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_ProductFlowsWithoutFlows_ShouldThrowInvalidCalculationException() {
        List<ProductFlow> productFlows = new ArrayList<>();
        productFlows.add(new ProductFlow("Test ProductFlow", new ArrayList<>()));
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(ProcessType.RawMaterialProduction, productFlows));
        Product product = new Product("Test Product", "Country", 1.0, Unit.KG, new ProductSystem(processes));
        List<Emission> emissions = createValidEmissions();

        assertThrows(InvalidCalculationException.class, () -> calculator.calculatePcf(product, emissions));
    }

    /**
     * Test methods for the {@link Calculator#calculatePcf(Product, List)} method with invalid input.
     * This test case is for when the flow type is not input, the flow should be skipped.
     * The product should have a pcf of 0.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_FlowTypeNotInput_ShouldSkipFlow() throws InvalidCalculationException {
        Product product = createValidProductWithNonInputFlow();
        List<Emission> emissions = createValidEmissions();

        calculator.calculatePcf(product, emissions);

        assertNotNull(product.getPcf());
        assertEquals(0, product.getPcf());
    }

    /**
     * Test methods for the {@link Calculator#calculatePcf(Product, List)} method with invalid input.
     * @throws InvalidCalculationException
     */
    @Test
    void calculatePcf_ValidCalculations_ShouldReturnExpectedValues() throws InvalidCalculationException {
        Product product = createValidProduct();
        List<Emission> emissions = createValidEmissions();
        emissions.get(0).setEmissionFactor(1.5);

        calculator.calculatePcf(product, emissions);

        double expectedPcf = 0.3 * 1.5;
        assertEquals(expectedPcf, product.getPcf());
    }
}