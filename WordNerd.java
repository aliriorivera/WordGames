/**
 * Alirio Rivera Cuervo
 * andrewID: ariverac
 */
package hw3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WordNerd extends Application{
	static BorderPane root = new BorderPane();
	Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//setting the properties of the main Stage
		primaryStage.setTitle("The Word Nerd");
		//creation of the scene which will contain all the elements of the game.
		Scene scene = new Scene(new VBox(), 600, 320);
		//calling the setupMenus Method
		setupMenus();
		//setting the main css file which contains the graphic properties of several objects in the 
		//application
		scene.getStylesheets().add("/hw3/gameStyles.css");
		//adding the main grid(rrot) to the scene
		scene.setRoot(root);
		//setting the scene of the primaryStage
		primaryStage.setScene(scene);
		//showing the stage
		primaryStage.show();       
	}

	/**
	 * Method used to build the Menu Bar showed at the top of the Application WindowS 
	 */
	public void setupMenus() {
		//I created a MenuBar
		MenuBar menuBar = new MenuBar();

		//Creation of the main menus of the menubar 
		Menu gameMenu = new Menu("Game");
		Menu playMenu = new Menu("Play");

		//Creation of the submenus for each one of the menus in the main bar of the window.
		MenuItem hangManMenu = new MenuItem("HangMan");
		//setting the eventhandler of the submeu Hangman.
		hangManMenu.setOnAction(new HangmanHandler());
		playMenu.getItems().add(hangManMenu);
		
		
		MenuItem anagramMenu = new MenuItem("Anagram");
		//setting the eventhandler of the submeu Hangman.
		anagramMenu.setOnAction(new AnagramHandler());
		playMenu.getItems().add(anagramMenu);

		//creation of the other general menus and adding their functionalities with anonymous classes.
		MenuItem closeGameMenu = new MenuItem("CloseGame");
		closeGameMenu.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				root.setBottom(null);
				root.setCenter(null);			
			}
		});
		MenuItem exitMenu = new MenuItem("Exit");
		//if the user click on the exit submenu, the application closes.
		exitMenu.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();				
			}			
		});

		//Adding all the menus to the manin MenuBar
		gameMenu.getItems().addAll(playMenu, closeGameMenu, exitMenu);

		//Creating the Help and About Menu
		Menu helpMenu = new Menu("Help");
		MenuItem aboutMenu = new MenuItem("About");

		//setting the event handler of the about menu to show the alert window.
		aboutMenu.setOnAction(new AboutEventHandler());

		helpMenu.getItems().add(aboutMenu);
		menuBar.getMenus().addAll(gameMenu, helpMenu);

		//adding the main menubar the top of the window.
		root.setTop(menuBar);
	}


	private class AboutEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//creation of the an alert type of window
			Alert alert = new Alert(AlertType.INFORMATION);
			//getting the png image, previously imported into the hw3 package
			Image image = new Image(this.getClass().getResource("image.png").toString()); 
			ImageView imageView = new ImageView();
			//setting the image to the ImageView
			imageView.setImage(image);
			imageView.setFitWidth(100);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			//attaching the image to the alert window
			alert.setGraphic(imageView);
			//setting the title of the alert window
			alert.setTitle("About");
			String textFinal = "Version 1.0\nRelease 1.0\nCopyright CMU\nDeveloped by a minion!";
			//setting the main header of the alert window
			alert.setHeaderText("The Word Nerd");
			//setting the text showed in the window
			alert.setContentText(textFinal);
			//showing the alert window to the user.
			alert.showAndWait();
		}
	}
}