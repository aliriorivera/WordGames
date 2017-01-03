package hw3;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestHangman {	
	WordGame hm;
	
	@Test
	public void testCountDashes() {
		hm = new Hangman();
		hm.gameWord = "syllabus";
		hm.clueWord = "-y--a-u-";
		assertEquals ("Test1. Test count dashes", 5, ((Hangman)hm).countDashes(hm.clueWord));		
	}
	
	@Test
	public void testNextTryCorrectClue() {
		hm = new Hangman();
		hm.gameWord = "syllabus";
		hm.clueWord = "-y--a-u-";
		assertEquals("Test2. Test correct clue", 1, hm.nextTry("l"));
	}
	
	@Test
	public void testNextTryWrongClue() {
		hm = new Hangman();
		hm.gameWord = "syllabus";
		hm.clueWord = "-y--a-u-";
		assertEquals("Test3. Test wrong clue", 0, hm.nextTry("e"));
	}
	

	@Test
	public void testGetScoreLost() {
		hm = new Hangman();
		hm.gameWord = "syllabus";
		hm.clueWord = "-y--a-u-";
		hm.nextTry("b");		//hit. Trial# 1
		hm.nextTry("c");		//miss. Trial# 2
		hm.nextTry("d");		//miss. Trial# 3
		hm.nextTry("e");		//miss. Trial# 4
		hm.nextTry("f");		//miss. Trial# 5
		hm.nextTry("g");		//miss. Trial# 6
		hm.nextTry("h");		//miss. Trial# 7
		hm.nextTry("i");		//miss. Trial# 8
		hm.nextTry("j");		//miss. Trial# 9
		hm.nextTry("k");		//miss. Trial# 10
		assertEquals("Test6. Test lost score", (double)1/9, hm.calcScore(), 0);
	}
	
	
	@Test
	public void testGetScoreWon() {
		hm = new Hangman();
		hm.gameWord = "syllabus";
		hm.clueWord = "-y--a-u-";
		hm.nextTry("l");
		hm.nextTry("s");
		hm.nextTry("b");
		assertEquals("Test7. Test won score", 3, hm.calcScore(), 0);
	}
	
}
