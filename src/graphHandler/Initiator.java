
package graphHandler;

import graphHandler.domain.Edge;
import graphHandler.domain.Graph;
import graphHandler.domain.Vertice;
import java.util.ArrayList;
import java.util.List;
import static pathfinder.PathFinder.graph;

/**
 *
 * @author Idavor
 */
public class Initiator {

    public static Graph init() throws InstantiationException, IllegalAccessException {
        
        List<Edge> edges = new ArrayList<Edge>();
        List<Vertice> vertices = new ArrayList<Vertice>();
    
        int i = 0;
        int n = 6;
        
        Graph graph = new Graph();
    
        Vertice[] vert = new Vertice[22];
        
        for(int v = 0; v<22; v++){
            vert[v] = new Vertice();
        }
        
         vert[0].setX(150);     vert[0].setY(750);
         vert[1].setX(100);      vert[1].setY(100);
         vert[2].setX(250);     vert[2].setY(150);
         vert[3].setX(300);     vert[3].setY(200);
         vert[4].setX(50);      vert[4].setY(250);
         vert[5].setX(400);     vert[5].setY(100);
         vert[6].setX(500);     vert[6].setY(200);
         vert[7].setX(250);     vert[7].setY(350);
         vert[8].setX(150);     vert[8].setY(450);
         vert[9].setX(400);     vert[9].setY(500);
         vert[10].setX(550);    vert[10].setY(450);
         vert[11].setX(800);    vert[11].setY(350);
         vert[12].setX(800);    vert[12].setY(200);
         vert[13].setX(1000);   vert[13].setY(100);
         vert[14].setX(1100);   vert[14].setY(350);
         vert[15].setX(1000);   vert[15].setY(550);
         vert[16].setX(800);    vert[16].setY(700);
         vert[17].setX(600);    vert[17].setY(700);
         vert[18].setX(350);   vert[18].setY(900);
         vert[19].setX(850);    vert[19].setY(1000);
         vert[20].setX(1050);   vert[20].setY(900);

         
        for(int v = 0; v<21; v++){
            vertices.add(vert[v]);
        }

        
        Edge[] edge= new Edge[34];
        for(int e = 0; e<34; e++){
            edge[e] = new Edge();
        }
        
        edge[0].setA(1); edge[0].setB(4); 
        edge[1].setA(2); edge[1].setB(4);
        edge[2].setA(1); edge[2].setB(2); 
        edge[3].setA(1); edge[3].setB(5);
        edge[4].setA(2); edge[4].setB(3); 
        edge[5].setA(3); edge[5].setB(5);
        edge[6].setA(4); edge[6].setB(8); 
        edge[7].setA(7); edge[7].setB(8);
        edge[8].setA(3); edge[8].setB(10); 
        edge[9].setA(3); edge[9].setB(7);
        edge[10].setA(6); edge[10].setB(5); 
        edge[11].setA(6); edge[11].setB(10);
        edge[12].setA(6); edge[12].setB(12); 
        edge[13].setA(10); edge[13].setB(12);
        edge[14].setA(11); edge[14].setB(12); 
        edge[15].setA(12); edge[15].setB(13);
        edge[16].setA(11); edge[16].setB(14); 
        edge[17].setA(13); edge[17].setB(14);
        edge[18].setA(11); edge[18].setB(16); 
        edge[19].setA(14); edge[19].setB(15);
        edge[20].setA(15); edge[20].setB(16); 
        edge[21].setA(20); edge[21].setB(15);
        edge[22].setA(20); edge[22].setB(19); 
        edge[23].setA(16); edge[23].setB(19);
        edge[24].setA(16); edge[24].setB(17); 
        edge[25].setA(18); edge[25].setB(19);
        edge[26].setA(10); edge[26].setB(9); 
        edge[27].setA(10); edge[27].setB(17);
        edge[28].setA(17); edge[28].setB(18); 
        edge[29].setA(9); edge[29].setB(18);
        edge[30].setA(8); edge[30].setB(9);
        edge[31].setA(8); edge[31].setB(18); 
        edge[32].setA(0); edge[32].setB(8);
        edge[33].setA(0); edge[33].setB(18); 

        
        for(int e = 0; e<34; e++){
            edges.add(edge[e]);
        }
  
        
        graph.verticesList = vertices;
        graph.edgesList = edges;
        
        
        return graph;
    }

}
