
import java.util.*;

public class Melody
{
    private final Queue <Note> notes;

    public Melody (Queue<Note> notes)
    {
        this.notes = notes;
    }

    public double getTotalDuration()
    {
        double sum = 0;
        double repSum = 0;
        Queue <Note> store = new LinkedList<>();
        while (!notes.isEmpty())
        {
            if (notes.peek().isRepeat())
            {
                repSum += notes.peek().getDuration();
                store.offer(notes.poll());
                while(!notes.peek().isRepeat())
                {
                    repSum += notes.peek().getDuration();
                    store.offer(notes.poll());
                }
                repSum += notes.peek().getDuration();
                store.offer(notes.poll());
                repSum *= 2;
                sum += repSum;
            }
            else
            {
                sum += (notes.peek()).getDuration();
                store.offer(notes.poll());
            }
        }
        while ((!store.isEmpty()))
        {
            notes.offer(store.poll());
        }
        return sum;
    }

    public String toString()
    {
        String out = "";
        Queue <Note> store = new LinkedList<>();
        while(!notes.isEmpty())
        {
            out += (notes.peek()).toString() + "\n";
            store.offer(notes.poll());
        }
        while ((!store.isEmpty()))
        {
            notes.offer(store.poll());
        }
        return out;
    }

    public void changeTempo(double tempo)
    {
        Queue <Note> store = new LinkedList<>();
        while(!notes.isEmpty()){
            notes.peek().setDuration((notes.peek().getDuration())/tempo);
            store.offer(notes.poll());
        }
        while(!store.isEmpty())
        {
            notes.offer(store.poll());
        }
    }

    public void reverse()
    {
        ArrayList<Note> list = new ArrayList<>();
        while(!notes.isEmpty())
        {
            list.add(notes.poll());
        }
        for (int i = list.size()-1; i >= 0 ; i--) {
            notes.offer(list.get(i));
        }
    }

    public void append(Melody other)
    {
        Queue <Note> store = other.getNotes();
        while(!store.isEmpty())
        {
            notes.offer(store.poll());
        }
    }

    public void play()
    {
        Queue <Note> store = new LinkedList<>();
        Queue <Note> Repstore = new LinkedList<>();
        while(!notes.isEmpty())
        {
            if (notes.peek().isRepeat())
            {
                notes.peek().play();
                Repstore.offer(notes.poll());
                while(!notes.peek().isRepeat())
                {
                    notes.peek().play();
                    Repstore.offer(notes.poll());
                }
                notes.peek().play();
                Repstore.offer(notes.poll());
                while(!Repstore.isEmpty())
                {
                    Repstore.poll().play();
                }
            }
            else
                notes.poll().play();
        }
    }

    public Queue<Note> getNotes()
    {
        return notes;
    }
}
