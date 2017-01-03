/**
 * Alirio Rivera Cuervo
 * andrewID: ariverac
 */
package hw3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class GameHandler implements EventHandler<ActionEvent> {

	WordGame game;	

	Label messageLabel = new Label(" ");		//used to display the message after very user-move
	Text[] clueWordLabels;	 					//displays clueWord 
	GridPane userGrid= new GridPane();			//holds the alphaGrid and the scoreGrid
	Label[] scoreValueLabels;					//scores used in scoreGrid	

	abstract void setupUserGrid();
	abstract void setupInputGrid();

	@Override
	public void handle(ActionEvent event) {	
		//methods for building the grid of the game
		setupUserGrid();
		setupScoreGrid();
		setupClueWordStack();
	}		

	/**
	 * Method used to create the StackPane which is going to content a label to show the clue word
	 * of the hagman game
	 */
	void setupClueWordStack() {		
		//creation of the label that is going to show the clueword
		clueWordLabels = new Text[1];
		//creation of the stackpane that is going to content the label of the clueWord
		StackPane clueWordStack = new StackPane();
		//setting properties of the label
		clueWordStack.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		//I set the clueWord to be shown through the label created
		Text clueText = new Text(game.clueWord);		
		clueWordLabels[0] = clueText;
		//I set the style thorough the css file of the program calling the  clueWordLabelStyle part
		clueWordLabels[0].getStyleClass().add("clueWordLabelStyle");
		clueWordStack.getChildren().add(clueWordLabels[0]);
		clueWordStack.setAlignment(Pos.CENTER);
		//Adding the stackPane to the main window layout.
		WordNerd.root.setCenter(clueWordStack);
	}

	/**
	 * Method used to build the panel which is going to content the information abut the game: 
	 * hit, miss and scores
	 */
	void setupScoreGrid() {
		scoreValueLabels = new Label[4];

		//Grid used to contet the information of the game
		GridPane scoreGrid = new GridPane();

		//Creation of the static labels to indicate what information is being showed in the game
		Label trialsToGo = new Label("Trials to go ");
		Label hitNMiss = new Label("Hit n Miss ");
		Label GameScore = new Label("Game Score ");

		//Labels used to show the information of the game while the playes is playing 
		Label trialsToGoLabel = new Label(String.valueOf(WordGame.MAX_TRIALS));
		Label hitLabel = new Label(String.valueOf(this.game.hit));
		Label missLabel = new Label(String.valueOf(this.game.miss));
		Label GameScoreLabel = new Label(String.valueOf(this.game.score));

		//addition of the labels to the array which is going to be used to update the information
		//of the game
		scoreValueLabels[0] = trialsToGoLabel;
		scoreValueLabels[1] = hitLabel;
		scoreValueLabels[2] = missLabel;
		scoreValueLabels[3] = GameScoreLabel;

		//adding the static labels to the scoreGrid
		scoreGrid.add(trialsToGo, 0, 0);
		scoreGrid.add(hitNMiss, 0, 1);
		scoreGrid.add(GameScore, 0, 2);

		//align the values of every label to a center position
		scoreValueLabels[0].setAlignment(Pos.CENTER);
		scoreValueLabels[1].setAlignment(Pos.CENTER);
		scoreValueLabels[2].setAlignment(Pos.CENTER);
		scoreValueLabels[3].setAlignment(Pos.CENTER);

		//setting the size of the labels which show the information of the game
		scoreValueLabels[0].setMinSize(80, 20);
		scoreValueLabels[1].setMinSize(40, 20);
		scoreValueLabels[2].setMinSize(40, 20);
		scoreValueLabels[3].setMinSize(80, 20);

		//setting the background of the labels which show the game information.		
		scoreValueLabels[0].setStyle("-fx-background-color: #D4DAEA;");
		scoreValueLabels[1].setStyle("-fx-background-color: #63DF78;");
		scoreValueLabels[2].setStyle("-fx-background-color: #DF6363;");
		scoreValueLabels[3].setStyle("-fx-background-color: #D4DAEA;");

		//adding the labels to the scoreGrid
		scoreGrid.add(scoreValueLabels[0], 1, 0, 2, 1);
		scoreGrid.add(scoreValueLabels[1], 1, 1, 1, 1);
		scoreGrid.add(scoreValueLabels[2], 2, 1, 1, 1);
		scoreGrid.add(scoreValueLabels[3], 1, 2, 2, 1);

		//we set the space between the scoreGrid and other elements of the userGrid
		scoreGrid.setPadding(new Insets(10,70,10,0));		

		//Lastly, I added the scoreGrid to the main UserGrid to be showed in the main window.
		userGrid.add(scoreGrid, 1, 1);
	}
}
