import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class MazeApp extends JFrame implements ActionListener {

	private static int fontSize = 16;

	private static int timerInterval = 500;

	private static final long serialVersionUID = 6228378229836664288L;

	Maze maze;
	MazeSolver solver;
	boolean mazeLoaded;

	JTextField filename;
	JTextField timerField;
	JTextField fontField;
	JTextArea  mazeDisplay;
	JTextArea  pathDisplay;
	JButton    loadButton;
	JButton    solveButton;
	JButton    stepButton;
	JButton    solverType;
	JButton    resetButton;
	JButton    quitButton;
	Timer      timer;

	public MazeApp() {
		super("Amazing Maze Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		filename = new JTextField(10);
		filename.setEditable(false);
		filename.setText("<no maze loaded>");
		timerField = new JTextField(5);
		fontField  = new JTextField(5);
		JPanel filenamePanel = new JPanel(new BorderLayout());
		filenamePanel.add(new JLabel("File: "), "West");
		filenamePanel.add(filename, "Center");

		JPanel fontPanel = new JPanel(new BorderLayout());
		fontPanel.add(new JLabel("Font size:"), "West");
		fontPanel.add(fontField, "Center");

		JPanel timerPanel = new JPanel(new BorderLayout());
		timerPanel.add(new JLabel("Timer (ms):"), "West");
		timerPanel.add(timerField, "Center");

		JPanel controls = new JPanel(new FlowLayout());
		controls.add(timerPanel);
		controls.add(fontPanel);
		loadButton = new JButton("load");
		resetButton = new JButton("reset");
		quitButton = new JButton("quit");
		solverType = new JButton("stack");
		solveButton = new JButton("start");
		stepButton = new JButton("step");
		JPanel buttons1 = new JPanel(new GridLayout(1, 3));
		JPanel buttons2 = new JPanel(new GridLayout(1, 3));
		JPanel buttonBar = new JPanel(new GridLayout(2, 2));
		buttons1.add(loadButton);
		buttons1.add(resetButton);
		buttons1.add(quitButton);
		buttons2.add(solverType);
		buttons2.add(solveButton);
		buttons2.add(stepButton);
		buttonBar.add(filenamePanel);
		buttonBar.add(buttons1);
		buttonBar.add(controls);
		buttonBar.add(buttons2);
		buttonBar.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		timer = new Timer(timerInterval, this);
		mazeDisplay = new JTextArea(20, 30);
		mazeDisplay.setEditable(false);
		pathDisplay = new JTextArea(4, 30);
		pathDisplay.setEditable(false);
		JPanel pane = new JPanel(new BorderLayout());
		pane.setBorder(BorderFactory.createEmptyBorder(
				10,
				10,
				10,
				10)
				);
		pane.add(new JScrollPane(mazeDisplay), "Center");
		pane.add(new JScrollPane(pathDisplay), "South");
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(buttonBar,"North");
		panel.add(pane);
		this.getContentPane().add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		loadButton.addActionListener(this);
		filename.addActionListener(this);
		solveButton.addActionListener(this);
		solverType.addActionListener(this);
		stepButton.addActionListener(this);
		resetButton.addActionListener(this);
		quitButton.addActionListener(this);

		timerField.addActionListener(this);
		fontField.addActionListener(this);
		doTimer();
		doFontSize();
		mazeLoaded = false;
		this.maze = new Maze();
		makeNewSolver();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( (e.getSource() == loadButton) || (e.getSource() == filename) ){
			loadFile();
		}
		if (e.getSource() == solveButton) {
			if (mazeLoaded) {
				makeNewSolver();
				solveButton();
			}
		}
		if (e.getSource() == resetButton) {
			reset();
		}
		if (e.getSource() == solverType) {
			toggleSolverType();
			makeNewSolver();
		}
		if (e.getSource() == quitButton) {
			doQuit();
		}
		if (e.getSource() == timerField) {
			doTimer();
		}
		if (e.getSource() == fontField) {
			doFontSize();
		}
		if (e.getSource() == stepButton) {
			if (mazeLoaded)
				doStep();
		}
		if (e.getSource() == timer) {
			if (mazeLoaded) {
				doStep();
			}
		}
	}

	private void doTimer() {
		int newValue = -1;
		try {
			newValue = Integer.parseInt(timerField.getText());
		} catch (NumberFormatException nfe) {
		}
		if (newValue>0)
			timerInterval = newValue;
		timerField.setText(Integer.toString(timerInterval));
		timer.setDelay(timerInterval);
	}


	private void doFontSize() {
		int newValue = -1;
		try {
			newValue = Integer.parseInt(fontField.getText());
		} catch (NumberFormatException nfe) {
		}
		if (newValue>0)
			fontSize = newValue;
		fontField.setText(Integer.toString(fontSize));
		mazeDisplay.setFont(new Font("Courier New",Font.BOLD, fontSize));
		pathDisplay.setFont(new Font("Courier New",Font.BOLD, fontSize));

	}

	private void doQuit() {
		System.exit(0);
	}

	private void reset() {
		maze.reset();
		makeNewSolver();
		updateMaze();
	}

	private void doStep() {
		if (mazeLoaded && !solver.isSolved()) {
			solver.step();
			if (solver.isSolved()) {
				solveButton();
				timer.stop();
				updateMaze();
			}
		}
		updateMaze();
	}

	private void toggleSolverType() {
		reset();
	}

	private void makeNewSolver() {
		solver = new MazeSolverStack(this.maze);
	}

	private void solveButton() {
		String label = solveButton.getText();
		if (solver.isSolved()) {
			solveButton.setBackground(Color.white);
			solveButton.setText("start");
			return;
		}
		if (label.equalsIgnoreCase("start")) {
			if ( mazeLoaded ) {
				solveButton.setText("stop");
				solveButton.setBackground(Color.red);
				timer.start();
			}
		} else if (label.equalsIgnoreCase("stop")) {
			solveButton.setText("start");
			solveButton.setBackground(Color.green);
			timer.stop();
		}
	}

	private void loadFile() {
		JFileChooser chooser = new JFileChooser(new File("."));
		chooser.setFileFilter(new FileFilter() {
			final String description = "Maze files";

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) 
					return true;


                return f.getName().startsWith("maze-");
            }

			@Override
			public String getDescription() {
				return this.description;
			}

		});
		File newFile = null;
		String newFileName = null;
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			newFile = chooser.getSelectedFile();
			newFileName = newFile.getName();
		} else {
			return;
		}
		if (! maze.loadMaze(newFile.getPath()) ) {
			JOptionPane.showMessageDialog(this, "Cannot load file: "+newFileName);
		} else {
			filename.setText(newFileName);
			solveButton.setText("start");
			solveButton.setBackground(Color.green);
			mazeLoaded=true;
			timer.stop();
			reset();
		}
	}

	private void updateMaze() {
		if (mazeLoaded) {
			mazeDisplay.setText(maze.toString());
			pathDisplay.setText(solver.getPath());
		}
	}

	public static void main(String[] args) {
		System.out.println("WORKING");
		new MazeApp();
	}

}

