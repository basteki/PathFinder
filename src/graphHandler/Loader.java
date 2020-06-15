/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphHandler;

import static graphHandler.Generator.generate;
import static graphHandler.Generator.integrityCheck;
import graphHandler.domain.*;
import pathfinder.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Loader {

    public static Graph load(String fileName) {
        List<String> verticesParsingList = new ArrayList<String>();
        List<String> edgesParsingList = new ArrayList<String>();
        Graph G = new Graph();

        boolean edgeParse = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = reader.readLine()) != null) {

                if (line.contains("*")) {
                    verticesParsingList.add(line);
                }
                if (line.contains("/") || line.contains("=")) {
                    edgeParse = true;
                    edgesParsingList.add(line);
                }
            }
            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", fileName);
        }

        G.setVertices(parseVertices(verticesParsingList));
        if (edgeParse) {
            G.setEdges(parseEdges(edgesParsingList));
        } else {
            G.setEdges(parseEdgesFromConnected(G.verticesList));
        }

        for (int i = 0; i < G.edgesList.size(); i++) {
            int A = G.edgesList.get(i).getA();
            int B = G.edgesList.get(i).getB();

            if (G.verticesList.get(A).connected.contains(B)) {

                G.verticesList.get(B).connected.add(A);
            }
            if (G.verticesList.get(B).connected.contains(A)) {

                G.verticesList.get(A).connected.add(B);
            }
        }
        if (!integrityCheck(G)) {
            JOptionPane.showMessageDialog(null, "Wczytany graf nie jest spójny, stworzono losowy graf");
            G = generate(20,3);
        }
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

            List<Integer> connected = new ArrayList<>();
            i2 = 5;
            nVert[i].setId(Integer.parseInt(lineSplit[1]));
            nVert[i].setX(Integer.parseInt(lineSplit[2]));
            nVert[i].setY(Integer.parseInt(lineSplit[3]));

            while (!lineSplit[i2].equals("}")) {
                connected.add(Integer.parseInt(lineSplit[i2]));
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

        while (i < toParse.size()) {

            String[] lineSplit = toParse.get(i).split(" ");

            nEdge[i].setId(Integer.parseInt(lineSplit[1]));
            nEdge[i].setA(Integer.parseInt(lineSplit[2]));
            nEdge[i].setB(Integer.parseInt(lineSplit[3]));

            if (lineSplit.length == 5) {
                nEdge[i].setWeight(Double.parseDouble(lineSplit[lineSplit.length - 1]));
            } else {
                nEdge[i].setWeight(0);
            }
            if (lineSplit[0] == "=") {
                nEdge[i].setOneWay(false);
            } else {
                nEdge[i].setOneWay(true);
            }

            retEdge.add(nEdge[i]);
            i++;
        }

        return retEdge;
    }

    public static List<Edge> parseEdgesFromConnected(List<Vertice> vertices) {
        List<Edge> retEdge = new ArrayList<Edge>();

        int i = 0;
        int counter = 0;
        while (i < vertices.size()) {
            int i2 = 0;
            while (i2 < vertices.get(i).connected.size()) {

                if (vertices.get(i).connected.get(i2) != 0) {
                    Edge edge = new Edge();
                    edge.setA(vertices.get(i).id);
                    edge.setB(vertices.get(i).connected.get(i2));

                    edge.setWeight(Math.hypot(
                            (vertices.get(i).x - vertices.get(vertices.get(i).connected.get(i2)).x),
                            (vertices.get(i).y - vertices.get(vertices.get(i).connected.get(i2)).y)
                    ));

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
