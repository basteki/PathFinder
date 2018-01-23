/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphHandler;

import graphHandler.domain.Edge;
import graphHandler.domain.Graph;
import graphHandler.domain.Vertice;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Idavor
 */
public class Generator {

    public static Graph generate(int vectN) {
        Graph G = new Graph();
        int maxVect = 500 + 10 * vectN;
        List<Vertice> vertices = new ArrayList<Vertice>();

        boolean collisionDetected = false;

        int edgeN = 5;
        Random generator = new Random();

        int i = 0;
        while (i < vectN) {
            int x = generator.nextInt(maxVect);
            int y = generator.nextInt(maxVect);
            for (int v = 0; v < vertices.size(); v++) {
                if (Math.sqrt(Math.pow(vertices.get(v).getX() - x, 2) + Math.pow(vertices.get(v).getY() - y, 2)) < maxVect / 30) {
                    collisionDetected = true;
                }
            }
            while (collisionDetected == true) {
                collisionDetected = false;
                x = generator.nextInt(maxVect);
                y = generator.nextInt(maxVect);
                for (int v = 0; v < vertices.size(); v++) {
                    if (Math.hypot(vertices.get(v).getX() - x, vertices.get(v).getY() - y) < maxVect / 30) {
                        collisionDetected = true;
                    }
                }
            }
            Vertice vert = new Vertice();
            vert.id = i;
            vert.x = x;
            vert.y = y;
            vert.weight = 0.0;
            vert.connected = new ArrayList<Integer>();

            vertices.add(vert);

            i++;
        }

        List<Vertice> closestVert = new ArrayList<Vertice>();

        for (int s = 0; s < edgeN; s++) {
            closestVert.add(vertices.get(s));
        }

        List<Edge> edges = new ArrayList<Edge>();

        for (int ve = 0; ve < vertices.size(); ve++) {
            int[] usedIds = new int[edgeN];
            for (int ve2 = 0; ve2 < vertices.size(); ve2++) {
                for (int ve3 = 0; ve3 < closestVert.size(); ve3++) {
                    if (Math.hypot(closestVert.get(ve3).x - vertices.get(ve).x, closestVert.get(ve3).y - vertices.get(ve).y)
                            > Math.hypot(vertices.get(ve2).x - vertices.get(ve).x, vertices.get(ve2).y - vertices.get(ve).y)
                            && vertices.get(ve).id != vertices.get(ve2).id) {

                        for (int ve4 = edgeN - 1; ve4 > ve3; ve4--) {
                            closestVert.set(ve4, closestVert.get(ve4 - 1));
                            closestVert.set(ve3, vertices.get(ve2));

                        }

                    }
                }
            }
            int randomE = generator.nextInt(edgeN - 1) + 1;
            for (int e = 0; e < edgeN; e++) {
                if (vertices.get(ve).id != 0 || closestVert.get(e).id != 0) {
                    Edge edge = new Edge();
                    edge.setA(vertices.get(ve).id);
                    edge.setB(closestVert.get(e).id);
                    edge.setOneWay(false);
                    edge.setWeight(Math.hypot(closestVert.get(e).x - vertices.get(ve).x, closestVert.get(e).y - vertices.get(ve).y));

                    edges.add(edge);
                }
            }
        }

        for (i = 0; i < edges.size(); i++) {
            int A = edges.get(i).getA();
            int B = edges.get(i).getB();
            if (!vertices.get(A).connected.contains(B)) {
                vertices.get(A).connected.add(B);
            }
            if (!vertices.get(B).connected.contains(A)) {
                vertices.get(B).connected.add(A);
            }
        }
        
        G.verticesList = vertices;
        G.edgesList = edges;
        return G;
    }
}
