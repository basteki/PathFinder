/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import graphHandler.domain.Graph;
import graphHandler.domain.Vertice;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {

    public List<Integer> findWay(int start, int finish, Graph graph) {
        System.out.println("Dijkstra start");
        
        List<Integer> path = new ArrayList<>();

        int nVertice = graph.verticesList.size();
        boolean[] visited = new boolean[nVertice];
        double[] accWeights = new double[nVertice];

        int i = 0;

        while (i < nVertice) {
            visited[i] = false;
            accWeights[i] = Double.POSITIVE_INFINITY;
            i++;
        }
      
        visited[start] = true;
        accWeights[start] = 0.0;

        int currentStep = start;

        while (currentStep != finish) {
            accWeights = weighConnected(visited, accWeights, graph, currentStep);
            int lowestWeighId = -1;
            double lowestWeight = Double.POSITIVE_INFINITY;
            
            for(i = 0; i<accWeights.length; i++){
                if(accWeights[i] < lowestWeight && !visited[i]){
                    lowestWeight = accWeights[i];
                    lowestWeighId = i;
                }
            }
            
            visited[currentStep] = true;
            currentStep = lowestWeighId;
            System.out.println("step: " + currentStep);
            
        }
        System.out.println("Dijkstra 1 faze end");
        path.add(currentStep);
        while (currentStep != start) {
            int lowestWeightId = -1;
            double lowestWeight = Double.POSITIVE_INFINITY;
            
            for (i = 0; i < graph.verticesList.get(currentStep).connected.size(); i++) {
                                
                int activeConnectedId = graph.verticesList.get(currentStep).connected.get(i);
                
                if(accWeights[activeConnectedId] < lowestWeight && visited[activeConnectedId]){
                    lowestWeight = accWeights[activeConnectedId];
                    lowestWeightId = activeConnectedId;
                }
                
            }
            if (lowestWeightId == -1){
                System.out.println("Wystąpił bląd, sprawdź spójność grafu!");
            }
            path.add(lowestWeightId);
            System.out.println("Krok " + lowestWeightId);
            currentStep = lowestWeightId;
        }
        System.out.println("Dijkstra 2 faze end");
        Collections.reverse(path);
        return path;
    }

    private double[] weighConnected(boolean[] visited, double[] accWeights, Graph graph, int currentStep) {
        
        System.out.println("Weighing connection start");
        
        int i = 0;
        
        for (i  = 0; i < graph.verticesList.get(currentStep).getConnected().size(); i++) {
            int activeConnectedId = graph.verticesList.get(currentStep).connected.get(i);
            int activeEdgeId = 0;

            for (int j = 0; j < graph.edgesList.size(); j++) {
                if (graph.edgesList.get(j).getA() == activeConnectedId && graph.edgesList.get(j).getB() == currentStep) {
                    activeEdgeId = j;
                }
                if (graph.edgesList.get(j).getB() == activeConnectedId && graph.edgesList.get(j).getA() == currentStep) {
                    activeEdgeId = j;
                }
            }

            if (accWeights[activeConnectedId]
                    > accWeights[currentStep] + graph.edgesList.get(activeEdgeId).getWeight() + graph.verticesList.get(activeConnectedId).weight
                    && !visited[activeConnectedId]) {

                accWeights[activeConnectedId] = accWeights[currentStep] + graph.edgesList.get(activeEdgeId).getWeight() + graph.verticesList.get(activeConnectedId).weight;
            }
        }
        System.out.println("Weighing connection end");
        
        return accWeights;
    }

}
