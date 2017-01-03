/**
 * Alirio Rivera Cuervo
 * andrewID: ariverac
 */
package hw3;

import java.util.Random;

public class Hangman extends WordGame{

	//Invokes pickGameWord and then creates the clueWord by randomly replacing alphabets 
	//in the word with dashes until the dashCount is equal to or 
	//more than half the length of the word
	//
	@Override
	public void setupWords(){
		//enter code here
		pickGameWord();
		clueWord = gameWord; //at the beggining both words must be the same

		char[] clueWordCharacters = clueWord.toCharArray();

		// the first time the program needs to put at least one dash. The second time it is necessary to
		//know how many dashes are in the clueword.
		do{
			//I generated a random number between zero(0) and the length of the gameWord 
			int randomNumberToHide = new Random().nextInt(((gameWord.length()-1) - 1) + 0) + 0;
			if(clueWordCharacters[randomNumberToHide] != '-'){
				//if the character is not a dash, then we save it temporarily 
				char reeplaceCharacter = clueWordCharacters[randomNumberToHide];
				//iteration over the characters of the clueword to know if there is another equal character to the replaceCharacter
				for(int i=0; i<clueWordCharacters.length; i++){
					if(clueWordCharacters[i] == reeplaceCharacter){
						//if there is another equal character in the word, we change it to a dash
						clueWordCharacters[i] = '-';
					}
				}
			}
			//the loop is executed while the number of dashes is less than the half of the length of the real word: gameWord 
		}while(countDashes(new String(clueWordCharacters)) < (gameWord.length()/2));

		clueWord = new String(clueWordCharacters);	
	}

	//dashCount() returns the number of dashes in the word 
	public int countDashes(String word) {
		//enter code here
		int numberOfDashes= 0;//variable to count the number of dashes
		char[] clueWordCharacters = word.toCharArray(); //I separated the characters of the clueword in a char array
		//loop to iterate over the array that contains the characters if the word.
		for(char allCharacters : clueWordCharacters){
			if(allCharacters == '-'){
				//if the character is a dash, I added 1 to the numberOfDashes counter.
				numberOfDashes++;
			}
		}
		return numberOfDashes;
	}

	@Override
	public int nextTry(String input) {
		//enter code here
		char[] guessWordCharacters = input.toCharArray();
		char[] clueWordCharacters = clueWord.toCharArray();
		char[] gameWordCharacters = gameWord.toCharArray();

		//get the first character in the input given by the user
		char searchCharacter = guessWordCharacters[0];

		//flag variable to know what type of message must be shown to the user at the end of the trial
		boolean setBadMessage = true;

		//loop to know if the position where is a dash, matches with the position where should be the character
		//typed by the user
		for(int i =0; i<clueWordCharacters.length; i++){
			if((clueWordCharacters[i] == '-') && (gameWordCharacters[i] == searchCharacter)){
				// if the position of the dash matches with the real position of the character typed by the user, then that position
				//is replaced for the character
				clueWordCharacters[i] = searchCharacter;
				//as it was found a match therefore, the application must show a message of success.
				setBadMessage = false;
			}
		}

		//convert the array of characters, of the clueWrod, to the real clueWord String
		clueWord = new String(clueWordCharacters);

		//if the number of dashes in the clueWord is zero, it means that the user completed all the characters
		//in the word and won
		if(countDashes(clueWord) == 0){
			won = true;
		}else{
			//I add 1 to the number of trials if the user has not won yet
			trialCount++;
		}

		//append the character, typed by the user, to the userInput variable
		userInputs.append(searchCharacter);

		//in this if-else I set the message to the user depending on the setBadMessage variable
		if(setBadMessage){
			//Set the sorry message, because the character typed by the user was not found in the word
			message = "Sorry! Got it wrong!";
			miss++;
			return 0;
		}else{
			//Set the success message, because the character typed by the user was found in the word.
			message = "*** You got that right! ***";
			hit++;
			return 1;
		}
	}

	@Override
	public double calcScore() {
		// if there were no any misses the score are the hits, otherwise, the score is the division between hit and misses
		//as a double variable
		if(miss == 0){
			return (double) hit;
		}else{
			return (double) hit/miss;
		}
	}
}

