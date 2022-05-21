/**
 * Outsourced child class to Part
 *
 * @author Easton Seidel
 */
package com.hermitfeather.ca482_pa_fx;

/**
 * Sub-class for outsourced parts
 */
public class Outsourced extends Part{
    // Variables
    private String companyName;

    /**
     * Constructor for the in house part objects.
     * @param id the id for the part object
     * @param name the name for the part object
     * @param price the price for the part object
     * @param stock the stock to set for the part object
     * @param min the min to set for the part object
     * @param max the max for the part object
     * @param companyName the company name to set for the outsourced parts
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName sets the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
