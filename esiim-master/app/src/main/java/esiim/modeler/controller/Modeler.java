package esiim.modeler.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import esiim.modeler.entity.Flow;
import esiim.modeler.entity.Process;
import esiim.modeler.entity.Product;
import esiim.modeler.entity.ProductFlow;
import esiim.modeler.entity.ProductSystem;
import esiim.modeler.entity.enums.Category;
import esiim.modeler.entity.enums.FlowType;
import esiim.modeler.entity.enums.ProcessType;
import esiim.modeler.entity.enums.Tag;
import esiim.modeler.entity.enums.Unit;
import esiim.modeler.exception.InvalidParseDataException;
import esiim.parser.entity.ParsedData;
import esiim.parser.entity.ParsedEmissionsData;
import esiim.modeler.boundary.ModelerService;
import esiim.modeler.entity.Emission;
import esiim.modeler.exception.InvalidParsedEmissionsDataException;

/**
 * Modeler class.
 */
public class Modeler implements ModelerService {
    private Product product;
    private List<Emission> emissionsList;

    /**
     * Models the product from the parsed data.
     * 
     * @param parsedData the parsed data
     * @return the product
     * @throws InvalidParseDataException if the parsed data is invalid
     */
    public Product modelProduct(ParsedData parsedData) throws InvalidParseDataException {
        if (parsedData == null) {
            throw new InvalidParseDataException("ParsedData is null");
        }

        String productString = parsedData.getProductString();
        if (productString == null || productString.isEmpty()) {
            throw new InvalidParseDataException("ParsedData productString is null or empty");
        }

        String[] lines = productString.split("\n");
        String[] headers = lines[0].split(",");

        String productName = null;
        String productCountry = null;
        double productWeight = 0;
        Unit productUnit = null;
        ProductSystem productSystem = new ProductSystem(new ArrayList<>());

        Map<ProcessType, Process> processMap = new HashMap<>();

        for (int i = 1; i < lines.length; i++) {
            String[] values = lines[i].split(",");

            if (productName == null) {
                productName = values[0];
                productCountry = values[1];
                productWeight = Double.parseDouble(values[2]);
                
                try {
                    productUnit = Unit.valueOf(values[3]);
                } catch (IllegalArgumentException e) {
                    throw new InvalidParseDataException("Invalid product unit value: " + values[3]);
                }
            }

            ProcessType processType;
            try {
                processType = ProcessType.valueOf(values[4]);
            } catch (IllegalArgumentException e) {
                throw new InvalidParseDataException("Invalid process type value: " + values[4]);
            }

            String productFlowName = values[5];
            String flowName = values[6];
            Unit flowUnit;
            try {
                flowUnit = Unit.valueOf(values[7]);
            } catch (IllegalArgumentException e) {
                throw new InvalidParseDataException("Invalid flow unit value: " + values[7]);
            }

            Category flowCategory;
            try {
                flowCategory = Category.valueOf(values[8]);
            } catch (IllegalArgumentException e) {
                throw new InvalidParseDataException("Invalid flow category value: " + values[8]);
            }

            double flowQuantity;
            flowQuantity = Double.parseDouble(values[9]);

            Tag flowTag;
            try {
                flowTag = Tag.valueOf(values[10]);
            } catch (IllegalArgumentException e) {
                throw new InvalidParseDataException("Invalid flow tag value: " + values[10]);
            }

            FlowType flowType;
            try {
                flowType = FlowType.valueOf(values[11]);
            } catch (IllegalArgumentException e) {
                throw new InvalidParseDataException("Invalid flow type value: " + values[11]);
            }

            String flowOriginCountry = values[12];

            validateUnitForCategory(flowUnit, flowCategory, () -> new InvalidParseDataException("Invalid unit " + flowUnit + " for category " + flowCategory));

            Flow flow = new Flow(flowName, flowUnit, flowCategory, flowQuantity, flowTag, flowType, flowOriginCountry);
            ProductFlow productFlow = new ProductFlow(productFlowName, new ArrayList<>());
            productFlow.addFlow(flow);

            Process process = processMap.get(processType);
            if (process == null) {
                process = new Process(processType, new ArrayList<>());
                processMap.put(processType, process);
                productSystem.addProcess(process);
            }
            process.addProductFlow(productFlow);
        }

        product = new Product(productName, productCountry, productWeight, productUnit, productSystem);
        return product;
    }

    /**
     * Models the emissions from the parsed emissions data.
     * @param parsedEmissionsData
     * @return the emissions
     * @throws InvalidParsedEmissionsDataException
     */
    public List<Emission> modelEmission(ParsedEmissionsData parsedEmissionsData) throws InvalidParsedEmissionsDataException {
        if (parsedEmissionsData == null) {
            throw new InvalidParsedEmissionsDataException("ParsedEmissionsData is null");
        }
    
        String emissionsString = parsedEmissionsData.getEmissionsString();
        if (emissionsString == null || emissionsString.isEmpty()) {
            throw new InvalidParsedEmissionsDataException("ParsedEmissionsData emissionsString is null or empty");
        }
    
        String[] lines = emissionsString.split("\n");
        String[] headers = lines[0].split(",");
    
        emissionsList = new ArrayList<>();
    
        for (int i = 1; i < lines.length; i++) {
            String[] values = lines[i].split(",");
    
            String name = values[0];
            Category category;
            try {
                category = Category.valueOf(values[1]);
            } catch (IllegalArgumentException e) {
                throw new InvalidParsedEmissionsDataException("Invalid emissions category value: " + values[1]);
            }
    
            Unit unit;
            try {
                unit = Unit.valueOf(values[2]);
            } catch (IllegalArgumentException e) {
                throw new InvalidParsedEmissionsDataException("Invalid emissions unit value: " + values[2]);
            }
    
            double quantity;
            quantity = Double.parseDouble(values[3]);
    
            double emissionFactor;
            emissionFactor = Double.parseDouble(values[4]);
    
            validateUnitForCategory(unit, category, () -> new InvalidParsedEmissionsDataException("Invalid unit " + unit + " for category " + category));

            Emission emission = new Emission(name, category, unit, quantity, emissionFactor);
            emissionsList.add(emission);
        }
    
        return emissionsList;
    }

    /**
     * Validates the unit for the category.
     * 
     * @param unit the unit
     * @param category the category
     * @param exceptionSupplier the supplier for the exception to be thrown
     * @throws Exception if the unit is invalid for the category
     */
    private <T extends Exception> void validateUnitForCategory(Unit unit, Category category, Supplier<T> exceptionSupplier) throws T {
        switch (category) {
            case Water:
                if (unit != Unit.Liters) {
                    throw exceptionSupplier.get();
                }
                break;
            case RawMaterial:
                if (unit == Unit.Joules || unit == Unit.Watts || unit == Unit.Kilowatts || unit == Unit.KgCO2) {
                    throw exceptionSupplier.get();
                }
                break;
            case WindEnergy:
            case HydroPowerEnergy:
            case SolarEnergy:
            case NaturalGasEnergy:
            case BiomassEnergy:
            case RadioactiveEnergy:
                if (unit != Unit.Joules && unit != Unit.Watts && unit != Unit.Kilowatts) {
                    throw exceptionSupplier.get();
                }
                break;
            case AirEmission:
            case WaterEmission:
            case GroundEmission:
                if (unit != Unit.KgCO2) {
                    throw exceptionSupplier.get();
                }
                break;
            case LandOccupation:
                if (unit != Unit.SquareMeters && unit != Unit.SquareKilometers) {
                    throw exceptionSupplier.get();
                }
                break;
            case LandTransport:
            case AirTransport:
            case SeaTransport:
                if (unit != Unit.Kilometers) {
                    throw exceptionSupplier.get();
                }
                break;
            default:
                throw exceptionSupplier.get();
        }
    }
}