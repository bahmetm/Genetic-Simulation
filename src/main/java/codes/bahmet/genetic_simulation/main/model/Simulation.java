package codes.bahmet.genetic_simulation.main.model;

/**
 * Interface Simulation represents genetic simulation.
 */
public interface Simulation {

    /**
     * Performs all operations of one step of simulation.
     * @return current condition of field
     */
    public Field nextStep();
}
