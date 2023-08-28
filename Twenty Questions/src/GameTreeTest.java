import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameTreeTest
{
	static final String PATH = "";
	static GameTree aGame;

	// This allows you to ALWAYS begin with the same exact questions and answers.
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		// Let outfile be an object like System.out. You can send println messages to outfile
		PrintWriter outFile = new PrintWriter(new FileOutputStream(PATH + "animals.txt"));

		// Write seven lines to an output file:
		outFile.println("Has feathers?");
		outFile.println("Barnyard?");
		outFile.println("chicken");
		outFile.println("owl");
		outFile.println("Is it a mammal?");
		outFile.println("tiger");
		outFile.println("rattlesnake");
		outFile.close(); // Make sure you close() the file

		/*
		 * This test uses the following input file animals.txt :
		 * Has feathers?
		 * Barnyard?
		 * chicken
		 * owl
		 * Is it a mammal?
		 * tiger
		 * rattlesnake
		 */
		aGame = new GameTree(PATH + "animals.txt");

		// Output from toString()
		// - - rattlesnake
		// - Is it a mammal?
		// - - tiger
		// Has feathers?
		// - - owl
		// - Barnyard?
		// - - chicken
	}

	@Test
	public void test01_rootNodeAtStart() {
		assertEquals("Has feathers?", aGame.getCurrent());
	}

	@Test
	public void test02_foundAnswerAtStart() {
		assertFalse("Found answer should be false", aGame.foundAnswer());
	}

	@Test
	public void test03_makeFirstYesChoice() {
		aGame.playerSelected(Choice.Yes);
		assertEquals("Barnyard?", aGame.getCurrent());
	}

	@Test
	public void test04_checkFoundAnswerAgain() {
		assertFalse("Should not be at a leaf node yet", aGame.foundAnswer());
	}

	@Test
	public void test05_makeNoChoice() {
		aGame.playerSelected(Choice.No);
		assertEquals("owl", aGame.getCurrent());
	}

	@Test
	public void test06_foundAnAnswerNode() {
		assertTrue(aGame.foundAnswer());
	}
}
