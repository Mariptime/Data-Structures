import java.awt.*;
import javax.swing.*;

public class LifeView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 60;
    private boolean randomColor;

    private LifeCell[][] grid;

    public LifeView() {
        randomColor = false;
        grid = new LifeCell[SIZE][SIZE];
    }

    public void updateView(LifeCell[][] mg) {
        grid = mg;
        repaint();
    }

    public void setColor(boolean b) {
        randomColor = b;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int testWidth = getWidth() / (SIZE + 1);
        int testHeight = getHeight() / (SIZE + 1);
        int boxSize = Math.min(testHeight, testWidth);

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] != null) {
                    if (grid[r][c].isAliveNow()) {
                        if (randomColor == false) {
                            g.setColor(Color.blue);
                        } else if (randomColor) {
                            g.setColor(new Color((int) (Math.random() * 0x1000000)));
                        }
                    } else
                        g.setColor(new Color(235, 235, 255));

                    g.fillRect((c + 1) * boxSize, (r + 1) * boxSize, boxSize - 2, boxSize - 2);
                }
            }
        }
    }
}
