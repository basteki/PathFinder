
package graphHandler.domain;

import java.util.List;

public class Vertice {

    public int id;
    public int x;
    public int y;
    public int[] connected;
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

    public void setConnected(int[] connected) {
        this.connected = connected;
    }

    public int[] getConnected() {
        return this.connected;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }
}
