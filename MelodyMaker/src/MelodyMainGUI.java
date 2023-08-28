import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

public class MelodyMainGUI extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 6228378229836664288L;

    private Melody  melody;
    private Melody  melodyCopy;
    private double  currTempo;

    private final JTextField fileName;
    private final JTextField tempo;
    private final JLabel     duration;
    private final JTextArea  printDisplay;
    private final JButton    loadButton;
    private final JButton    playButton;
    private final JButton    stopButton;
    private final JButton    reverseButton;
    private final JButton    saveButton;
    private final JButton    printButton;
    private final JButton    appendButton;
    private final JButton    clearButton;
    private final JButton    resetButton;

    private Thread musicThread;

    private MelodyMainGUI()
    {
        super("Melody Maker");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fileName = new JTextField(10);
        fileName.setEditable(false);
        fileName.setText("<no song loaded>");
        JPanel filenamePanel = new JPanel(new BorderLayout());
        filenamePanel.add(new JLabel("File: "), "West");
        filenamePanel.add(fileName, "Center");
        tempo = new JTextField(5);
        tempo.setEditable(true);
        tempo.setText("1.0");

        JPanel tempoPanel1 = new JPanel(new GridLayout(1, 3));
        tempoPanel1.add(new JLabel("Tempo: "), "West");
        tempoPanel1.add(tempo, "West");
        tempo.setToolTipText("0.5 makes half as long, 2.0 makes twice as long");

        JPanel tempoPanel = new JPanel(new GridLayout(1, 3));
        tempoPanel.add(tempoPanel1);
        tempoPanel.add(new JPanel(new GridLayout(1, 2))); tempoPanel.add(new JPanel(new GridLayout(1, 2)));
        loadButton     = new JButton("load");
        playButton     = new JButton("play");
        stopButton     = new JButton("stop");
        reverseButton  = new JButton("reverse");
        printButton    = new JButton("print"); printButton.setToolTipText("Print the contents of the melody to the output pane below");
        appendButton   = new JButton("append");
        saveButton     = new JButton("save");
        clearButton    = new JButton("clear"); clearButton.setToolTipText("Clear the output pane below");
        resetButton    = new JButton("reset");

        this.setInitialButtonEnabledState();
        JPanel buttons1  = new JPanel(new GridLayout(1, 3));
        JPanel buttons2  = new JPanel(new GridLayout(1, 3));
        JPanel buttons3  = new JPanel(new GridLayout(1, 3));
        JPanel buttonBar = new JPanel(new GridLayout(3, 2));
        buttons1.add(loadButton);
        buttons1.add(saveButton);
        buttons1.add(resetButton);

        buttons2.add(playButton);
        buttons2.add(stopButton);
        buttons2.add(reverseButton);

        buttons3.add(appendButton);
        buttons3.add(printButton);
        buttons3.add(clearButton);
        buttonBar.add(filenamePanel);
        buttonBar.add(buttons1);
        buttonBar.add(tempoPanel);
        buttonBar.add(buttons2);
        duration = new JLabel("Duration: ");
        buttonBar.add(duration);
        buttonBar.add(buttons3);
        buttonBar.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        printDisplay = new JTextArea(20, 30);
        printDisplay.setToolTipText("Print the melody to the pane below");
        printDisplay.setEditable(false);
        printDisplay.setLineWrap(true);

        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(BorderFactory.createEmptyBorder(
                10,
                10,
                10,
                10));
        pane.add(new JScrollPane(printDisplay), "South");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonBar,"North");
        panel.add(pane);
        this.getContentPane().add(panel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addButtonListeners();
        currTempo = 1.0;
    }

    private void setInitialButtonEnabledState()
    {
    	loadButton.setEnabled(true);
        playButton.setEnabled(false);
        stopButton.setEnabled(false);
        reverseButton.setEnabled(false);
        stopButton.setEnabled(false);
        appendButton.setEnabled(false);
        printButton.setEnabled(false);
        tempo.setEnabled(false);
        saveButton.setEnabled(false);
        clearButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    private void addButtonListeners()
    {
        loadButton.addActionListener(this);
        playButton.addActionListener(this);
        reverseButton.addActionListener(this);
        stopButton.addActionListener(this);
        appendButton.addActionListener(this);
        printButton.addActionListener(this);
        tempo.addActionListener(this);
        saveButton.addActionListener(this);
        clearButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == loadButton)
            loadFile();

        if (e.getSource() == playButton) {
        	if (playButton.getText().equalsIgnoreCase("play"))
        		doPlay();

        	else if (playButton.getText().equalsIgnoreCase("pause"))
        		doPause();

        	else if (playButton.getText().equalsIgnoreCase("resume"))
        		doResume();
        }

        if (e.getSource() == stopButton)
            doStop();

        if (e.getSource() == tempo)
        	changeTempo();

        if (e.getSource() == reverseButton)
            doReverse();

        if (e.getSource() == appendButton)
        	appendSong();

        if (e.getSource() == printButton)
        	printSong();

        if (e.getSource() == saveButton)
        	doSave();

        if (e.getSource() == clearButton)
        	doClear();

        if (e.getSource() == resetButton)
        	reset();
    }

    private void doPlay()
    {
    	this.loadButton.setEnabled(false);
        this.stopButton.setEnabled(true);
        this.appendButton.setEnabled(false);
        this.reverseButton.setEnabled(false);
        this.tempo.setEnabled(false);
        this.saveButton.setEnabled(false);
        this.resetButton.setEnabled(false);
        melodyCopy = copyMelody(melody);
        musicThread = new Thread() {
        	@Override
        	public void run() {
        		melody.play();
        		loadButton.setEnabled(true);
                stopButton.setEnabled(false);
                appendButton.setEnabled(true);
                reverseButton.setEnabled(true);
                tempo.setEnabled(true);
                saveButton.setEnabled(true);
                resetButton.setEnabled(true);
                melodyCopy = null;
                playButton.setText("play");
        	}
        };
        musicThread.start();
        playButton.setText("pause");
    }

    @SuppressWarnings("deprecation")
    private void doPause() {
    	musicThread.suspend();
    	playButton.setText("resume");
    }

    @SuppressWarnings("deprecation")
    private void doResume() {
    	musicThread.resume();
    	playButton.setText("pause");
    }

	@SuppressWarnings("deprecation")
	private void doStop()
    {
    	musicThread.stop();
    	StdAudio.init();
    	melody = melodyCopy;
    	melodyCopy = null;
    	loadButton.setEnabled(true);
    	playButton.setEnabled(true);
        stopButton.setEnabled(false);
        appendButton.setEnabled(true);
        reverseButton.setEnabled(true);
        tempo.setEnabled(true);
        saveButton.setEnabled(true);
        resetButton.setEnabled(true);
        playButton.setText("play");
    }

	private void changeTempo()
    {
    	Scanner input = new Scanner(tempo.getText());
    	Double inTempo = null;
    	if (input.hasNextDouble())
    		inTempo = input.nextDouble();
    	if (inTempo == null || inTempo <= 0)
    		tempo.setText(currTempo+"");

    	else{
    		double newTempo = currTempo / inTempo;
    		this.melody.changeTempo(newTempo);

    		currTempo = inTempo;
    		duration.setText("Duration: "+this.melody.getTotalDuration()+" seconds");
    	}
    	input.close();
    }

    private void doReverse()
    {
        if (this.reverseButton.getText().equals("reverse"))
            this.reverseButton.setText("re-reverse");
        else
            this.reverseButton.setText("reverse");

        this.melody.reverse();
    }

    private void appendSong()
    {
        JFileChooser chooser = new JFileChooser(new File("."));

        chooser.setDialogTitle("Append a song");

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;


                return f.getName().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Song files";
            }
        });

        File   newFile     = null;
        String newFileName = null;

        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            newFile     = chooser.getSelectedFile();
            newFileName = newFile.getName();
        }
        else
            return;

        Scanner input = null;
        try {
            input = new Scanner(newFile);
            this.melody.append(new Melody(read(input)));
            duration.setText("Duration: "+this.melody.getTotalDuration()+" seconds");
            tempo.setText("1.0");
            currTempo = 1.0;
            reverseButton.setText("reverse");
        }
        catch (FileNotFoundException e) {
            System.out.println("Can't find file: " + newFileName);
        }
    }

    private void printSong()
    {
    	String str = "";
    	Melody temp;
    	if (melodyCopy != null)
    		temp = copyMelody(melodyCopy);
    	else
    		temp = copyMelody(melody);
    	for (Note n : temp.getNotes())
    		str += n.toString() + "\n";
    	printDisplay.setText(printDisplay.getText()+str+"\n");
    }

    private String melodyString() {
    	String str = "";
    	Melody temp = copyMelody(melody);
    	Queue<Note> notes = temp.getNotes();
    	while (!notes.isEmpty()) {
    		str += notes.poll().toString() + "\n";
    	}
    	return str;
    }

    private void doSave()
    {
    	JFileChooser saver = new JFileChooser(new File("."));
    	saver.setDialogTitle("Save song as .txt file");

    	File newFile;
    	String newFileDir;
    	String newFileName;
    	int returnVal = saver.showSaveDialog(this);

    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		newFile = saver.getSelectedFile();
    		newFileDir = newFile.toString();
    		if (!newFileDir.endsWith(".txt")) {
    			File newFile2 = new File(newFileDir + ".txt");
    			try {
    				Files.move(newFile.toPath(), newFile2.toPath(), StandardCopyOption.REPLACE_EXISTING);
    			}
    			catch (IOException e) {
    			}
    			newFile = newFile2;
    		}

    		newFileName = newFile.getName();
    	}

    	else
    		return;
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
    		writer.write(melodyString());
    		writer.close();
    		loadFile(newFile);
    	}
    	catch(IOException e) {
    		System.out.println("Can't find file: " + newFileName);
    	}
    }

    private void doClear()
    {
    	printDisplay.setText("");
    }

    private void reset()
    {
        setInitialButtonEnabledState();
        fileName.setText("<no song loaded>");
        reverseButton.setText("reverse");
        tempo.setText("1.0");
        currTempo = 1.0;
        duration.setText("Duration: ");
        printDisplay.setText("");
    }

    private void loadFile()
    {
        JFileChooser chooser = new JFileChooser(new File("."));

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;

                return f.getName().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Song files";
            }
        });

        File   newFile     = null;
        String newFileName = null;

        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            newFile     = chooser.getSelectedFile();
            newFileName = newFile.getName();
        }
        else
            return;

        Scanner input = null;
        try {
            input = new Scanner(newFile);
            this.melody = new Melody(read(input));
            this.fileName.setText(newFileName);
            this.playButton.setEnabled(true);
            this.reverseButton.setEnabled(true);
            this.appendButton.setEnabled(true);
            this.tempo.setEnabled(true);
            this.printButton.setEnabled(true);
            this.saveButton.setEnabled(true);
            this.duration.setText("Duration: "+this.melody.getTotalDuration()+" seconds");
            this.tempo.setText("1.0");
            currTempo = 1.0;
            this.reverseButton.setText("reverse");
        }
        catch (FileNotFoundException e) {
            System.out.println("Can't find file: " + newFileName);
        }
    }

    private void loadFile(File file) {
    	try {
            Scanner input = new Scanner(file);
            this.melody = new Melody(read(input));
            this.fileName.setText(file.getName());
        }
        catch (FileNotFoundException e) {
            System.out.println("Can't find file: " + file.getName());
        }
    }

    private static Queue<Note> read(Scanner input)
    {
        Queue<Note> song = new LinkedList<Note>();

        while (input.hasNext()) {
            double duration = input.nextDouble();
            Pitch pitch = Pitch.valueOf(input.next());
            if(pitch.equals(Pitch.R)) {
                song.add(new Note(duration, pitch, input.nextBoolean()));
            } else {
                song.add(new Note(duration, pitch, input.nextInt(),
                    Accidental.valueOf(input.next()), input.nextBoolean()));
            }
        }

        return song;
    }

    private Melody copyMelody(Melody other) {
    	Queue<Note> notes = other.getNotes();
    	Queue<Note> notesCopy = new LinkedList<>();
    	for (Note note : notes) {
    		notesCopy.offer(note);
    	}
    	return new Melody(notesCopy);
    }

    public static void main(String[] args)
    {
        System.out.println("WORKING");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { }

        new MelodyMainGUI();
    }
}
