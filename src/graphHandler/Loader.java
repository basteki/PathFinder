/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphHandler;

import graphHandler.domain.*;
import pathfinder.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Math.*;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;

public class Loader {

    public static Graph load(String fileName) {
        List<String> verticesParsingList = new ArrayList<String>();
        List<String> edgesParsingList = new ArrayList<String>();
        Graph G = new Graph();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
          
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.contains("*")) {
                    verticesParsingList.add(line);
                }
                if (line.contains("/")) {
                    edgesParsingList.add(line);
                }
            }
            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", fileName);
            e.printStackTrace();
        }

        G.setVertices(parseVertices(verticesParsingList));
       // if (edgesParsingList != null) {
        //      G.setEdges(parseEdges(edgesParsingList));
        // } else {
        G.setEdges(parseEdgesFromConnected(G.verticesList));
        // }
        return G;
    }

    private static List<Vertice> parseVertices(List<String> toParse) {
        int i = 0;
        int i2 = 5;
        int n = toParse.size();

        Vertice nVert[] = new Vertice[n];
        for (i = 0; i < n; i++) {
            nVert[i] = new Vertice();
        }

        i = 0;
        List<Vertice> retVer = new ArrayList<Vertice>();

        while (i < toParse.size()) {

            String[] lineSplit = toParse.get(i).split(" ");

            int connected[] = new int[10];
            i2 = 5;
            nVert[i].setId(Integer.parseInt(lineSplit[1]));
            nVert[i].setX(Integer.parseInt(lineSplit[2]));
            nVert[i].setY(Integer.parseInt(lineSplit[3]));

            while (!lineSplit[i2].equals("}")) {
                connected[i2 - 5] = Integer.parseInt(lineSplit[i2]);
                i2++;
            }

            nVert[i].setConnected(connected);

            if (!lineSplit[lineSplit.length - 1].equals("}")) {
                nVert[i].setWeight(Double.parseDouble(lineSplit[lineSplit.length - 1]));
            } else {
                nVert[i].setWeight(0);
            }

            retVer.add(nVert[i]);

            i++;
        }

        return retVer;
    }

    public static List<Edge> parseEdges(List<String> toParse) {
        int i = 0;

        int n = toParse.size();

        Edge nEdge[] = new Edge[n];
        for (i = 0; i < n; i++) {
            nEdge[i] = new Edge();
        }

        i = 0;
        List<Edge> retEdge = new ArrayList<Edge>();

        if (toParse == null) {
            return null;
        }

        while (i < toParse.size()) {

            String[] lineSplit = toParse.get(i).split(" ");

            nEdge[i].setId(Integer.parseInt(lineSplit[1]));
            nEdge[i].setA(Integer.parseInt(lineSplit[2]));
            nEdge[i].setB(Integer.parseInt(lineSplit[2]));

            if (!lineSplit[lineSplit.length - 1].equals("}")) {
                nEdge[i].setWeight(Double.parseDouble(lineSplit[lineSplit.length - 1]));
            } else {
                nEdge[i].setWeight(0);
            }
            retEdge.add(nEdge[i]);

        }
        return retEdge;
    }

    public static List<Edge> parseEdgesFromConnected(List<Vertice> vertices) {
        List<Edge> retEdge = new ArrayList<Edge>();

        int i = 0;
        int counter = 0;
        while (i < vertices.size()) {
            int i2 = 0;
            while (i2 < vertices.get(i).connected.length) {

                if (vertices.get(i).connected[i2] != 0) {
                    Edge edge = new Edge();
                    edge.setA(vertices.get(i).id);
                    edge.setB(vertices.get(i).connected[i2]);

                    edge.setWeight(Math.hypot(vertices.get(i).x - vertices.get(vertices.get(i).connected[i2]).x, vertices.get(i).y - vertices.get(vertices.get(i).connected[i2]).y));


                    edge.setOneWay(false);
                    retEdge.add(edge);
                   
                }
                i2++;
            }
            i++;
        }
        return retEdge;
    }
}
