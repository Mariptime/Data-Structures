import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

public class StdDrawDemo 
{
	public static void drawSquare()
	{
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.filledRectangle(0.5, 0.5, 0.1, 0.1);
	}
	
	public static void drawCircleOnClick()
	{
		Random rng = new Random();
		
		while (true)
		{
			if (StdDraw.isMousePressed()) {
				StdDraw.setPenColor(new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256)));
				StdDraw.filledCircle(StdDraw.mouseX(), StdDraw.mouseY(), rng.nextDouble() / 4);
				while (StdDraw.isMousePressed());
			}
		}
	}
	
	public static void spinningCircles()
	{
		StdDraw.setScale(-2, +2);
		StdDraw.enableDoubleBuffering();
		StdDraw.setPenColor(Color.RED);
		
		boolean right = true;
		
		double t = 0.0;

		while (true)
		{
			if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
				right = false;
			else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
				right = true;
			
			double x = Math.sin(t);
			double y = Math.cos(t);
			
			StdDraw.clear();
			StdDraw.filledCircle(x, y, 0.2);
			StdDraw.filledCircle(-x, -y, 0.2);
			StdDraw.show();
			StdDraw.pause(10);
			
			if (right)
				t += 0.02;
			else
				t -= 0.02;
		}
	}
	
	public static void main(String[] args) 
	{
	}
}