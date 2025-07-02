package esiim.exporter.boundary;

import esiim.modeler.entity.Product;
import esiim.exporter.exception.InvalidProductException;

/**
 * Exporter csv service interface.
 */
public interface ExporterCsvService {
    public void exportToCsv(Product product) throws InvalidProductException;
}
