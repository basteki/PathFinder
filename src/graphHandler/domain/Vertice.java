
package graphHandler.domain;

import java.util.List;

public class Vertice {

    public int id;
    public int x;
    public int y;
    public List<Integer> connected;
    public Double weight;

        
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public void setConnected(List<Integer> connected) {
        this.connected = connected;
    }

    public List<Integer> getConnected() {
        return this.connected;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }
}
