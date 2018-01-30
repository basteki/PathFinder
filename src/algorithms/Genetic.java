/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import graphHandler.domain.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pathfinder.MainGUI;

/**
 *
 * @author Idavor
 */
public class Genetic {

    public List<Integer> findWay(int start, int finish, Graph graph, int populationCount, int nGeneration, boolean heur, int crossing, int mutation, int selection, double crossP, double mutationP) {
        List<Integer> path = new ArrayList<>();

        List<List<Integer>> chromosomes = new ArrayList<>();
        List<Double> fitness = new ArrayList<>();
        List<Double> heurFitness = new ArrayList<>();

        for (int p = 0; p < populationCount; p++) {
            List<Integer> chrom = randomizeChromosome(start, finish, graph, heur, heurFitness);

            chromosomes.add(chrom);
            fitness.add(MainGUI.measurePath(chrom));

            heurFitness.add(MainGUI.measurePath(chrom));

            ///heurFitness.add(Math.hypot(graph.verticesList.get(i).x - graph.verticesList.get(start).x,
            //        graph.verticesList.get(i).y - graph.verticesList.get(start).y)
            //);
            //System.out.println("looop1");
        }

        for (int i = 0; i < nGeneration; i++) {
            // System.out.println("looop2");
            List<List<Integer>> newChromosomes = new ArrayList<>();

            newChromosomes = selectNextPop(graph, selection, chromosomes, fitness);

            newChromosomes = crossing(graph, crossing, chromosomes, fitness);

            chromosomes = newChromosomes;
        }
        double lowestWeight = Double.POSITIVE_INFINITY;
        for (int i = 0; i < populationCount; i++) {
            if (lowestWeight > MainGUI.measurePath(chromosomes.get(i))) {
                lowestWeight = MainGUI.measurePath(chromosomes.get(i));
                path = chromosomes.get(i);
            }
        }

        return path;
    }

    private List<Integer> randomizeChromosome(int start, int finish, Graph graph, boolean heur, List<Double> heurFitness) {
        List<Integer> chromosome = new ArrayList<>();
        Random generator = new Random();
        heur = false;
        chromosome.add(start);

        int previousStep = start;
        int currentStep = start;
        int nextStep = start;

        List<Integer> connected = graph.verticesList.get(currentStep).connected;

        while (currentStep != finish) {
            //System.out.println("looopRandom1");

            if (!heur) {
                int randomConnected = generator.nextInt(connected.size());
                nextStep = connected.get(randomConnected);
                while (nextStep == previousStep) {
                    //  System.out.println("looopRandom1a " + previousStep + " " + currentStep + " " + nextStep + " " + connected.size() );

                    randomConnected = generator.nextInt(connected.size());
                    //System.out.println(randomConnected);
                    nextStep = connected.get(randomConnected);

                }
            } else if (heur) {
                List<Double> maxRoulette = new ArrayList<>();;
                List<Integer> conRoulette = new ArrayList<>();

                for (int i = 0; i < connected.size(); i++) {
                    if (connected.get(i) == currentStep || connected.get(i) == previousStep) {
                        int activeConnectedId = graph.verticesList.get(currentStep).connected.get(i);
                        double activeConnectionWeight = 0.0;

                        for (int j = 0; j < graph.edgesList.size(); j++) {
                            if (graph.edgesList.get(j).getA() == activeConnectedId
                                    && graph.edgesList.get(j).getB() == currentStep) {
                                activeConnectionWeight = graph.edgesList.get(j).getWeight();
                                break;
                            }
                            if (graph.edgesList.get(j).getB() == activeConnectedId
                                    && graph.edgesList.get(j).getA() == currentStep) {
                                activeConnectionWeight = graph.edgesList.get(j).getWeight();
                                break;
                            }

                        }
                        conRoulette.add(activeConnectedId);
                        if (maxRoulette.size() == 0) {
                            maxRoulette.add(
                                    (activeConnectionWeight + heurFitness.get(activeConnectedId))
                                    / Math.pow((activeConnectionWeight + heurFitness.get(activeConnectedId)), 2)
                            );
                        } else {
                            maxRoulette.add(
                                    (activeConnectionWeight + heurFitness.get(activeConnectedId))
                                    + maxRoulette.get(maxRoulette.size() - 1)
                                    / (Math.pow((activeConnectionWeight + heurFitness.get(activeConnectedId))
                                            + maxRoulette.get(maxRoulette.size() - 1), 2))
                            );
                        }

                    }
                }
                //  System.out.println(heur);
//              //  Double randD = generator.nextDouble() * maxRoulette.get(maxRoulette.size() - 1);
//                for (int i = 0; i < maxRoulette.size(); i++) {
//                    if (randD <= maxRoulette.get(i)) {
//                        nextStep = conRoulette.get(i);
                // }
            }
            previousStep = currentStep;
            currentStep = nextStep;

            connected = graph.verticesList.get(currentStep).connected;

            chromosome.add(currentStep);
        }

        // }
        return chromosome;
    }

    private List<List<Integer>> selectNextPop(Graph graph, int selectionType, List<List<Integer>> chromosomes, List<Double> fitness) {
        List<List<Integer>> chosenChromosomes = chromosomes;
        System.out.println(selectionType);
        if (selectionType == 0) { // tournee
            int cap = (int) (fitness.size() * 0.85);
            double[] survivors = new double[cap];
            for (int i = 0; i < cap; i++) {
                survivors[i] = Double.POSITIVE_INFINITY;
            }
            for (int i = 0; i < fitness.size(); i++) {
                double current = fitness.get(i);

                for (int j = 0; j < cap; j++) {
                    if (current < survivors[j]) {
                        double tmp = survivors[j];
                        survivors[j] = current;
                        tmp = current;
                    }
                }
            }

            for (int i = fitness.size() - 1; i > 0; i--) {
                if (fitness.get(i) > survivors[survivors.length - 1]) {
                    //System.out.println("Size " + chosenChromosomes.size());
                    chosenChromosomes.remove(i);
                }
            }
            int i = 0;
            while (chosenChromosomes.size() < chromosomes.size()) {
                chosenChromosomes.add(chosenChromosomes.get(i));
                i++;
                // System.out.println("looopSelect");
            }

        } else {                  // roulette
            List<Double> rouletteTicks = new ArrayList<>();
            for (int i = 0; i < chromosomes.size(); i++) {

                if (i == 0) {
                    rouletteTicks.add(fitness.get(i) / Math.pow(fitness.get(i), 2));
                } else {
                    rouletteTicks.add(rouletteTicks.get(i - 1) + fitness.get(i) / Math.pow(fitness.get(i), 2));
                }
            }

            while (chosenChromosomes.size() < chromosomes.size()) {
                Random generator = new Random();
                Double randD = generator.nextDouble() * rouletteTicks.get(rouletteTicks.size());

                for (int i = 0; i < rouletteTicks.size(); i++) {
                    if (randD < rouletteTicks.get(i)) {
                        chosenChromosomes.add(chromosomes.get(i));
                    }
                }

            }
        }

        return chosenChromosomes;
    }

    private List<List<Integer>> crossing(Graph graph, int crossing, List<List<Integer>> chromosomes, List<Double> heurFitness) {
        List<List<Integer>> newChromosomes = new ArrayList<>();
        Random generator = new Random();

        int chromCount = 0;
        while (newChromosomes.size() != chromosomes.size() && chromCount < chromosomes.size()) {
            
            int parentAid = chromCount;
            int parentBid = chromCount+1;
            if(parentBid == chromosomes.size()){
                parentBid = 0;
            }
            
            int split = generator.nextInt(chromosomes.get(parentAid).size());
            
            int splitValue = chromosomes.get(parentAid).get(split);
            
            while (  !(chromosomes.get(parentAid).contains(splitValue) || chromosomes.get(parentBid).contains(splitValue) ) ) {
                
                if(chromosomes.get(parentBid).size() > chromosomes.get(parentAid).size()){
                    split = generator.nextInt(chromosomes.get(parentAid).size());
                    splitValue = chromosomes.get(parentAid).get(split);
                }
                if(chromosomes.get(parentBid).size() < chromosomes.get(parentAid).size()){
                    split = generator.nextInt(chromosomes.get(parentBid).size());
                    splitValue = chromosomes.get(parentAid).get(split);
                }
            }

            //System.out.println("looopCrossing1");
            List<Integer> newChrom = new ArrayList<>();
            int i =0;
            while( !newChrom.contains(splitValue)){
               newChrom.add(chromosomes.get(parentAid).get(i));
               i++;
            }
            i = chromosomes.get(parentBid).indexOf(splitValue) + 1 ;
            while(i < chromosomes.get(parentBid).size() ){
                newChrom.add(chromosomes.get(parentBid).get(i));
                i++;
            }
//            for (int i = 0; i < split; i++) {
//                /// System.out.println("looopCrossing2");
//                newChrom.add(chromosomes.get(chromCount).get(i));
//            }
////            if (chromCount < chromosomes.size()-2) {
////                List<Integer> filler = randomizeChromosome(chromosomes.get(chromCount).get(split),
////                        chromosomes.get(chromCount + 1).get(split),
////                        graph, false, heurFitness);
////                if (filler.size() > 1) {
////                    filler.remove(0);
////                    filler.remove(filler.size() - 1);
////                }
////                newChrom.addAll(filler);
////            } else {
////                List<Integer> filler = randomizeChromosome(chromosomes.get(chromCount).get(split),
////                        chromosomes.get(1).get(split),
////                        graph, false, heurFitness);
////                if (filler.size() > 1) {
////                    filler.remove(0);
////                    filler.remove(filler.size() - 1);
////                }
////                newChrom.addAll(filler);
////            }
//
//            for (int i = split; i < chromosomes.get(chromCount + 1).size(); i++) {
//                //  System.out.println("looopCrossing3");
//                newChrom.add(chromosomes.get(chromCount + 1).get(i));
//            }
            newChromosomes.add(newChrom);
            chromCount ++;
        }
        return newChromosomes;
    }

}
