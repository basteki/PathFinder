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

/**
 *
 * @author Idavor
 */
public class Astar {

    public List<Integer> findWay(int start, int finish, Graph graph) {

        List<Integer> path = new ArrayList<>();
        
        int nVertice = graph.verticesList.size();
        boolean[] visited = new boolean[nVertice];
        double[] accWeights = new double[nVertice];
        double[] heurScore = new double[graph.verticesList.size()];
        
        int i = 0;

        while (i < nVertice) {
            visited[i] = false;
            accWeights[i] = Double.POSITIVE_INFINITY;
            heurScore[i] = Math.hypot(graph.verticesList.get(i).x - graph.verticesList.get(start).x,
                                   graph.verticesList.get(i).y - graph.verticesList.get(start).y  );
            i++;
        }
      
        visited[start] = true;
        accWeights[start] = 0.0;

        int currentStep = start;
        int previousStep = start;

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

        }

        path.add(currentStep);
       
        
        while (currentStep != start) {
            int lowestWeightId = -1;
            double lowestWeight = Double.POSITIVE_INFINITY;
            
            for (i = 0; i < graph.verticesList.get(currentStep).connected.size(); i++) {
                                
                int activeConnectedId = graph.verticesList.get(currentStep).connected.get(i);
                double activeConnectionWeight = 0.0;
                
                for(int j = 0; j < graph.edgesList.size(); j++){
                    if(graph.edgesList.get(j).getA() == activeConnectedId
                            && graph.edgesList.get(j).getB() == currentStep ){
                        activeConnectionWeight = graph.edgesList.get(j).getWeight();
                        break;
                    }
                    if(graph.edgesList.get(j).getB() == activeConnectedId
                            && graph.edgesList.get(j).getA() == currentStep ){
                        activeConnectionWeight = graph.edgesList.get(j).getWeight();
                        break;
                    }
                        
                }
                
                
                if(accWeights[activeConnectedId] + activeConnectionWeight + heurScore[activeConnectedId] < lowestWeight && visited[activeConnectedId]){
                    lowestWeight = accWeights[activeConnectedId] + activeConnectionWeight +  heurScore[activeConnectedId];
                    lowestWeightId = activeConnectedId;
                }
                
            }
            if (lowestWeightId == -1){
                System.out.println("Wystąpił bląd, sprawdź spójność grafu!");
            }
            path.add(lowestWeightId);

            currentStep = lowestWeightId;
        }

        Collections.reverse(path);
        
    
        return path;
    }

    private double[] weighConnected(boolean[] visited, double[] accWeights, Graph graph, int currentStep) {
        

        
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
        
        return accWeights;
    }
}
  //  public List<Integer> findWay(int start, int finish, Graph graph) {
//
//        List<Integer> path = new ArrayList<>();
//
//        List<Integer> visited = new ArrayList<>();
//        List<Integer> notVisited = new ArrayList<>();
//        int[] cameFrom = new int[graph.verticesList.size()];
//        
//        double[] heurScore = new double[graph.verticesList.size()];
//        
//        for (int i = 0; i < graph.verticesList.size(); i++) {
//            System.out.println("A");
//            cameFrom[i] = -1;
//            notVisited.add(i);
//            heurScore[i] = Math.hypot(graph.verticesList.get(i).x - graph.verticesList.get(start).x,
//                                     graph.verticesList.get(i).y - graph.verticesList.get(start).y  );
//        }
//
//        double[] accWeights = new double[graph.verticesList.size()];
//        accWeights[start] = 0;
//
//        int currentStep = start;
//        int lowestWeightId = currentStep;
//
//        while (notVisited != null) {
//            System.out.println("B");
//            System.out.println(notVisited.size());
//            double lowestWeight = Double.POSITIVE_INFINITY;
//
//            for (int i = 0; i < graph.verticesList.size(); i++) {
//                System.out.println("C");
//                if (accWeights[i] < lowestWeight && notVisited.contains(i)) {
//                    lowestWeight = accWeights[i];
//                    lowestWeightId = i;
//                }
//            }
//
//            if (lowestWeightId == finish) {
//                return reconstructPath(cameFrom, finish);
//            }
//            notVisited.remove(lowestWeightId);
//            visited.add(lowestWeightId);
//
//            for (int i = 0; i < graph.verticesList.get(currentStep).connected.size(); i++) {
//                System.out.println("D");
//                int activeConnectedId = graph.verticesList.get(currentStep).connected.get(i);
//                double activeConnectionWeight = 0.0;
//                boolean isBetter = false;
//                double tmpWeight = Double.POSITIVE_INFINITY;
//                
//                if (visited.contains(activeConnectedId)) {
//
//                    for (int j = 0; j < graph.edgesList.size(); j++) {
//                        if (graph.edgesList.get(j).getA() == activeConnectedId
//                                && graph.edgesList.get(j).getB() == currentStep) {
//                            activeConnectionWeight = graph.edgesList.get(j).getWeight();
//                            break;
//                        }
//                        if (graph.edgesList.get(j).getB() == activeConnectedId
//                                && graph.edgesList.get(j).getA() == currentStep) {
//                            activeConnectionWeight = graph.edgesList.get(j).getWeight();
//                            break;
//                        }
//                        System.out.println("E");
//                    }
//                    
//                   tmpWeight = accWeights[currentStep] + activeConnectionWeight ;
//                    
//                }
//                if(!notVisited.contains(activeConnectedId) ){
//                    notVisited.add(activeConnectedId);
//                    isBetter = true;
//                }else if(tmpWeight < accWeights[activeConnectedId] ) {
//                    isBetter = true;
//                }
//                if(isBetter){
//                    cameFrom[activeConnectedId] = currentStep;
//                    accWeights[activeConnectedId]= tmpWeight;
//                }
//                
//            }
//
//        }
//        
//        return path;
//    }
//
//    private List<Integer> reconstructPath(int[] cameFrom, int currentStep) {
//
//        List<Integer> path = new ArrayList<>();
//        System.out.println("F");
//        if (cameFrom[currentStep] != -1) {
//            path = reconstructPath(cameFrom, cameFrom[currentStep]);
//            path.add(currentStep);
//            return path;
//        } else {
//            return null;
//        }

