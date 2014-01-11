import javax.swing.ImageIcon;

/**
 * <b>CollisionMonster class</b>
 * <p>
 * Models a heavier, multi-image ball that collides elastically with other balls.
 * <p>
 * These ball objects inherit all properties of the CollisionObject balls in that 
 * they also have radii, masses, centers. However, this subclass adds on the features 
 * of having two associated image icons that it switches between on collision with 
 * other balls. Additionally, ball objects of CollisionMonster type are twice as
 * heavy as CollisionObject ball objects.
 * <p>
 * The superman.png and batman.png images must be in the project directory for the 
 * image to appear.
 * <p>
 * The defaultActive boolean is what determines whether the current image on the ball
 * is of the Superman logo (default) or Batman logo.
 * @author James Wen - jrw2175
 */
public class CollisionMonster extends CollisionObject{
	private ImageIcon image1;
	private ImageIcon image2;
	private boolean defaultActive;
	
	/**
	 * Constructs a ball object with the superman logo as the initial image icon 
	 * and the specified position and speed. The ball is twice as heavy as the regular
	 * balls.
	 * @param initialX - the initial x position of the ball
	 * @param initialY - the initial y position of the ball
	 * @param xSpeed - the initial horizontal speed of the ball
	 * @param xSpeed - the initial vertical speed of the ball
	 */
	CollisionMonster(int initialX, int initialY, int xSpeed, int ySpeed){
		super(initialX, initialY, xSpeed, ySpeed);
		this.changeMass(2);
		image1 = new ImageIcon ("superman.png");
		image2 = new ImageIcon ("batman.png");
		defaultActive = true;
	}
	
	/**
	 * <b>collideReact</b>
	 * <p>
	 * Changes the current image of the ball.
	 */
	public void collideReact() {
		if(defaultActive) {
			this.changeImage(image2);
			defaultActive = false;
		}
		else {
			this.changeImage(image1);
			defaultActive = true;
		}
		
	}
}//End of CollisionMonster class