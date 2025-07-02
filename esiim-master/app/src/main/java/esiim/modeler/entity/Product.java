package esiim.modeler.entity;

import esiim.modeler.entity.enums.*;

/*
 * This class represents a product in the system.
 */
public class Product {
    private String name;
    private String country;
    private double weight;
    private Unit unit;
    private ProductSystem productSystem;
    private double pcf;

    /*
     * Constructor for the Product class.
     * @param name The name of the product
     * @param country The country of origin of the product
     * @param weight The weight of the product
     * @param unit The unit of measurement for the product
     * @param productSystem The product system that the product is a part of
     */
    public Product(String name, String country, double weight, Unit unit, ProductSystem productSystem) {
        this.name = name;
        this.country = country;
        this.weight = weight;
        this.unit = unit;
        this.productSystem = productSystem;
    }

    /*
     * Returns the name of the product.
     */
    public String getName() {
        return name;
    }

    /*
     * Sets the name of the product.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Returns the country of origin of the product.
     */
    public String getCountry() {
        return country;
    }

    /*
     * Sets the country of origin of the product.
     * @param country The country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /*
     * Returns the weight of the product.
     */
    public double getWeight() {
        return weight;
    }

    /*
     * Sets the weight of the product.
     * @param weight The weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /*
     * Returns the unit of measurement for the product.
     */
    public Unit getUnit() {
        return unit;
    }

    /*
     * Sets the unit of measurement for the product.
     * @param unit The unit to set
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /*
     * Returns the product carbon footprint of the product.
     */
    public double getPcf() {
        return pcf;
    }

    /*
     * Sets the product carbon footprint of the product.
     * @param pcf The product carbon footprint to set
     */
    public void setPcf(double pcf) {
        this.pcf = pcf;
    }

    /*
     * Returns the product system that the product is a part of.
     */
    public ProductSystem getProductSystem() {
        return productSystem;
    }

    /*
     * Sets the product system that the product is a part of.
     * @param productSystem The product system to set
     */
    public void setProductSystem(ProductSystem productSystem) {
        this.productSystem = productSystem;
    }
}