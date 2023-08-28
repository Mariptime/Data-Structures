import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.Timer;

public class LifeModel implements ActionListener {

    private static final int SIZE = 60;
    private final LifeCell[][] grid;
    private final String file;
    private boolean randomColor;

    LifeView myView;
    Timer timer;

    public LifeModel(LifeView view, String fileName) throws IOException {
        file = fileName;
        randomColor = true;
        int r, c;
        grid = new LifeCell[SIZE][SIZE];

        for (r = 0; r < SIZE; r++) {
            for (c = 0; c < SIZE; c++) {
                grid[r][c] = new LifeCell();

            }
        }
        if (fileName == null)
        {
            for (r = 0; r < SIZE; r++) {
                for (c = 0; c < SIZE; c++) {
                    if (Math.random() > 0.85)
                        grid[r][c].setAliveNow(true);
                }
            }
        } else {
            Scanner input = new Scanner(new File(fileName));
            int numInitialCells = input.nextInt();
            for (int count = 0; count < numInitialCells; count++) {
                r = input.nextInt();
                c = input.nextInt();
                grid[r][c].setAliveNow(true);

            }
            input.close();
        }

        myView = view;
        myView.updateView(grid);

    }

    public LifeModel(LifeView view) throws IOException {
        this(view, null);
    }

    public void pause() {
        timer.stop();
    }

    public void resume() {
        timer.restart();
    }

    public void run() {
        timer = new Timer(50, this);
        timer.setCoalesce(true);
        timer.start();
    }

    public void color() {
        myView.setColor(randomColor);
        randomColor = !randomColor;
    }

    public void reset() throws IOException {
        timer.stop();
        int r, c;

        for (r = 0; r < SIZE; r++) {
            for (c = 0; c < SIZE; c++) {
                grid[r][c] = new LifeCell();

            }
        }
        if (file == null)
        {
            for (r = 0; r < SIZE; r++) {
                for (c = 0; c < SIZE; c++) {
                    if (Math.random() > 0.85)
                        grid[r][c].setAliveNow(true);
                }
            }
        } else {
            Scanner input = new Scanner(new File(file));
            int numInitialCells = input.nextInt();
            for (int count = 0; count < numInitialCells; count++) {
                r = input.nextInt();
                c = input.nextInt();
                grid[r][c].setAliveNow(true);

            }
            input.close();
        }
        myView.updateView(grid);
    }


    public void actionPerformed(ActionEvent e) {
        oneGeneration();
        myView.updateView(grid);
    }

    private List<LifeCell> getNeighbors(int x, int y) {
        List<LifeCell> neighbors = new ArrayList<>();

        int START = 0;
        int END = SIZE - 1;

        if (x == START && y == START) {
            neighbors.add(grid[x + 1][y]);
            neighbors.add(grid[x + 1][y + 1]);
            neighbors.add(grid[x][y + 1]);
        }

        if (x > START && x < END && y == START) {
            neighbors.add(grid[x + 1][y]);
            neighbors.add(grid[x + 1][y + 1]);
            neighbors.add(grid[x][y + 1]);
            neighbors.add(grid[x - 1][y + 1]);
            neighbors.add(grid[x - 1][y]);
        }

        if (x == END && y == START) {
            neighbors.add(grid[x][y + 1]);
            neighbors.add(grid[x - 1][y + 1]);
            neighbors.add(grid[x - 1][y]);
        }

        if (x == END && y > START && y < END) {
            neighbors.add(grid[x][y - 1]);
            neighbors.add(grid[x][y + 1]);
            neighbors.add(grid[x - 1][y + 1]);
            neighbors.add(grid[x - 1][y]);
        }

        if (x == END && y == END) {
            neighbors.add(grid[x][y - 1]);
            neighbors.add(grid[x - 1][y]);
            neighbors.add(grid[x - 1][y - 1]);
        }

        if (x > START && x < END && y == END) {
            neighbors.add(grid[x][y - 1]);
            neighbors.add(grid[x + 1][y - 1]);
            neighbors.add(grid[x + 1][y]);
            neighbors.add(grid[x - 1][y]);
            neighbors.add(grid[x - 1][y - 1]);
        }

        if (x == START && y == END) {
            neighbors.add(grid[x][y - 1]);
            neighbors.add(grid[x + 1][y - 1]);
            neighbors.add(grid[x + 1][y]);
        }

        if (x == START && y > START && y < END) {
            neighbors.add(grid[x][y - 1]);
            neighbors.add(grid[x + 1][y - 1]);
            neighbors.add(grid[x + 1][y]);
            neighbors.add(grid[x + 1][y + 1]);
            neighbors.add(grid[x][y + 1]);
        }

        if (x > START && x < END && y > START && y < END) {
            neighbors.add(grid[x][y - 1]);
            neighbors.add(grid[x + 1][y - 1]);
            neighbors.add(grid[x + 1][y]);
            neighbors.add(grid[x + 1][y + 1]);
            neighbors.add(grid[x][y + 1]);
            neighbors.add(grid[x - 1][y + 1]);
            neighbors.add(grid[x - 1][y]);
            neighbors.add(grid[x - 1][y - 1]);
        }

        return neighbors;

    }

    private void oneGeneration() {
        LifeCell slot;

        for (int count = 1; count <= 2; count++) {
            if (count == 1) {
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {

                        slot = grid[x][y];
                        int aliveNeighbors = 0;

                        List<LifeCell> neighborList = getNeighbors(x, y);

                        for (LifeCell neighbor : neighborList) {

                            if (neighbor.isAliveNow()) {
                                aliveNeighbors += 1;
                            } else {
                                aliveNeighbors += 0;
                            }
                        }


                        if (!slot.isAliveNow() && aliveNeighbors == 3) {
                            slot.setAliveNext(true);
                        } else if (slot.isAliveNow() && aliveNeighbors < 2) {
                            slot.setAliveNext(false);
                        } else if (slot.isAliveNow() && aliveNeighbors >= 4) {
                            slot.setAliveNext(false);
                        } else if (slot.isAliveNow() && (aliveNeighbors == 2 || aliveNeighbors == 3)) {
                            slot.setAliveNext(true);
                        }

                    }

                }

            } else if (count == 2) {
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {
                        slot = grid[x][y];

                        if (slot.isAliveNext()) {
                            slot.setAliveNow(true);
                        } else if (!slot.isAliveNext()) {
                            slot.setAliveNow(false);
                        }
                    }
                }
            }
        }
    }
}