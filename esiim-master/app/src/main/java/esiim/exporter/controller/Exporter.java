package esiim.exporter.controller;

import java.io.FileWriter;

import esiim.exporter.boundary.ExporterService;
import esiim.exporter.exception.InvalidProductException;
import esiim.modeler.entity.Flow;
import esiim.modeler.entity.Process;
import esiim.modeler.entity.Product;
import esiim.modeler.entity.ProductFlow;
import esiim.modeler.entity.ProductSystem;

/**
 * Exporter service implementation.
 */
public class Exporter implements ExporterService {

    /**
     * Exports the product data to a CSV file.
     * @param product the product
     * @throws InvalidProductException if an error occurs in the export
     */
    public void exportToCsv(Product product) throws InvalidProductException {
        if (product == null) {
            throw new InvalidProductException("Product is null");
        }

        ProductSystem productSystem = product.getProductSystem();
        if (productSystem == null) {
            throw new InvalidProductException("ProductSystem is null");
        }

        String fileName = "product_export.csv";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Product\n");
            writer.append("Name: " + product.getName()).append(",")
                  .append("Country: " + product.getCountry()).append(",")
                  .append("Weight: " + String.valueOf(product.getWeight()))
                  .append(product.getUnit() != null ? " " + product.getUnit().toString() : "").append(",")
                  .append("Total PCF: " + String.valueOf(product.getPcf())).append("\n\n");

            for (Process process : productSystem.getProcesses()) {
                if (process == null) {
                    throw new InvalidProductException("Process is null");
                }

                double processPcf = process.getPcf();
                writer.append("Process\n");
                writer.append(process.getProcessType() != null ? process.getProcessType().toString() : "").append(",")
                      .append(String.valueOf(processPcf)).append("\n\n");

                for (ProductFlow productFlow : process.getProductFlows()) {
                    if (productFlow == null) {
                        throw new InvalidProductException("ProductFlow is null");
                    }

                    writer.append("Product Flow\n");
                    writer.append(productFlow.getName()).append(",")
                          .append(String.valueOf(productFlow.getPcf())).append("\n\n");

                    writer.append("Flows\n");
                    for (Flow flow : productFlow.getFlows()) {
                        if (flow == null) {
                            throw new InvalidProductException("Flow is null");
                        }

                        writer.append(flow.getName()).append(",")
                              .append(flow.getUnit() != null ? flow.getUnit().toString() : "").append(",")
                              .append(flow.getCategory() != null ? flow.getCategory().toString() : "").append(",")
                              .append(String.valueOf(flow.getQuantity())).append(",")
                              .append(flow.getTag() != null ? flow.getTag().toString() : "").append(",")
                              .append(flow.getType() != null ? flow.getType().toString() : "").append(",")
                              .append(flow.getOriginCountry() != null ? flow.getOriginCountry() : "").append("\n");
                    }
                    writer.append("\n");
                }
                writer.append("\n");
            }

            System.out.println("Product data exported successfully to " + fileName);
        } catch (Exception e) {
            throw new InvalidProductException("Error exporting product data: " + e.getMessage());
        }
    }
}