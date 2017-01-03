/**
 * Alirio Rivera Cuervo
 * andrewID: ariverac
 */
package hw3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class AnagramHandler extends GameHandler{

	public TextField inputTextField = new TextField();
	public Button submitButton ;
	public TextArea anagramsTextArea = new TextArea();;


	public void handle(ActionEvent event) {
		game = new Anagrammer();
		super.handle(event);
	}

	void setupInputGrid() {
		//creation of the inputGrid to put all the input elements
		GridPane inputGrid = new GridPane();
		//everytime the user starts a game, the inputtext is cleared,
		inputTextField.clear();
		//label that shows the number of anagrams of the clueword.
		Label anagramsToFind = new Label("Find "+String.valueOf(((Anagrammer) game).anagrams.size() -1) + " Anagrams");
		inputTextField.setPrefWidth(200);
		inputTextField.setDisable(false);
		//creation of the submit button
		submitButton = new Button("Submit");
		submitButton.setPrefWidth(200);
		//set the eventhandler when there is a click on the button
		submitButton.setOnAction(new SubmitButtonEventHandler());
		//set the eventHandler when the Enter key is pressed and the button has the focus of the application.
		submitButton.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER){
				submitButton.fire();
			}
		}); 
		//set the eventhandler when the Enter key is pressed and the inputText has the focus of the application
		inputTextField.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER){
				submitButton.fire();
			}
		}); 
		//adding the elements to the main inputGrid
		inputGrid.add(inputTextField, 0, 0);
		inputGrid.add(submitButton, 0, 1);
		inputGrid.add(anagramsToFind, 0, 2);
		//setting the characteristics of the text area
		anagramsTextArea.setPrefSize(200, 100);
		anagramsTextArea.setEditable(false);
		anagramsTextArea.setDisable(false);
		anagramsTextArea.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
		anagramsTextArea.clear();
		inputGrid.add(anagramsTextArea, 0, 3);
		userGrid.setPadding(new Insets(40,30,10,30));
		//adding the inputGrid that contains all the elements to the UserGrid
		userGrid.add(inputGrid, 0, 1);
	}


	@Override
	void setupUserGrid() {
		//every time the user click on the Anagram game, a GridPane is created to show a new game
		userGrid = new GridPane();
		//Setting the properties of the information label of the Anagram game
		messageLabel.setText("Let's play Anamgrams!");
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


	public class SubmitButtonEventHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			//getting the input form the user
			String inputUser = inputTextField.getText();
			//calling the nextry method to evaluate the user input.
			int finalValue = game.nextTry(inputUser);
			//if the returned value is 1, it means that the input was correct and it is an anagram of clueword
			if(finalValue == 1){
				//addind one to the number of trials
				game.trialCount++;
				//appending the user input to the text area to show the correctness of the input.
				anagramsTextArea.appendText(inputUser + "\n");
				if(game.won){
					//if the user won, we disabled all the input elements and show the final score.
					inputTextField.setDisable(true);
					anagramsTextArea.setDisable(true);
					submitButton.setDisable(true);
					scoreValueLabels[3].setText(String.format("%2.2f", game.calcScore()));
				}else if(10 - game.trialCount == 0){
					//setting the message showing the user he/she has lost.
					game.message = "Sorry you lost!!";
					//we disabled all the input elements and show the final score.
					inputTextField.setDisable(true);
					anagramsTextArea.setDisable(true);
					submitButton.setDisable(true);
					scoreValueLabels[3].setText(String.format("%2.2f", game.calcScore()));
				}
			}else if(finalValue == 0){
				//if the user input is not a clueword anagram, we add one to the number of trials.
				game.trialCount++;
				//if the number of trials is equals to zero, it means that the user has lost.
				if(10 - game.trialCount == 0){
					//setting the message showing the user he/she has lost.
					game.message = "Sorry you lost!!";
					//we disabled all the input elements and show the final score.
					inputTextField.setDisable(true);
					anagramsTextArea.setDisable(true);
					submitButton.setDisable(true);
					scoreValueLabels[3].setText(String.format("%2.2f", game.calcScore()));
				}
			}
			//showing all the messages which were set in the steps above
			messageLabel.setText(game.message);
			//updating the score values
			scoreValueLabels[0].setText(String.valueOf(10 - game.trialCount));
			scoreValueLabels[1].setText(String.valueOf(game.hit));
			scoreValueLabels[2].setText(String.valueOf(game.miss));
			//Clearing the inputTextField
			inputTextField.setText("");
			//appending the userinput to the guesse meade previously.
			game.userInputs.append(inputUser.toLowerCase() + "\n");
		}
	}
}