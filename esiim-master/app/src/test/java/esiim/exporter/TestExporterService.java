package esiim.exporter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import esiim.exporter.controller.Exporter;
import esiim.exporter.exception.InvalidProductException;
import esiim.modeler.entity.Flow;
import esiim.modeler.entity.Product;
import esiim.modeler.entity.ProductFlow;
import esiim.modeler.entity.ProductSystem;
import esiim.modeler.entity.enums.Category;
import esiim.modeler.entity.enums.FlowType;
import esiim.modeler.entity.enums.ProcessType;
import esiim.modeler.entity.enums.Tag;
import esiim.modeler.entity.enums.Unit;
import esiim.modeler.entity.Process;

/**
 * Test class for ExporterService.
 */
public class TestExporterService {
    
    private Exporter exporter;
    private Product product;
    private File file;

    /**
     * Set up the test environment.
     * Create an instance of Exporter and a file.
     * Create a mock product.
     */
    @BeforeEach
    void setUp() {
        exporter = new Exporter();
        file = new File("product_export.csv");
        product = createMockProduct();
    }

    /**
     * Tear down the test environment.
     * Delete the file if it exists.
     */
    @AfterEach
    void tearDown() {
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test the exportToCsv method with a valid product.
     * @throws InvalidProductException
     */
    @Test
    void testExportToCsv_ValidProduct() throws InvalidProductException {
        exporter.exportToCsv(product);
        assertTrue(file.exists(), "CSV file should be created");
    }

    /**
     * Test the exportToCsv method with a null product.
     * @throws InvalidProductException
     */
    @Test
    void testExportToCsv_NullProduct() {
        assertThrows(InvalidProductException.class, () -> exporter.exportToCsv(null), "Product is null");
    }

    /**
     * Test the exportToCsv method with a null product system.
     * @throws InvalidProductException
     */
    @Test
    void testExportToCsv_NullProductSystem() {
        product.setProductSystem(null);
        assertThrows(InvalidProductException.class, () -> exporter.exportToCsv(product), "ProductSystem is null");
    }

    /**
     * Test the exportToCsv method with a null process.
     * @throws InvalidProductException
     */
    @Test
    void testExportToCsv_NullProcess() {
        product.getProductSystem().setProcesses(Collections.singletonList(null));
        assertThrows(InvalidProductException.class, () -> exporter.exportToCsv(product), "Process is null");
    }

    /**
     * Test the exportToCsv method with a null product flow.
     * @throws InvalidProductException
     */
    @Test
    void testExportToCsv_NullProductFlow() {
        product.getProductSystem().getProcesses().get(0).setProductFlows(Collections.singletonList(null));
        assertThrows(InvalidProductException.class, () -> exporter.exportToCsv(product), "ProductFlow is null");
    }

    /**
     * Test the exportToCsv method with a null flow.
     * @throws InvalidProductException
     */
    @Test
    void testExportToCsv_NullFlow() {
        product.getProductSystem().getProcesses().get(0).getProductFlows().get(0).setFlows(Collections.singletonList(null));
        assertThrows(InvalidProductException.class, () -> exporter.exportToCsv(product), "Flow is null");
    }

    /**
     * Create a mock product.
     * @return the mock product
     */
    private Product createMockProduct() {
        ProductSystem productSystem = new ProductSystem(new ArrayList<>());
        Product product = new Product("Sample Product", "Sample Country", 10.0, Unit.KG, productSystem);
    
        Flow flow = new Flow(
            "Sample Flow", Unit.KG, Category.RawMaterial, 500.0, Tag.Virgin, FlowType.Input, "Sample Country"
        );
        ProductFlow productFlow = new ProductFlow("Sample Product Flow", new ArrayList<>());
        
        Process process = new Process(ProcessType.Manufacturing, new ArrayList<>());
        
        productFlow.addFlow(flow);
        process.addProductFlow(productFlow);
        productSystem.addProcess(process);
    
        return product;
    }
}