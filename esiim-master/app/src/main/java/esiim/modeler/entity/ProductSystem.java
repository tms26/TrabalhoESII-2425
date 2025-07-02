package esiim.modeler.entity;

import java.util.List;

/*
 * This class represents a product system, which is a collection of processes.
 */
public class ProductSystem {
    private List<Process> processes;

    /*
     * Constructor for the ProductSystem class.
     * @param processes The list of processes that make up the product system.
     */
    public ProductSystem(List<Process> processes) {
        this.processes = processes;
    }

    /*
     * Returns the list of processes that make up the product system.
     */
    public List<Process> getProcesses() {
        return processes;
    }

    /*
     * Sets the list of processes that make up the product system.
     * @param processes The list of processes to set
     */
    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    /*
     * Adds a process to the product system.
     */
    public void addProcess(Process process) {
        this.processes.add(process);
    }

    /*
     * Removes a process from the product system.
     * @param process The process to remove
     */
    public void removeProcess(Process process) {
        this.processes.remove(process);
    }
}