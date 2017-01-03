/**
 * Alirio Rivera Cuervo
 * andrewID: ariverac
 */
package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public abstract class WordGame {
	public String[] dictionaryWords;		//stores words read from the dictionary
	public String gameWord;  				//word picked up from the dictionary for the puzzle 
	public StringBuilder userInputs = new StringBuilder(); //stores all guesses entered by the user
	public String message;					//message to be printed on console after each user interaction
	public static final int MAX_TRIALS = 10; 
	public int trialCount=0;				//incremented everytime user enters a valid guess
	public boolean won = false;			//set to true when user input matches the gameWord
	public String clueWord;				//clue shown to the user on console
	public double score;					//updated by calcScore() 
	public int hit; //number of correct guesses made by player
	public int miss; //number of wrong guesses made by player

	WordGame() {
		dictionaryWords = readFile();
		setupWords();
	}

	//readfile() opens the file and reads it into StringBuffer
	//returns an array of String by splitting the words on new line
	public String[] readFile() {
		//enter code here
		//stringBuilder containing all the  information presented in the Dictionary.txt file.
		StringBuilder fileContent = new StringBuilder();
		Scanner fileInformationReader = null;
		try {
			//read all the information in the file and append all the lines to the StringBuilder filecontent, 
			//separating each line with a \n
			fileInformationReader = new Scanner (new File ("dictionary.txt"));
			while (fileInformationReader.hasNextLine()) {
				fileContent.append(fileInformationReader.nextLine().toLowerCase() + "\n");
			}
			fileInformationReader.close();
		} catch (FileNotFoundException e) {
			//if the file was not found, show the error
			System.out.println("dictionary.txt file not found!!");
			e.printStackTrace();
		}
		return new String(fileContent).split("\\r?\\n");
	}

	//pickWord() picks a word randomly from within the dictionaryWords array
	//It returns the word that has at least 4 or more characters in it.
	public void pickGameWord(){
		//enter code here
		//this line generates a random number between 1 and the number of words included in the dictionary word array
		int randomLineOfWord = new Random().nextInt((dictionaryWords.length - 1) + 1) + 1;
		//this boolean controls that the chosen word has more than 4 words, otherwise kept choosing a  new word.
		boolean wordWithLessThan4Letters = true;
		do{ 
			if(dictionaryWords[randomLineOfWord].length() >= 4){
				//if the word has more than 4 characters, set the wordGame
				gameWord = dictionaryWords[randomLineOfWord];
				wordWithLessThan4Letters = false;
			}
		}while(wordWithLessThan4Letters);
	}


	public abstract int nextTry(String input);

	public abstract double calcScore();

	public abstract void setupWords();

}
