
package graphHandler.domain;

import graphHandler.domain.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    public List<Edge> edgesList = new ArrayList<Edge>();
    public List<Vertice> verticesList = new ArrayList<Vertice>();

    public void setEdges(List<Edge> edgesList) {
        this.edgesList = edgesList;
    }

    public List<Edge> getEdges() {
        return this.edgesList;
    }

    public void setVertices(List<Vertice> verticesList) {
        this.verticesList = verticesList;
    }

    public List<Vertice> getVertices() {
        return this.verticesList;
    }
}
