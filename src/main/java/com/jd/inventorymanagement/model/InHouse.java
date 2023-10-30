package com.jd.inventorymanagement.model;
/**
 * This class object extends part with a machine ID variable.
 * @author James Dunaway
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Class constructor (using part class Super())
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
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
