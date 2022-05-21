/**
 * Cute little child class to Part
 * @author Easton Seidel
 */

package com.hermitfeather.ca482_pa_fx;

/**
 * Sub-class for inhouse parts
 */
public class InHouse extends Part {
    // Variables
    private int machineId;

    /**
     * Constructor for the in house part objects.
     * @param id the id for the part object
     * @param name the name for the part object
     * @param price the price for the part object
     * @param stock the stock to set for the part object
     * @param min the min to set for the part object
     * @param max the max for the part object
     * @param machineId the machine id to set for the in house objects
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
