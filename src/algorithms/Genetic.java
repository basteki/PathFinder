/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import graphHandler.domain.Graph;
import graphHandler.domain.Vertice;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pathfinder.MainGUI;
import pathfinder.Options;
import pathfinder.PathFinder;

/**
 *
 * @author Idavor
 */
public class Genetic {

    private int optimalGeneration;
    public  List<Integer> optimalPath = new ArrayList<>();
    public  double lowestWeightGlobal = Double.POSITIVE_INFINITY;

    public List<Integer> findWay(int start, int finish, Graph graph, Options opt) {

       int populationCount = opt.nPopulation;
        int nGeneration = opt.nGeneration;
        int crossingType = opt.crossingType;
        int chromosomeType = opt.chromosomeType;
        boolean heur = opt.heuristic;

        int selectionType = opt.selectionType;
        double crossP = opt.crossP;
        double mutationP = opt.mutationP;
        double eliminationP = opt.eliminationP;

        PathFinder.operationCount = 0;
        List<Integer> path = new ArrayList<>();

        List<List<Integer>> chromosomes = new ArrayList<>();
        List<Double> fitness = new ArrayList<>();
        List<Double> heuristicList = new ArrayList<>();

        
         
        //1Inicjalizacja heurystyki
        if (heur == true) {
            for (int i = 0; i < graph.verticesList.size(); i++) {
                heuristicList.add(Math.hypot(graph.verticesList.get(i).x - graph.verticesList.get(finish).x, graph.verticesList.get(i).y - graph.verticesList.get(finish).y));
                PathFinder.operationCount++;

            }
        } else {
            for (int h = 0; h < graph.verticesList.size(); h++) {
                heuristicList.add(1.0);

            }
        }
        
        //1
        //2Chromosomy pierwszej populacji
        if (chromosomeType == 0) {

            for (int p = 0; p < populationCount; p++) {
                List<Integer> chromosome = randomizeChromosomeVert(start, finish, graph, heuristicList);
                //System.out.println("2." + chromosomes.size());
                chromosomes.add(chromosome);
                fitness.add(MainGUI.measurePath(chromosome));
                PathFinder.operationCount++;
            }
            
        }
        if (chromosomeType == 1) {

            for (int p = 0; p < populationCount; p++) {

                List<Integer> chromosome = randomizeChromosomeEdge(start, finish, graph, heuristicList);
                chromosomes.add(chromosome);
                fitness.add(measurePathByEdge(chromosome, start, finish));
                PathFinder.operationCount++;
            }
        }
        //2

        //3 Pętla generacji
        for (int i = 0; i < nGeneration; i++) {
            //aktualizacja przystosowania
            if (chromosomeType == 0) {
                fitness.clear();
                for (int p = 0; p < chromosomes.size(); p++) {
                    fitness.add(MainGUI.measurePath(chromosomes.get(p)));
                }
                PathFinder.operationCount++;
            }
            if (chromosomeType == 1) {
                fitness.clear();
                for (int p = 0; p < populationCount; p++) {
                    fitness.add(measurePathByEdge(chromosomes.get(p), start, finish));
                }
                PathFinder.operationCount++;
            }
            //
            //System.out.println("4 " + chromosomes.size());
            //4 wybierz rodziców
            chromosomes = selectNextPop(graph, selectionType, eliminationP, chromosomes, fitness);

                //4
            //5 Krzyzuj
            //System.out.println("5 " + chromosomes.size());
            if (crossingType == 0) {
                chromosomes = oxCrossing(graph, chromosomes, crossP);
                
            }
            if (crossingType == 1) {
                chromosomes = splitCrossing(graph, chromosomes, crossP);

            }
            PathFinder.operationCount++;
            //5
            //6Mutuj
            //System.out.println("6 " + chromosomes.size());
            chromosomes = mutate(chromosomes, mutationP);
            //
            //7Napraw Ścieżki
            // System.out.println("7 " + chromosomes.size());
            if (chromosomeType == 0) {
//                for (int j = 0; j < chromosomes.size() - 1; j++) {
//                    for (int k = 0; k < chromosomes.get(j).size() - 1; k++) {
//                        if (!graph.verticesList.get(chromosomes.get(j).get(k)).connected.contains(chromosomes.get(j).get(k + 1))) {
//                            List<Integer> fix = randomizeChromosomeVert(chromosomes.get(j).get(k), chromosomes.get(j).get(k + 1), graph, heuristicList);
//                            fix.remove(0);
//                            if (fix.size() > 1) {
//                                fix.remove(fix.size() - 1);
//                            } else {
//                                fix.clear();
//                            }
//
//                            chromosomes.get(j).addAll(k, fix);
//                            PathFinder.operationCount++;
//                        }
//
//                    }
//                    if (chromosomes.get(j).size() == 0) {
//                        chromosomes.get(j).addAll(
//                                randomizeChromosomeVert(
//                                        start, finish, graph, heuristicList)
//                        );
//                    }
//
//                }

                while (chromosomes.size() != populationCount) {
                    chromosomes.add(this.randomizeChromosomeVert(start, finish, graph, heuristicList));
                    PathFinder.operationCount++;
                }

            }
            if (chromosomeType == 1) {

                int currentStepId = start;
                
                for (int j = 0; j < chromosomes.size(); j++) {
                    PathFinder.operationCount++;
                    if(measurePathByEdge(chromosomes.get(j), start, finish) == 0.0){
                        chromosomes.set(j, randomizeChromosomeEdge(start, finish, graph, heuristicList));
                    }
                    
                    boolean fixed = false;
                    
                    while (fixed) {

                        for (int k = 0; k < chromosomes.get(j).size(); k++) {
                            if (!graph.verticesList.get(currentStepId).connected.contains(chromosomes.get(j).get(k))) {
                                Random gen = new Random();
                                int rAllel = gen.nextInt(graph.verticesList.get(currentStepId).connected.size() - 1);
                                chromosomes.get(j).set(k, rAllel);

                                currentStepId = graph.verticesList.get(currentStepId).connected.get(rAllel);
                            }
                        }
                        if (currentStepId == finish) {
                            fixed = true;
                        }
                        if (currentStepId != finish) {
                            chromosomes.get(j).addAll(randomizeChromosomeEdge(currentStepId, finish, graph, heuristicList));
                            PathFinder.operationCount++;
                        }

                    }
                }
                while (chromosomes.size() != populationCount) {
                    chromosomes.add(this.randomizeChromosomeEdge(start, finish, graph, heuristicList));
                }
                 
            }

            //
            for (int j= 0; j < chromosomes.size()-1; j++) {
                PathFinder.operationCount++;
                if (chromosomeType == 0) {
                    
                    if (MainGUI.measurePath(chromosomes.get(j)) < lowestWeightGlobal && chromosomes.get(j).contains(finish) && MainGUI.measurePath(chromosomes.get(j)) != 0.0) {
                        lowestWeightGlobal = MainGUI.measurePath(chromosomes.get(j));
                        optimalGeneration = i;
                                           
                        optimalPath = chromosomes.get(j);
                    }
                }
             
                if (chromosomeType == 1) {
                    if (lowestWeightGlobal > measurePathByEdge(chromosomes.get(j), start, finish) && measurePathByEdge(chromosomes.get(j), start, finish) !=0 ) {
                        lowestWeightGlobal = measurePathByEdge(chromosomes.get(j), start, finish);
                         
                        optimalGeneration = i;

                        optimalPath = chromosomes.get(j);
                    }
                }

            }
            //3
        }

        double lowestWeight = Double.POSITIVE_INFINITY;

        for (int j = 0; j < chromosomes.size(); j++) {
            
            if (chromosomeType == 0) {
               
                if (lowestWeight > MainGUI.measurePath(chromosomes.get(j)) && chromosomes.get(j).contains(finish) ) {
                    lowestWeight = MainGUI.measurePath(chromosomes.get(j));

                    path = chromosomes.get(j);
                }
                if (chromosomeType == 1) {
                    if (lowestWeight > measurePathByEdge(chromosomes.get(j), start, finish) && measurePathByEdge(chromosomes.get(j), start, finish) !=0.0 ) {
                        lowestWeight = measurePathByEdge(chromosomes.get(j), start, finish);
                        
                        path = chromosomes.get(j);
                    }
                }
            }
            PathFinder.operationCount++;
        }
        if (chromosomeType == 1) {
            path = this.randomizeChromosomeEdge(start, finish, graph, heuristicList);
            List<Integer> edgePath = new ArrayList<>();
            edgePath.add(start);
            int curr = start;
            int ic = 0;
            while (ic < path.size()) {
                edgePath.add(graph.verticesList.get(curr).connected.get(path.get(ic)));
                curr = graph.verticesList.get(curr).connected.get(path.get(ic));
                ic++;
            }
            path = edgePath;
        }

        return path;
    }

    private List<Integer> randomizeChromosomeVert(int start, int finish, Graph graph, List<Double> heuristicList) {
        //System.out.println("1.1");
        List<Integer> chromosome = new ArrayList<>();
        chromosome.add(start);

        int previousStep = start;
        int currentStep = start;
        int nextStep = start;

        List<Integer> connected = graph.verticesList.get(currentStep).connected;
        //System.out.println("1.2");
        while (currentStep != finish) {

            double[] heuristicTab = new double[connected.size()];

            for (int i = 0; i < connected.size(); i++) {
                heuristicTab[i] = heuristicList.get(i);
                //System.out.println("her: " +heuristicList.get(i));
            }
            int nextConnected = roulette(heuristicTab);

            while (connected.get(nextConnected) == previousStep) {
                nextConnected = roulette(heuristicTab);

            }

            nextStep = connected.get(nextConnected);
            previousStep = currentStep;
            currentStep = nextStep;

            connected = graph.verticesList.get(currentStep).connected;

            //System.out.println("Current step: " +currentStep);
            chromosome.add(currentStep);
        }

        return chromosome;
    }

    private List<Integer> randomizeChromosomeEdge(int start, int finish, Graph graph, List<Double> heuristicList) {
        List<Integer> path = new ArrayList<>();
        List<Integer> chromosome = new ArrayList<>();

        path.add(start);
        int currentStep = start;
        int prevStep = start;
        int nextStep = start;

        while (!path.contains(finish)) {

            List<Integer> connected = graph.verticesList.get(currentStep).connected;

            double[] heuristicTab = new double[connected.size()];

            for (int i = 0; i < connected.size(); i++) {
                heuristicTab[i] = heuristicList.get(i);
            }
            int nextConnected = roulette(heuristicTab);

            while (connected.get(nextConnected) == prevStep) {
                nextConnected = roulette(heuristicTab);
            }
            chromosome.add(nextConnected);
            path.add(connected.get(nextConnected));
            nextStep = connected.get(nextConnected);

            prevStep = currentStep;
            currentStep = nextStep;
        }

        return chromosome;
    }

    private int roulette(double[] numTab) {

        double[] partitions = new double[numTab.length];

        int chosenId = 1;

        partitions[0] = numTab[0];
        int i = 1;

        while (i < numTab.length) {

            partitions[i] = partitions[i - 1] + numTab[i];
            i++;

        }

        Random r = new Random();
        double randomValue = (partitions[partitions.length - 1]) * r.nextDouble();
        //System.out.println("Rvalue:" +randomValue);
        i = 0;

        while (i < numTab.length) {
            if (randomValue < partitions[i]) {
                chosenId = i;

                return chosenId;
            }
            i++;

        }
        //System.out.println("Rchosen:" + chosenId);
        return chosenId;
    }

    private double measurePathByEdge(List<Integer> path, int start, int finish) {
        double weight = 0.0;

        List<Integer> realPath = new ArrayList<>();
        
        int currentStepId = start;

        for (int i = 0; i < path.size() - 1; i++) {

            if (path.get(i) >= PathFinder.graph.verticesList.get(currentStepId).connected.size()) {
                path.set(i, PathFinder.graph.verticesList.get(currentStepId).connected.size() - 1);
            }
            
            int nextStepId = PathFinder.graph.verticesList.get(currentStepId).connected.get(path.get(i));
            realPath.add(currentStepId);
//            Vertice currentStep = PathFinder.graph.verticesList.get(currentStepId);
//            Vertice nextStep = PathFinder.graph.verticesList.get(nextStepId);
//
//            weight += Math.hypot(currentStep.x - nextStep.x, currentStep.y - nextStep.y);

            currentStepId = nextStepId;
        }

        return MainGUI.measurePath(realPath);
    }

    private List<List<Integer>> selectNextPop(Graph graph, int selectionType, double crossP, List<List<Integer>> chromosomes, List<Double> fitness) {
        List<List<Integer>> chosenChromosomes = new ArrayList<>();

        int cap = (int) (fitness.size() * (100 - crossP) / 100);

        if (selectionType == 0) { // tournee
            List<List<Integer>> chosenChromosomesTournee = chromosomes;
            double[] survivorsF = new double[cap];

            for (int i = 0; i < cap; i++) {
                survivorsF[i] = Double.POSITIVE_INFINITY;
            }

            for (int i = 0; i < fitness.size(); i++) {
                double current = fitness.get(i);

                for (int j = 0; j < cap; j++) {
                    if (current < survivorsF[j]) {
                        double tmp = survivorsF[j];
                        survivorsF[j] = current;
                        current = tmp;
                    }
                }

            }
            for (int i = fitness.size() - 1; i > 0; i--) {
                //System.out.println("fit" + fitness.size());   
                //System.out.println("cc" + chosenChromosomesTournee.size());   
                if (fitness.get(i) > survivorsF[survivorsF.length - 1]) {

                    chosenChromosomesTournee.remove(i);

                }

            }

            int i = 0;
            while (chosenChromosomesTournee.size() != chromosomes.size()) {
                chosenChromosomesTournee.add(chosenChromosomesTournee.get(i));
                i++;

            }

            chosenChromosomes = chosenChromosomesTournee;

        } else if (selectionType == 1) {                  // roulette
            List<List<Integer>> chosenChromosomesRoulette = new ArrayList<>();

            double[] fitnessTab = new double[fitness.size()];

            for (int i = 0; i < fitness.size(); i++) {
                fitnessTab[i] = fitness.get(i);

            }
            double maxFit = 0.0;
            for (int j = 0; j < fitness.size(); j++) {
                fitnessTab[j] = fitness.get(j);
                if (fitnessTab[j] > maxFit) {
                    maxFit = fitnessTab[j];
                }
            }
            for (int l = 0; l < fitness.size(); l++) {
                fitnessTab[l] = fitnessTab[l] - maxFit;

            }
            int co = 0;
            while (chosenChromosomesRoulette.size() != chromosomes.size()) {
                chosenChromosomesRoulette.add(chromosomes.get(roulette(fitnessTab)));
           
                co++;
            }

            chosenChromosomes = chosenChromosomesRoulette;

        }

        return chosenChromosomes;
    }

    private List<List<Integer>> splitCrossing(Graph graph, List<List<Integer>> chromosomes, double crossP) {
        List<List<Integer>> newChromosomes = new ArrayList<>();
        Random generator = new Random();

        int chromCount = 0;
        while (newChromosomes.size() != chromosomes.size() && chromCount < chromosomes.size()) {

            int parentAid = chromCount;
            int parentBid = chromCount + 1;
            if (parentBid == chromosomes.size()) {
                parentBid = 0;
            }

            double r = generator.nextDouble();
            if (r > crossP) {
                newChromosomes.add(chromosomes.get(parentAid));
                chromCount++;
            } else {
                int split = generator.nextInt(chromosomes.get(parentAid).size());
                boolean commonJointFound = false;
                int splitValue = chromosomes.get(parentAid).get(split);

                for (int j = 0; j < graph.verticesList.size(); j++) {
                    if (chromosomes.get(parentAid).contains(graph.verticesList.get(j).id)
                            && chromosomes.get(parentBid).contains(graph.verticesList.get(j).id)) {
                        if (graph.verticesList.get(j).id != chromosomes.get(parentBid).get(0)
                                && graph.verticesList.get(j).id != chromosomes.get(parentAid).get(chromosomes.get(parentAid).size() - 1)) {
                            splitValue = graph.verticesList.get(j).id;

                            for (int k = 0; k < chromosomes.get(parentAid).size(); k++) {

                                if (chromosomes.get(parentAid).get(k) == splitValue) {
                                    split = k;
                                }
                                commonJointFound = true;
                            }
                        }
                    }

                }
                if (!commonJointFound) {
                    while (!(chromosomes.get(parentAid).contains(splitValue) || chromosomes.get(parentBid).contains(splitValue))) {

                        if (chromosomes.get(parentBid).size() > chromosomes.get(parentAid).size()) {
                            split = generator.nextInt(chromosomes.get(parentAid).size());
                            splitValue = chromosomes.get(parentAid).get(split);
                        }
                        split = chromosomes.get(parentBid).indexOf(splitValue);

                    }
                }

                List<Integer> newChrom = new ArrayList<>();
                int i = 0;
                while (!newChrom.contains(splitValue)) {
                    newChrom.add(chromosomes.get(parentAid).get(i));
                    i++;
                }
                i = chromosomes.get(parentBid).indexOf(splitValue) + 1;
                while (i < chromosomes.get(parentBid).size()) {
                    newChrom.add(chromosomes.get(parentBid).get(i));
                    i++;
                }

                newChromosomes.add(newChrom);
                chromCount++;
            }
        }

        return newChromosomes;
    }

    private List<List<Integer>> oxCrossing(Graph graph, List<List<Integer>> chromosomes, double crossP) {
        List<List<Integer>> newChromosomes = new ArrayList<>();

        int chromCount = 0;
        while (newChromosomes.size() != chromosomes.size()) {

            List<Integer> chromosome = new ArrayList<>();

            int parentAid = chromCount;
            int parentBid = chromCount + 1;

            if (parentBid == chromosomes.size()) {
                parentBid = 0;
            }

            Random generator = new Random();

            double rd = generator.nextDouble();

            if (rd > crossP || chromosomes.get(parentAid).size() <= 2 || chromosomes.get(parentBid).size() <= 2) {
                newChromosomes.add(chromosomes.get(parentAid));
                chromCount++;
            } else {

                int chromosomeSize = chromosomes.get(parentAid).size();
                //System.out.println("chromosome size " + chromosomes.get(parentAid).size());
                if (chromosomeSize == 0) {
                    chromosomeSize = 1;
                }
                double chromosomeMult = ((double) chromosomes.get(parentBid).size() / (double) chromosomeSize);

                int k1 = generator.nextInt(chromosomeSize - 1) + 1;
                int k2 = generator.nextInt(chromosomeSize - k1) + k1;

                int k1R = (int) ((double) chromosomeMult * (double) k1);
                int k2R = (int) ((double) chromosomeMult * (double) k2);

               
                for (int i = 0; i < k1; i++) {
                    chromosome.add(chromosomes.get(parentAid).get(i));
                }
                for (int j = k1R; j > k2R; j++) {
                    chromosome.add(chromosomes.get(parentBid).get(j));
                }
                for (int i = k1; i < k2; i++) {
                    chromosome.add(chromosomes.get(parentAid).get(i));
                }

                newChromosomes.add(chromosome);
                chromCount++;
            }
        }
        return newChromosomes;
    }

    private List<List<Integer>> mutate(List<List<Integer>> chromosomes, double mutP) {
        List<List<Integer>> mutatedChromosomes = new ArrayList<>();

        for (int i = 0; i < chromosomes.size(); i++) {
            Random generator = new Random();
            double r = generator.nextDouble();
            if (r < mutP) {
                int max = 1;
                for (int j = 0; j < chromosomes.get(i).size(); j++) {
                    if (chromosomes.get(i).get(j) > max) {
                        max = chromosomes.get(i).get(j);
                    }
                    int mutantId = generator.nextInt(chromosomes.get(i).size());
                    int mutant = generator.nextInt(max);

                    chromosomes.get(i).set(mutantId, mutant);
                }
            } else {
                mutatedChromosomes.add(chromosomes.get(i));
            }
        }

        return mutatedChromosomes;
    }

    public int getOptimalGen() {
        return this.optimalGeneration;
    }

    public List<Integer> getOptimalPath() {
        return this.optimalPath;
    }
}
