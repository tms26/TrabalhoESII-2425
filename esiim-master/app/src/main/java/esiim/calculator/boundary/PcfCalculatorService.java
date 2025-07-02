package esiim.calculator.boundary;

import java.util.List;
import esiim.modeler.entity.Product;
import esiim.calculator.exception.InvalidCalculationException;
import esiim.modeler.entity.Emission;

/**
 * PcfCalculatorService interface.
 */
public interface PcfCalculatorService{
    public void calculatePcf(Product product, List<Emission> emissions) throws InvalidCalculationException;   
}