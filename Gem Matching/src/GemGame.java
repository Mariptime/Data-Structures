import java.awt.Font;

public class GemGame 
{
	static final int MAX_GEMS = 16;
	
	public static double indexToX(int i)
	{
		return (0.5 + i) * (1.0 / MAX_GEMS);
	}

	public static int xToIndex(double x)
	{
		return (int) ((x + 0.5 / MAX_GEMS) / (1.0 / MAX_GEMS));
	}

	public static void main(String [] args)
	{
	}
}
