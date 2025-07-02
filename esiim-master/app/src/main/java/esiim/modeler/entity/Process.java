package esiim.modeler.entity;

import java.util.List;

import esiim.modeler.entity.enums.*;

/*
 * This class represents a process in the system. A process is a set of product flows that are related to each other.
 */
public class Process {
    private List<ProductFlow> productFlows;
    private ProcessType processType;
    private double pcf;

    /*
     * Constructor for the Process class.
     * @param processType The type of process
     * @param productFlows The list of product flows that make up the process
     */
    public Process(ProcessType processType, List<ProductFlow> productFlows) {
        this.productFlows = productFlows;
        this.processType = processType;
    }

    /*
     * Returns the list of product flows that make up the process.
     */
    public List<ProductFlow> getProductFlows() {
        return productFlows;
    }

    /*
     * Sets the list of product flows that make up the process.
     * @param productFlows The list of product flows to set
     */
    public void setProductFlows(List<ProductFlow> productFlows) {
        this.productFlows = productFlows;
    }

    /*
     * Returns the type of process.
     */
    public ProcessType getProcessType() {
        return processType;
    }

    /*
     * Sets the type of process.
     * @param processType The process type to set
     */
    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    /*
     * Returns the product carbon footprint of the process.
     */
    public double getPcf() {
        return pcf;
    }

    /*
     * Sets the product carbon footprint of the process.
     * @param pcf The product carbon footprint to set
     */
    public void setPcf(double pcf) {
        this.pcf = pcf;
    }

    /*
     * Adds a product flow to the process.
     * @param productFlow The product flow to add
     */
    public void addProductFlow(ProductFlow productFlow) {
        this.productFlows.add(productFlow);
    }

    /*
     * Removes a product flow from the process.
     * @param productFlow The product flow to remove
     */
    public void removeProductFlow(ProductFlow productFlow) {
        this.productFlows.remove(productFlow);
    }
}