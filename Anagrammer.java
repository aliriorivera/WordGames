/**
 * Alirio Rivera Cuervo
 * andrewID: ariverac
 */
package hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Anagrammer extends WordGame{

	public Map<String, List<String>> anagramMap;
	public List<String> anagrams;

	@Override
	public int nextTry(String input) {

		input = input.toLowerCase();
		//if the user's input is equal to the clueword, it returns a -1 and returns -1
		if(input.equals(clueWord)){
			message = "That’s the clue!!";
			return -1;
		}else if(userInputs.toString().contains(input)){
			//if the user has already entered the input, returns -1
			message = "You already entered " + input +"!!" ;
			return -1;
		}

		//I created and array with the letters of the input
		char[] firstString = input.toCharArray();
		//I sorted the array alphabetically
		Arrays.sort(firstString);

		//boolean variable to know if the word exists in the dictionary.
		boolean allOk = false;
		if(anagramMap.containsKey(new String(firstString))){
			//if the set of characters exists in the hasmap, it means that the word could exist in the dictionary.
			for(String a : anagramMap.get(new String(firstString))){
				if(a.equals(input)){
					//if the word is in any of the values of the anagrams, it means that the word is in the dictionary.
					allOk = true;
				}
			}
		}
		if(allOk){
			//if the owrd exists in the dictionary, we try to establish if the input is in the anagrams of the clue word
			if(this.anagrams.contains(input)){
				hit++;
				//if the input is an anagram, we remove the owrd form the anagrams list
				anagrams.remove(anagrams.indexOf(input));
				if(this.anagrams.size() == 1){
					//if the size of the anagrams list is 1, it means that the only word in the arraylist is the clueword
					won = true;
					message = "Congratulations! You won!";
				}else{
					//if the size of the anagrams is not 1, it means that the user needs to keep playing 
					message = "You got that right! " + (anagrams.size()-1)  + " more to go!";
				}
				return 1;
			}else{
				//if the input is not an anagram of the clueword return 0
				message = "Sorry: " + input + " is not " +  anagrams.get(0)+"'s anagram!";
				miss++;
				return 0;
			}
		}else{
			//if the word is not in the dictionary returns 0 and set the message 
			miss++;
			message  ="Sorry " + input + " is not in the dictionary.";
			return 0;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setupWords() {
		//initialization of the anagram arraylist
		anagrams = new ArrayList<>();
		//calling the method that fill up the hashmap with all the anagrasm. 
		loadAnagramMap();
		
		//boolean variable to know if the number of anagrams of the selected word is more than 2
		boolean moreThanThree = false;
		do{
			//get a random number to select the clueword and their anagrams.
			int randomWord = new Random().nextInt((anagramMap.size() - 1) + 1) + 1;

			//counter for selecting the object in the random  position.
			int counter = 1;
			Set sets = anagramMap.entrySet();
			for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
				Map.Entry object = (Map.Entry) iterator.next();
				if(counter == randomWord){
					//if the counter is equal to the random number, it means that this is the selected clueword
					anagrams = (List<String>) object.getValue();
					break;
				}
				counter++;
			}
			//if the number of anagrams for the clueword is more than 2, it means that the cluewrod is valid.
			if(anagrams.size()>=3){
				moreThanThree=true;
			}
		}while(!moreThanThree);
		//set the clueword
		clueWord = anagrams.get(0);
		//clear the usrinputs stringbuilder variable.
		userInputs.setLength(0);
	}

	public void loadAnagramMap(){
		//initializing the hashmap that is going to save all the anagrams.
		anagramMap = new HashMap<String, List<String>>();
		//for to iterate over all  the owrds in the dictionary
		for(String wordAnaGram : dictionaryWords){
			//get all the letters of the word in a char array
			char[] letters = wordAnaGram.toLowerCase().toCharArray();
			//sort the letters of the letters char array
			Arrays.sort(letters);
			//get the sorted array as string because this is going to be the key of the hashmap
			String alpha = new String(letters);
			//search of the anagram has the key already
			List<String> listWords = anagramMap.get(alpha);
			//if there is no the same key in the hashmap, we proceed to create it.
			if (listWords == null)
				anagramMap.put(alpha, listWords=new ArrayList<String>());
			boolean exists = false;
			//for to cycle over all the anagrams of the specified clueword
			for(String wordIterate : listWords){
				if(wordIterate.equals(wordAnaGram)){
					//if the word exist, we set the exist boolean variable as true. this is because there are 
					//repeated words in the dictionary.
					exists = true;
				}
			}
			//if the word does not exist in the arraylist, we add it.
			if(!exists){
				listWords.add(wordAnaGram);
			}
		}
	}
}