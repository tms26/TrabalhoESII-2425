package esiim.calculator.controller;

import java.util.ArrayList;
import java.util.List;

import esiim.modeler.entity.Process;
import esiim.calculator.boundary.CalculatorService;
import esiim.calculator.exception.InvalidCalculationException;
import esiim.modeler.entity.Emission;
import esiim.modeler.entity.Flow;
import esiim.modeler.entity.Product;
import esiim.modeler.entity.ProductFlow;
import esiim.modeler.entity.ProductSystem;
import esiim.modeler.entity.enums.Category;
import esiim.modeler.entity.enums.FlowType;
import esiim.modeler.entity.enums.Unit;

/**
 * Calculator class that implements the CalculatorService interface.
 */
public class Calculator implements CalculatorService {

    /**
     * Calculate the Product Carbon Footprint (PCF) of a product.
     * @param product the product
     * @param emissions the emissions
     * @throws InvalidCalculationException if an error occurs in the calculation
     */
    public void calculatePcf(Product product, List<Emission> emissions) throws InvalidCalculationException {
        if (product == null) {
            throw new InvalidCalculationException("Product cannot be null.");
        }
        if (emissions == null) {
            throw new InvalidCalculationException("List of emissions cannot be null.");
        }

        ProductSystem productSystem = product.getProductSystem();
        if (productSystem == null || productSystem.getProcesses() == null || productSystem.getProcesses().isEmpty()) {
            throw new InvalidCalculationException("Product system must contain processes.");
        }

        double totalEmissions = 0.0;

        for (Process process : productSystem.getProcesses()) {
            if (process.getProductFlows() == null || process.getProductFlows().isEmpty()) {
                throw new InvalidCalculationException("Process must contain product flows.");
            }

            double processEmissions = 0.0;
            for (ProductFlow productFlow : process.getProductFlows()) {
                if (productFlow.getFlows() == null || productFlow.getFlows().isEmpty()) {
                    throw new InvalidCalculationException("Product flow must contain flows.");
                }

                double productFlowEmissions = 0.0;
                List<Flow> newFlows = new ArrayList<>();
                for (Flow inputFlow : productFlow.getFlows()) {
                    if (inputFlow.getType() != FlowType.Input) {
                        continue;
                    }

                    Emission matchingEmission = findMatchingEmission(inputFlow, emissions);
                    if (matchingEmission != null) {
                        double emissionQuantity = inputFlow.getQuantity() * matchingEmission.getEmissionFactor();
                        productFlowEmissions += emissionQuantity;
                        Flow outputFlow = new Flow(
                            inputFlow.getName() + " Emission",
                            Unit.KgCO2,
                            Category.AirEmission,
                            emissionQuantity,
                            FlowType.Output,
                            inputFlow.getOriginCountry()
                        );
                        newFlows.add(outputFlow);
                    } else {
                        throw new InvalidCalculationException("No matching emission found for input flow: " + inputFlow.getName());
                    }
                }
                productFlow.getFlows().addAll(newFlows);
                productFlow.setPcf(productFlowEmissions);
                processEmissions += productFlowEmissions;
            }
            process.setPcf(processEmissions);
            totalEmissions += processEmissions;
        }

        product.setPcf(totalEmissions);
    }

    /**
     * Find a matching emission for a given input flow.
     * @param inputFlow
     * @param emissions
     * @return the matching emission
     * @throws InvalidCalculationException if no matching emission is found
     */
    private Emission findMatchingEmission(Flow inputFlow, List<Emission> emissions) throws InvalidCalculationException {
        for (Emission emission : emissions) {
            if (inputFlow.getCategory() == Category.RawMaterial || inputFlow.getCategory() == Category.LandOccupation) {
                if (emission.getName().equals(inputFlow.getName())) {
                    return emission;
                }
            } else if (inputFlow.getCategory() == Category.WindEnergy ||
                       inputFlow.getCategory() == Category.HydroPowerEnergy ||
                       inputFlow.getCategory() == Category.SolarEnergy ||
                       inputFlow.getCategory() == Category.NaturalGasEnergy ||
                       inputFlow.getCategory() == Category.BiomassEnergy ||
                       inputFlow.getCategory() == Category.RadioactiveEnergy ||
                       inputFlow.getCategory() == Category.LandTransport ||
                       inputFlow.getCategory() == Category.AirTransport ||
                       inputFlow.getCategory() == Category.SeaTransport) {
                if (emission.getCategory() == inputFlow.getCategory() &&
                    emission.getUnit() == inputFlow.getUnit()) {
                    return emission;
                }
            }
        }
        return null;
    }
}