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
import pathfinder.Options;
import pathfinder.PathFinder;

/**
 *
 * @author Idavor
 */
public class AntSwarm {

    public List<Integer> findWay(int start, int finish, Graph graph, Options opt) {

        int initialAntCount = opt.initialAntCount;
        int colonyCount = opt.colonyCount;
        double pheromoneDetoriation = opt.pheromoneDetoriation;
        double distancePriority = opt.distancePriority;
        double randomFactor = opt.randomFactor;

        System.out.println("ColonyCount" + MainGUI.opt0.initialAntCount);
        System.out.println("ColonyCount" + MainGUI.opt1.initialAntCount);

        PathFinder.operationCount = 0;
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> ants = new ArrayList<>();

        List<Double> pheromones = new ArrayList<>();

        for (int i = 0; i < graph.verticesList.size(); i++) {
            pheromones.add(i, 0.0);
            PathFinder.operationCount++;
        }

        for (int i = 0; i < initialAntCount; i++) {
            ants.add(createAnt(start, finish, graph, 1.0, pheromones));
            PathFinder.operationCount++;
        }
        pheromones = updatePheromones(ants, graph, pheromones, pheromoneDetoriation);
        int counter = 0;

        for (int i = 0; i < colonyCount; i++) {
            List<List<Integer>> newAnts = new ArrayList<>();
            newAnts.add(createAnt(start, finish, graph, randomFactor, pheromones));

            PathFinder.operationCount++;
            if (newAnts.get(newAnts.size() - 1) == null) {
                newAnts.remove(newAnts.size() - 1);
            } else {
                counter++;
                if (counter == initialAntCount) {
                    pheromones = updatePheromones(newAnts, graph, pheromones, pheromoneDetoriation);
                    newAnts = new ArrayList<>();
                    counter = 0;
                }
            }
            PathFinder.operationCount++;
        }
        List<Integer> finalAnt = null;
        double stallmateBreaker = 0.0;
        while (finalAnt == null) {
            finalAnt = createAnt(start, finish, graph, stallmateBreaker, pheromones);
            stallmateBreaker += 0.05;
        }
        finalAnt.remove(finish);
        return finalAnt;
    }

    private List<Integer> createAnt(int start, int finish, Graph graph, double randomFactor, List<Double> pheromones) {
        List<Integer> ant = new ArrayList<>();

        ant.add(start);
        Random generator = new Random();

        int chosen = start;

        while (chosen != finish) {

            List<Integer> connected = graph.verticesList.get(ant.get(ant.size() - 1)).connected;

            PathFinder.operationCount++;
            if (generator.nextDouble() < randomFactor) {
                int randStep = generator.nextInt(connected.size());
                ant.add(connected.get(randStep));
                chosen = connected.get(randStep);
            } else {
                int highestId = connected.get(0);
                double highestPheromoneLevel = 0;

                for (int i = 0; i < connected.size(); i++) {
                    if (pheromones.get(connected.get(i)) >= highestPheromoneLevel && !ant.contains(connected.get(i))) {
                        highestPheromoneLevel = pheromones.get(connected.get(i));
                        highestId = connected.get(i);
                    }
                }

                if (chosen != highestId) {
                    chosen = highestId;
                    ant.add(highestId);
                } else {
                    ant.clear();
                    ant.add(start);
                    chosen = start;

                }

                if (ant.size() > graph.verticesList.size() * 2) {
                    ant.clear();
                    ant.add(start);
                    chosen = start;
                    return null;
                }
                PathFinder.operationCount++;

            }

        }
        ant.add(finish);
        return ant;
    }

    private List<Double> updatePheromones(List<List<Integer>> ants, Graph graph, List<Double> pheromones, double pheromoneDetoriation) {
        int[] vertAntCount = new int[graph.verticesList.size()];

        for (int i = 0; i < graph.verticesList.size(); i++) {
            vertAntCount[i] = 0;
            PathFinder.operationCount++;
        }
        for (int i = 0; i < pheromones.size(); i++) {
            pheromones.set(i, pheromones.get(i) * (1 - pheromoneDetoriation));
            PathFinder.operationCount++;
        }

        for (int i = 0; i < ants.size(); i++) {
            double antWeight = 0;

            for (int j = 0; j < ants.get(i).size() - 1; j++) {
                int currentAntId = ants.get(i).get(j);
                vertAntCount[currentAntId] += 1;

                for (int k = 0; k < graph.edgesList.size(); k++) {

                    if (graph.edgesList.get(k).getA() == currentAntId
                            && graph.edgesList.get(k).getB() == ants.get(i).get(j + 1)) {
                        antWeight += graph.edgesList.get(k).getWeight();
                    } else if (graph.edgesList.get(k).getB() == currentAntId
                            && graph.edgesList.get(k).getA() == ants.get(i).get(j + 1)) {
                        antWeight += graph.edgesList.get(k).getWeight();
                    }
                    PathFinder.operationCount++;
                }
            }
            List<Integer> repeated = new ArrayList<>();

            for (int j = 0; j < ants.get(i).size(); j++) {
                int currentAntId = ants.get(i).get(j);

                if (!repeated.contains(currentAntId)) {
                    pheromones.set(currentAntId,
                            pheromones.get(currentAntId) + (graph.verticesList.size() * 10 / Math.pow(antWeight, 3)) * (1 + (vertAntCount[currentAntId] / Math.pow(vertAntCount[currentAntId], 2))));

                }
                PathFinder.operationCount++;
            }
        }

        return pheromones;
    }

}
