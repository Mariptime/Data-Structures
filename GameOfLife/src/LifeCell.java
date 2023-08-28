import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LifeCell
{
    private boolean aliveNow;

	private boolean aliveNext;

    public LifeCell()
    {
        aliveNow = false;
        aliveNext = false;
    }

    public boolean isAliveNow() { return aliveNow; }

    public void    setAliveNow(boolean a) { aliveNow = a; }

    public boolean isAliveNext() { return aliveNext; }

    public void    setAliveNext(boolean a) { aliveNext = a; }
}
