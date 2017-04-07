package monopoly;

import monopoly.GameScreen;
import monopoly.UserInputMethods;

public class UserInput {

	UserInputMethods userInputMethods = new UserInputMethods();

	//method for all user inputs on a players turn. gets the input and calls methods to implement choice
	public void userInput() {
		GameScreen gameScreen = GameScreen.screen;
		//Set currentPlayer based on currentPlayerNumber
		gameScreen.currentPlayer = gameScreen.Players.get(gameScreen.currentPlayerNumber-1);
		//Get user input
		gameScreen.choice = gameScreen.commandPanel.getText().trim().toLowerCase();
		//Remove text from command panel
		gameScreen.commandPanel.setText(null);
		//Add text to info panel
		gameScreen.infoPanel.append(gameScreen.choice + "\n"); 


		//switch case needs just build or demolish in the string to activate
		//this if extracts the property name and number of buildings and sets choice to be build/demolish
		if (gameScreen.choice.startsWith("build") || gameScreen.choice.startsWith("demolish")) {
			String choiceCopy = gameScreen.choice;
			try {
				gameScreen.numBuildings = Integer.parseInt(choiceCopy.replaceAll("\\D+",""));
			} catch (NumberFormatException e) {
				//Don't need this in final product
				//System.out.println("Number expected");
				//e.printStackTrace();
			}
			choiceCopy = choiceCopy.replace("build ", "");
			choiceCopy = choiceCopy.replace("demolish ", "");
			choiceCopy = choiceCopy.replaceAll("\\d","").trim();

			for (Tile tile : gameScreen.Tiles) {
				if (choiceCopy.equals(tile.getShortName())){
					gameScreen.propertyName = tile.getShortName();
				}
			}

			if (gameScreen.choice.startsWith("build")) {
				gameScreen.choice = "build";
			}
			else {
				gameScreen.choice = "demolish";
			}
		}

		//switch case just needs mortgage to activate
		//this if statement extracts the name of the property to be mortgaged
		if (gameScreen.choice.startsWith("mortgage") || gameScreen.choice.startsWith("redeem")) {
			String choiceCopy = gameScreen.choice;
			boolean found = false;
			choiceCopy = choiceCopy.replace("mortgage", "").trim();
			choiceCopy = choiceCopy.replace("redeem", "").trim();

			for (Tile tile : gameScreen.Tiles) {
				if (choiceCopy.equals(tile.getShortName())){
					found = true;
					gameScreen.propertyName = tile.getShortName();
				}
			}
			if (found == false) {
				gameScreen.propertyName=null;
				gameScreen.infoPanel.append("\nError invalid property name");
			}
			if (gameScreen.choice.startsWith("mortgage")) {
				gameScreen.choice = "mortgage";
			}
			else {
				gameScreen.choice = "redeem";
			}
		}

		//Check user if input is one of our defined commands
		switch(gameScreen.choice) {
		case "help":
			gameScreen.infoPanel.append(gameScreen.helpString);
			break;

			//Rolls the dice and calls move player method. Checks for any rent owed and assigns that to player
		case "roll":
			//Re-roll the dice to get new values
			gameScreen.dice.roll();

			//If player is in jail
			if(gameScreen.currentPlayer.inJail){
				//If player hasn't rolled yet
				if(gameScreen.rollAgain) {
					gameScreen.infoPanel.append(gameScreen.currentPlayer.getName() + " rolled " + gameScreen.dice.getDice1() + " and " + gameScreen.dice.getDice2() + "\n");

					//Check if player successfully rolled doubles
					if(gameScreen.dice.checkDouble()) {
						gameScreen.infoPanel.append("You rolled doubles and got out of Jail!\n");
						gameScreen.currentPlayer.inJail = false;
					}
					else{
						gameScreen.infoPanel.append("Unlucky!, You need to roll doubles or pay a fine of 50 to get out of Jail");
						gameScreen.rollAgain = false;
					}
				//If player has already rolled
				}else{
					gameScreen.infoPanel.append("You can't roll again this turn. To get out of Jail either pay a fine of 50 or roll doubles on your next turn");
				}
			}

			else {
				//Check if player has rolled doubles less than 3 times but is still allowed roll again
				if (gameScreen.rollTurns < 3 && gameScreen.rollTurns >= 0 && gameScreen.rollAgain) {

					//Check positive balance
					if(gameScreen.currentPlayer.getBalance() >= 0){

						
						//Move currentPlayer the value of the dice, Pass Go is true
						gameScreen.infoPanel.append(gameScreen.currentPlayer.getName() + " rolled " + gameScreen.dice.getDice1() + " and " + gameScreen.dice.getDice2() + ". Moved " + gameScreen.dice.getValue() + " squares"); //Says how many squares a player has moved
						gameScreen.userInputMethods.movePlayer(gameScreen.dice.getValue(), true);
						
						if(gameScreen.dice.checkDouble() && !gameScreen.currentPlayer.inJail) {
							gameScreen.infoPanel.append("\nDoubles! Roll again!"); //add text to info pane
						}

						//Action of tile player landed on
						userInputMethods.landedOnNewTile(gameScreen);

					}else{
						gameScreen.infoPanel.append("\nCan't continue with a negative balance! Try mortgaging property with 'mortgage' or declare bankruptcy with 'bankrupt'");
					}

				}

				//Player has already rolled and didn't get doubles
				else {
					gameScreen.infoPanel.append("Error you cant roll again this turn. Please end turn with 'done'\nor type 'help' for the other options");
				}
			}

			break;

		case "balance": 
			//Returns the players current balance
			gameScreen.infoPanel.append("Player " + gameScreen.currentPlayer.getName() + " has a balance of: " + gameScreen.currentPlayer.getBalance());
			break;

		case "buy":
			//Checks to make sure the player has rolled before allowing them to buy a property
			if(gameScreen.rollTurns==0) {  
				gameScreen.infoPanel.append("Error you must roll before you can buy a property");	
			}
			else {
				gameScreen.infoPanel.append(userInputMethods.buy());
			}
			break;

		case "property":
			//Prints properties owned by currentPlayer
			userInputMethods.propertiesOwnedBycurrentPlayerNumber();
			break;

		case "done":
			//Check if player must roll again this turn
			if((gameScreen.rollTurns>0 && gameScreen.doubleCount == 0) || !gameScreen.rollAgain) {

				//Check if player has positive  balance
				if(gameScreen.currentPlayer.getBalance() >=0){

					//Move on to next player
					userInputMethods.done();
					//Check if only one Player left
					if (gameScreen.Players.size() <= 1){
						userInputMethods.quitGame();
					}
					//Has negative balance
				}else{
					gameScreen.infoPanel.append("\nCan't continue with a negative balance! Try mortgaging property or declare bankruptcy with 'bankrupt'");
				}
			}
			//Must roll again
			else {
				gameScreen.infoPanel.append("\nError: Must roll before turn can end");
			}
			break;

		case "quit":
			userInputMethods.quitGame();
			break;

			//Pay fine when in jail
		case "pay":
			if(!gameScreen.currentPlayer.inJail) {
				gameScreen.infoPanel.append("\nError: " + gameScreen.currentPlayer.getName() + " isn't in Jail.");
			}
			else{
				gameScreen.currentPlayer.spend(50);
				gameScreen.currentPlayer.inJail = false;
				gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " paid a fine of 50 and got out of Jail.");
			}
			break;

			//Use GOOJ card when in jail
		case "card":
			//Is player in jail
			if(!gameScreen.currentPlayer.inJail) {
				gameScreen.infoPanel.append("\nError: " + gameScreen.currentPlayer.getName() + " isn't in Jail.");
			}
			else{
				//Does player have any gooj cards
				if(gameScreen.currentPlayer.numberOfGOOJCards == 0){
					gameScreen.infoPanel.append("\nError: " + gameScreen.currentPlayer.getName() + " doesn't have any 'Get out of Jail' cards to use.");
				}else{
					//Use card
					gameScreen.currentPlayer.numberOfGOOJCards --;
					gameScreen.currentPlayer.inJail = false;
					gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " used a 'Get out of Jail' card and got out of Jail.");
				}
			}
			break;

			//build houses
		case "build":
			//Error handling checks
			if(gameScreen.numBuildings > 0 && gameScreen.propertyName != null){
				userInputMethods.build(gameScreen.numBuildings, gameScreen.propertyName);
			}else{
				gameScreen.infoPanel.append("Error Need positive number and valid property name for building.");
			}
			break;

			//demolish houses
		case "demolish":
			//Error handling checks
			if(gameScreen.numBuildings > 0 && gameScreen.propertyName != null){
				userInputMethods.demolish(gameScreen.numBuildings, gameScreen.propertyName);
			}else{
				gameScreen.infoPanel.append("Error Need positive number and valid property name for demolishing.");
			}
			break;

			//mortgage an owned property
		case "mortgage":
			userInputMethods.mortgage(gameScreen.propertyName);
			break;

			//buy back a mortgaged property
		case "redeem":
			userInputMethods.redeem(gameScreen.propertyName);
			break;

			//declare bankruptcy
		case "bankrupt":
			userInputMethods.bankrupt();
			break;

			//get the short name of the current tile
		case "info" :
			userInputMethods.info();
			break;

		case "exit" :
			System.exit(0);
			break;

		default:
			gameScreen.infoPanel.append("Error: Invalid command");
			break;
		}

		//Asks the next player for input
		gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " :");  
	}
}
