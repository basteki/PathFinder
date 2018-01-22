/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphHandler;

import graphHandler.domain.Graph;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Idavor
 */
public class Saver {
     public static void save(String fileName, Graph graph) throws IOException {
        String path = "src\\graphHandler\\graphs\\" + fileName +".graph";
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        int i =0;
        for(;i<graph.verticesList.size();i++){
            String line = "* ";
            String connected = "";
            line += graph.verticesList.get(i).id + " ";
            line += graph.verticesList.get(i).x + " ";
            line += graph.verticesList.get(i).y + " ";
            line += "{ ";
            for(int j = 0 ; j < graph.edgesList.size();j++){
                if(graph.verticesList.get(i).id == graph.edgesList.get(j).getA() 
                        && !connected.contains(Integer.toString(graph.edgesList.get(j).getB()))){
                    connected += graph.edgesList.get(j).getB() + " ";
                }
                if(graph.verticesList.get(i).id == graph.edgesList.get(j).getB()
                        && !connected.contains(Integer.toString(graph.edgesList.get(j).getA()))){
                    connected += graph.edgesList.get(j).getA() + " ";
                }
                
            }
            line += connected;
            line += "}";
            System.out.println(line);
            writer.append(line);
            writer.newLine();
            
        }
       writer.close();
     }
}
