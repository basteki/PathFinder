/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphHandler.domain;

import java.util.List;

/**
 *
 * @author Idavor
 */
public class Vertice {
    int id;
    int x;
    int y;
    private List<Edge> connected;
    double weight;
    
    public void setId(int id){
        this.id = id;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    
    public void setConnected(List<Edge>  connected){
        this.connected = connected;
    }
    
    public void setWeight(int weight){
        this.weight = weight;
    }
    
    public int getId(){
        return this.id;
    }
    
    public List<Edge> getConnected(){
        return this.connected;
    }
    
    public double getWeight(){
        return this.weight;
    }
}
