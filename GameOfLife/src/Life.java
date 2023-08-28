import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Life extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private final LifeView view;
	private final LifeModel model;
	private final JButton runButton;
	private final JButton pauseButton;
	private final JButton resumeButton;
	private final JButton resetButton;
	private final JButton randomColor;
	private final String file;

	Life() throws IOException
	{
		this(null);
	}
	
	Life(String fileName) throws IOException
	{
		super("Conway's Life");
		file = fileName;
		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		runButton = new JButton("Run");
		runButton.addActionListener(this);
		runButton.setEnabled(true);
		controlPanel.add(runButton);

		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(this);
		pauseButton.setEnabled(false);
		controlPanel.add(pauseButton);

		resumeButton = new JButton("Resume");
		resumeButton.addActionListener(this);
		resumeButton.setEnabled(false);
		controlPanel.add(resumeButton);

		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		resetButton.setEnabled(false);
		controlPanel.add(resetButton);

		randomColor = new JButton("Change Color Scheme");
		randomColor.addActionListener(this);
		resetButton.setEnabled(false);
		controlPanel.add(randomColor);
		view = new LifeView();
		view.setBackground(Color.white);
		Container c = getContentPane();
		c.add(controlPanel, BorderLayout.NORTH);
		c.add(view, BorderLayout.CENTER);
		model = new LifeModel(view, file);
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if ( b == runButton )
		{
			model.run();
			runButton.setEnabled(false);
			pauseButton.setEnabled(true);
			resumeButton.setEnabled(false);
			resetButton.setEnabled(true);
		}
		else if ( b == pauseButton )
		{
			model.pause();
			runButton.setEnabled(false);
			pauseButton.setEnabled(false);
			resumeButton.setEnabled(true);
			resetButton.setEnabled(true);
		}
		else if ( b == resumeButton )
		{
			model.resume();
			runButton.setEnabled(false);
			pauseButton.setEnabled(true);
			resumeButton.setEnabled(false);
			resetButton.setEnabled(true);
		}
		else if ( b == resetButton ) {
			try {
				model.reset();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
			runButton.setEnabled(true);
			pauseButton.setEnabled(false);
			resumeButton.setEnabled(false);
			resetButton.setEnabled(false);
		}
		else if ( b == randomColor )
		{
			model.color();
			randomColor.setName("Mono Color");
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		Life conway = new Life();

		conway.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		conway.setSize(570, 640);
		conway.setVisible(true);
	}
}