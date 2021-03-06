/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinder;

/**
 *
 * @author Idavor
 */
public class Options {

    //AntSwarm 
    public  int initialAntCount = 200;
    public  int colonyCount = 1000;
    public  double pheromoneDetoriation = 0.7;
    public  double randomFactor = 0.4;
    public  double distancePriority = 0.1;

   ///Genetic 
    public  int nPopulation = 100;
    public  int nGeneration = 100;
    
    public  int crossingType = 0;
    public  int chromosomeType = 0;
    public  int selectionType = 0;
    
    public  boolean heuristic = false;
    public boolean pathFixing = true;
    
    
    public  double crossP = 0.8;
    public  double mutationP = 0.01;
    public double eliminationP = 0.20;
}