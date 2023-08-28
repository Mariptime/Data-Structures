import java.util.ArrayList;

public class FireModel
{
    public static int SIZE = 47;
    private final FireCell[][] myGrid;
    private final FireView myView;

    public FireModel(FireView view)
    {
        myGrid = new FireCell[SIZE][SIZE];
        int setNum = 0;
        for (int r=0; r<SIZE; r++)
        {
            for (int c=0; c<SIZE; c++)
            {
                myGrid[r][c] = new FireCell();
            }
        }
        myView = view;
        myView.updateView(myGrid);
    }

    public void solve()
    {
        boolean happened = false;
        for(int r = myGrid.length - 1 ; r > 0; r--)
        {
            for (int c = 0; c <myGrid[0].length; c++)
            {
                if (r == myGrid.length - 1  && myGrid[r][c].getStatus() == FireCell.GREEN && !happened)
                {
                    myGrid[r][c].setStatus(FireCell.BURNING);
                    happened = true;

                }

                if (myGrid[r][c].getStatus() == FireCell.BURNING)
                {
                    if(r + 1 < myGrid.length && myGrid[r+1][c].getStatus() == FireCell.GREEN)
                    {
                        myGrid[r+1][c].setStatus(FireCell.BURNING);
                    }
                    if(r - 1 > 0 && myGrid[r-1][c].getStatus() == FireCell.GREEN)
                    {
                        myGrid[r-1][c].setStatus(FireCell.BURNING);
                    }
                    if(c + 1 < myGrid[r].length &&myGrid[r][c+1].getStatus() == FireCell.GREEN)
                    {
                        myGrid[r][c+1].setStatus(FireCell.BURNING);
                    }
                    if(c - 1 > 0 && myGrid[r][c-1].getStatus() == FireCell.GREEN)
                    {
                        myGrid[r][c-1].setStatus(FireCell.BURNING);
                    }
                }
                else {return;}
            }
        }
        solve();
        myView.updateView(myGrid);

    }

}