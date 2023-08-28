import javax.swing.*;
import java.awt.* ;
import java.lang.Math ;

class SnowFlakePanel extends JPanel
{

	public SnowFlakePanel()
	{
		setPreferredSize( new Dimension(400, 400) );
		setBackground( Color.WHITE );
	}

	private void drawStar( Graphics gr, int x, int y, int size )
	{
		int endX ;
		int endY ;

		if ( size <= 2 ) return;
		for ( int i = 0; i<6; i++ )
		{
			endX = x + (int)(size*Math.cos( (2*Math.PI/6)*i ));
			endY = y - (int)(size*Math.sin( (2*Math.PI/6)*i ));
			gr.drawLine( x, y, endX, endY );
			drawStar( gr, endX, endY, size/3 );
		}
	}

	public void paintComponent( Graphics g )
	{
		int width  = getWidth();
		int height = getHeight();
		int min;

		super.paintComponent( g );
		g.setColor( new Color((int)(Math.random() * 0x1000000)) );

		min = Math.max(height, width);

		drawStar( g, width/2, height/2, min/4 );
	}
}

public class Snowflake
{
	public static void main ( String[] args )
	{
		JFrame frame = new JFrame( "Snowflake" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().add( new SnowFlakePanel() );
		frame.pack();
		frame.setVisible( true );
	}
}