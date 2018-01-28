/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinder;

import graphHandler.Initiator;
import graphHandler.domain.Graph;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Idavor
 */
public class PathFinder {

    public static Graph graph;
    public static List<Integer> path = new ArrayList<>();
    public static List<Integer> path1 = new ArrayList<>();
    public static List<Integer> path2 = new ArrayList<>();
    public static List<Integer> path3 = new ArrayList<>();
    
    public static pathfinder.MainGUI gui = new pathfinder.MainGUI();
    public static    pathfinder.GraphPanel graphUI = new pathfinder.GraphPanel();
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        graph = Initiator.init();

        startGUI();
    }
    
    public static void startGUI(){
  
        gui.pack();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int xBoundary = (int) rect.getMaxX()/8*7 - gui.getWidth();
        int yBoundary = (int) rect.getMaxX()/16;
        gui.setLocation(xBoundary, yBoundary);
        gui.setVisible(true);
        gui.setVisible(true);
        
        graphUI.pack();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int xBoundary2 = (int) rect.getMaxX()/16 ;
        int yBoundary2 = (int) rect.getMaxX()/16;
        
        graphUI.setName("Schemat grafu");
        graphUI.setLocation(xBoundary2, yBoundary2);
        graphUI.setVisible(true);
    }
}
