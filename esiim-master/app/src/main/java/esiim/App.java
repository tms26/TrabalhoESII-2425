package esiim;

import java.util.List;

import esiim.modeler.controller.Modeler;
import esiim.modeler.entity.Emission;
import esiim.modeler.entity.Flow;
import esiim.modeler.entity.Process;
import esiim.modeler.entity.Product;
import esiim.modeler.entity.ProductFlow;
import esiim.modeler.entity.ProductSystem;
import esiim.modeler.exception.InvalidParseDataException;
import esiim.modeler.exception.InvalidParsedEmissionsDataException;
import esiim.modeler.boundary.ModelerService;

import esiim.exporter.boundary.ExporterService;
import esiim.exporter.controller.Exporter;
import esiim.exporter.exception.InvalidProductException;

import esiim.calculator.boundary.CalculatorService;
import esiim.calculator.controller.Calculator;
import esiim.calculator.exception.InvalidCalculationException;

import esiim.parser.boundary.ParserService;
import esiim.parser.controller.Parser;
import esiim.parser.entity.ParsedData;
import esiim.parser.entity.ParsedEmissionsData;
import esiim.parser.exception.InvalidDataException;

/*
 * Class to run the application
 */
public class App {

    /**
     * Main method to run the application
     * @param args the command line arguments
     * @throws InvalidDataException
     * @throws InvalidParseDataException
     * @throws InvalidParsedEmissionsDataException
     * @throws InvalidCalculationException
     * @throws InvalidProductException
     */
    public static void main(String[] args) throws InvalidDataException, InvalidParseDataException, InvalidParsedEmissionsDataException, InvalidCalculationException, InvalidProductException {
        String filePath = "app/src/main/resources/shoe_production.csv";
        String emissionsFilePath = "app/src/main/resources/emissions.csv";

        try {
            ParserService parser = new Parser();
            ParsedData parsedData = parser.parseCsv(filePath);
            ParsedEmissionsData parsedEmissionsData = parser.parseEmissionsCsv(emissionsFilePath);

            ModelerService modeler = new Modeler();
            Product product = modeler.modelProduct(parsedData);
            List<Emission> emissionsList = modeler.modelEmission(parsedEmissionsData);

            CalculatorService calculator = new Calculator();
            calculator.calculatePcf(product, emissionsList);

            ExporterService exporter = new Exporter();
            exporter.exportToCsv(product);

            //printProductString(parsedData);
            //printEmissionsData(parsedEmissionsData);
            
            //printProductDetails(product);
            //printEmissionsDetails(emissionsList);
        } catch (InvalidDataException | InvalidParseDataException | InvalidParsedEmissionsDataException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Print the product string
     * @param parsedData
     */
    private static void printProductString(ParsedData parsedData) {
        System.out.println("Product String:");
        System.out.println(parsedData.getProductString());
    }

    /*
     * Print the emissions data
     * @param parsedEmissionsData
     */
    private static void printEmissionsData(ParsedEmissionsData parsedEmissionsData) {
        System.out.println("Emissions Data:");
        System.out.println(parsedEmissionsData.getEmissionsString());
    }

    /*
     * Print the product details
     * @param product
     */
    private static void printProductDetails(Product product) {
        System.out.println("Product Name: " + product.getName());
        System.out.println("Country: " + product.getCountry());
        System.out.println("Weight: " + product.getWeight());
        System.out.println("Unit: " + product.getUnit());
        System.out.println("PCF: " + product.getPcf());

        ProductSystem productSystem = product.getProductSystem();
        for (Process process : productSystem.getProcesses()) {
            System.out.println("Process Type: " + process.getProcessType());
            System.out.println("Process PCF: " + process.getPcf());
            for (ProductFlow productFlow : process.getProductFlows()) {
                System.out.println("  Product Flow Name: " + productFlow.getName());
                System.out.println("  Product Flow PCF: " + productFlow.getPcf());
                for (Flow flow : productFlow.getFlows()) {
                    System.out.println("    Flow Name: " + flow.getName());
                    System.out.println("    Unit: " + flow.getUnit());
                    System.out.println("    Category: " + flow.getCategory());
                    System.out.println("    Quantity: " + flow.getQuantity());
                    System.out.println("    Tag: " + flow.getTag());
                    System.out.println("    Type: " + flow.getType());
                    System.out.println("    Origin Country: " + flow.getOriginCountry());
                }
            }
        }
    }

    /**
     * Print the emissions
     * @param emissionsList
     */
    private static void printEmissionsDetails(List<Emission> emissionsList) {
        System.out.println("\nEmissions:");
        for (Emission emission : emissionsList) {
            System.out.println("Name: " + emission.getName());
            System.out.println("Category: " + emission.getCategory());
            System.out.println("Unit: " + emission.getUnit());
            System.out.println("Quantity: " + emission.getQuantity());
            System.out.println("Emission Factor: " + emission.getEmissionFactor());
            System.out.println();
        }
    }
}
