package codes.bahmet.genetic_simulation.main.model;


import codes.bahmet.genetic_simulation.main.model.cell.Bot;

/**
 * Interface Generation represents generation of bots.
 * Generation can be pure or have mutations.
 */
public interface Generation {
    /**
     * @return generation of bots
     */
    public Bot[] getGeneration();
}
