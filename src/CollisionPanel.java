import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <b>CollisionPanel class</b>
 * <p>
 * Creates and maintains a visual representation of ball objects and simulates
 * elastic collisions between them.
 * <p>
 * It is essential to find a good balance between the DELAY variable (decides refresh
 * rate) and the speed of the balls (how far it travels between each refresh). 
 * Finding a good balance allows for better resolution (more smooth animation), less
 * sticking errors (balls sticking to each other or to walls), and lessens elastic
 * violations.
 * <p>
 * The array was created large enough that a user would not be able to feasibly 
 * create enough ball objects to fill up the array completely and cause an
 * IndexOutOfBounds exception. Hence, an IndexOutOfBounds exception is not thrown or
 * caught regarding the ball object array.
 * <p>
 * The currentDirection and objectNormal variables dictate the currently set velocity
 * and ball type that any balls created by clicks would have. 
 * <p>
 * Collision detection is performed through two for loops (nested) that checks each
 * ball object against each other ball object and sees whether their centers are close
 * enough to be considered colliding (less than 2 * radius distance). 
 * <p>
 * Collision resolution is performed through a series of mathematical formulas for
 * 2D Elastic Collisions. Equations referred to are from: 
 * http://www.hoomanr.com/Demos/Elastic2/
 * <p>
 * Also, due to the way that the formulas are implemented and necessary rounding due
 * to double to int conversions, there is an overall increase in momentum as time and
 * collisions increases. However, with a reasonable amount of balls and activity, this
 * is negligible.
 * @author James Wen - jrw2175
 */

public class CollisionPanel extends JPanel {
	private final int WIDTH = 800; 
	private final int HEIGHT = 600;
	private final int DELAY = 3;
	private Timer timer;   
	private Point click;
	
	private CollisionObject[] objects = new CollisionObject[1000];
	private int usableIndex;
	
	private CardinalDirection currentDirection;
	private boolean objectNormal;
	
	CollisionObject regBall;//regular ball placeholder
	CollisionMonster bigBall;//heavier ball placeholder
	int moveSpeedX;
	int moveSpeedY;
	
	//Variables used in collision detection formula
	double xDistance;
	double yDistance;
	double xDistSquared;
	double yDistSquared;
    double pointsDistance;
	
    //Variables used in collision resolution (elastic collisions in 2D) formulas
	double dx;
    double dy;
	double collisionAngle;
	double magnitudeM;
	double magnitudeN;
	double directionM;
	double directionN;
	double calcXSpeedM;
	double calcYSpeedM;
	double calcXSpeedN;
	double calcYSpeedN;
	double finalXSpeedM;
	double finalXSpeedN;
	double finalYSpeedM;
	double finalYSpeedN;
	double actualXSpeedM;
	double actualYSpeedM;
	double actualXSpeedN;
	double actualYSpeedN;
	
	/**
	 * Sets up the panel and configures the mouse and key listeners.
	 */  
	   public CollisionPanel(){      
		  timer = new Timer(DELAY, new CollisionListener());

	      addMouseListener(new CreationListener());
	      addKeyListener(new DirectionalListener());
	      
	      usableIndex = -1;
	      
	      currentDirection = CardinalDirection.SE;
	      objectNormal = true;
	      setPreferredSize (new Dimension(WIDTH, HEIGHT));      
	      setBackground (Color.white);
	      timer.start();
	      setFocusable(true);
	   }
		
	    /**
	     *<b>paintComponent</b> 
		 * Draws all currently created balls.
		 */
	   public void paintComponent(Graphics page){
		   super.paintComponent (page);
		   for(int i=0; i <= usableIndex; i++) {
			  objects[i].getImage().paintIcon (this, page, objects[i].getX(), 
					  						   objects[i].getY());   
		   }
	   }

	   /**
	    *<b>checkCollision</b>
		* Performs collision detection and collision resolution. See class
		* explanation for methods.
		* <p>
		* The 
		*/
	   private void checkCollision() {
	   for(int m = 0; m <= usableIndex; m++) {
		   for(int n = 0; n <= usableIndex; n++) {
			   if (n != m) {//checks to see if comparing the same object
				   xDistance = (double) objects[m].getCenterX() - objects[n].getCenterX();
				   yDistance = (double) objects[m].getCenterY() - objects[n].getCenterY();
				   xDistSquared = Math.pow(xDistance, 2);
				   yDistSquared = Math.pow(yDistance, 2);
				   pointsDistance = Math.sqrt((xDistSquared + yDistSquared));
				   if(pointsDistance - 2*objects[n].getRadius() < .001 && xDistance > 0) {
					   	dx = (double) objects[m].getCenterX()-objects[n].getCenterX();
					    dy = (double) objects[m].getCenterY()-objects[n].getCenterY();
						collisionAngle = Math.atan2(dy, dx);
						magnitudeM = Math.sqrt(objects[m].getXMove()*objects[m].getXMove()
									 +objects[m].getYMove()*objects[m].getYMove());
						magnitudeN = Math.sqrt(objects[n].getXMove()*objects[n].getXMove()
									 +objects[n].getYMove()*objects[n].getYMove());
						directionM = Math.atan2(objects[m].getYMove(), objects[m].getXMove());
						directionN = Math.atan2(objects[n].getYMove(), objects[n].getXMove());
						calcXSpeedM = magnitudeM*Math.cos(directionM-collisionAngle);
						calcYSpeedM = magnitudeM*Math.sin(directionM-collisionAngle);
						calcXSpeedN = magnitudeN*Math.cos(directionN-collisionAngle);
						calcYSpeedN = magnitudeN*Math.sin(directionN-collisionAngle);
						finalXSpeedM = ((objects[m].getMass()-objects[n].getMass())*
										calcXSpeedM+(objects[n].getMass()+objects[n].getMass())
										*calcXSpeedN)/(objects[m].getMass()+objects[n].getMass());
						finalXSpeedN = ((objects[m].getMass()+objects[m].getMass())*
										calcXSpeedM+(objects[n].getMass()-objects[m].getMass())
										*calcXSpeedN)/(objects[m].getMass()+objects[n].getMass());
						finalYSpeedM = calcYSpeedM;
						finalYSpeedN = calcYSpeedN;
						actualXSpeedM = (Math.cos(collisionAngle)*finalXSpeedM+
										Math.cos(collisionAngle+Math.PI/2)*finalYSpeedM);
						actualYSpeedM = (Math.sin(collisionAngle)*finalXSpeedM+
										Math.sin(collisionAngle+Math.PI/2)*finalYSpeedM);
						actualXSpeedN = (Math.cos(collisionAngle)*finalXSpeedN+
										Math.cos(collisionAngle+Math.PI/2)*finalYSpeedN);
						actualYSpeedN = (Math.sin(collisionAngle)*finalXSpeedN+
										Math.sin(collisionAngle+Math.PI/2)*finalYSpeedN);
						objects[m].changeXSpeed(actualXSpeedM);
						objects[m].changeYSpeed(actualYSpeedM); 
						objects[n].changeXSpeed(actualXSpeedN);
						objects[n].changeYSpeed(actualYSpeedN);
						//Lessens frequency of sticking
						objects[m].moveX();
						objects[m].moveY();
						objects[n].moveX();
						objects[n].moveY();
					 if(objects[m].getMass()==2) {
						 bigBall = (CollisionMonster)objects[m];
						 bigBall.collideReact();
						 objects[m] = bigBall;
					 }
					 if(objects[n].getMass()==2) {
						 bigBall = (CollisionMonster)objects[n];
						 bigBall.collideReact();
						 objects[n] = bigBall;
					 }
				   }//End of Colliding If
			   }//End of Same Object If
		   }//End of Inner For Loop
	     }//End of Outer For Loop
	   }//End of checkCollision Method
	   
		/**
		 * <b>CollisionListener Class</b>
		 * <p>
		 * An action listener that repaints the ball objects according to the timer 
		 * to simulate movement. This also resolves collisions between the balls and
		 * the walls of the window.
		 */
	   private class CollisionListener implements ActionListener{      
		   public void actionPerformed (ActionEvent event){  
			   for(int k=0; k <= usableIndex; k++) {
				   objects[k].moveX();
				   objects[k].moveY();
				   objects[k].updateCenter();
				   if (objects[k].getX() <= 0 || objects[k].getX() >= WIDTH-objects[k].getImageSize()){
					   objects[k].changeXDirection();
					   objects[k].moveX();
				   }
				   if (objects[k].getY() <= 0 || objects[k].getY() >= HEIGHT-objects[k].getImageSize()){
					   objects[k].changeYDirection();
					   objects[k].moveY();
				   } 
			   }
			   checkCollision();
			   repaint();
		   }
	   }
	   
		/**
		 * <b>CreationListener Class</b>
		 * <p>
		 * A Mouse listener that creates a new ball object according to the current
		 * ball parameters and point at which the mouse was clicked.
		 */
	   private class CreationListener implements MouseListener{
		   public void mouseClicked(MouseEvent event){
			    click = event.getPoint();			   	
				switch (currentDirection){
		   			case NW:
		   				moveSpeedX = -1;
		   				moveSpeedY = -1;
		   				break;
		   			case N:
		   				moveSpeedX = 0;
		   				moveSpeedY = -1;
		   				break;
		   			case NE:
		   				moveSpeedX = 1;
		   				moveSpeedY = -1;
		   				break;
		   			case W:
		   				moveSpeedX = -1;
		   				moveSpeedY = 0;
		   				break;
		   			case C:
		   				moveSpeedX = 0;
		   				moveSpeedY = 0;
		   				break;
		   			case E:
		   				moveSpeedX = 1;
		   				moveSpeedY = 0;
		   				break;
		   			case SW:
		   				moveSpeedX = -1;
		   				moveSpeedY = 1;
		   				break;
		   			case S:
		   				moveSpeedX = 0;
		   				moveSpeedY = 1;
		   				break;
		   			case SE:
		   				moveSpeedX = 1;
		   				moveSpeedY = 1;
		   				break;
		   	   }
			   int initialX = (int)click.getX();
			   int initialY = (int)click.getY();
			   if(objectNormal) {
				   regBall = new CollisionObject(initialX,initialY,moveSpeedX,moveSpeedY);
				   usableIndex++;
				   	objects[usableIndex] = regBall;
			   }
			   else {
				   bigBall = new CollisionMonster(initialX,initialY,moveSpeedX,moveSpeedY);
				   usableIndex++;
				   	objects[usableIndex] = bigBall;
			   }
		   }
		   public void mouseEntered(MouseEvent event){}
		   public void mouseExited(MouseEvent event){}
		   public void mousePressed(MouseEvent event){}
		   public void mouseReleased(MouseEvent event){}
	   }
		
	   /**
		 * <b>DirectionalListener Class</b>
		 * <p>
		 * A Key Listener that sets the current ball parameters (the velocity vectors
		 * and ball type) of any current balls to be created based on which keys 
		 * are pressed.
		 */
	   private class DirectionalListener implements KeyListener{
		   public void keyPressed(KeyEvent event){
			   switch (event.getKeyCode()){
			   		case KeyEvent.VK_W:
			   			currentDirection = CardinalDirection.NW;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_E:
			   			currentDirection = CardinalDirection.N;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_R:
			   			currentDirection = CardinalDirection.NE;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_S:
			   			currentDirection = CardinalDirection.W;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_D:
			   			currentDirection = CardinalDirection.C;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_F:
			   			currentDirection = CardinalDirection.E;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_X:
			   			currentDirection = CardinalDirection.SW;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_C:
			   			currentDirection = CardinalDirection.S;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_V:
			   			currentDirection = CardinalDirection.SE;
			   			objectNormal = true;
			   			break;
			   		case KeyEvent.VK_U:
			   			currentDirection = CardinalDirection.NW;
			   			objectNormal = false;
			   			break;
			   		case KeyEvent.VK_I:
			   			currentDirection = CardinalDirection.N;
			   			objectNormal = false;
			   			break;
			   		case KeyEvent.VK_O:
			   			currentDirection = CardinalDirection.NE;
			   			objectNormal = false;
			   			break;
			   		case KeyEvent.VK_J:
			   			currentDirection = CardinalDirection.W;
			   			objectNormal = false;
			   			break;
			   		case KeyEvent.VK_K:
			   			currentDirection = CardinalDirection.C;
			   			objectNormal = false;
			   			break;
			   		case KeyEvent.VK_L:
			   			currentDirection = CardinalDirection.E;
			   			objectNormal = false;
			   			break;
			   		case KeyEvent.VK_M:
			   			currentDirection = CardinalDirection.SW;
			   			objectNormal = false;
			   			break;
			   		case KeyEvent.VK_COMMA:
			   			currentDirection = CardinalDirection.S;
			   			objectNormal = false;
			   			break;
			   		case KeyEvent.VK_PERIOD:
			   			currentDirection = CardinalDirection.SE;
			   			objectNormal = false;
			   			break;
			   }
		   }
		   public void keyTyped(KeyEvent event){}
		   public void keyReleased(KeyEvent event){}
	   }
}//End of CollisionPanel class