package esiim.modeler.entity;

import java.util.List;

/*
 * This class represents a product flow in the system.
 */
public class ProductFlow {
    private String name;
    private List<Flow> flows;
    private double pcf;

    /*
     * Constructor for the ProductFlow class.
     * @param name The name of the product flow
     * @param flows The list of flows that make up the product flow
     */
    public ProductFlow(String name, List<Flow> flows) {
        this.name = name;
        this.flows = flows;
    }

    /*
     * Returns the name of the product flow.
     */
    public String getName() {
        return name;
    }

    /*
     * Sets the name of the product flow.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Returns the list of flows that make up the product flow.
     */
    public List<Flow> getFlows() {
        return flows;
    }

    /*
     * Sets the list of flows that make up the product flow.
     * @param flows The list of flows to set
     */
    public void setFlows(List<Flow> flows) {
        this.flows = flows;
    }

    /*
     * Returns the product carbon footprint of the product flow.
     */
    public double getPcf() {
        return pcf;
    }

    /*
     * Sets the product carbon footprint of the product flow.
     * @param pcf The product carbon footprint to set
     */
    public void setPcf(double pcf) {
        this.pcf = pcf;
    }

    /*
     * Adds a flow to the product flow.
     * @param flow The flow to add
     */
    public void addFlow(Flow flow) {
        this.flows.add(flow);
    }

    /*
     * Removes a flow from the product flow.
     * @param flow The flow to remove
     */
    public void removeFlow(Flow flow) {
        this.flows.remove(flow);
    }
}