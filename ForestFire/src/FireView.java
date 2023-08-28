import java.awt.*;
import javax.swing.*;

class FireView extends JPanel
{
    private FireCell[][] myGrid;

    public void updateView( FireCell[][] mg )
    {
        myGrid = mg;
        repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int testWidth = getWidth() / (FireModel.SIZE+1);
        int testHeight = getHeight() / (FireModel.SIZE+1);
        int boxSize = Math.min(testHeight, testWidth);

        for ( int r = 0; r < FireModel.SIZE; r++ )
        {
            for (int c = 0; c < FireModel.SIZE; c++ )
            {
                if (myGrid[r][c] != null)
                {
                    int ulX = (c+1) * boxSize;
                    int ulY = (r+1) * boxSize;
                    if ( myGrid[r][c].getStatus() == FireCell.DIRT )
                        g.setColor( new Color(240, 240, 180)  );
                    else if ( myGrid[r][c].getStatus() == FireCell.GREEN )
                        g.setColor( new Color(90, 190, 90) );
                    else if (myGrid[r][c].getStatus() == FireCell.COOL)
                        g.setColor((new Color(0,0,255)));
                    else
                        g.setColor( new Color(240, 40, 40) );

                    int topLeftX = ulX+2, topLeftY = ulY+2;
                    int sizeX = boxSize-2, sizeY = boxSize-2;
                    g.fillRect( topLeftX, topLeftY, sizeX, sizeY);
                }
            }
        }
    }
}
