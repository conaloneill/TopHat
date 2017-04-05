package monopoly;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import cards.Card;
import monopoly.GameScreen;
import monopoly.Player;
import property.PropertyInfo;

public class UserInputMethods {


	//This method moves the players around the board based on player x/y position and value of the dice. 
	public void movePlayer(int move, boolean passGo) {
		GameScreen gameScreen = GameScreen.screen;

		Player currPlayer = gameScreen.Players.get(gameScreen.currentPlayerNumber-1);
		for(int i = 1; i <= move; i++) {
			//While on the bottom squares, players move to the left
			if(currPlayer.currentTile <= 9) { 
				currPlayer.xPosition -= GameScreen.TILESIZE;
				currPlayer.currentTile++;
			}
			//While along the left side of the board, players move upwards
			else if(currPlayer.currentTile > 9 && currPlayer.currentTile <= 19) {  
				currPlayer.yPosition -= GameScreen.TILESIZE;
				currPlayer.currentTile++;
			}
			//While along the top squares, players move to the right
			else if(currPlayer.currentTile > 19 && currPlayer.currentTile <= 29) {  
				currPlayer.xPosition += GameScreen.TILESIZE;
				currPlayer.currentTile++;
			}
			//While along the left side of the board, players move downwards
			else if(currPlayer.currentTile > 29 && currPlayer.currentTile <= 39) { 
				currPlayer.yPosition += GameScreen.TILESIZE;
				currPlayer.currentTile++;

				//Resets their current tile number to zero when they reach the "go" square
				if(currPlayer.currentTile >= gameScreen.Tiles.size()) {  
					currPlayer.currentTile = 0;

					//Pass go, collect 200
					if(passGo) {
						gameScreen.infoPanel.append("Player " + currPlayer.getName() + " passed Go and received 200!");
						currPlayer.deposit(200);
					}
				}
			}
		}	
		gameScreen.rollAgain = false;


		//Checks to keep track how many times Player rolls doubles
		if (gameScreen.dice.checkDouble() && gameScreen.doubleCount < 4) {
			gameScreen.doubleCount++;
			gameScreen.rollAgain = true;
			gameScreen.rollTurns++;
		} 
		else {
			gameScreen.rollTurns = 1;
			gameScreen.rollAgain = false;
			gameScreen.doubleCount = 0;
		}


		if (gameScreen.doubleCount == 3) {
			gameScreen.rollAgain = false;
			gameScreen.rollTurns = 1;
			gameScreen.doubleCount = 0;
		}
	}


	public void payRentOwed() {
		GameScreen gameScreen = GameScreen.screen;

		Tile currTile = gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile);
		int debt = 0;
		int playerNumberOwed = -1;;
		//Check for Tile isn't mortgaged
		if (!currTile.checkMortgaged()) {
			if (currTile.getType() == PropertyInfo.TYPE_PROPERTY) {
				//Set debt amount to rent of tile
				debt = currTile.getRent();
			}
			int i = 0, j = 0;
			for (Tile tile : gameScreen.Tiles) {
				if (currTile.getType() == PropertyInfo.TYPE_STATION && tile.getType() == PropertyInfo.TYPE_STATION) {
					if (tile.getOwnerNumber() == currTile.getOwnerNumber()) {
						i++;
					}
				}
				if (currTile.getType() == PropertyInfo.TYPE_UTILITY && tile.getType() == PropertyInfo.TYPE_UTILITY) {
					if (tile.getOwnerNumber() == currTile.getOwnerNumber()) {
						j++;
					}
				}
			}
			//Set debt equal to the station rent from the tile rent array if i has been changed
			if (i > 0) {
				debt = currTile.getStationRent(i);
			}
			//Set debt equal to dice value times the multiplier stored in the tile rent array if j has been changed
			else if (j > 0) {
				debt = (gameScreen.dice.getValue() * currTile.getUtilityRentMultiplier(j));
			}

			//Set which player is owed money
			playerNumberOwed = currTile.getOwnerNumber();

			//Tell player money is owed
			//infoPanel.append("\n" + currentPlayer.getName() + " owes " + Players.get(playerNumberOwed-1).getName() + " " + debt + ".");

			//Take money from player
			gameScreen.currentPlayer.spend(debt);
			//Give money to player owed
			gameScreen.Players.get(playerNumberOwed-1).deposit(debt);
			String s = "\n" + gameScreen.currentPlayer.getName() + " payed " + debt + " to " + gameScreen.Players.get(playerNumberOwed-1).getName() + ".";

			gameScreen.infoPanel.append(s);
		}
		else {
			gameScreen.infoPanel.append("\nProperty is mortgaged");
		}
	}


	public void goToJail() {
		GameScreen gameScreen = GameScreen.screen;

		gameScreen.currentPlayer.inJail = true;
		while(gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile).getType() != PropertyInfo.TYPE_JAIL) {
			movePlayer(1, false);
		}
		gameScreen.rollAgain = false;
	}


	public void payTax() {
		GameScreen gameScreen = GameScreen.screen;

		Tile currTile = gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile);
		int tax = currTile.getTaxAmount(); 

		gameScreen.currentPlayer.spend(tax);
		gameScreen.infoPanel.append(" and had to pay " + tax + " in tax to the Bank");

	}


	public String buy() {
		GameScreen gameScreen = GameScreen.screen;

		//If Tile is a property
		Tile currTile = gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile);
		if(currTile.getType() == PropertyInfo.TYPE_STATION ||
				currTile.getType() == PropertyInfo.TYPE_PROPERTY ||
				currTile.getType() == PropertyInfo.TYPE_UTILITY) {
			//If Tile is not owned
			if(currTile.getOwnerNumber() == -1) {
				//If player has enough money
				if(gameScreen.currentPlayer.getBalance() >= currTile.getPrice()){

					//Player spends price of property
					gameScreen.currentPlayer.spend(currTile.getPrice());
					currTile.setOwnerNumber(gameScreen.currentPlayer.playerNumber);
					//infoPanel.append("buy:: player number: " + currentPlayer.playerNumber + " owner num: " + currTile.getOwnerNumber() + "\n");

					//Adds number of stations
					if(currTile.getType() == PropertyInfo.TYPE_STATION) {
						gameScreen.currentPlayer.stationsOwned++;
					}
					//Adds number of utilities
					if(currTile.getType() == PropertyInfo.TYPE_UTILITY){
						gameScreen.currentPlayer.utilitiesOwned++;
					}

					//Handles same color groups
					if(currTile.getType() == PropertyInfo.TYPE_PROPERTY){
						if (currTile.getColour().equals("brown") || currTile.getColour().equals("navy")) {
							int numProperties = 2;
							setAllColoursOwned(currTile, numProperties);
						}
						else {
							int numProperties = 3;
							setAllColoursOwned(currTile, numProperties);
						}
					}
					return gameScreen.currentPlayer.getName() + " bought " + currTile.getName() + " for " + currTile.getPrice();
				}
				//Not enough Money
				else{
					return "Unable to buy Tile. Mortgage properties or demolish buildings to get money, or declare bankruptcy.";
				}
			}
			//Tile is already owned by a player
			else{
				if (currTile.getOwnerNumber() == gameScreen.currentPlayer.playerNumber) {
					return "Unable to buy Tile. You already own this Tile. Type \"property\" to see a list of owned properties";
				}
				return "Unable to buy Tile. Tile is already owned by a player.";
			}
		}
		//Tile isn't a property
		else{
			return "Unable to buy Tile. Not a property";
		}
	}

	//Loops through Tiles ArrayList and displays all Tiles owned by currentPlayer
	public void propertiesOwnedBycurrentPlayerNumber() {
		GameScreen gameScreen = GameScreen.screen;

		String properties = "Property owned by " + gameScreen.currentPlayer.getName() + " :";
		for(Tile o : gameScreen.Tiles){
			if(o.getOwnerNumber() == gameScreen.currentPlayer.playerNumber){
				properties += o.getName() + ", ";
			}
		}
		gameScreen.infoPanel.append(properties);
	}

	//ends players turn and increments counter to the next player
	public void done() {
		GameScreen gameScreen = GameScreen.screen;

		if (gameScreen.currentPlayerNumber >= gameScreen.Players.size()) { //If every player has had a turn, resets to player 1
			gameScreen.currentPlayerNumber = 1;
			gameScreen.currentPlayer = gameScreen.Players.get(gameScreen.currentPlayerNumber-1);
		} else { //Moves on to the next player
			gameScreen.currentPlayerNumber++;
			gameScreen.currentPlayer = gameScreen.Players.get(gameScreen.currentPlayerNumber-1);
		}
		gameScreen.rollTurns = 0;
		gameScreen.rollAgain = true;
		gameScreen.doubleCount = 0;
	}


	public void quitGame() {
		GameScreen gameScreen = GameScreen.screen;

		//Check if more than 1 Player left
		if (gameScreen.Players.size() > 1) {
			//calculate AssetValue for each player
			for (Player player : gameScreen.Players) {
				player.calculateAssetValue(gameScreen.Tiles);
			}

			//Sort Players array based on assetValue property of Player objects
			Collections.sort(gameScreen.Players, new Comparator<Player>() {
				@Override public int compare(Player p1, Player p2) {
					return p1.getAssetValue() - p2.getAssetValue(); // Ascending order
				}
			});
		}else{
			//If only 1 Player, Calculate Players assets
			gameScreen.Players.get(gameScreen.Players.size()-1).calculateAssetValue(gameScreen.Tiles);
		}

		//Sort is in ascending order
		Player winner = gameScreen.Players.get(gameScreen.Players.size()-1);

		gameScreen.infoPanel.append("Winner is Player " + winner.getName() + " with a total of " + winner.getAssetValue() + " in assets!");
		gameScreen.infoPanel.append("\nEnter \"exit\" to end the program\n");
		gameScreen.gameOver = true;
	}

	//builds x num of houses on the tile given by short name
	public void build(int num, String name) {
		GameScreen gameScreen = GameScreen.screen;

		for (Tile tile : gameScreen.Tiles) {
			if (gameScreen.propertyName.equals(tile.getShortName())) {
				//check if enough balance for building houses
				if (gameScreen.currentPlayer.getBalance() > (tile.getHousePrice()*num)) {
					//check player owns property
					if (tile.getOwnerNumber() == gameScreen.currentPlayer.playerNumber) {
						//Check if Tile is a property
						if (tile.getType() == PropertyInfo.TYPE_PROPERTY) {
							//check if property is currently mortgaged
							if (!tile.checkMortgaged()) {
								//Check if all color group owned
								if (tile.isAllColourOwned()) {
									//checks new amount of buildings plus existing wont be more than allowed
									if ((gameScreen.numBuildings + tile.getBuildings()) <= 5) {
										tile.addBuildings(num);
										int cost = tile.getHousePrice() * num;
										gameScreen.currentPlayer.spend(cost);
										gameScreen.infoPanel.append("Player " + gameScreen.currentPlayer.getName()
										+ " put " + num + " houses on " + tile.getName() + " at a cost of "
										+ cost);
									} else {
										gameScreen.infoPanel.append("Error max number of houses allowed is 5");
									}
								} else {
									gameScreen.infoPanel.append("You must own a full color group before you can build houses.");
								}
							} else {
								gameScreen.infoPanel.append("Error can't build houses on mortgaged property");
							}
						} else {
							gameScreen.infoPanel.append("You can't build on this type of property");
						}
					} else {
						gameScreen.infoPanel.append("Error can't build houses on a property you dont own!");
					} 
				} else {
					gameScreen.infoPanel.append("Error Player does not have enough money to build houses. Mortgage properties or demolish buildings to get money, or declare bankruptcy");
				}
			}
		}
		gameScreen.numBuildings = 0;
		gameScreen.propertyName = null;
	}

	// removes x num of houses on the tile given by short name
	public void demolish(int num, String name) {
		GameScreen gameScreen = GameScreen.screen;

		for (Tile tile : gameScreen.Tiles) {
			if (gameScreen.propertyName.equals(tile.getShortName())) {
				//check player owns property
				if (tile.getOwnerNumber() == gameScreen.currentPlayer.playerNumber) {
					//Check tile is a property type
					if (tile.getType() == PropertyInfo.TYPE_PROPERTY) {
						//check not trying to demolish more building than exists
						if (gameScreen.numBuildings <= tile.getBuildings()) {
							tile.removeBuildings(num);
							int cost = (tile.getHousePrice() / 2) * num;
							//Give player value from demolishing
							gameScreen.currentPlayer.deposit(cost);
							gameScreen.infoPanel.append("Player " + gameScreen.currentPlayer.getName() + " removed "
									+ num + " houses from " + tile.getName() + " for a gain of " + cost);
						} else {
							gameScreen.infoPanel.append("Error cant demolish more buildings than are built on property!");
						} 
					} else {
						gameScreen.infoPanel.append("Error cant demolish buildings as cant build on this type of property");
					}
				}
				else {
					gameScreen.infoPanel.append("Error get demolish houses on a property you dont own!");
				}
			}
		}
		gameScreen.numBuildings = 0;
		gameScreen.propertyName = null;
	}


	public void mortgage(String name) {
		GameScreen gameScreen = GameScreen.screen;

		for (Tile tile : gameScreen.Tiles) {
			//Searches for the property with the name the player entered
			if (name.equals(tile.getShortName())) {
				//check player owns property
				if (tile.getOwnerNumber() == gameScreen.currentPlayer.playerNumber) {
					//Checks if property has already been mortgaged
					if (tile.checkMortgaged()==false) {
						//Checks if property has buildings on it
						if (tile.getBuildings()==0) {
							tile.setMortgaged(true);
							//Give player mortgage value
							gameScreen.currentPlayer.deposit(tile.getMortgageValue());
							gameScreen.infoPanel.append("\nPlayer " + gameScreen.currentPlayer.getName() + " mortgaged " +tile.getName()+" for "+tile.getMortgageValue());
						}
						else {
							gameScreen.infoPanel.append("\nError can't mortgage a property with buildings on it. \nPlease use 'demolish' command to remove buildings from this property before mortgaging");
						}
					}
					else {
						gameScreen.infoPanel.append("\nError property already mortgaged");
					}
				}
				else {
					gameScreen.infoPanel.append("\nError can't mortgage a property you dont own!");
				}
			}
		}
	}


	public void redeem(String name) {
		GameScreen gameScreen = GameScreen.screen;

		for (Tile tile : gameScreen.Tiles) {
			//Searches for the property with the name the player entered
			if (name.equals(tile.getShortName())) {
				//Checks if player can afford to redeem property
				if (gameScreen.currentPlayer.getBalance() > ((tile.getMortgageValue()/10) *11 )) {
					//check player owns property
					if (tile.getOwnerNumber() == gameScreen.currentPlayer.playerNumber) {
						//Checks if property has already been mortgaged
						if (tile.checkMortgaged() == true) {
							tile.setMortgaged(false);
							gameScreen.currentPlayer.spend((tile.getMortgageValue() / 10) * 11);
							gameScreen.infoPanel.append("\nPlayer " + gameScreen.currentPlayer.getName() + " redeemed "
									+ tile.getName() + " for " + (tile.getMortgageValue() / 10) * 11);
						} else {
							gameScreen.infoPanel.append("\nError property isn't mortgaged");
						}
					} else {
						gameScreen.infoPanel.append("\nError can't redeem a property you dont own!");
					} 
				} else {
					gameScreen.infoPanel.append("Error Player does not have enough money to redeem property. Mortgage properties or demolish buildings to get money, or declare bankruptcy");
				}
			}
		}
	}

	//Sets all property owned by currentPlayer to have no owner (-1 denotes no owner)
	//Used when a player goes bankrupt
	public void bankrupt() {
		GameScreen gameScreen = GameScreen.screen;

		//Remove all buildings from tiles owned by player
		for (Tile tile : gameScreen.Tiles) {
			if (tile.getOwnerNumber() == gameScreen.currentPlayer.playerNumber) {
				gameScreen.currentPlayer.deposit(tile.removeAllBuildings());
			}
		}

		gameScreen.currentPlayer.calculateAssetValue(gameScreen.Tiles);
		int assets = gameScreen.currentPlayer.getAssetValue();
		//Return property to bank
		setPropertyUnowned();

		gameScreen.infoPanel.append(gameScreen.currentPlayer.getName() + " has declared bankruptcy and has left the game with assets of " 
				+ assets + ". All properties and buildings have been returned to the bank");

		//Removes player from ArrayList
		Player prevPlayer = gameScreen.currentPlayer;
		gameScreen.Players.remove(prevPlayer);


		if (gameScreen.currentPlayerNumber > gameScreen.Players.size()) { //If every player has had a turn, resets to player 1
			gameScreen.currentPlayerNumber = 1;
			gameScreen.currentPlayer = gameScreen.Players.get(gameScreen.currentPlayerNumber-1);
		} 
		else if (gameScreen.currentPlayerNumber <= gameScreen.Players.size()) {
			gameScreen.currentPlayer = gameScreen.Players.get(gameScreen.currentPlayerNumber-1);
		}
		gameScreen.rollTurns = 0;
		gameScreen.rollAgain = true;
		gameScreen.doubleCount = 0;

	}


	public void info() {
		GameScreen gameScreen = GameScreen.screen;

		gameScreen.infoPanel.append("Short name of " + gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile).getName() + " is " + gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile).getShortName());
	}


	//method to check the game is over by checking if only 1 player left in the player 
	//array or user enter's "quit". This also prints the winner.
	public boolean checkGameOver() {
		GameScreen gameScreen = GameScreen.screen;

		if (gameScreen.Players.size() <= 1 || gameScreen.choice == "quit") {
			gameScreen.infoPanel.append("Game Over! Only one player remaining.\n Winner is " + gameScreen.Players.get(gameScreen.Players.size()-1).getName()
					+ " with assets worth " + gameScreen.Players.get(gameScreen.Players.size()-1).getAssetValue());
			return true;
		}
		return false;
	}

	//Checks if group of colors all owned by same player
	//change to holding record of tile numbers and scrolling array of 2/3 size not arraylist of 40 tiles
	public void setAllColoursOwned(Tile currTile, int numProperties) {
		GameScreen gameScreen = GameScreen.screen;

		int i = 0;
		for (Tile tile : gameScreen.Tiles) {
			if (tile.getType() == PropertyInfo.TYPE_PROPERTY) {
				if (tile.getOwnerNumber() == gameScreen.currentPlayer.playerNumber && tile.getColour().equals(currTile.getColour())) {
					i++; 
					if (i == numProperties) {
						for (Tile tile2 : gameScreen.Tiles) {
							if (tile2.getColour().equals(currTile.getColour())) {
								tile2.setAllColourOwned(true);
							}
						}
					}
				}
			}
		}
	}


	public void setPropertyUnowned() {
		GameScreen gameScreen = GameScreen.screen;

		for (Tile tile : gameScreen.Tiles) {
			if (tile.getOwnerNumber() == gameScreen.currentPlayer.playerNumber) {
				tile.setOwnerNumber(-1);
			}
		}
	}

	public void drawCard(int tileType) {
		GameScreen gameScreen = GameScreen.screen;
		Card cardDrawn;
		int cardNum = ThreadLocalRandom.current().nextInt(0, 16);
		if (tileType == PropertyInfo.TYPE_CHANCE) {
			cardDrawn = gameScreen.ChanceCards.get(cardNum);
		}
		else{
			cardDrawn = gameScreen.ComChestCards.get(cardNum);
		}
		//Get card type
		int cardType = cardDrawn.getType();
		//Show card message
		gameScreen.infoPanel.append("\n\"" + cardDrawn.getMessage() + "\"");

		//Do card action based on type
		switch(cardType){
		case Card.TYPE_FINE :
			gameScreen.currentPlayer.spend(cardDrawn.getAmount());
			gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " spent " + cardDrawn.getAmount() + ".");
			break;

		case Card.TYPE_REWARD :
			gameScreen.currentPlayer.deposit(cardDrawn.getAmount());
			gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " got " + cardDrawn.getAmount() + ".");
			break;

		case Card.TYPE_GOOJ :
			gameScreen.currentPlayer.numberOfGOOJCards++;
			gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " got a 'Get Out of Jail' card.");
			break;

		case Card.TYPE_GOTO : 
			gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " moved to " + gameScreen.Tiles.get(cardDrawn.getDestination()).getName() + ".");

			while (gameScreen.currentPlayer.currentTile != cardDrawn.getDestination()) {
				//Move Player
				movePlayer(1, cardDrawn.passGo);
			}
			
			landedOnNewTile(gameScreen);
			break;

		case Card.TYPE_BUILDINGFINE :

			int houseCount = 0;
			int hotelCount = 0;
			int totalCost = 0;
			//Count number of houses and hotels
			for(Tile tile : gameScreen.Tiles){
				if(tile.getOwnerNumber() == gameScreen.currentPlayerNumber){
					if(tile.getBuildings() <= 4){
						houseCount += tile.getBuildings();
					}else if(tile.getBuildings() == 5){
						hotelCount ++;
					}
				}
			}
			totalCost = (cardDrawn.getHouseCost() * houseCount) + (cardDrawn.getHotelCost() * hotelCount);
			gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " spent " + totalCost + ".");
			gameScreen.currentPlayer.spend(totalCost);

			break;

		case Card.TYPE_MONEYFROMEACHPLAYER :
			int total = 0;
			int moneyFromPlayer = cardDrawn.getAmount();

			//Take money from each player and notify them
			for(Player player : gameScreen.Players){
				if(player != gameScreen.currentPlayer){
					player.spend(moneyFromPlayer);
					total += moneyFromPlayer;
					gameScreen.infoPanel.append("\n" + player.getName() + " spent " + moneyFromPlayer + ".");
				}
			}

			//Give money to player
			gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " got " + total + ".");
			gameScreen.currentPlayer.deposit(total);

			break;

		case Card.TYPE_MOVEXSPACES :

			int moveSpaces = cardDrawn.getDestination();
			int destination = 0;

			//Check if moving backwards or forwards
			if(moveSpaces < 0){
				//Find destination tile
				destination = gameScreen.currentPlayer.currentTile - moveSpaces;
				//Find Distance to move
				moveSpaces = gameScreen.Tiles.size()  - (destination - gameScreen.currentPlayer.currentTile);
				//Move Player Backwards
				movePlayer(moveSpaces, false);

			}else{
				//Move Player Forward
				movePlayer(moveSpaces, true);
			}

			
			landedOnNewTile(gameScreen);

			break;

		case Card.TYPE_FINEORCHANCE :
			//Display Card message
			gameScreen.infoPanel.append("\nEnter 'fine' to pay the fine or enter 'chance' to take a chance card.");
			//Put game into Loop until selection is made.
			//Selection is handled in GameScreen Class
			gameScreen.inFineOrChanceLoop = true;
			//Set amount to tax player if 'fine' is chosen
			gameScreen.taxAmount = cardDrawn.getAmount();
			
			break;
			
		default :
			gameScreen.infoPanel.append("\nError retrieving card.");
		}
	}


	//Called when a player Lands on a new tile
	public void landedOnNewTile(GameScreen gameScreen) {
		//Landed on a new Tile
		//Info about Tile player landed on:
		gameScreen.infoPanel.append("\n" + gameScreen.currentPlayer.getName() + " landed on " + gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile).getName());

		Tile currTile = gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile);
		int currTileType = gameScreen.Tiles.get(gameScreen.currentPlayer.currentTile).getType();

		//If property can be bought(Is property, station or utility with no owner)
		if((currTileType == PropertyInfo.TYPE_PROPERTY || currTileType == PropertyInfo.TYPE_STATION || currTileType == PropertyInfo.TYPE_UTILITY)  && currTile.getOwnerNumber() == -1) {
			gameScreen.infoPanel.append("\nThis property may be bought for " + currTile.getPrice() + ".");
		}
		//If Tile landed on is owned and not by current player
		else if(currTile.getOwnerNumber() != -1 && currTile.getOwnerNumber() != gameScreen.currentPlayer.playerNumber){
			payRentOwed();
		}
		//If landed on go to jail
		else if(currTileType == PropertyInfo.TYPE_GOTO_JAIL) {
			goToJail();
			gameScreen.infoPanel.append("\nYou have been sent to Jail. In order to get out, you must pay a fine of 50 or roll doubles on your next turn."
					+ "\nUse 'pay' to pay the fine, 'roll' to roll for doubles or 'card' to use a 'Get Out of Jail' card");
		}

		//If landed on tax
		else if(currTileType == PropertyInfo.TYPE_TAX) {
			payTax();
		}
		//If landed on card
		else if (currTileType == PropertyInfo.TYPE_CHANCE || currTileType == PropertyInfo.TYPE_COMMUNITY) {
			drawCard(currTileType);
		}
	}

}
