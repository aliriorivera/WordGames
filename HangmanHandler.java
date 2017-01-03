/**
 * Alirio Rivera Cuervo
 * andrewID: ariverac
 */
package hw3;


import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


public class HangmanHandler extends GameHandler {

	Button[] alphaButtons;						

	public void handle(ActionEvent event) {
		game = new Hangman();
		super.handle(event);
	}

	/**
	 * Method used to build the UserGrid and add all the buttons and information to the main window 
	 * of the application.
	 */
	void setupUserGrid() {
		//every tiem the user click on the hangman game, a GridPane is created to show a new game
		userGrid = new GridPane();
		//Setting the properties of the information label of the Hangman game
		messageLabel.setText("Let's play Hangman");
		messageLabel.setAlignment(Pos.BASELINE_CENTER);
		messageLabel.setPadding(Insets.EMPTY);
		//adding the message label to the main userGrid
		userGrid.add(messageLabel, 1,3,1,1);		
		setupInputGrid();

		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setFillWidth(true);
		columnConstraints.setHgrow(Priority.ALWAYS);
		userGrid.getColumnConstraints().add(columnConstraints);

		//lastly, when all the grids were added, I added the userGrid to the main Layout
		//of the Window Application
		WordNerd.root.setBottom(userGrid);
	}

	/**
	 * Method used to setup the input grid, adding the buttons with the alphabet, enabling and disabling
	 * them depending on the cleWord  set at the creation of the new object Hangman.
	 */
	void setupInputGrid() {
		Label nextLetter = new Label("         Choose Next Letter: ");
		GridPane inputGrid = new GridPane();

		//I used a Lambda expression to get all the letters of the alphabet in a single statement and 
		//converting them to an array which is going to be used later.
		char[] alphabetArray = IntStream.rangeClosed('a', 'z')
				.mapToObj(javaAlphabet -> "" + (char) javaAlphabet).collect(Collectors.joining()).toCharArray();

		alphaButtons = new Button[alphabetArray.length];

		//Iteration over the array which contains the letters of the alphabet. 
		int moveAlphabet = 0;
		for (int i = 0; i < 5; i++) {
			for(int j = 0; j < 6; j++){
				if(i == 4 && j<2){
					j =2;
				}
				if(i==4 && j==4){
					break;
				}

				//for every letter is being created a new button with the corresponding letter as text
				alphaButtons[moveAlphabet] = new Button(String.valueOf(alphabetArray[moveAlphabet]));
				//I added a ButtonHandler to each one of the buttons
				alphaButtons[moveAlphabet].setOnAction(new AlphaButtonHandler());
				////I apply a css style to the normal button
				alphaButtons[moveAlphabet].getStyleClass().add("normalButton");

				//set a size of the buttons
				alphaButtons[moveAlphabet].setMinSize(30, 30);
				CharSequence charValue = "" + alphabetArray[moveAlphabet];
				//condition to know if the text of the button is in the clueword, it means that the button must be disabled.
				if(game.clueWord.contains(charValue)){
					alphaButtons[moveAlphabet].setDisable(true);
					alphaButtons[moveAlphabet].getStyleClass().add("buttonLetterInClueHangman");
				}

				//Adding the button to the inputgrid
				inputGrid.add(alphaButtons[moveAlphabet], j, i);
				moveAlphabet++;
			}
		}
		//adding the information label tot the usergrid.
		userGrid.add(nextLetter, 0, 0);
		inputGrid.setPadding(new Insets(10,30,10,30));

		//Adding the grid which contains the buttons to the main userGrid. 
		userGrid.add(inputGrid, 0, 1);
		userGrid.setPadding(new Insets(40,30,10,30));
		userGrid.setAlignment(Pos.BASELINE_CENTER);
	}

	/**
	 * Method used to disable all the alpha buttons at the same time.
	 */
	private void disableAlphaButtons() {
		for (int i = 0; i < alphaButtons.length; i++) {
			alphaButtons[i].setDisable(true);
		}
	}

	/**
	 * Handler of the click actions for the alpha buttons
	 */
	private class AlphaButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {

			//passing the letter of the button clicked by the user to the nextTry method
			int resultGame = game.nextTry(((Button) event.getSource()).getText());

			String finalMessage = game.message;

			//as the main game counts form 0 to 10, it is necessary to show the opposite in the main window.
			if((10 - game.trialCount) == 0){
				//if there is not more trials, all the buttons are disabled and the message is shown
				finalMessage = "Sorry you missed it!! Its " + game.gameWord;
				disableAlphaButtons();
			}else if(resultGame == 1){
				//if the letter of the button clicked by the user is in the clueword, I applied a css style 
				// called buttonDisabledRightLetter
				((Button) event.getSource()).getStyleClass().add("buttonDisabledRightLetter");
				if(game.won){
					//if the suser won, all the alpha buttons are disabled and the corresponding message 
					//is showed in the window
					finalMessage = "Congratulations! You got it!";
					game.trialCount++;
					disableAlphaButtons();
				}
			}else if(resultGame == 0){
				//if the letter of the button clicked by the user is not in the clueword, I applied a css style 
				// called buttonDisabledBadLetter
				((Button) event.getSource()).getStyleClass().add("buttonDisabledBadLetter");
			}

			//I disabled the button clicked by the user.
			((Button) event.getSource()).setDisable(true);
			clueWordLabels[0].setText(game.clueWord);
			messageLabel.setText(finalMessage);

			//With the next four lines I updated the label which show the information of the Hnagman game.
			scoreValueLabels[0].setText(String.valueOf(10 - game.trialCount));
			scoreValueLabels[1].setText(String.valueOf(game.hit));
			scoreValueLabels[2].setText(String.valueOf(game.miss));
			scoreValueLabels[3].setText(String.format("%2.2f", game.calcScore()));
		}
	}

}