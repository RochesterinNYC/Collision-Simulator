/**
 * <b>CollisionObject class</b>
 * <p>
 * Models a ball that collides elastically with other balls.
 * <p>
 * The ball has a radius, mass, center, and associated image icon.
 * <p>
 * The superman.png image must be in the project directory for the image to appear.
 * @author James Wen - jrw2175
 */

import javax.swing.ImageIcon;

public class CollisionObject {
	
	private final int IMAGE_SIZE = 45;
	private ImageIcon image;
	private int x;
	private int y;
	private int moveX;
	private int moveY;
	private int mass;
	private int centerX;
	private int centerY;
	private int radius;
	
	/**
	 * Constructs a ball object with the superman logo as the image icon and the
	 * specified position and speed.
	 * @param initialX - the initial x position of the ball
	 * @param initialY - the initial y position of the ball
	 * @param xSpeed - the initial horizontal speed of the ball
	 * @param xSpeed - the initial vertical speed of the ball
	 */
	public CollisionObject(int initialX, int initialY, int xSpeed, int ySpeed) {
	    image = new ImageIcon ("superman.png");
	    x = initialX;
	    y = initialY;
	    moveX = xSpeed;
	    moveY = ySpeed;
	    radius = IMAGE_SIZE/2;
	    centerX = x + radius;
	    centerY = y + radius;
	    mass = 1;
	}
	
	/**
	 * <b>changeMass</b>
	 * <p>
	 * Changes the mass of the ball.
	 * <p>
	 * @param newMass - the new mass of the ball
	 */
	public void changeMass(int newMass) {
		mass = newMass;
	}
	
	/**
	 * <b>changeImage</b>
	 * <p>
	 * Changes the image of the ball.
	 * <p>
	 * @param newImage - the new image of the ball
	 */	
	public void changeImage(ImageIcon newImage){
		image = newImage;
	}
	
	/**
	 * <b>moveX</b>
	 * <p>
	 * Moves the ball horizontally.
	 */		
	public void moveX(){
		x += moveX;
	}

	/**
	 * <b>moveY</b>
	 * <p>
	 * Moves the ball vertically.
	 */
	public void moveY(){
		y += moveY;
	}
	
	/**
	 * <b>changeXDirection</b>
	 * <p>
	 * Changes the ball's horizontal direction.
	 */
	public void changeXDirection(){
		moveX *= -1;
	}
	
	/**
	 * <b>changeYDirection</b>
	 * <p>
	 * Changes the ball's vertical direction.
	 */
	public void changeYDirection(){
		moveY *= -1;
	}
	
	/**
	 * <b>changeXSpeed</b>
	 * <p>
	 * Changes the ball's horizontal speed.
	 * @param xSpeed - the new horizontal speed of the ball
	 */
	public void changeXSpeed(double xSpeed) {
		moveX = (int) Math.ceil(xSpeed);
	}
	
	/**
	 * <b>changeYSpeed</b>
	 * <p>
	 * Changes the ball's vertical speed.
	 * @param ySpeed - the new vertical speed of the ball
	 */
	public void changeYSpeed(double ySpeed) {
		moveY = (int) Math.ceil(ySpeed);
	}
	
	/**
	 * <b>updateCenter</b>
	 * <p>
	 * Updates the ball's center coordinates.
	 */
	public void updateCenter(){
		centerX = x + IMAGE_SIZE/2;
	    centerY = y + IMAGE_SIZE/2;
	}
	
	/**
	 * <b>getImage</b>
	 * <p>
	 * @return image - the ball's current image
	 */
	public ImageIcon getImage(){
		return image;
	}
	
	/**
	 * <b>getImageSize</b>
	 * <p>
	 * @return IMAGE_SIZE - the ball's image size
	 */
	public int getImageSize() {
		return IMAGE_SIZE;
	}
	
	/**
	 * <b>getRadius</b>
	 * <p>
	 * @return radius - the ball's radius
	 */
	public int getRadius(){
		return radius;
	}
	/**
	 * <b>getMass</b>
	 * <p>
	 * @return mass - the ball's mass
	 */
	public int getMass(){
		return mass;
	}
	
	/**
	 * <b>getX</b>
	 * <p>
	 * @return x - the ball's current x position
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * <b>getY</b>
	 * <p>
	 * @return y - the ball's current y position
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * <b>getCenterX</b>
	 * <p>
	 * @return centerX - the center of the ball's x position
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * <b>getCenterY</b>
	 * <p>
	 * @return centerY - the center of the ball's Y position
	 */	
	public int getCenterY() {
		return centerY;
	}
	
	/**
	 * <b>getXMove</b>
	 * <p>
	 * @return moveX - the horizontal speed of the ball
	 */
	public int getXMove() {
		return moveX;
	}

	/**
	 * <b>getYMove</b>
	 * <p>
	 * @return moveY - the vertical speed of the ball
	 */
	public int getYMove() {
		return moveY;
	}
}//End of CollisionObject class