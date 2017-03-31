package cards;

import monopoly.GameScreen;

public class CardInfo {
	
	public void assignCards(){
		//Allow reference to our game screen
		GameScreen screen = GameScreen.screen;
		
		//Create 16 blank cards in each deck
		for(int i = 0;i<15;i++){
			screen.ChanceCards.add(new Card(i));
			screen.ComChestCards.add(new Card(i));
		}

		//Assign card info
		for(Card c : screen.ChanceCards){
			switch(c.cardNumber){
			case 0:
				c.setMessage("Advance to Go.");
				c.setDestination(0);
				c.passGo = true;
				c.setType(Card.TYPE_GOTO);
				break;
			case 1:
<<<<<<< HEAD
				c.setMessage("Go to jail. Move directly to jail. Do not pass Go. Do not collect Â£200.");
=======
				c.setMessage("Go to jail. Move directly to jail. Do not pass Go. Do not collect £200.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setDestination(30);
				c.passGo = false;
				c.setType(Card.TYPE_GOTO);
				break;
			case 2:
<<<<<<< HEAD
				c.setMessage("Advance to Pall Mall. If you pass Go collect Â£200.");
=======
				c.setMessage("Advance to Pall Mall. If you pass Go collect £200.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setDestination(11);
				c.passGo = true;
				c.setType(Card.TYPE_GOTO);
				break;
			case 3:
<<<<<<< HEAD
				c.setMessage("Take a trip to Marylebone Station and if you pass Go collect Â£200");
=======
				c.setMessage("Take a trip to Marylebone Station and if you pass Go collect £200");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setDestination(15);
				c.passGo = true;
				c.setType(Card.TYPE_GOTO);
				break;
			case 4:
<<<<<<< HEAD
				c.setMessage("Advance to Trafalgar Square. If you pass Go collect Â£200");
=======
				c.setMessage("Advance to Trafalgar Square. If you pass Go collect £200");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setDestination(24);
				c.passGo = true;
				c.setType(Card.TYPE_GOTO);
				break;
			case 5:
				c.setMessage("Advance to Mayfair");
				c.setDestination(39);
				c.passGo = false;
				c.setType(Card.TYPE_GOTO);
				break;
			case 6:
				c.setMessage("Go back three spaces");
				c.setDestination(-3);
				c.passGo = false;
				c.setType(Card.TYPE_MOVEXSPACES);             //Maybe??
				break;
			case 7:
<<<<<<< HEAD
				c.setMessage("Make general repairs on all of your houses. For each house pay Â£25. For each hotel pay Â£100.");
=======
				c.setMessage("Make general repairs on all of your houses. For each house pay £25. For each hotel pay £100.");
				c.setBuildingCosts(25, 100);
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setType(Card.TYPE_BUILDINGFINE);
				break;
			case 8:
<<<<<<< HEAD
				c.setMessage("You are assessed for street repairs: Â£40 per house, Â£115 per hotel.");
=======
				c.setMessage("You are assessed for street repairs: £40 per house, £115 per hotel.");
				c.setBuildingCosts(40, 115);
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setType(Card.TYPE_BUILDINGFINE);
				break;
			case 9:
<<<<<<< HEAD
				c.setMessage("Pay school fees of Â£150.");
=======
				c.setMessage("Pay school fees of £150.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(150);
				c.setType(Card.TYPE_FINE);
				break;
			case 10:
<<<<<<< HEAD
				c.setMessage("Drunk in charge fine Â£20.");
=======
				c.setMessage("Drunk in charge fine £20.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(20);
				c.setType(Card.TYPE_FINE);
				break;
			case 11:
<<<<<<< HEAD
				c.setMessage("Speeding fine Â£15");
=======
				c.setMessage("Speeding fine £15");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(15);
				c.setType(Card.TYPE_FINE);
				break;
			case 12:
<<<<<<< HEAD
				c.setMessage("Your building loan matures. Receive Â£150");
=======
				c.setMessage("Your building loan matures. Receive £150");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(150);
				c.setType(Card.TYPE_REWARD);
				break;
			case 13:
<<<<<<< HEAD
				c.setMessage("You have won a crossword competition. Collect Â£100");
=======
				c.setMessage("You have won a crossword competition. Collect £100");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(100);
				c.setType(Card.TYPE_REWARD);
				break;
			case 14:
<<<<<<< HEAD
				c.setMessage("Bank pays you dividend of Â£50");
=======
				c.setMessage("Bank pays you dividend of £50");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(50);
				c.setType(Card.TYPE_REWARD);
				break;
			case 15:
				c.setMessage("Get out of jail free. This card may be kept until needed or sold.");
				c.setType(Card.TYPE_GOOJ);
				break;

			default:
				break;
			}
		}
		for(Card c : screen.ComChestCards){
			switch(c.cardNumber){
			case 0:
				c.setMessage("Advance to Go.");
				c.setDestination(0);
				c.passGo = true;
				c.setType(Card.TYPE_GOTO);
				break;
			case 1:
				c.setMessage("Go back to Old Kent Road");
				c.setDestination(1);
				c.passGo = false;
				c.setType(Card.TYPE_GOTO);
				break;
			case 2:
<<<<<<< HEAD
				c.setMessage("Go to jail. Move directly to jail. Do not pass Go. Do not collect Â£200.");
=======
				c.setMessage("Go to jail. Move directly to jail. Do not pass Go. Do not collect £200.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setDestination(30);
				c.passGo = false;
				c.setType(Card.TYPE_GOTO);
				break;
			case 3:
<<<<<<< HEAD
				c.setMessage("Pay hospital Â£100");
=======
				c.setMessage("Pay hospital £100");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(100);
				c.setType(Card.TYPE_FINE);
				break;
			case 4:
<<<<<<< HEAD
				c.setMessage("Doctor's fee. Pay Â£50.");
=======
				c.setMessage("Doctor's fee. Pay £50.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(50);
				c.setType(Card.TYPE_FINE);
				break;
			case 5:
<<<<<<< HEAD
				c.setMessage("Pay your insurance premium Â£50");
=======
				c.setMessage("Pay your insurance premium £50");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(50);
				c.setType(Card.TYPE_FINE);
				break;
			case 6:
<<<<<<< HEAD
				c.setMessage("Bank error in your favour. Collect Â£200.");
=======
				c.setMessage("Bank error in your favour. Collect £200.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(200);
				c.setType(Card.TYPE_REWARD);
				break;
			case 7:
<<<<<<< HEAD
				c.setMessage("Annuity matures. Collect Â£100.");
=======
				c.setMessage("Annuity matures. Collect £100.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(100);
				c.setType(Card.TYPE_REWARD);
				break;
			case 8:
<<<<<<< HEAD
				c.setMessage("You inherit Â£100.");
=======
				c.setMessage("You inherit £100.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(100);
				c.setType(Card.TYPE_REWARD);
				break;
			case 9:
<<<<<<< HEAD
				c.setMessage("From sale of stock you get Â£50.");
=======
				c.setMessage("From sale of stock you get £50.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(50);
				c.setType(Card.TYPE_REWARD);
				break;
			case 10:
<<<<<<< HEAD
				c.setMessage("Receive interest on 7% preference shares: Â£25");
=======
				c.setMessage("Receive interest on 7% preference shares: £25");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(25);
				c.setType(Card.TYPE_REWARD);
				break;
			case 11:
<<<<<<< HEAD
				c.setMessage("Income tax refund. Collect Â£20");
=======
				c.setMessage("Income tax refund. Collect £20");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(20);
				c.setType(Card.TYPE_REWARD);
				break;
			case 12:
<<<<<<< HEAD
				c.setMessage("You have won second prize in a beauty contest. Collect Â£10.");
=======
				c.setMessage("You have won second prize in a beauty contest. Collect £10.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setAmount(10);
				c.setType(Card.TYPE_REWARD);
				break;
			case 13:
<<<<<<< HEAD
				c.setMessage("It is your birthday. Collect Â£10 from each player");
=======
				c.setMessage("It is your birthday. Collect £10 from each player");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setType(Card.TYPE_MONEYFROMEACHPLAYER);
				break;
			case 14:
				c.setMessage("Get out of jail free. This card may be kept until needed or sold.");
				c.setType(Card.TYPE_GOOJ);
				break;
			case 15:				
<<<<<<< HEAD
				c.setMessage("Pay a Â£10 fine or take a Chance.");
=======
				c.setMessage("Pay a £10 fine or take a Chance.");
>>>>>>> branch 'master' of https://github.com/UCD-COMP20050/TopHat.git
				c.setType(Card.TYPE_FINEORCHANCE);
				break;

			default:
				break;
			}
		}
	}
}
