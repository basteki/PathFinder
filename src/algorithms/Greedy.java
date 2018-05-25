package algorithms;

import graphHandler.domain.Graph;
import java.util.ArrayList;
import java.util.List;
import pathfinder.PathFinder;

/**
 *
 * @author Idavor
 */
public class Greedy {

    public List<Integer> findWay(int start, int finish, Graph graph) {

        PathFinder.operationCount = 0;
        List<Integer> path = new ArrayList<>();

        boolean[] excluded = new boolean[graph.verticesList.size()];
        boolean[] visited = new boolean[graph.verticesList.size()];

        int currentStep = start;
        int nextStep = currentStep;

        while (excluded[start] != true && currentStep != finish) {

            visited[currentStep] = true;
            path.add(currentStep);

            double minDistance = Double.POSITIVE_INFINITY;

            for (int i = 0; i < graph.verticesList.get(currentStep).connected.size(); i++) {

                int check = graph.verticesList.get(currentStep).connected.get(i);
                PathFinder.operationCount++;

                double distance = Math.hypot(graph.verticesList.get(check).x - graph.verticesList.get(finish).x, graph.verticesList.get(check).y - graph.verticesList.get(finish).y);
                PathFinder.operationCount++;

                if (distance < minDistance & excluded[check] != true) {
                    minDistance = distance;
                    nextStep = check;
                }

            }

            if (visited[nextStep] != true) {
                currentStep = nextStep;
            } else {
                excluded[nextStep] = true;
                currentStep = start;
                visited = new boolean[graph.verticesList.size()];
                path = new ArrayList<>();
            }
            PathFinder.operationCount++;
        }

        path.add(currentStep);
        return path;

    }
}
