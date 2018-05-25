package graphHandler.domain;

public class Edge {

    int id;
    int A;
    int B;
    double weight;
    boolean oneWay;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setA(int A) {
        this.A = A;
    }

    public int getA() {
        return this.A;
    }

    public void setB(int B) {
        this.B = B;
    }

    public int getB() {
        return this.B;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public boolean getOneWay() {
        return this.oneWay;
    }
}
