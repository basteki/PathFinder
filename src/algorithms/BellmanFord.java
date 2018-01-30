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
public class BellmanFord {

    public List<Integer> findWay(int start, int finish, Graph graph) {

        List<Integer> path = new ArrayList<>();
        int nVertice = graph.verticesList.size();

        double[] accWeights = new double[nVertice];

        int n = nVertice;
        while (n > 0) {
            n--;
            accWeights[n] = Double.POSITIVE_INFINITY;
        }

        accWeights[start] = 0;

        for (int i = 0; i < nVertice - 1; i++) {
            for (int j = 0; j < nVertice ; j++) {
                accWeights[j] = update(graph, accWeights, j);
            }   
        }
        
        int currentStep = finish;
        path.add(finish);
        
        while(currentStep != start){
            List<Integer> connected = graph.verticesList.get(currentStep).connected;
            
            int lowestId = currentStep;
            double lowestWeight = Double.POSITIVE_INFINITY;
            
            for(int i = 0; i < connected.size(); i++){
                
                
                
                if(accWeights[connected.get(i)] < lowestWeight ){
                    lowestWeight =accWeights[connected.get(i)];
                    lowestId = connected.get(i);
                }
            }
            
            path.add(lowestId);
            currentStep = lowestId;
            
        }
        
        Collections.reverse(path);
        
        
        return path;
    }

    public double update(Graph graph, double accWeights[], int currentStep) {
        List<Integer> connected = graph.verticesList.get(currentStep).connected;

        for (int i = 0; i < connected.size(); i++) {
            
            int activeConnectedId = graph.verticesList.get(connected.get(i)).id;
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
            if (accWeights[currentStep] > accWeights[activeConnectedId] + activeConnectionWeight ) {
                accWeights[currentStep] = accWeights[activeConnectedId] + activeConnectionWeight;
            }   
        }
        return accWeights[currentStep];
    }

//    Bellman-Ford(G,w,s):
//
//dla każdego wierzchołka v w V[G] wykonaj
//  d[v] = nieskończone
//  poprzednik[v] = niezdefiniowane
//d[s] = 0
//dla i od 1 do |V[G]| - 1 wykonaj
//  dla każdej krawędzi (u,v) w E[G] wykonaj
//    jeżeli d[v] > d[u] + w(u,v) to
//      d[v] = d[u] + w(u,v)
//      poprzednik[v] = u
}
