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
   public int initialAntCount = 200;
   public int colonyCount = 1000;
   public double pheromoneDetoriation = 0.7;
   public double randomFactor = 0.4;
   public double distancePriority = 0.8; 
   
   ///Genetic
   
   public boolean heuristic = false;
   public int crossing = 0;
   public int mutation = 0;
   public int selection = 1;
   public int population = 100;
   public int generations = 100;
   public double crossP = 0.8;
   public double mutationP = 0.01;
           
   
}
