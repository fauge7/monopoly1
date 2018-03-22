package monopoly.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Board{
	private Random random;
	private ArrayList<Player> players;
	private Space[] spaces;

	final int CHANCE_CARD_COUNT = 15;
	final int COMMUNITY_CHEST_CARD_COUNT = 16;

	//Default Constructor
	public Board(){
		//Seeds the random number generator
		random = new Random(System.currentTimeMillis());

		final int MAX_SPACES = 40;

		players = new ArrayList<Player>();
		spaces = new Space[MAX_SPACES];

		initializeSpaces();
	}

	// ** Methods directly related to the players and spaces variables **

	//Getters for the players and spaces variables
	public ArrayList<Player> getPlayers(){
		return players;
	}
	public Space[] getSpaces(){
		return spaces;
	}
	//Adds a Player object to the players ArrayList
	public void addPlayer(String name,int token){
			players.add(new Player(name,token));
	}

	//Checks whether or not the game has ended
	public boolean gameOver(){
		boolean gameOver = true;

		int inGame = 0;		//Number of players who are not bankrupt
		for(int i = 0;i < players.size();i++){
			if(players.get(i).getBankrupt() == false){
				inGame++;
			}
		}
		//There is more than one player not bankrupt
		if(inGame > 1){
			gameOver = false;
		}

		return gameOver;
	}
	//Creates a DialogWindow announcing the winner
	public void winner(){
		for(int i = 0;i < players.size();i++){
			if(players.get(i).getBankrupt() == false){
				new DialogWindow("Winner",players.get(i).getName() + " wins!!!");
			}
		}
	}

	// ** All methods related to the Player rolling **

	//Rolls the dice for a given players index
	public int rollDice(int playerNumber){
		//Maximum and Minimum roll constants
		final int MAX_ROLL = 6;
		final int MIN_ROLL = 1;

		//Assigns two dice to a value between one and six
		int dice1 = random.nextInt(MAX_ROLL - MIN_ROLL + 1) + MIN_ROLL;
		int dice2 = random.nextInt(MAX_ROLL - MIN_ROLL + 1) + MIN_ROLL;

		//Tracks the total value of the players rolls
		int totalRolled = 0;

		if(players.get(playerNumber).getInJail()){
			//If a player rolls doubles they are free
			if(dice1 == dice2){
				players.get(playerNumber).setInJail(false);
				new DialogWindow("Jail",players.get(playerNumber).getName() + " rolled doubles and was released.",Color.LIGHT_GRAY);
				totalRolled = dice1 + dice2;
			}
			//If the player does not roll doubles jailTurn++
			else if(dice1 != dice2 && players.get(playerNumber).getJailTurns() < ((Jail)spaces[10]).getJailTime()){
				players.get(playerNumber).incrementJailTurns();
				if(players.get(playerNumber).getJailTurns() == 1){
					new DialogWindow("Jail",players.get(playerNumber).getName() + " did not roll doubles. They have spent " + players.get(playerNumber).getJailTurns() + " turn in jail.",Color.LIGHT_GRAY);
				}
				else{
					new DialogWindow("Jail",players.get(playerNumber).getName() + " did not roll doubles. They have spent " + players.get(playerNumber).getJailTurns() + " turns in jail.",Color.LIGHT_GRAY);
				}
			}
			//If the player has spent that max turns in jail pay the fine and be released
			else{
				players.get(playerNumber).subtractCash(((Jail)spaces[10]).getJailFine());
				new DialogWindow("Jail",players.get(playerNumber).getName() + " has been fined $" + ((Jail)spaces[10]).getJailFine() + " and released.",Color.LIGHT_GRAY);
				players.get(playerNumber).setInJail(false);
				totalRolled = dice1 + dice2;
			}
		}
		else{
			//The players total roll
			totalRolled = dice1 + dice2;

			if(dice1 != dice2){
				players.get(playerNumber).setRollAgain(false);
				players.get(playerNumber).setDoublesCount(0);
			}
			else if(dice1 == dice2 && !players.get(playerNumber).getInJail()){
				players.get(playerNumber).setRollAgain(true);
				players.get(playerNumber).incrementDoublesCount();
				new DialogWindow("Doubles",players.get(playerNumber).getName() + " rolled doubles!");
			}

			//Moves the player to jail if they rolled doubles three times
			if(players.get(playerNumber).getDoublesCount() == 3){
				players.get(playerNumber).setRollAgain(false);
				players.get(playerNumber).setDoublesCount(0);

				//Arrest the Player and announces it
				new DialogWindow("Arrested",players.get(playerNumber).getName() + " has been arrested for rolling doubles three times.",Color.LIGHT_GRAY);
				players.get(playerNumber).setPosition(10);
				players.get(playerNumber).setInJail(true);
			}
		}

		//Increments the players position if they are not in jail
		if(players.get(playerNumber).getInJail() == false){

			//Adds 200 if they pass go on their roll
			if(players.get(playerNumber).getPosition() + totalRolled >= 40){
				players.get(playerNumber).addCash(200);
				System.out.println("Passed Go");
			}

			players.get(playerNumber).incrementPosition(totalRolled);
			players.get(playerNumber).setLastRoll(totalRolled);

			interact(players.get(playerNumber),spaces[players.get(playerNumber).getPosition()]);
		}
		return totalRolled;
	}
	//Rolls the dice for a given Player object
	public int rollDice(Player player){
		//Maximum and Minimum roll constants
				final int MAX_ROLL = 6;
				final int MIN_ROLL = 1;

				//Assigns two dice to a value between one and six
				int dice1 = random.nextInt(MAX_ROLL - MIN_ROLL + 1) + MIN_ROLL;
				int dice2 = random.nextInt(MAX_ROLL - MIN_ROLL + 1) + MIN_ROLL;

				//Tracks the total value of the players rolls
				int totalRolled = 0;

				if(player.getInJail()){
					//If a player rolls doubles they are free
					if(dice1 == dice2){
						player.setInJail(false);
						new DialogWindow("Jail",player.getName() + " rolled doubles and was released.");
						totalRolled = dice1 + dice2;
					}
					//If the player does not roll doubles jailTurn++
					else if(dice1 != dice2 && player.getJailTurns() < ((Jail)spaces[10]).getJailTime()){
						player.incrementJailTurns();
						if(player.getJailTurns() == 1){
							new DialogWindow("Jail",player.getName() + " did not roll doubles. They have spent " + player.getJailTurns() + " turn in jail.",Color.LIGHT_GRAY);
						}
						else{
							new DialogWindow("Jail",player.getName() + " did not roll doubles. They have spent " + player.getJailTurns() + " turns in jail.",Color.LIGHT_GRAY);
						}
					}
					//If the player has spent that max turns in jail pay the fine and be released
					else{
						player.subtractCash(((Jail)spaces[10]).getJailFine());
						new DialogWindow("Jail",player.getName() + " has been fined $" + ((Jail)spaces[10]).getJailFine() + " and released.");
						player.setInJail(false);
						totalRolled = dice1 + dice2;
					}
				}
				else{
					//The players total roll
					totalRolled = dice1 + dice2;

					if(dice1 != dice2){
						player.setRollAgain(false);
						player.setDoublesCount(0);
					}
					else if(dice1 == dice2 && !player.getInJail()){
						player.setRollAgain(true);
						player.incrementDoublesCount();
						new DialogWindow("Doubles",player.getName() + " rolled doubles!");
					}

					//Moves the player to jail if they rolled doubles three times
					if(player.getDoublesCount() == 3){
						player.setRollAgain(false);
						player.setDoublesCount(0);

						//Arrest the Player and announces it
						new DialogWindow("Arrested",player.getName() + " has been arrested for rolling doubles three times.");
						player.setPosition(10);
						player.setInJail(true);
					}
				}

				//Increments the players position if they are not in jail
				if(player.getInJail() == false){

					//Adds 200 if they pass go on their roll
					if(player.getPosition() + totalRolled >= 40){
						player.addCash(200);
						System.out.println("Passed Go");
					}

					player.incrementPosition(totalRolled);
					player.setLastRoll(totalRolled);

					interact(player,spaces[player.getPosition()]);
				}
				return totalRolled;
	}

	// ** All methods related the the Player buying and selling houses **

	//Checks if the Player can upgrade properties, used for displaying Upgrade button
	public boolean canUpgrade(Player player){
		boolean canUpgrade = false;

		//Brown Lots
		if(((Lot)spaces[1]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[3]).getOwner() == player){
				canUpgrade = true;
			}
		}
		//Light blue Lots
		else if(((Lot)spaces[6]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[8]).getOwner() == player && ((Lot)spaces[9]).getOwner() == player){
				canUpgrade = true;
			}
		}
		//Pink Lots
		else if(((Lot)spaces[11]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[13]).getOwner() == player && ((Lot)spaces[14]).getOwner() == player){
				canUpgrade = true;
			}
		}
		//Orange Lots
		else if(((Lot)spaces[16]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[18]).getOwner() == player && ((Lot)spaces[19]).getOwner() == player){
				canUpgrade = true;
			}
		}
		//Red Lots
		else if(((Lot)spaces[21]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[23]).getOwner() == player && ((Lot)spaces[24]).getOwner() == player){
				canUpgrade = true;
			}
		}
		//Yellow Lots
		else if(((Lot)spaces[26]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[27]).getOwner() == player && ((Lot)spaces[29]).getOwner() == player){
				canUpgrade = true;
			}
		}
		//Green Lots
		else if(((Lot)spaces[31]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[32]).getOwner() == player && ((Lot)spaces[34]).getOwner() == player){
				canUpgrade = true;
			}
		}
		//Dark blue Lots
		else if(((Lot)spaces[37]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[39]).getOwner() == player){
				canUpgrade = true;
			}
		}

		return canUpgrade;
	}
	//Returns what Lots the Player can upgrade
	public ArrayList<Lot> canUpgradeList(Player player){
		ArrayList<Lot> upgradableLots = new ArrayList<Lot>(0);

		//Brown Lots
		if(((Lot)spaces[1]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[3]).getOwner() == player){
				upgradableLots.add((Lot)spaces[1]);
				upgradableLots.add((Lot)spaces[3]);
			}
		}
		//Light blue Lots
		else if(((Lot)spaces[6]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[8]).getOwner() == player && ((Lot)spaces[9]).getOwner() == player){
				upgradableLots.add((Lot)spaces[6]);
				upgradableLots.add((Lot)spaces[8]);
				upgradableLots.add((Lot)spaces[9]);
			}
		}
		//Pink Lots
		else if(((Lot)spaces[11]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[13]).getOwner() == player && ((Lot)spaces[14]).getOwner() == player){
				upgradableLots.add((Lot)spaces[11]);
				upgradableLots.add((Lot)spaces[13]);
				upgradableLots.add((Lot)spaces[14]);
			}
		}
		//Orange Lots
		else if(((Lot)spaces[16]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[18]).getOwner() == player && ((Lot)spaces[19]).getOwner() == player){
				upgradableLots.add((Lot)spaces[16]);
				upgradableLots.add((Lot)spaces[18]);
				upgradableLots.add((Lot)spaces[19]);
			}
		}
		//Red Lots
		else if(((Lot)spaces[21]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[23]).getOwner() == player && ((Lot)spaces[24]).getOwner() == player){
				upgradableLots.add((Lot)spaces[21]);
				upgradableLots.add((Lot)spaces[23]);
				upgradableLots.add((Lot)spaces[24]);
			}
		}
		//Yellow Lots
		else if(((Lot)spaces[26]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[27]).getOwner() == player && ((Lot)spaces[29]).getOwner() == player){
				upgradableLots.add((Lot)spaces[26]);
				upgradableLots.add((Lot)spaces[27]);
				upgradableLots.add((Lot)spaces[29]);
			}
		}
		//Green Lots
		else if(((Lot)spaces[31]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[32]).getOwner() == player && ((Lot)spaces[34]).getOwner() == player){
				upgradableLots.add((Lot)spaces[31]);
				upgradableLots.add((Lot)spaces[32]);
				upgradableLots.add((Lot)spaces[34]);
			}
		}
		//Dark blue Lots
		else if(((Lot)spaces[37]).getOwner() == player){
			//Checks the owner of the other properties of this color
			if(((Lot)spaces[39]).getOwner() == player){
				upgradableLots.add((Lot)spaces[37]);
				upgradableLots.add((Lot)spaces[39]);
			}
		}

		return upgradableLots;

	}
	//Checks if the given Player can buy a house on the given Lot
	public boolean canBuyHouse(Player player,Lot lot){
		boolean canBuyHouse = true;

		//Checks if the player can afford the house
		if(player.getCash() < lot.getHouseCost()){
			canBuyHouse = false;
			new DialogWindow("Cannot Upgrade",player.getName() + " cannot afford to upgrade that property.");
		}
		//Checks if the Lot is already fully upgraded
		else if(lot.getHotel()){
			canBuyHouse = false;
			new DialogWindow("Cannot Upgrade","That property is already fully upgraded.");
		}

		return canBuyHouse;
	}
	//Buys a house for the given Player on the given Lot
	public void buyHouse(Player player,Lot lot){
		//Stores how much building a house on the given Lot cost
		int housingCost = lot.getHouseCost();

		//Fines the Player for the house
		player.subtractCash(housingCost);

		//Builds the house
		player.incrementHouseCount();
		lot.buildHouse();

		new DialogWindow("Upgrade",player.getName() + " has upgraded " + lot.getName());
	}
	//Checks if the given Player can sell a house on the given Lot
	public boolean canSellHouse(Player player,Lot lot){
		boolean canSellHouse = false;

		//Checks if there is a house on the Lot
		if(lot.getHouses() > 0){
			canSellHouse = true;

		}else{
			new DialogWindow("Cannot Downgrade","That property has not been upgraded.");
		}
		return canSellHouse;
	}
	//Sells a house for the given Player on the the given Lot
	public void sellHouse(Player player,Lot lot){
		//Stores how much building a house on the given Lot cost
		int housingCost = lot.getHouseCost() / 2;

		//Refunds the Player the cost of the house
		player.addCash(housingCost);

		//Removes the house
		player.decrementHouseCount();
		lot.sellHouse();

		new DialogWindow("Downgrade",player.getName() + " has downgraded " + lot.getName());
	}

	// ** Methods related to the Player getting out of jail **

	//Checks if the given Player can pay to get out of jail
	public boolean canPayJail(Player player){
		boolean canPayJail = false;

		//Player is in jail and can afford the fine or has a Get Out of Jail Free card
		if(player.getInJail() && (player.getCash() >= ((Jail)spaces[10]).getJailFine() || player.getJailFreeCards() > 0)){
			//Checks if the player has spent less than three turns in jail
			if(player.getJailTurns() < 3){
				canPayJail = true;
			}
		}

		return canPayJail;
	}
	//Pays the jailFine and releases the Player
	public void payJail(Player player){
		//Uses a Get Out of Jail Free card if the Player has one
		if(player.getJailFreeCards() > 0){
			player.decrementJailFreeCards();
			player.setInJail(false);
		}
		//Player pays the jailFine
		else{
			player.subtractCash(((Jail)spaces[10]).getJailFine());
			player.setInJail(false);
		}

		//Rolls the players dice
		rollDice(player);
	}

	// ** Methods related to the Player mortgaging Properties **

	//Checks if the given Player can mortgage properties
	public boolean canMortgage(Player player){
		boolean canMortgage = false;

		for(int i = 0;i < spaces.length;i++){
			//Checks if the Space is a lot
			if(spaces[i] instanceof Lot){
				//Checks if the Player owns the Space
				if(((Lot)spaces[i]).getOwner() == player){
					//Checks if the Space has been upgraded
					if(((Lot)spaces[i]).getHouses() == 0){
						canMortgage = true;
					}
				}
			}
			//If the Space is a utility or a railroad it can be mortgaged
			else if(spaces[i] instanceof Utility || spaces[i] instanceof Railroad){
				canMortgage = true;
			}
		}

		return canMortgage;
	}
	//Returns what Property the given Player can mortgage
	public ArrayList<Property> canMortgageList(Player player){
		ArrayList<Property> canMortgageList = new ArrayList<Property>(0);

		for(int i = 0;i < spaces.length;i++){
			//Checks if the Space is a lot
			if(spaces[i] instanceof Lot){
				//Checks if the Player owns the Space
				if(((Lot)spaces[i]).getOwner() == player){
					//Checks if the Space has been upgraded
					if(((Lot)spaces[i]).getHouses() == 0){
						canMortgageList.add((Property)spaces[i]);
					}
				}
			}
			//If the Space is a utility or a railroad it can be mortgaged
			else if(spaces[i] instanceof Utility || spaces[i] instanceof Railroad){
				if(((Property)spaces[i]).getOwner() == player){
					canMortgageList.add((Property)spaces[i]);
				}
			}
		}

		return canMortgageList;
	}
	//Checks if the given Player can mortgage the given Property
	public boolean canMortgage(Player player,Property property){
		boolean canMortgage = true;

		//Checks if the player is mortgaged
		if(property.getMortgaged()){
			canMortgage = false;
			new DialogWindow("Cannot Mortgage",property.getName() + " is already mortgaged.");
		}

		return canMortgage;
	}
	//Mortgages the given Property
	public void mortgageProperty(Property property){
		//The owner of the Property
		Player owner = property.getOwner();
		//How much mortgaging the Property is worth
		int mortgageValue = property.getMortgage();

		//Pays the owner the mortgage
		owner.addCash(mortgageValue);

		//Sets the property as mortgaged
		property.setMortgaged(true);

		new DialogWindow("Mortgaged",property.getOwner().getName() + " has mortgaged " + property.getName());
	}
	//Checks if the given Player can unmortgage
	public boolean canUnmortgage(Player player,Property property){
		boolean canUnmortgage = false;

		//Checks if the Property is mortgaged and the Player can afford to unmortgage it
		if(property.getMortgaged() && player.getCash() >= property.getMortgage()){
			canUnmortgage = true;
		}
		else{
			new DialogWindow("Cannot Unmortgage",player.getName() + " cannot unmortgage that property.");
		}

		return canUnmortgage;
	}
	//Unmortgages the given Property
	public void unmortgageProperty(Property property){
		//The owner of the Property
		Player owner = property.getOwner();
		//How much mortgaging the Property is worth
		int mortgageValue = property.getMortgage();

		//Pays the owner the mortgage
		owner.subtractCash(mortgageValue);

		//Sets the property as mortgaged
		property.setMortgaged(false);

		new DialogWindow("Unmortgaged",property.getOwner().getName() + " has unmortgaged " + property.getName());
	}

	// ** Methods related to buying and selling Property **

	//Checks if the given Player can by the given Space
	public boolean canBuyProperty(Player player,Space space){
		boolean canBuy = false;

		//Checks if the Space is a Lot, Utility, or Railroad
		if(space instanceof Property){
			//Checks if the Property has no owner
			if(((Property)space).getOwner() == null){
				//Checks that the Player can afford the Property
				if(player.getCash() >= ((Property)space).getPrice()){
					canBuy = true;
				}
			}
		}

		return canBuy;
	}
	//The given Player buys the given Property
	public void buyProperty(Player player,Property property){
		//The price of the Property
		int price = property.getPrice();

		//Assigns the Player as the owner of the Property
		property.setOwner(player);

		//Increments railroadsOwned and uiltilitiesOwned
		if(property instanceof Railroad){
			player.incrementRailroadsOwned();
			System.out.println("railroads owned " + player.getRailroadsOwned());
		}
		else if(property instanceof Utility){
			player.incrementUtilitiesOwned();
		}

		//Charges the Player for the Property
		player.subtractCash(price);
	}
	//Checks if the given Player can sell Property
	public boolean canSellProperty(Player player){
		boolean canSellProperty = false;

		//Index of all Property
		int[] propertyIndex = getPropertyIndex();

		//Loops through all the Property in spaces
		for(int i = 0;i < propertyIndex.length;i++){
			int index = propertyIndex[i];

			//The Player owns the Property
			if(((Property)spaces[index]).getOwner() == player){
				//The Property is not mortgaged
				if(((Property)spaces[index]).getMortgaged() == false){
					canSellProperty = true;
					break;
				}
			}
		}

		return canSellProperty;
	}
	//Returns what Property the given Player can sell
	public ArrayList<Property> canSellList(Player player){
		ArrayList<Property> propertyList = new ArrayList<Property>(0);

		//Index of all Property
		int[] propertyIndex = getPropertyIndex();

		//Loops through all the Property in spaces
		for(int i = 0;i < propertyIndex.length;i++){
			int index = propertyIndex[i];

			//The Player owns the Property
			if(((Property)spaces[index]).getOwner() == player){
				//The Property is not mortgaged
				if(((Property)spaces[index]).getMortgaged() == false){
					propertyList.add(((Property)spaces[index]));
				}
			}
		}

		return propertyList;
	}
	//The given Player sells the given Property
	public void sellProperty(Player player,Property property){	// ************ INCOMPLETE ******************

	}

	// ** Methods related to paying rent **

	//Calculates the rent a Player owes for landing on a Property
	public int calcRent(Player player,Space space){
    	Player owner = ((Property)space).getOwner();

    	final int ONE_UTILITY = 4;
    	final int TWO_UTILITY = 10;

    	int rent = 0;
    	if(space instanceof Lot){
    		rent = ((Lot)space).getRent();
    	}
    	else if(space instanceof Utility){
    		int lastRoll = player.getLastRoll();
    		int utilitiesOwned = owner.getUtilitiesOwned();
    		if(utilitiesOwned == 1){
    			rent = lastRoll * ONE_UTILITY;
    		}
    		else{
    			rent = lastRoll * TWO_UTILITY;
    		}

    	}
    	else if(space instanceof Railroad){
    		int railroadsOwned = owner.getRailroadsOwned();
    		System.out.println(owner.getName() + " | railroadsOwned: " + railroadsOwned);
    		rent = ((Railroad)space).getRent(railroadsOwned);
    	}

    	return rent;
    }

	// ** Methods for Charging the a Player

	public void charge(Player player,int amount){
		//Player does not have enough cash to pay
		if(!player.canPay(amount)){
			new DialogWindow("In Debt",player.getName() + " owes $" + player.getDebt() + " if they do not pay before ending their turn they lose.",Color.RED);
			player.setInDebt(true);
			player.setDebt(amount);
		}
		//Player has enough cash
		else{
			player.setInDebt(false);
			player.setDebt(0);
			player.subtractCash(amount);
		}
	}
	public void charge(Player player,int amount,Player recipient){
		//Player does not have enough cash to pay
				if(!player.canPay(amount)){
					new DialogWindow("In Debt",player.getName() + " owes " + recipient.getName() + " $" + player.getDebt() + ", if they do not pay before ending their turn they lose.",Color.RED);
					player.setInDebt(true);
					player.setDebt(amount);

				}
				//Player has enough cash
				else{
					player.setInDebt(false);
					player.setDebt(0);
					player.subtractCash(amount);
					recipient.addCash(amount);

				}
	}
	//Pays the Player's debt
	public void payDebt(Player player){
		if(player.getOwesPlayer()){
			charge(player,player.getDebt(),player.getPlayerOwed());
		}
		else{
			charge(player,player.getDebt());
		}
	}
	//Gives one Player everything the other Player owns
	public void giveAll(Player player,Player recipient){
		//Gives the recipient all of the Player's cash
		recipient.addCash(player.getCash());
		player.setCash(0);

		//Give the recipient all of the Player's Property
		for(int i = 0;i < spaces.length;i++){
			//If the Player is the owner of the Property
			if(spaces[i] instanceof Property && ((Property)spaces[i]).getOwner() == player){
				((Property)spaces[i]).setOwner(recipient);
			}
		}
	}

	// ** Methods to return indexes in the spaces array **

	//Returns the indexes of all Property objects
	public int[] getPropertyIndex(){
		String str = "";

		for(int i = 0;i < spaces.length;i++){
			if(spaces[i] instanceof Property){
				str += i + "!";
			}
		}

		String[] tempArray = str.split("!");
		int[] propertyIndex = new int[tempArray.length];

		for(int i = 0;i < tempArray.length;i++){
			propertyIndex[i] = Integer.parseInt(tempArray[i]);
		}

		return propertyIndex;
	}

	//Initializes all the Space objects and stores them in the spaces array
	private void initializeSpaces(){
		//Creates the GO
		spaces[0] = new Go();
		//Creates the Mediterranean Avenue
		spaces[1] = new Lot("Mediterranean Avenue",60,new int[]{2,10,30,90,160,250},30,50);
		//Creates a Community Chest
		spaces[2] = new CommunityChest();
		//Creates the Baltic Avenue
		spaces[3] = new Lot("Baltic Avenue",60,new int[]{4,20,60,180,320,450},30,50);
		//Creates the Income Tax
		spaces[4] = new IncomeTax();
		//Creates the Reading Railroad
		spaces[5] = new Railroad("Reading Railroad");
		//Creates the Oriental Avenue
		spaces[6] = new Lot("Oriental Avenue",100,new int[]{6,30,90,270,400,550},50,50);
		//Creates a Chance
		spaces[7] = new Chance();
		//Creates the Vermont Avenue
		spaces[8] = new Lot("Vermont Avenue",100,new int[]{6,30,90,270,400,550},50,50);
		//Creates the Connecticut Avenue
		spaces[9] = new Lot("Connecticut Avenue",120,new int[]{8,40,100,300,450,600},60,50);


		//Creates the Jail
		spaces[10] = new Jail();
		//Creates the St. Charles Place
		spaces[11] = new Lot("St. Charles Place",140,new int[]{10,50,150,450,625,750},70,100);
		//Creates the Electric Company
		spaces[12] = new Utility("Electric Company");
		//Creates the States Avenue
		spaces[13] = new Lot("States Avenue",140,new int[]{10,50,150,450,625,750},70,100);
		//Creates the Virginia Avenue
		spaces[14] = new Lot("Virginia Avenue",160,new int[]{12,60,180,500,700,900},80,100);
		//Creates the Pennsylvania Railroad
		spaces[15] = new Railroad("Pennsylvania Railroad");
		//Creates the St. James Place
		spaces[16] = new Lot("St. James Place",180,new int[]{14,70,200,550,750,950},90,100);
		//Creates a Community Chest
		spaces[17] = new CommunityChest();
		//Creates the Tennessee Avenue
		spaces[18] = new Lot("Tennessee Avenue",180,new int[]{14,70,200,550,750,950},90,100);
		//Creates the New York Avenue
		spaces[19] = new Lot("New York Avenue",200,new int[]{16,80,220,600,800,1000},100,100);


		//Creates the Free Parking
		spaces[20] = new FreeParking();
		//Creates the Kentucky Avenue
		spaces[21] = new Lot("Kentucky Avenue",220,new int[]{18,90,250,700,875,1050},110,150);
		//Creates a Chance
		spaces[22] = new Chance();
		//Creates the Indiana Avenue
		spaces[23] = new Lot("Indiana Avenue",220,new int[]{18,90,250,700,875,1050},110,150);
		//Creates the Illinois Avenue
		spaces[24] = new Lot("Illinois Avenue",240,new int[]{20,100,300,750,925,1100},120,150);
		//Creates the B. & O. Railroad
		spaces[25] = new Railroad("B. & O. Railroad");
		//Creates the Atlantic Avenue
		spaces[26] = new Lot("Atlantic Avenue",260,new int[]{22,110,330,800,975,1150},130,150);
		//Creates the Ventnor Avenue
		spaces[27] = new Lot("Ventnor Avenue",260,new int[]{22,110,330,800,975,1150},130,150);
		//Creates the Water Works
		spaces[28] = new Utility("Water Works");
		//Creates the Marvin Gardens
		spaces[29] = new Lot("Marvin Gardens",280,new int[]{24,120,360,850,1025,1200},140,150);


		//Creates the Go To Jail
		spaces[30] = new GoToJail();
		//Creates the Pacific Avenue
		spaces[31] = new Lot("Pacific Avenue",300,new int[]{26,130,390,900,1100,1275},150,200);
		//Creates the North Carolina Avenue
		spaces[32] = new Lot("North Carolina Avenue",300,new int[]{26,130,390,900,1100,1275},150,200);
		//Creates a Community Chest
		spaces[33] = new CommunityChest();
		//Creates the Pennsylvania Avenue
		spaces[34] = new Lot("Pennsylvania Avenue",320,new int[]{28,150,450,1000,1200,1400},160,200);
		//Creates the Short Line
		spaces[35] = new Railroad("Short Line");
		//Creates a Chance
		spaces[36] = new Chance();
		//Creates the Park Place
		spaces[37] = new Lot("Park Place",350,new int[]{35,175,500,110,1300,1500},175,200);
		//Creates the Luxury Tax
		spaces[38] = new LuxuryTax();
		//Creates the Boardwalk
		spaces[39] = new Lot("Boardwalk",400,new int[]{50,200,600,1400,1700,2000},200,200);
	}

    public void interact(Player player,Space space){
		String output = new String();
		int rent = 0;

		if(space instanceof Property){
			Property property = (Property)space;
			if(property.getOwner() == player){
				output = player.getName() + " already owns " + property.getName();
			}
			else if(((Property)space).getOwner() == null){
				output = property.getName() + " is not owned";
				new BuyPropertyWindow(player,property);
			}
			else if(property.getMortgaged() == false){
				rent = calcRent(player,property);
				new DialogWindow("Rent Owed",player.getName() + " has landed on " + property.getName() + " and owes " + property.getOwner().getName() + " $" + rent + " in rent");
				charge(player,rent,property.getOwner());
			}
		}
		else if (space instanceof IncomeTax){
            output = player.getName() + " has landed on Income Tax. They must pay $200 to the bank.";
            new DialogWindow("Income Tax",player.getName() + " has landed on Income Tax and must pay $200");
            charge(player,200);
        } else if (space instanceof CommunityChest){
            output = player.getName() + " draws a Community Chest card...";
            drawChest(player);
        } else if (space instanceof Chance){
        	output = player.getName() + " draws a Chance card...";
        	drawChance(player);
        }
        else if (space instanceof GoToJail){
            new DialogWindow("Go To Jail","Go directly to jail. Do not pass Go. Do not collect $200.");
            player.setInJail(true);
            player.setPosition(10);
        } else if (space instanceof LuxuryTax){
            new DialogWindow("Luxury Tax",player.getName() + " must pay $" + ((LuxuryTax)space).getAmount() + " in taxes.");
            charge(player,((LuxuryTax)space).getAmount());
        } else {
            output = "Nothing happens.";
        }
		System.out.println(output);
	}

    ////////
    //interact() HELPER FUNCTIONS
    ////////
    public void drawChance(Player player){
        Random randInt = new Random();
        //Selects the chance card to draw

        switch (randInt.nextInt(CHANCE_CARD_COUNT + 1)){

            case 0:
                //Advance to the nearest railroad
                                        //This space is Short Line RR
                new DialogWindow("Chance","Advance to the nearest Railroad!",Color.ORANGE);		//Used to be double rent
                //waitForClose(card);
                if (player.getPosition() >= 35){
                                    //Reading Railroad
                    player.setPosition(5);
                    //passed Go
                    player.addCash(200);
                    interact(player, spaces[5]);
                    //interact(player, spaces[5]);
                                            //Between Short Line and B&O
                } else if (player.getPosition() >= 25){
                    player.setPosition(35);
                    interact(player, spaces[35]);
                    //interact(player, spaces[35]);
                                                //Between Penn. and B&O
                } else if (player.getPosition() >= 15){
                    player.setPosition(25);
                    interact(player, spaces[25]);
                    //interact(player, spaces[25]);
                                                //Between Reading and Penn.
                } else if (player.getPosition() >= 5){
                    player.setPosition(15);
                    interact(player, spaces[15]);
                    //interact(player, spaces[15]);
                    //Between Reading and Go
                } else {
                    player.setPosition(5);
                    interact(player, spaces[5]);
                   //interact(player, spaces[5]);
                }
                break;
            case 1:
                //Collect 150
                new DialogWindow("Chance","Your building loan matures, collect $150.",Color.ORANGE);
                //waitForClose(card);
                player.addCash(150);
                break;
            case 2:
                //Go back 3 spaces
                //Luckily, no possibility of passing Go.
                new DialogWindow("Chance","Go back 3 spaces.",Color.ORANGE);
                //waitForClose(card);
                player.setPosition(player.getPosition() - 3);
                interact(player, spaces[player.getPosition()]);
                break;
            case 3:
                //pay $15
                new DialogWindow("Chance","Pay poor tax of $15.",Color.ORANGE);
                //waitForClose(card);
                charge(player,15);
                break;
            case 4:
                //Boardwalk
                new DialogWindow("Chance","Advance token to Boardwalk.",Color.ORANGE);
                //waitForClose(card);
                //Can't pass Go
                                //Boardwalk
                player.setPosition(39);
                interact(player, spaces[39]);
                break;
            case 5:
                //collect 50
                new DialogWindow("Chance","Bank pays you dividend of $50.",Color.ORANGE);
                //waitForClose(card);
                player.addCash(50);
                break;
            case 6:
                //go to jail
                new DialogWindow("Chance","Go directly to Jail. Do not pass Go. Do not collect $200.",Color.ORANGE);
                //waitForClose(card);
                player.setInJail(true);
                                //Jail space
                player.setPosition(10);
                break;
            case 7:
                //RRR
                new DialogWindow("Chance","Take a ride on the Reading Railroad. If you pass Go, collect $200.",Color.ORANGE);
                //waitForClose(card);
                //Check for passing go
                if (player.getPosition() >= 5){
                    player.addCash(200);
                }
                player.setPosition(5);
                interact(player, spaces[5]);
                break;
            case 8:
                //To St. Charles
                new DialogWindow("Chance","Advance to St. Charles Place.",Color.ORANGE);
                //waitForClose(card);

                //check for passing Go
                if (player.getPosition() >= 11){
                    player.addCash(200);
                }
                                //St.Charles Place
                player.setPosition(11);
                interact(player, spaces[11]);
                break;
            case 9:
                //move to Nearest Utility.
                new DialogWindow("Chance","Advance Token to nearest Utility.",Color.ORANGE);
                //waitForClose(card);
                //Check for passing Go, and which Utility.
                                        //WaterWorks
                if (player.getPosition() >= 28){
                    player.addCash(200);
                                   //Electric Co.
                    player.setPosition(12);
                } else if (player.getPosition() >= 12){
                    player.setPosition(28);
                } else {
                    player.setPosition(12);
                }
                interact(player, spaces[player.getPosition()]);
                break;
            case 10:
                //pay all players 50
                new DialogWindow("Chance","You are elected chairman of the board. "+
                        "pay each player $50.",Color.YELLOW);
                //waitForClose(card);
                for (int i = 0; i < players.size(); i++){
                    if (players.get(i) != player && ! players.get(i).getBankrupt()){
                    	charge(player,50,players.get(i));
                        ////////////////////////////////////////////////player.charge(50, players.get(i));	////////////////////////////////WAS player[i]
                    }
                }
                break;
            case 11:
                //Get out of Jail free
                new DialogWindow("Chance","Get out of Jail free.",Color.ORANGE);
                //waitForClose(card);
                player.incrementJailFreeCards();
                break;
            case 12:
                //Advance to Illinois ave.
                new DialogWindow("Chance","Advance to Illinois Ave.",Color.ORANGE);
                //waitForClose(card);
                //check for passing go
                            //Illinois ave.
                if (player.getPosition() >= 24){
                    player.addCash(200);
                }
                player.setPosition(24);
                interact(player, spaces[player.getPosition()]);
                break;
            case 13:
                //Advance to Go.
                new DialogWindow("Chance","Advance to Go.",Color.ORANGE);
                //waitForClose(card);
                player.addCash(200);
                break;
            default:
                //property repairs: 25, 100
                new DialogWindow("Chance","Make general repairs on all your property: for each house, " +
                        "pay $25. for each hotel, pay $100",Color.ORANGE);
                //waitForClose(card);
                charge(player,25*player.getHouseCount() + 100*player.getHotelCount());
        }
    }

    public void drawChest(Player player){
        Random randInt = new Random();
        switch (randInt.nextInt(COMMUNITY_CHEST_CARD_COUNT + 1)){
            case 0:
                //Go to Jail
                new DialogWindow("Community Chest","Go directly to Jail. Do not pass go, do not collect $200.",Color.YELLOW);
                //waitForClose(card);
                player.setPosition(10);
                player.setInJail(true);
                break;
            case 1:
                //go to go
                new DialogWindow("Community Chest","Advance to Go.",Color.YELLOW);
                //waitForClose(card);
                player.setPosition(0);
                player.addCash(200);
                break;
            case 2:
                //Pay 150
                new DialogWindow("Community Chest","Pay school tax of $150.",Color.YELLOW);
                //waitForClose(card);
                charge(player,150);
                break;
            case 3:
                //TODO property repairs: 40, 115
                new DialogWindow("Community Chest","You are assessed for street repairs: $40 per house, $115 per hotel.",Color.YELLOW);
                //waitForClose(card);
                charge(player,40*player.getHouseCount() + 115*player.getHotelCount());
                break;
            case 4:
                //Get out of Jail
                new DialogWindow("Community Chest","Get out of jail free!",Color.YELLOW);
                //waitForClose(card);
                player.incrementJailFreeCards();
                break;
            case 5:
                //get 100
                new DialogWindow("Community Chest","You inherit $100.",Color.YELLOW);
                //waitForClose(card);
                player.addCash(100);
                break;
            case 6:
                //Also get 100
                new DialogWindow("Community Chest","Xmas fund matures, collect $100.",Color.YELLOW);
                //waitForClose(card);
                player.addCash(100);
                break;
            case 7:
                //get 45
                new DialogWindow("Community Chest","From sale of stock, recieve $45",Color.YELLOW);
                //waitForClose(card);
                player.addCash(45);
                break;
            case 8:
                //get25
                new DialogWindow("Community Chest","Recieve for services $25.",Color.YELLOW);
                //waitForClose(card);
                player.addCash(25);
                break;
            case 9:
                //get10
                new DialogWindow("Community Chest","You have won second prize in a beauty contest, collect $10.",Color.YELLOW);
                //waitForClose(card);
                player.addCash(10);
                break;
            case 10:
                //get 100
                new DialogWindow("Community Chest","Life insurance matures, collect $100.",Color.YELLOW);
                //waitForClose(card);
                player.addCash(100);
                break;
            case 11:
                //get 20
                new DialogWindow("Community Chest","Income tax refund; collect $20.",Color.YELLOW);
                //waitForClose(card);
                player.addCash(20);
                break;
            case 12:
                //pay 50
                new DialogWindow("Community Chest","Doctor's fee, pay $50.",Color.YELLOW);
                //waitForClose(card);
                charge(player,50);
                break;
            case 13:
                //pay 100
                new DialogWindow("Community Chest","Pay hospital $100",Color.YELLOW);
                //waitForClose(card);
                charge(player,100);
                break;
            case 14:
                //collect 200
                new DialogWindow("Community Chest","Bank error in your favor, collect $200.",Color.YELLOW);
                //waitForClose(card);
                player.addCash(200);
                break;
            case 15:
                //collect 50 each
                new DialogWindow("Community Chest","Grand Opera Opening, collect $50 from each player.",Color.YELLOW);
                //waitForClose(card);
                for (int i = 0; i < players.size(); i++){
                    if (players.get(i) != player && ! players.get(i).getBankrupt()){
                        /////////////////////////////////////////////////////////////////players.get(i).charge(50, player);
                        charge(players.get(i),50,player);
                    }
                }
        }
    }


   /**

    //This function keeps further code from executing until the window is closed
    private void waitForClose(JDialog window){

    	System.out.println("adam is always right");
        while (window.isVisible()){
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        window.dispose();
    }

    **/

    /////////////////////
    //GUI POPUPS
    /////////////////////
    private class BuyPropertyWindow implements ActionListener{
    	JFrame frame;
        JPanel panel;
        JButton buyButton;
        JButton auctionButton;
        JLabel label;

        public BuyPropertyWindow(final Player player, final Property property){
        	frame = new JFrame("Buy Property");
        	frame.setVisible(true);
            label = new JLabel();
            panel = new JPanel();
            label.setText("Would you like to buy " + property.getName() + "?");
            buyButton = new JButton("Buy!");
            auctionButton = new JButton("Auction!");
            buyButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent click){
                    clickBuy(player, property);
                    frame.setVisible(false);
                }
            });
            auctionButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent click){
                    //start auctioning
                    auction(player,property);

                }
            });
            buyButton.setBounds(10,10,40,40);
            auctionButton.setBounds(60, 10, 40, 40);
            panel.add(buyButton);
            panel.add(auctionButton);
            panel.add(label);
            panel.revalidate();
            panel.repaint();
            frame.add(panel);
            frame.pack();
            frame.revalidate();
            frame.repaint();
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setResizable(false);
            frame.setBounds(600, 600, 300, 100);
            frame.setVisible(true);
            frame.revalidate();
            frame.repaint();
        }

        public void actionPerformed(ActionEvent click){
            frame.setVisible(false);
        }
        private void clickBuy(Player player, Property property){
            System.out.println("You bought it!");
            buyProperty(player, property);	//****************************************WAS CHARGE
        }
        private void auction(Player firstBidder, Property property){
            System.out.println("up for auction");
            AuctionWindow window = new AuctionWindow(firstBidder, property);
            buyButton.setEnabled(false);
            auctionButton.setEnabled(false);
            //waitForClose((JDialog)window);
            frame.setVisible(false);
        }
    }

    //The menu for auctioning properties!
    private class AuctionWindow extends JDialog implements ActionListener{
        JLabel label;
        JPanel panel;
        JButton fold;
        JButton bid20;
        JButton bid50;
        JButton bid100;
        JButton bid200;
        JButton bid300;
        JButton bid500;
        int currentBid;
        Player currentBidder;
        int playersIn;
        Property prize;
        public void actionPerformed(ActionEvent click){
            //do nothing yet.
        }
        public AuctionWindow(Player firstBidder, Property property){
            panel = new JPanel();
            label = new JLabel();
            fold = new JButton("fold");
            bid20 = new JButton("$20");
            bid50 = new JButton("$50");
            bid100 = new JButton("$100");
            bid200 = new JButton("$200");
            bid300 = new JButton("$300");
            bid500 = new JButton("$500");
            currentBid = 0;
            currentBidder = firstBidder;
            playersIn = 0;
            prize = property;
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            for (int i = 0; i < players.size(); i++){
                players.get(i).setFolded(false);
                if (!players.get(i).getBankrupt()){
                    playersIn ++;
                }
            }

            fold.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent click){
                    currentBidder.setFolded(true);
                    playersIn --;
                    step(0);
                }
            });

            bid20.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent click){
                    step(20);
                }
            });

            bid50.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent click){
                    step(50);
                }
            });

            bid100.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent click){
                    step(100);
                }
            });

            bid200.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent click){
                    step(200);
                }
            });

            bid300.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent click){
                    step(300);
                }
            });

            bid500.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent click){
                    step(500);
                }
            });


            label.setText(currentBidder.getName() + ", raise or fold. /nCurrent Bid: $" + currentBid + "/nBidding for: " + property.getName());

            panel.add(label);
            panel.add(fold);
            panel.add(bid20);
            panel.add(bid50);
            panel.add(bid100);
            panel.add(bid200);
            panel.add(bid300);
            panel.add(bid500);
            add(panel);
            setBounds(600, 600, 400, 400);
            pack();
            setVisible(true);


        }

        private void step(int incBid){

            //Steps to the next player in the auction process
            //and increments the bid

            //Finds the next player in players[] still in the auction
            if (playersIn == 1) {
                Player winner = getWinner();
                new DialogWindow("Auction Complete",winner.getName() + " has won the auction! They must now pay $" +
                        currentBid + " for " + prize.getName() + ".");
                //waitForClose(card);
                charge(winner,currentBid);
                if (!(winner.getBankrupt())){
                    prize.setOwner(winner);
                }
                this.setVisible(false);
                return;
            }

            Player nextBidder = null;
            for (int i = 0; i < players.size(); i ++){
                if (players.get(i) == currentBidder){
                    for (int j = (i + 1) % players.size();j  != i; j = (j+1)%players.size()){
                        if (!(players.get(j).getFolded() || players.get(j).getBankrupt())){
                            nextBidder = players.get(j);
                        }
                    }
                    //this statement should NEVER be true.
                    if (nextBidder == null){
                        System.out.println("ERROR: GHOST BIDDERS!");
                        break;
                    }
                    //no need to continue this loop past currentBidder
                    break;
                }

            }
            //If there's only one bidder remaining, sell the property.

            //no need for an else statement here.
            currentBidder = nextBidder;
            currentBid += incBid;
            label.setText(currentBidder.getName() + ", raise or fold. /nCurrent Bid: $" +
                    currentBid + "/nBidding for: " + prize.getName());

        }

        private Player getWinner(){
            //returns the first player who has not folded or gone bankrupt.
            //Should not be called until 1 player remaining
            for (int i = 0; i < players.size(); i++){
                if (!(players.get(i).getFolded() || players.get(i).getBankrupt())){
                    return players.get(i);
                }
            }
            return null;
        }

    }

    public void initiateTrade(Player player){
    	new TradeWithWindow(player);
    }

//////////////////////////////////////////////
///////////TRADING WINDOWS!!!!!!!!

//////////////////////////////////////////////
    public class TradeWithWindow extends JDialog{
    	JPanel panel;
    	JLabel label;
    	JButton[] buttons;
    	//Actually a "done" button, too late to change the name.
    	JButton nevermind;

    	//These are needed for a constant

    	//creates a window with buttons
    	//with the names of other players on them
    	//to select trading partner
    	public TradeWithWindow (final Player trader){
    		panel = new JPanel();
    		label = new JLabel("Select who you would " +
    				"like to trade with.");
    		//max number of players
    		buttons = new JButton[4];
    		nevermind = new JButton("done");
    		//Hopefully bigger than the
    		setBounds(300, 300, 400, 400);
    		panel.add(label);


    		////Couldn't put these in a for loop because something about final variables: redundancy ahead
    		if (players.size() > 3){
    			if (players.get(3) != trader && !players.get(3).getBankrupt()){
    				buttons[3] = new JButton(players.get(3).getName());
    				buttons[3].addActionListener(new ActionListener(){
    					public void actionPerformed(ActionEvent click){
    						TradeWindow window = new TradeWindow(trader, players.get(3));
    						//waitForClose(window);
    					}
    				});
    				panel.add(buttons[3]);
    			}
    		}

    		if (players.size() > 2){
    			if (players.get(2) != trader && !players.get(2).getBankrupt()){
    				buttons[2] = new JButton(players.get(2).getName());
    				buttons[2].addActionListener(new ActionListener(){
    					public void actionPerformed(ActionEvent click){
    						TradeWindow window = new TradeWindow(trader, players.get(2));
    						//waitForClose(window);
    					}
    				});
    				panel.add(buttons[2]);
    			}
    		}

    		if (players.size() > 1){
    			if (players.get(1) != trader && !players.get(1).getBankrupt()){
    				buttons[1] = new JButton(players.get(1).getName());
    				buttons[1].addActionListener(new ActionListener(){
    					public void actionPerformed(ActionEvent click){
    						TradeWindow window = new TradeWindow(trader, players.get(1));
    						//waitForClose(window);
    					}
    				});
    				panel.add(buttons[1]);
    			}
    		}

    		if (players.size() > 0){
    			if (players.get(0) != trader && !players.get(0).getBankrupt()){
    				buttons[0] = new JButton(players.get(0).getName());
    				buttons[0].addActionListener(new ActionListener(){
    					public void actionPerformed(ActionEvent click){
    						TradeWindow window = new TradeWindow(trader, players.get(0));
    						//waitForClose(window);
    					}
    				});
    				panel.add(buttons[0]);
    			}
    		}

    		nevermind.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent click){
    				dispose();
    			}
    		});

    		panel.add(nevermind);
    		add(panel);
    		pack();
    		setVisible(true);
    	}
    }




    private class TradeWindow extends JDialog{
    	JPanel panel;
    	JPanel offerPanel;
    	JLabel offerLabel;


    	JSlider moneyOffered;
    	JLabel moneyOfferedLabel;
    	JSlider outOfJailOffered;
    	JCheckBox[] propertyOffered;


    	JPanel requestPanel;
    	JLabel requestLabel;

    	JSlider moneyRequested;
    	JLabel moneyRequestedLabel;
    	JSlider outOfJailRequested;
    	JCheckBox[] propertyRequested;


    	JPanel confirmationPanel;

    	JButton sendRequest;
    	JButton accept;
    	JButton decline;


    	Property[] propertyList;

    	final int PROPERTY_COUNT = 28;

    	TradeWindow(final Player offerer, final Player decider){
    		this.setTitle(offerer.getName() + ": MAKE YOUR OFFER!");
    		setBounds(300, 300, 600, 600);
    		panel = new JPanel();
    		//Window contains 3 panels, the WEST panel is the offerer's assets:
    		//2 sliders which will allow them to indicate how much money
    		//and how many out of jail cards they would like to offer
    		//and a chackbox for every property they own.
    		//an EAST panel, which contains all the same, except those of
    		//the player they're trading with, and a third panel with 3 buttons.
    		//one for offerer to send the trade request, and 2 for decider to decide.
    		//accept/decline must be disabled for offerer's turn.
    		//all other input must be disabled for decider.
    		offerPanel = new JPanel();
    		offerLabel = new JLabel("Your assets");
    		offerPanel.add(offerLabel);
    		requestPanel = new JPanel();
    		requestLabel = new JLabel("Their Assets");
    		requestPanel.add(requestLabel);
    		moneyOfferedLabel = new JLabel("Offer cash: $0");
    		if (offerer.getCash() > 0){
    			moneyOffered = new JSlider(0, offerer.getCash(), 0);
    			moneyOffered.addChangeListener(new ChangeListener(){
    				public void stateChanged (ChangeEvent slide){
    					moneyOfferedLabel.setText("Offer cash: $" + moneyOffered.getValue());
    				}
    			});
    		} else {
    			moneyOffered = new JSlider(0, 1, 0);
    			moneyOffered.setEnabled(false);
    		}
    		offerPanel.add(moneyOffered);

    		moneyRequested = new JSlider(0, offerer.getCash(), 0);
    		moneyRequestedLabel = new JLabel("Request Cash: $0");

    		if (decider.getCash() > 0){
    			moneyRequested = new JSlider(0, offerer.getCash(), 0);
    			moneyRequested.addChangeListener(new ChangeListener(){
    				public void stateChanged (ChangeEvent slide){
    					moneyRequestedLabel.setText("Request cash: $" + moneyOffered.getValue());
    				}
    			});
    		} else {
    			moneyRequested = new JSlider(0, 1, 0);
    			moneyRequested.setEnabled(false);
    		}


    		if (offerer.getJailFreeCards() > 0){
    			outOfJailOffered = new JSlider(0, offerer.getJailFreeCards(), 0);
    			outOfJailOffered.setMajorTickSpacing(1);
    			outOfJailOffered.setPaintTicks(true);
    			outOfJailOffered.setPaintLabels(true);
    		} else{
    			outOfJailOffered = new JSlider(0, 1, 0);
    			outOfJailOffered.setEnabled(false);
    		}
    		offerPanel.add(outOfJailOffered);


    		if (decider.getJailFreeCards() > 0){
    			outOfJailRequested = new JSlider(0, offerer.getJailFreeCards(), 0);
    			outOfJailRequested.setMajorTickSpacing(1);
    			outOfJailRequested.setPaintTicks(true);
    			outOfJailRequested.setPaintLabels(true);
    		} else{
    			outOfJailRequested = new JSlider(0, 1, 0);
    			outOfJailRequested.setEnabled(false);
    		}
    		requestPanel.add(outOfJailRequested);


    		//This array should be accessible to the whole class, but no time.

    		propertyList = new Property[PROPERTY_COUNT];
    		int propertiesAdded = 0;
    		for (int i = 0; i < spaces.length; i ++){
    			if (spaces[i].getType().equals("Utility")||
    					spaces[i].getType().equals("Railroad")||
    					spaces[i].getType().equals("Lot")){
    				propertyList[propertiesAdded] = (Property)spaces[i];
    				propertiesAdded ++;
    			}

    		}



    		propertyOffered = new JCheckBox[PROPERTY_COUNT];
    		propertyRequested = new JCheckBox[PROPERTY_COUNT];


    		//This Loop fills out the array and adds the proper checkboxes
    		for (int i = 0; i < PROPERTY_COUNT; i++){


    			propertyOffered[i] = new JCheckBox(propertyList[i].getName());
    			propertyOffered[i].setSelected(false);

    			propertyRequested[i] = new JCheckBox(propertyList[i].getName());
    			propertyRequested[i].setSelected(false);

    			if (propertyList[i].getOwner() == offerer){
    				offerPanel.add(propertyOffered[i]);
    				//Cannot sell improved property
    				if (propertyList[i].getType().equals("Lot")){
    					if (((Lot)propertyList[i]).getHouses() > 0){
    						propertyOffered[i].setEnabled(false);
    					}
    				}
    			} else if (propertyList[i].getOwner() == decider){
    				requestPanel.add(propertyRequested[i]);
    				//Cannot request improved property
    				if (propertyList[i].getType().equals("Lot")){
    					if (((Lot)propertyList[i]).getHouses() > 0){
    						propertyRequested[i].setEnabled(false);
    					}
    				}
    			}

    		}

    		sendRequest = new JButton("Send Offer");
    		sendRequest.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent click){
    				moneyOffered.setEnabled(false);
    				moneyRequested.setEnabled(false);
    				outOfJailOffered.setEnabled(false);
    				outOfJailRequested.setEnabled(false);

    				for (int i = 0; i < PROPERTY_COUNT; i ++){
    					propertyOffered[i].setEnabled(false);
    					propertyRequested[i].setEnabled(false);
    				}
    				sendRequest.setEnabled(false);
    				accept.setEnabled(true);
    				decline.setEnabled(true);
    				setTitle(decider.getName() + ", DO YOU ACCEPT?");
    			}
    		});

    		accept = new JButton("Accept");
    		decline = new JButton("Decline");

    		//do the trade
    		accept.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent click){
    				offerer.addCash(moneyRequested.getValue());
    				charge(decider, moneyRequested.getValue());
    				offerer.setJailFreeCards(offerer.getJailFreeCards() + outOfJailRequested.getValue());
    				decider.setJailFreeCards(decider.getJailFreeCards() - outOfJailRequested.getValue());

    				decider.addCash(moneyOffered.getValue());
    				charge(offerer, moneyOffered.getValue());
    				decider.setJailFreeCards(offerer.getJailFreeCards() + outOfJailOffered.getValue());
    				offerer.setJailFreeCards(decider.getJailFreeCards() - outOfJailOffered.getValue());

    				for (int i = 0; i < PROPERTY_COUNT; i++){
    					if (propertyOffered[i].isSelected()){
    						propertyList[i].setOwner(decider);
    					} else if (propertyRequested[i].isSelected()){
    						propertyList[i].setOwner(offerer);
    					}
    				}
    				dispose();
    			}
    		});

    		decline.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent click){
    				dispose();
    			}
    		});


    		accept.setEnabled(false);
    		decline.setEnabled(false);

    		confirmationPanel.add(sendRequest);
    		confirmationPanel.add(accept);
    		confirmationPanel.add(decline);

    		confirmationPanel.add(sendRequest);
    		panel.add(offerPanel, BorderLayout.WEST);
    		panel.add(requestPanel, BorderLayout.EAST);
    		panel.add(confirmationPanel, BorderLayout.SOUTH);
    		add(panel);
    		pack();
    		setVisible(true);

    	}
    }

}
