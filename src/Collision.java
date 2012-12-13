import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

/**
 * <b>Collision class</b>
 * <p>
 * Creates an elastic collision simulator for two types of balls.
 * @author James Wen - jrw2175
 */

public class Collision {
	public static void main(String[] args) {
	      JFrame frame = new JFrame ("Collisions");      
	      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	      frame.getContentPane().add(new CollisionPanel());      
	      frame.pack();      
	      frame.setVisible(true);
	}
}//End of Collision class