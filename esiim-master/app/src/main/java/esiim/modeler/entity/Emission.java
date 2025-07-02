package esiim.modeler.entity;

import esiim.modeler.entity.enums.Category;
import esiim.modeler.entity.enums.Unit;

/**
 * Emission entity.
 */
public class Emission {
    private String name;
    private Category category;
    private Unit unit;
    private double quantity;
    private double emissionFactor;

    /**
     * Constructor.
     * 
     * @param name Emission name.
     * @param category Emission category.
     * @param unit Emission unit.
     * @param quantity Emission quantity.
     * @param emissionFactor Emission factor.
     */
    public Emission(String name, Category category, Unit unit, double quantity, double emissionFactor) {
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.quantity = quantity;
        this.emissionFactor = emissionFactor;
    }

    /**
     * Get emission name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set emission name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get emission category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Set emission category.
     * @param category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Get emission unit.
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Set emission unit.
     * @param unit
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Get emission quantity.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Set emission quantity.
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Get emission factor.
     */
    public double getEmissionFactor() {
        return emissionFactor;
    }

    /**
     * Set emission factor.
     * @param emissionFactor
     */
    public void setEmissionFactor(double emissionFactor) {
        this.emissionFactor = emissionFactor;
    }
}