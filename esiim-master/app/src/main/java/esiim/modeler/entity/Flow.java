package esiim.modeler.entity;

import esiim.modeler.entity.enums.*;

/*
 * This class represents a flow in the system. A flow is a material or energy that is used in a process.
 */
public class Flow {
    private String name;
    private Unit unit;
    private Category category;
    private double quantity;
    private Tag tag;
    private FlowType type;
    private String originCountry;

    /*
     * Constructor for the Flow class.
     * @param name The name of the flow
     * @param unit The unit of measurement for the flow
     * @param category The category of the flow
     * @param quantity The quantity of the flow
     * @param tag The tag of the flow
     * @param type The type of flow
     * @param originCountry The country of origin of the flow
     */
    public Flow(String name, Unit unit, Category category, double quantity, Tag tag, FlowType type, String originCountry) {
        this.name = name;
        this.unit = unit;
        this.category = category;
        this.quantity = quantity;
        this.tag = tag;
        this.type = type;
        this.originCountry = originCountry;
    }

    /*
     * Constructor for the Flow class.
     * @param name The name of the flow
     * @param unit The unit of measurement for the flow
     * @param category The category of the flow
     * @param quantity The quantity of the flow
     * @param type The type of flow
     * @param originCountry The country of origin of the flow
     */
    public Flow(String name, Unit unit, Category category, double quantity, FlowType type, String originCountry) {
        this.name = name;
        this.unit = unit;
        this.category = category;
        this.quantity = quantity;
        this.type = type;
        this.originCountry = originCountry;
    }

    /*
     * Returns the name of the flow.
     */
    public String getName() {
        return name;
    }

    /*
     * Sets the name of the flow.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Returns the unit of measurement for the flow.
     */
    public Unit getUnit() {
        return unit;
    }

    /*
     * Sets the unit of measurement for the flow.
     * @param unit The unit to set
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /* 
     * Returns the category of the flow.
     */
    public Category getCategory() {
        return category;
    }

    /*
     * Sets the category of the flow.
     * @param category The category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /*
     * Returns the quantity of the flow.
     */
    public double getQuantity() {
        return quantity;
    }

    /*
     * Sets the quantity of the flow.
     * @param quantity The quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /*
     * Returns the tag of the flow.
     */
    public Tag getTag() {
        return tag;
    }

    /*
     * Sets the tag of the flow.
     * @param tag The tag to set
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /*
     * Returns the type of flow.
     */
    public FlowType getType() {
        return type;
    }

    /*
     * Sets the type of flow.
     * @param type The type to set
     */
    public void setType(FlowType type) {
        this.type = type;
    }

    /*
     * Returns the country of origin of the flow.
     */
    public String getOriginCountry() {
        return originCountry;
    }

    /*
     * Sets the country of origin of the flow.
     * @param originCountry The country to set
     */
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
}