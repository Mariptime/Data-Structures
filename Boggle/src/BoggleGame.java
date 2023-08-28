import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class BoggleGame extends JFrame {
	final String PATH = "./data/";

    private static final int GAME_TIME = 180;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int FOUND_WORDS_DISPLAY_COUNT = 17;
    private static final int ALL_WORDS_DISPLAY_COUNT   = 7;

    @SuppressWarnings("unused")
	private static final int DEF_HEIGHT = 550;
    @SuppressWarnings("unused")
	private static final int DEF_WIDTH = 700;
    private static final int WORD_PANEL_WIDTH  = 205;
    private static final int WORD_PANEL_HEIGHT = 325;

    private static final Color PLAYER_POINT_WORD = new Color(0xBFBFBF);
    private static final Color OPP_POINT_WORD    = new Color(0xBFBFBF);
    private static final Color NONPOINT_WORD     = new Color(0xBFBFBF);

    // if making adjustments to levels, endGame (~line 400) contains hard-coded elements
    // menu items will be adjusted automatically
    private static final int NUMBER_OF_LEVELS = 5;
    private static final String[] LEVEL_DESCRIPTION = {
        "Nursery",
        "Shakespeare",
        "Algorithms 4/e",
        "Hard",
        "Impossible"
    };
    private static final int NURSERY     = 0;
    private static final int SHAKESPEARE = 1;
    private static final int ALGORITHMS  = 2;
    private static final int HARD        = 3;
    private static final int IMPOSSIBLE  = 4;

    private static final int DEF_COLUMNS = 10;
    private static final String MAX_WORD_SIZE = "INCONSEQUENTIALLY";


    private int gameDifficulty = 0;

    private final int BOARD_ROWS;
    private final int BOARD_COLS;

    private boolean inGame = true;
    private int elapsedTime = 0;
    private int points = 0;
    private Timer timer = new Timer();

    private final String[] emptyList = new String[0];

    private LinkedHashSet<String> foundWords;
    private TreeSet<String> validWords;
    private TreeSet<String> opponentFoundWords;
    @SuppressWarnings("rawtypes")
	private final JList foundWordsList;
    @SuppressWarnings("rawtypes")
	private final JList validWordsList;
    @SuppressWarnings("rawtypes")
	private final JList opponentFoundWordsList;
    private int oppCurScore;
    private BoggleBoard board;

    private final Set<String> shakespeareDictionary;
    private final Set<String> nurseryDictionary;
    private final Set<String> commonDictionary;
    private final Set<String> algs4Dictionary;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JRadioButtonMenuItem[] difficultySelection;
    private final BoggleSolver solver;
    private final JLabel clock;
    private final BoardPanel bp;
    private final JTextField entryField;
    private final JLabel scoreLabel;
    private final JLabel possiblePointsLabel;
    private final JLabel oppScoreLabel;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public BoggleGame(int rows, int cols) {
        BOARD_ROWS = rows;
        BOARD_COLS = cols;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Boggle");
        setLocationRelativeTo(null);
        this.makeMenuBar();
        JPanel timerPanel = new JPanel();
        JLabel timerLabel = new JLabel("Timer:");
        String seconds = String.format("%02d", (GAME_TIME - elapsedTime) % SECONDS_PER_MINUTE);
        String minutes = String.format("%02d", (GAME_TIME - elapsedTime) / SECONDS_PER_MINUTE);
        String time = minutes + ":" + seconds;
        clock = new JLabel(time);
        timerPanel.add(timerLabel);
        timerPanel.add(clock);
        entryField = new JTextField(DEF_COLUMNS);
        entryField.setMaximumSize(new Dimension(entryField.getPreferredSize().width,
                                                entryField.getPreferredSize().height));
        entryField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkWord();
            }
        });
        entryField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) { }
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField txtSrc = (JTextField) e.getSource();
                String text = txtSrc.getText().toUpperCase();
                bp.matchWord(text);
            }
            @Override
            public void keyTyped(KeyEvent e) { }
        });
        foundWordsList = new JList();
        foundWordsList.setPrototypeCellValue(MAX_WORD_SIZE);
        foundWordsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        foundWordsList.setListData(emptyList);
        foundWordsList.setVisibleRowCount(FOUND_WORDS_DISPLAY_COUNT);
        foundWordsList.setLayoutOrientation(JList.VERTICAL_WRAP);
        foundWordsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, false, false);
                String word = (String) value;
                if (!inGame && inGame) {
                    if (foundWords.contains(word) && !opponentFoundWords.contains(word)) {
                        comp.setBackground(PLAYER_POINT_WORD);
                    }
                    else if (foundWords.contains(word) && opponentFoundWords.contains(word)) {
                        comp.setBackground(NONPOINT_WORD);
                    }
                }
                comp.setForeground(Color.black);
                return comp;
            }
        });
        JScrollPane foundWordsScrollPane = new JScrollPane(foundWordsList);
        foundWordsScrollPane.setPreferredSize(new Dimension(WORD_PANEL_WIDTH, WORD_PANEL_HEIGHT));
        foundWordsScrollPane.setMinimumSize(foundWordsScrollPane.getPreferredSize());
        foundWordsScrollPane.setMaximumSize(foundWordsScrollPane.getPreferredSize());
        JPanel scoreLabelPanel = new JPanel();
        scoreLabel = new JLabel("My Points:");
        scoreLabelPanel.add(scoreLabel);
        JPanel controlPanel = new JPanel();
        GroupLayout controlLayout = new GroupLayout(controlPanel);
        controlPanel.setLayout(controlLayout);
        controlLayout.setAutoCreateGaps(true);
        controlLayout.setAutoCreateContainerGaps(true);
        controlLayout.setHorizontalGroup(
            controlLayout.createSequentialGroup()
                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(timerPanel)
                    .addComponent(entryField)
                    .addComponent(foundWordsScrollPane)
                    .addComponent(scoreLabelPanel))
        );
        controlLayout.setVerticalGroup(
            controlLayout.createSequentialGroup()
               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(timerPanel,           GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
               .addComponent(entryField,           GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
               .addComponent(foundWordsScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
               .addComponent(scoreLabelPanel,      GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bp = new BoardPanel();
        validWordsList = new JList();
        validWordsList.setVisible(true);
        validWordsList.setVisibleRowCount(ALL_WORDS_DISPLAY_COUNT);
        validWordsList.setPrototypeCellValue(MAX_WORD_SIZE);
        validWordsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        validWordsList.setLayoutOrientation(JList.VERTICAL_WRAP);
        validWordsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, false, false);
                String word = (String) value;
                if (!inGame) {
                    if (foundWords.contains(word)) {
                        comp.setBackground(OPP_POINT_WORD);
                    }
                }
                comp.setForeground(Color.black);
                return comp;
            }
        });
        JScrollPane validWordsScrollPane = new JScrollPane(validWordsList);
        validWordsScrollPane.setPreferredSize(new Dimension(300, 145));
        validWordsScrollPane.setMinimumSize(validWordsScrollPane.getPreferredSize());
        validWordsScrollPane.setMaximumSize(validWordsScrollPane.getPreferredSize());
        JPanel possiblePointsPanel = new JPanel();
        possiblePointsLabel = new JLabel();
        possiblePointsPanel.add(possiblePointsLabel);
        JPanel gamePanel = new JPanel();
        GroupLayout gameLayout = new GroupLayout(gamePanel);
        gamePanel.setLayout(gameLayout);
        gameLayout.setAutoCreateGaps(true);
        gameLayout.setAutoCreateContainerGaps(true);
        gameLayout.setHorizontalGroup(
                gameLayout.createSequentialGroup()
                    .addGroup(gameLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(bp)
                        .addComponent(validWordsScrollPane)
                        .addComponent(possiblePointsPanel))
        );
        gameLayout.setVerticalGroup(
             gameLayout.createSequentialGroup()
                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE,   Short.MAX_VALUE)
                 .addComponent(bp,                   GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                 .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE)
                 .addComponent(validWordsScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                 .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE)
                 .addComponent(possiblePointsPanel,  GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE,   Short.MAX_VALUE)
        );
        JLabel opponentLabel = new JLabel("Opponent's Words:");
        JPanel opponentLabelPanel = new JPanel();
        opponentLabelPanel.add(opponentLabel);
        oppScoreLabel = new JLabel("Opponent's Points: ");
        JPanel oppScoreLabelPanel = new JPanel();
        oppScoreLabelPanel.add(oppScoreLabel);
        opponentFoundWordsList = new JList();
        opponentFoundWordsList.setPrototypeCellValue(MAX_WORD_SIZE);
        opponentFoundWordsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        opponentFoundWordsList.setListData(emptyList);
        opponentFoundWordsList.setVisibleRowCount(FOUND_WORDS_DISPLAY_COUNT);
        opponentFoundWordsList.setLayoutOrientation(JList.VERTICAL_WRAP);
        opponentFoundWordsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, false, false);
                String word = (String) value;
                if (!inGame && inGame) {
                    if (!foundWords.contains(word) && opponentFoundWords.contains(word)) {
                        comp.setBackground(OPP_POINT_WORD);
                    }
                    else if (foundWords.contains(word) && opponentFoundWords.contains(word)) {
                        comp.setBackground(NONPOINT_WORD);
                    }
                }
                comp.setForeground(Color.black);
                return comp;
            }
        });

        JScrollPane opponentWordsScrollPane = new JScrollPane(opponentFoundWordsList);
        opponentWordsScrollPane.setPreferredSize(new Dimension(WORD_PANEL_WIDTH, WORD_PANEL_HEIGHT));
        opponentWordsScrollPane.setMinimumSize(opponentWordsScrollPane.getPreferredSize());
        opponentWordsScrollPane.setMaximumSize(opponentWordsScrollPane.getPreferredSize());
        JPanel spacingPanel = new JPanel();
        spacingPanel.setPreferredSize(new Dimension(WORD_PANEL_WIDTH, 22));
        JPanel opponentPanel = new JPanel();
        GroupLayout buttonsLayout = new GroupLayout(opponentPanel);
        opponentPanel.setLayout(buttonsLayout);
        buttonsLayout.setAutoCreateContainerGaps(true);
        buttonsLayout.setAutoCreateGaps(true);
        buttonsLayout.setHorizontalGroup(
            buttonsLayout.createSequentialGroup()
                .addGroup(buttonsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(spacingPanel)
                    .addComponent(opponentLabelPanel)
                    .addComponent(opponentWordsScrollPane)
                    .addComponent(oppScoreLabelPanel))
        );
        buttonsLayout.setVerticalGroup(
            buttonsLayout.createSequentialGroup()
                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,           GroupLayout.DEFAULT_SIZE,   Short.MAX_VALUE)
                 .addComponent(spacingPanel,            GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE)
                 .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,         GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE)
                 .addComponent(opponentLabelPanel,      GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE)
                 .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,         GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE)
                 .addComponent(opponentWordsScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                 .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,         GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE)
                 .addComponent(oppScoreLabelPanel,      GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE)
                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,           GroupLayout.DEFAULT_SIZE,   Short.MAX_VALUE)
        );
        Container content = getContentPane();
        GroupLayout layout = new GroupLayout(content);
        content.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(controlPanel,    GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addComponent(gamePanel,       GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addComponent(opponentPanel,   GroupLayout.DEFAULT_SIZE,   GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false)
                    .addComponent(controlPanel)
                    .addComponent(gamePanel)
                    .addComponent(opponentPanel))
        );
        Scanner in = null;
        try { in = new Scanner(new File(PATH + "dictionary-shakespeare.txt")); } catch (FileNotFoundException f) {}
        shakespeareDictionary = new HashSet<String>();
        while (in.hasNext())
            shakespeareDictionary.add(in.next());
        try { in = new Scanner(new File(PATH + "dictionary-nursery.txt")); } catch (FileNotFoundException f) {}
        nurseryDictionary = new HashSet<String>();
        while (in.hasNext())
            nurseryDictionary.add(in.next());
        try { in = new Scanner(new File(PATH + "dictionary-common.txt")); } catch (FileNotFoundException f) {}
        commonDictionary = new HashSet<String>();
        while (in.hasNext())
            commonDictionary.add(in.next());
        try { in = new Scanner(new File(PATH + "dictionary-algs4.txt")); } catch (FileNotFoundException f) {}
        algs4Dictionary = new HashSet<String>();
        while (in.hasNext())
            algs4Dictionary.add(in.next());
        solver = new BoggleSolver(PATH + "dictionary-yawl.txt");

        newGame();
        this.pack();
    }

    @SuppressWarnings("unchecked")
	private void newGame() {
        if (BOARD_ROWS == 4 && BOARD_COLS == 4) {
            board = new BoggleBoard();
        }
        else {
            board = new BoggleBoard(BOARD_ROWS, BOARD_COLS);
        }
        clock.setForeground(Color.BLACK);
        entryField.requestFocus();
        inGame = true;
        points = 0;
        scoreLabel.setText("Current Points:" + points);
        entryField.setEnabled(true);

        foundWords = new LinkedHashSet<String>();
        foundWordsList.setListData(emptyList);
        validWordsList.setListData(emptyList);
        opponentFoundWordsList.setListData(emptyList);

        bp.setBoard();
        bp.unhighlightCubes();
        Iterable<String> words = solver.getAllValidWords(board);
        validWords = new TreeSet<String>();
        int possiblePoints = 0;
        for (String s : words) {
            validWords.add(s);
            possiblePoints += scoreWord(s);
        }
        possiblePointsLabel.setText("Possible Points: " + possiblePoints);
        opponentFoundWords = new TreeSet<String>();
        if (gameDifficulty == NURSERY) {
            for (String word : validWords)
                if (nurseryDictionary.contains(word))
                    opponentFoundWords.add(word);
        }

        else if (gameDifficulty == SHAKESPEARE) {
            for (String word : validWords)
                if (shakespeareDictionary.contains(word) && StdRandom.uniform(3) != 0)
                    opponentFoundWords.add(word);
        }

        else if (gameDifficulty == ALGORITHMS) {
            for (String word : validWords)
                if (algs4Dictionary.contains(word))
                    opponentFoundWords.add(word);
        }

        else if (gameDifficulty == HARD) {
            for (String word : validWords)
                if (commonDictionary.contains(word) && StdRandom.bernoulli())
                    opponentFoundWords.add(word);
        }

        else if (gameDifficulty == IMPOSSIBLE) {
            for (String word : validWords)
                if (StdRandom.uniform(4) != 0)
                    opponentFoundWords.add(word);
        }
        oppCurScore = 0;
        for (String word : opponentFoundWords)
            oppCurScore += scoreWord(word);

        oppScoreLabel.setText("Opponent's Points: " + oppCurScore);
        timer.cancel();
        elapsedTime = -1;
        timer = new Timer();
        timer.schedule(new Countdown(), 0, 1000);
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
	private void endGame() {

        clock.setText("00:00");
        clock.setForeground(Color.RED);
        timer.cancel();
        entryField.setText("");
        entryField.setEnabled(false);
        validWordsList.setListData(validWords.toArray());
        int[] indices = new int[foundWords.size()];
        int i = 0;
        int n = 0;
        for (String s : validWords) {
            if (foundWords.contains(s))
                indices[i++] = n;
            n++;
        }
        validWordsList.setSelectedIndices(indices);

        inGame = false;
        int playerScore = points;
        int opponentScore = oppCurScore;
        for (String s : foundWords) {
            if (opponentFoundWords.contains(s)) {
                playerScore   -= scoreWord(s);
                opponentScore -= scoreWord(s);
            }
        }
        Object[] list1 = foundWords.toArray();
        for (int j = 0; j < list1.length; j++) {
            if (opponentFoundWords.contains(list1[j])) {
                list1[j] = "<html><strike>" + list1[j] + "</strike></html>";
            }
        }
        foundWordsList.setListData(list1);
        Object[] list2 = opponentFoundWords.toArray();
        for (int j = 0; j < list2.length; j++) {
            if (foundWords.contains(list2[j])) {
                list2[j] = "<html><strike>" + list2[j] + "</strike></html>";
            }
        }
        opponentFoundWordsList.setListData(list2);
        String winnerMessage = "";
        if      (playerScore > opponentScore) winnerMessage = "                   You win!\n\n";
        else if (playerScore < opponentScore) winnerMessage = "            The computer wins!\n\n";
        else                                  winnerMessage = "                     Tie!\n\n";
        String scoreMessage  = "                  Final score:\n          You: " +  playerScore + " - Computer: " + opponentScore;
        JOptionPane.showMessageDialog(this, winnerMessage + scoreMessage, "Game finished", JOptionPane.PLAIN_MESSAGE);
    }

    private class Countdown extends TimerTask {
        @Override
        public void run() {
            if (elapsedTime < GAME_TIME - 1) {
                elapsedTime++;
                String seconds = String.format("%02d", (GAME_TIME - elapsedTime) % SECONDS_PER_MINUTE);
                String minutes = String.format("%02d", (GAME_TIME - elapsedTime) / SECONDS_PER_MINUTE);
                String time = minutes + ":" + seconds;
                clock.setText(time);
            }
            else {
                endGame();
            }
        }
    }

    @SuppressWarnings("unchecked")
	private void checkWord() {
        String s;
        if (entryField.getText().length() >= bp.getCurrentPath().length())
            s = entryField.getText().toUpperCase();
        else
            s = bp.getCurrentPath().toUpperCase();
        s = s.trim();
        if (s.equals("")) return;
        if (validWords.contains(s) && !foundWords.contains(s)) {
            foundWords.add(s);
            foundWordsList.setListData(foundWords.toArray());
            points += scoreWord(s);
            scoreLabel.setText("Current Points: " + points);
            entryField.setText("");
        }

        else if (s.equals("GODMODE")) {
            for (String str : solver.getAllValidWords(board)) {
                entryField.setText(str);
                checkWord();
            }
        }

        else if (s.equals("GODMODE4")) {
            for (String str : solver.getAllValidWords(board)) {
                if (StdRandom.uniform(4) == 0) {
                    entryField.setText(str);
                    checkWord();
                }
            }
        }

        else {
            Toolkit.getDefaultToolkit().beep();
            entryField.setText("");
        }
    }

    private int scoreWord(String s) {
        int pointValue;
        int length = s.length();
        if      (length < 5)  pointValue = 1;
        else if (length == 5) pointValue = 2;
        else if (length == 6) pointValue = 3;
        else if (length == 7) pointValue = 5;
        else                  pointValue = 11;
        return pointValue;
    }

    private class BoardPanel extends JPanel {
        private final int NUM_OF_CUBES = BOARD_ROWS * BOARD_COLS;
        private final JLabel[] cubes = new JLabel[NUM_OF_CUBES];
        private final int CUBE_DIM = 60;
        private int[] path;
        private boolean foundWord;

        public BoardPanel() {
            GridLayout cubeLayout = new GridLayout(BOARD_ROWS, BOARD_COLS);
            this.setPreferredSize(new Dimension(CUBE_DIM*BOARD_COLS, CUBE_DIM*BOARD_ROWS));
            this.setMinimumSize(this.getPreferredSize());
            this.setMaximumSize(this.getPreferredSize());
            this.setLayout(cubeLayout);
            for (int i = 0; i < NUM_OF_CUBES; i++) {
                final int cur = i;
                cubes[i] = new JLabel("", JLabel.CENTER);
                cubes[i].setFont(new Font("SansSerif", Font.PLAIN, 28));
                cubes[i].setPreferredSize(new Dimension(CUBE_DIM, CUBE_DIM));
                cubes[i].setMinimumSize(cubes[i].getPreferredSize());
                cubes[i].setMaximumSize(cubes[i].getPreferredSize());
                cubes[i].setBorder(BorderFactory.createRaisedBevelBorder());
                cubes[i].setOpaque(true);
                cubes[i].setBackground(new Color(146, 183, 219));
                cubes[i].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent arg0) {
                        if (inGame) {
                            if (path == null) {
                                path = new int[NUM_OF_CUBES];
                                for (int n = 0; n < path.length; n++) {
                                    path[n] = -1;
                                }
                                path[0] = cur;
                                highlightCubes();
                                return;
                            }
                            for (int j = 0; j < path.length; j++) {
                                if (j == 0 && path[j] == -1) {
                                    path[j] = cur;
                                    break;
                                }
                                else if (path[j] == cur) {
                                    if (j == path.length-1 || path[j+1] == -1) {
                                        cubes[cur].setBackground(new Color(146, 183, 219));
                                        path[j] = -1;
                                    }
                                    break;
                                }
                                else if (path[j] == -1) {
                                    if (path[j-1] >= cur-BOARD_COLS-1 && path[j-1] <= cur-BOARD_COLS+1)
                                        path[j] = cur;
                                    else if (path[j-1] == cur-1 || path[j-1] == cur+1)
                                        path[j] = cur;
                                    else if (path[j-1] >= cur+BOARD_COLS-1 && path[j-1] <= cur+BOARD_COLS+1)
                                        path[j] = cur;

                                    break;
                                }
                            }
                            highlightCubes();
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent arg0) { }
                    @Override
                    public void mouseExited(MouseEvent arg0) { }
                    @Override
                    public void mousePressed(MouseEvent arg0) { }
                    @Override
                    public void mouseReleased(MouseEvent arg0) { }
                });
                cubes[i].addKeyListener(new KeyListener() {
                    @Override
                    public void keyPressed(KeyEvent arg0) { }
                    @Override
                    public void keyReleased(KeyEvent arg0) { }
                    @Override
                    public void keyTyped(KeyEvent arg0) {
                        int keyCode = arg0.getKeyCode();
                        if (keyCode == KeyEvent.VK_ENTER) {
                            checkWord();
                        }
                    }
                });
                this.add(cubes[i]);
            }
        }
        @SuppressWarnings("unused")
		public void clearSelection() {
            for (int i = 0; i < path.length; i++) {
                path[i] = -1;
                cubes[i].setBackground(new Color(146, 183, 219));
            }
        }

        public String getCurrentPath() {
            StringBuilder selectedWord = new StringBuilder(8);
            for (int s : path) {
                if (s < 0) break;
                selectedWord.append(cubes[s].getText().charAt(0));
                if (cubes[s].getText().charAt(0) == 'Q') selectedWord.append('U');
            }
            return selectedWord.toString();
        }

        public void setBoard() {
            for (int i = 0; i < BOARD_ROWS; i++) {
                for (int j = 0; j < BOARD_COLS; j++) {
                    char letter = board.getLetter(i, j);
                    if (letter == 'Q')
                        cubes[i*BOARD_COLS + j].setText("Qu");
                    else
                        cubes[i*BOARD_COLS + j].setText("" + letter);
                }
            }
        }

        public void highlightCubes() {
            for (int i = 0; i < path.length; i++) {
                if (path[i] == -1) break;
                cubes[path[i]].setBackground(new Color(232, 237, 76));
            }
        }

        public void unhighlightCubes() {
            if (path == null) return;
            for (int i = 0; i < path.length; i++) {
                if (path[i] == -1) break;
                cubes[path[i]].setBackground(new Color(146, 183, 219));
            }
        }

        public void matchWord(String s) {
            if (path != null) unhighlightCubes();
            path = new int[NUM_OF_CUBES];
            for (int i = 0; i < path.length; i++) {
                path[i] = -1;
            }
            foundWord = false;
            s = s.toUpperCase();
            for (int i = 0; i < cubes.length; i++) {
                if (s.startsWith(cubes[i].getText().toUpperCase())) {
                    dfs(s, 0, 0, i / BOARD_COLS, i % BOARD_COLS);
                }
                if (foundWord) break;
            }
            if (foundWord) {
                highlightCubes();
            }
        }

        private void dfs(String s, int curChar, int pathIndex, int i, int j) {
            if (i < 0 || j < 0 || i >= BOARD_ROWS || j >= BOARD_COLS) return;
            if (curChar >= s.length()) {
                foundWord = true;
                return;
            }
            for (int n = 0; n < path.length; n++) {
                if (path[n] == (i*BOARD_COLS)+j) return;
            }
            if (curChar != 0 && s.charAt(curChar-1) == 'Q' && s.charAt(curChar) != 'U')
                return;
            if (curChar != 0 && s.charAt(curChar-1) == 'Q' && s.charAt(curChar) == 'U')
                curChar += 1;
            if (curChar >= s.length()) {
                foundWord = true;
                return;
            }
            if (cubes[(i*BOARD_COLS)+j].getText().charAt(0) != s.charAt(curChar)) {
                return;
            }
            path[pathIndex] = (i*BOARD_COLS)+j;
            for (int ii = -1; ii <= 1; ii++)
                for (int jj = -1; jj <= 1; jj++)
                    if (!foundWord) dfs(s, curChar+1, pathIndex+1, i + ii, j + jj);

            if (!foundWord) path[curChar] = -1;
        }
    }

    private void makeMenuBar() {
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        gameMenu.getAccessibleContext().setAccessibleDescription("This menu contains game options");
        menuBar.add(gameMenu);
        JMenuItem newGameMenuItem = new JMenuItem("New...", KeyEvent.VK_N);
        newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        newGameMenuItem.getAccessibleContext().setAccessibleDescription("Starts a new game");
        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                newGame();
            }
        });
        JMenuItem endGameMenuItem = new JMenuItem("End Game", KeyEvent.VK_E);
        endGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        endGameMenuItem.getAccessibleContext().setAccessibleDescription("Ends the current game");
        endGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                endGame();
            }
        });
        gameMenu.add(newGameMenuItem);
        gameMenu.add(endGameMenuItem);
        gameMenu.addSeparator();
        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultySelection = new JRadioButtonMenuItem[NUMBER_OF_LEVELS];
        for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
            difficultySelection[i]  = new JRadioButtonMenuItem(LEVEL_DESCRIPTION[i % LEVEL_DESCRIPTION.length]);
            if (i == 0) difficultySelection[i].setSelected(true);
            difficultySelection[i].setActionCommand(LEVEL_DESCRIPTION[i % LEVEL_DESCRIPTION.length]);
            difficultySelection[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    for (int i = 0; i < LEVEL_DESCRIPTION.length; i++) {
                        if (ae.getActionCommand().equals(LEVEL_DESCRIPTION[i])) {
                            gameDifficulty = i;
                            newGame();
                            break;
                        }
                    }
                }
            });
            difficultyGroup.add(difficultySelection[i]);
            gameMenu.add(difficultySelection[i]);
        }
        JMenuItem quitMenuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        quitMenuItem.getAccessibleContext().setAccessibleDescription("Quits the program");
        quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                timer.cancel();
                System.exit(0);
            }
        });
        gameMenu.addSeparator();
        gameMenu.add(quitMenuItem);
        setJMenuBar(menuBar);
    }


    public static void main(final String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int rows = 0;
                int cols = 0;
                if (args.length == 0) {
                    rows = 4;
                    cols = 4;
                }
                else if (args.length == 1) {
                    try {
                        rows = Integer.parseInt(args[0]);
                        cols = rows;
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Usage: java BoggleGame " +
                                           "\nor:    java BoggleGame [rows]" +
                                           "\nor:    java BoggleGame [rows] [cols]");
                        System.exit(1);
                    }
                }
                else if (args.length == 2) {
                    try {
                        rows = Integer.parseInt(args[0]);
                        cols  = Integer.parseInt(args[1]);
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Usage: java BoggleGame " +
                                           "\nor:    java BoggleGame [rows]" +
                                           "\nor:    java BoggleGame [rows] [cols]");
                        System.exit(1);
                    }
                }
                else {
                    System.err.println("Usage: java BoggleGame " +
                                       "\nor:    java BoggleGame [rows]" +
                                       "\nor:    java BoggleGame [rows] [cols]");
                    System.exit(1);
                }

                if (rows <= 0 || cols <= 0) {
                    throw new java.lang.IllegalArgumentException("Rows and columns must be positive" +
                                                                 "\nUsage: java BoggleGame " +
                                                                 "\nor:    java BoggleGame [rows]" +
                                                                 "\nor:    java BoggleGame [rows] [cols]");
                }
                new BoggleGame(rows, cols).setVisible(true);
            }
        });
    }

}
