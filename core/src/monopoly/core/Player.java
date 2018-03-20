package monopoly.core;

public class Player{
	private String name;
	private int token;
	private int cash;
	private int position;
	private final int STARTING_CASH;
	private boolean inJail;
	private int jailTurns;
	private int utilitiesOwned;
	private int railroadsOwned;
	private int jailFreeCards;
	private int lastRoll;
	private int houseCount;
	private int hotelCount;
	private boolean folded;
	private boolean bankrupt;
	private boolean inDebt;
	private boolean owesPlayer;
	private Player playerOwed;
	private int debt;
	private boolean rollAgain;
	private int doublesCount;
	//Parameterized Constructor
	public Player(String name, int token){
		this.name = name;
		this.token = token;

		STARTING_CASH = 1500;
		cash = STARTING_CASH;
		position = 0;

		inJail = false;			//Whether or not the Player is in jail
		jailTurns = 0;			//How long the Player has been in jail

		utilitiesOwned = 0;		//Number of utilities owned
		railroadsOwned = 0;		//Number of railroads owned

		jailFreeCards = 0;		//Number of Get Out of Jail Free cards the Player has
		lastRoll = 0;			//Value of the Player's last roll

		houseCount = 0;			//Number of houses the Player owns
		hotelCount = 0;			//Number of hotels the Player owns

		folded = false;			//Whether or not the Player has folded in an auction
		bankrupt = false;		//Whether or not the Player is still in the game

		inDebt = false;			//Whether or not the Player owes money
		owesPlayer = false;		//Whether or not the debt is owed to a Player
		playerOwed = null;		//Which Player this Player owes
		debt = 0;				//Amount of debt the Player owes

		rollAgain = true;		//Whether or not the player can roll;
	}

	// ** Methods related to the name variable **

	//Getters and Setters for the name variable
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}

	// ** Methods related to the token variable **

	//Getters and Setters for the token variable
	public int getToken(){
		return token;
	}
	public void setToken(int token){
		this.token = token;
	}

	// ** Methods related to the cash variable **

	//Getters and Setters for the cash variable
	public int getCash(){
		return cash;
	}
	public void setCash(int cash){
		this.cash = cash;
	}
	//Adds an amount to the Player's cash
	public void addCash(int amount){
		this.cash += amount;
	}
	//Subtracts and amount from the Player's cash
	public void subtractCash(int amount){
		this.cash -= amount;
	}
	//Checks whether or not the Player can afford to pay
	public boolean canPay(int amount){
		boolean canPay = false;
		if((cash - amount - debt) >= 0){
			canPay = true;
		}
		return canPay;
	}

	// ** Methods related to the position variable **

	//Getters and Setters for the position variable
	public int getPosition(){
		return position;
	}
	public void setPosition(int position){
		this.position = position;
	}
	//Increments the players position by a given amount
	public void incrementPosition(int amount){
		position += amount;
		position %= 40;
	}

	// ** Methods related to the inJail and jailTurn variable **

	//Getters and Setters for the inJail variable
	public boolean getInJail(){
		return inJail;
	}
	public void setInJail(boolean inJail){
		this.inJail = inJail;
	}
	//Getters and Setters for the jailTurn variable
	public int getJailTurns(){
		return jailTurns;
	}
	public void setJailTurn(int jailTurns){
		this.jailTurns = jailTurns;
	}
	//Increments the jailTurn variable by 1
	public void incrementJailTurns(){
		jailTurns++;
	}

	// ** Methods related to the utilitiesOwned and railroadsOwned variables **

	//Getters and Setters for the utilitiesOwned variable
	public int getUtilitiesOwned(){
		return utilitiesOwned;
	}
	public void setUtilitiesOwned(int utilitiesOwned){
		this.utilitiesOwned = utilitiesOwned;
	}
	//Increments the utilitiesOwned variable by 1
	public void incrementUtilitiesOwned(){
		utilitiesOwned++;
	}
	//Decrements the utilitiesOwned variable by 1
	public void decrementUtilitiesOwned(){
		utilitiesOwned--;
	}
	//Getters and Setters for the railroadsOwned variable
	public int getRailroadsOwned(){
		return railroadsOwned;
	}
	public void setRailroadsOwned(int railroadsOwned){
		this.railroadsOwned = railroadsOwned;
	}
	//Increments the railroadsOwned variable by 1
	public void incrementRailroadsOwned(){
		railroadsOwned++;
	}
	//Decrements the railroadsOwned variable by 1
	public void decrementRailroadsOwned(){
		railroadsOwned--;
	}

	// ** Methods related to the jailFreeCards variable **

	//Getters and Setters for the jailFreeCards variable
	public int getJailFreeCards(){
		return jailFreeCards;
	}
	public void setJailFreeCards(int jailFreeCards){
		this.jailFreeCards = jailFreeCards;
	}
	//Increments the jailFreeCard variable by 1
	public void incrementJailFreeCards(){
		jailFreeCards++;
	}
	//Decrements the jailFreeCard variable by 1
	public void decrementJailFreeCards(){
		jailFreeCards--;
	}

	// ** Methods related to the lastRoll variable **

	//Getters and Setters for the lastRoll variable
	public int getLastRoll(){
		return lastRoll;
	}
	public void setLastRoll(int lastRoll){
		this.lastRoll = lastRoll;
	}

	// ** Methods related to the houseCount and hotelCount variables

	//Getters and Setters for the houseCount variable
	public int getHouseCount(){
		return houseCount;
	}
	public void setHouseCount(int houseCount){
		this.houseCount = houseCount;
	}
	//Increments the houseCount variable by 1
	public void incrementHouseCount(){
		if(houseCount + 1 == 5){
			houseCount = 0;
			hotelCount++;
		}
		else{
			houseCount++;
		}
	}
	//Decrements the houseCount variable by 1
	public void decrementHouseCount(){
		if(hotelCount == 1){
			houseCount = 4;
			hotelCount = 0;
		}
		else{
			houseCount--;
		}
	}
	//Getters and Setters for the hotelCount variable
	public int getHotelCount(){
		return hotelCount;
	}
	public void setHotelCount(int hotelCount){
		this.hotelCount = hotelCount;
	}

	// ** Methods related to the folded variable **

	//Getters and Setters for the folded variable
	public boolean getFolded(){
		return folded;
	}
	public void setFolded(boolean folded){
		this.folded = folded;
	}

	// ** Methods related to the bankrupt variable **

	//Getters and Setters for the bankrupt variable
	public boolean getBankrupt(){
		return bankrupt;
	}
	public void setBankrupt(boolean bankrupt){
		this.bankrupt = bankrupt;
	}

	// ** Methods related to the Player being in debt **

	//Getters and Setters
	public boolean getInDebt(){
		return inDebt;
	}
	public void setInDebt(boolean inDebt){
		this.inDebt = inDebt;
	}
	public int getDebt(){
		return debt;
	}
	public void setDebt(int debt){
		this.debt = debt;
	}
	public boolean getOwesPlayer(){
		return owesPlayer;
	}
	public void setOwesPlayer(boolean owesPlayer){
		this.owesPlayer = owesPlayer;
	}
	public Player getPlayerOwed(){
		return playerOwed;
	}
	public void setPlayedOwed(Player playerOwed){
		this.playerOwed = playerOwed;
	}


	// ** Methods related to rolling doubles **

	//Getters and Setters for the rollAgain and doublesCount variables
	public boolean getRollAgain(){
		return rollAgain;
	}
	public void setRollAgain(boolean rollAgain){
		this.rollAgain = rollAgain;
	}
	public int getDoublesCount(){
		return doublesCount;
	}
	public void setDoublesCount(int doublesCount){
		this.doublesCount = doublesCount;
	}
	//Increments the doublesCount variable by 1
	public void incrementDoublesCount(){
		doublesCount++;
	}
}
