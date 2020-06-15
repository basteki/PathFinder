/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import graphHandler.domain.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pathfinder.PathFinder;

/**
 *
 * @author Idavor
 */
public class BellmanFord {
    public int[] last = new int[PathFinder.graph.edgesList.size()];
    

    public List<Integer> findWay(int start, int finish, Graph graph) {

        PathFinder.operationCount = 0;
        List<Integer> path = new ArrayList<>();
        int nVertice = graph.verticesList.size();

        double[] accWeights = new double[nVertice];
        
        int n = nVertice;
        while (n > 0) {
            n--;
            accWeights[n] = Double.POSITIVE_INFINITY;
            last[n] = start;
        }

        accWeights[start] = 0;

        for (int i = 0; i < nVertice - 1; i++) {
            for (int j = 0; j < nVertice; j++) {
                accWeights[j] = update(graph, accWeights, last, j);
                PathFinder.operationCount++;
            }
        }

        int currentStep = finish;
        path.add(finish);

        while (currentStep != start) {
//            List<Integer> connected = graph.verticesList.get(currentStep).connected;
//
//            int lowestId = currentStep;
//            double lowestWeight = Double.POSITIVE_INFINITY;
//
//            for (int i = 0; i < connected.size(); i++) {
//
//                if (accWeights[connected.get(i)] < lowestWeight) {
//                    lowestWeight = accWeights[connected.get(i)];
//                    lowestId = connected.get(i);
//                }
//
//            }
//
//            path.add(lowestId);
//            currentStep = lowestId;
            path.add(last[currentStep]);
            currentStep = last[currentStep];
        }

        Collections.reverse(path);

        return path;
    }

    public double update(Graph graph, double accWeights[], int[] last, int currentStep) {
        List<Integer> connected = graph.verticesList.get(currentStep).connected;

        for (int i = 0; i < connected.size(); i++) {

            int activeConnectedId = graph.verticesList.get(connected.get(i)).id;
            double activeConnectionWeight = 0.0;
            int l = activeConnectedId;

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
            if (accWeights[currentStep] > accWeights[activeConnectedId] + activeConnectionWeight) {
                accWeights[currentStep] = accWeights[activeConnectedId] + activeConnectionWeight;
                last[currentStep] = activeConnectedId;
                
            }
            PathFinder.operationCount++;
        }
        return accWeights[currentStep];
    }

}
