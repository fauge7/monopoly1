package monopoly.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameState extends State{

	private Board board;
	private int numPlayers;

	private Texture gameBoard;
	private Texture rollUp;
	private Texture rollDown;
	private Texture payDebtUp;
	private Texture payDebtDown;
	private Texture endTurnUp;
	private Texture endTurnDown;
	private Texture mortgageUp;
	private Texture mortgageDown;
	private Texture tradeUp;
	private Texture tradeDown;
	private Texture upgradeUp;
	private Texture upgradeDown;
	private Texture payBailUp;
	private Texture payBailDown;
	private Texture dog;
	private Texture car;
	private Texture iron;
	private Texture boot;
	private Texture dogIcon;
	private Texture carIcon;
	private Texture ironIcon;
	private Texture bootIcon;
	private Texture dogOwnedVert;
	private Texture dogOwnedHoriz;
	private Texture carOwnedVert;
	private Texture carOwnedHoriz;
	private Texture ironOwnedVert;
	private Texture ironOwnedHoriz;
	private Texture bootOwnedVert;
	private Texture bootOwnedHoriz;
	private Texture house;

	private BitmapFont fontTitle;
	private BitmapFont fontDescription;
	private BitmapFont fontRoll;
	private BitmapFont fontHouse;

	private int rollX;
	private int rollY;
	private int payDebtX;
	private int payDebtY;
	private int endTurnX;
	private int endTurnY;
	private int mortgageX;
	private int mortgageY;
	private int tradeX;
	private int tradeY;
	private int upgradeX;
	private int upgradeY;
	private int payBailX;
	private int payBailY;
	private int dogIconX;
	private int dogIconY;
	private int carIconX;
	private int carIconY;
	private int ironIconX;
	private int ironIconY;
	private int bootIconX;
	private int bootIconY;

	private int rollLeft;
	private int rollRight;
	private int rollTop;
	private int rollBottom;

	private int endTurnLeft;
	private int endTurnRight;
	private int endTurnTop;
	private int endTurnBottom;

	private int payDebtLeft;
	private int payDebtRight;
	private int payDebtTop;
	private int payDebtBottom;

	private int tradeLeft;
	private int tradeRight;
	private int tradeTop;
	private int tradeBottom;

	private int mortgageLeft;
	private int mortgageRight;
	private int mortgageTop;
	private int mortgageBottom;

	private int upgradeLeft;
	private int upgradeRight;
	private int upgradeTop;
	private int upgradeBottom;

	private int payBailLeft;
	private int payBailRight;
	private int payBailTop;
	private int payBailBottom;

	private float timer;
	private boolean rollPressed;
	private boolean endTurnPressed;
	private boolean mortgagePressed;
	private boolean tradePressed;
	private boolean payDebtPressed;
	private boolean upgradePressed;
	private boolean payBailPressed;

	private boolean playerRolling;
	private boolean playerChoosing;

	private int currentPlayer;
	private int currentPlayerRoll;
	private boolean currentPlayerInDebt;
	private boolean playerCanUpgrade;
	private boolean playerCanMortgage;
	private boolean currentPlayerInJail;
	private boolean endGame;

	private SpriteBatch batch;

	public GameState(StateManager manager, SpriteBatch batch , int numPlayers){
		super(manager);

		this.batch = batch;
		this.numPlayers = numPlayers;

		//Initializes all textures needed for the game
		gameBoard = new Texture(Gdx.files.internal("gameboard.png"));
		rollUp = new Texture(Gdx.files.internal("roll_up.png"));
		rollDown = new Texture(Gdx.files.internal("roll_down.png"));
		endTurnUp = new Texture(Gdx.files.internal("endturn_up.png"));
		endTurnDown = new Texture(Gdx.files.internal("endturn_down.png"));
		tradeUp = new Texture(Gdx.files.internal("trade_up.png"));
		tradeDown = new Texture(Gdx.files.internal("trade_down.png"));
		mortgageUp = new Texture(Gdx.files.internal("mortgage_up.png"));
		mortgageDown = new Texture(Gdx.files.internal("mortgage_down.png"));
		upgradeUp = new Texture(Gdx.files.internal("upgrade_up.png"));
		upgradeDown = new Texture(Gdx.files.internal("upgrade_down.png"));
		payBailUp = new Texture(Gdx.files.internal("paybail_up.png"));
		payBailDown = new Texture(Gdx.files.internal("paybail_down.png"));
		dog = new Texture(Gdx.files.internal("dog.png"));
		car = new Texture(Gdx.files.internal("car.png"));
		iron = new Texture(Gdx.files.internal("iron.png"));
		boot = new Texture(Gdx.files.internal("boot.png"));
		dogIcon = new Texture(Gdx.files.internal("dog_icon.png"));
		carIcon = new Texture(Gdx.files.internal("car_icon.png"));
		ironIcon = new Texture(Gdx.files.internal("iron_icon.png"));
		bootIcon = new Texture(Gdx.files.internal("boot_icon.png"));
		dogOwnedVert = new Texture(Gdx.files.internal("dog_owned_vert.png"));
		dogOwnedHoriz = new Texture(Gdx.files.internal("dog_owned_horiz.png"));
		carOwnedVert = new Texture(Gdx.files.internal("car_owned_vert.png"));
		carOwnedHoriz = new Texture(Gdx.files.internal("car_owned_horiz.png"));
		ironOwnedVert = new Texture(Gdx.files.internal("iron_owned_vert.png"));
		ironOwnedHoriz = new Texture(Gdx.files.internal("iron_owned_horiz.png"));
		bootOwnedVert = new Texture(Gdx.files.internal("boot_owned_vert.png"));
		bootOwnedHoriz = new Texture(Gdx.files.internal("boot_owned_horiz.png"));
		house = new Texture(Gdx.files.internal("house.png"));
		payDebtUp = new Texture(Gdx.files.internal("pay_debt_up.png"));
		payDebtDown = new Texture(Gdx.files.internal("pay_debt_down.png"));

		//Defines and initializes the font generators for the game
		FreeTypeFontGenerator fontGeneratorBH = new FreeTypeFontGenerator(Gdx.files.internal("blue_highway_d.ttf"));
		FreeTypeFontGenerator fontGeneratorBH2 = new FreeTypeFontGenerator(Gdx.files.internal("blue_highway_rg.ttf"));
		FreeTypeFontGenerator fontGeneratorBM = new FreeTypeFontGenerator(Gdx.files.internal("pixel_emulator.otf"));

		//Creates the fonts needed
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 28;
		fontTitle = fontGeneratorBH.generateFont(parameter);
		parameter.size = 24;
		fontDescription = fontGeneratorBH2.generateFont(parameter);
		parameter.size = 42;
		fontRoll = fontGeneratorBH.generateFont(parameter);
		parameter.size = 20;
		fontHouse = fontGeneratorBH.generateFont(parameter);

		//Sets font colors to black
		fontHouse.setColor(new Color(0,0,0,1));
		fontTitle.setColor(new Color(0,0,0,1));
		fontDescription.setColor(new Color(0,0,0,1));
		fontRoll.setColor(new Color(0,0,0,1));

		//Initializes the positions of the buttons
		rollX = 950;
		rollY = 120;
		endTurnX = 950;
		endTurnY = 50;
		payDebtX = 1000;
		payDebtY = 120;
		tradeX = 890;
		tradeY = 190;
		mortgageX = 890;
		mortgageY = 120;
		upgradeX = 1000;
		upgradeY = 190;
		payBailX = 1110;
		payBailY = 160;

		dogIconX = 830;
		dogIconY = 580 - (30 * 0);
		carIconX = 830;
		carIconY = 580 - (30 * 1);
		ironIconX = 830;
		ironIconY = 580 - (30 * 2);
		bootIconX = 830;
		bootIconY = 580 - (30 * 3);

		//Initializes the edges of the buttons for easy click checking
		rollLeft = rollX;
		rollRight = rollX + rollUp.getWidth();
		rollTop = rollY + rollUp.getHeight();
		rollBottom = rollY;

		endTurnLeft = endTurnX;
		endTurnRight = endTurnX + endTurnUp.getWidth();
		endTurnTop = endTurnY +  endTurnUp.getHeight();
		endTurnBottom = endTurnY;

		payDebtLeft = payDebtX;
		payDebtRight = payDebtX + payDebtUp.getWidth();
		payDebtTop = payDebtY +  payDebtUp.getHeight();
		payDebtBottom = payDebtY;

		tradeLeft = tradeX;
		tradeRight = tradeX + tradeUp.getWidth();
		tradeTop = tradeY +  tradeUp.getHeight();
		tradeBottom = tradeY;

		mortgageLeft = mortgageX;
		mortgageRight = mortgageX + mortgageUp.getWidth();
		mortgageTop = mortgageY +  mortgageUp.getHeight();
		mortgageBottom = mortgageY;

		upgradeLeft = upgradeX;
		upgradeRight = upgradeX + upgradeUp.getWidth();
		upgradeTop = upgradeY +  upgradeUp.getHeight();
		upgradeBottom = upgradeY;

		payBailLeft = payBailX;
		payBailRight = payBailX + payBailUp.getWidth();
		payBailTop = payBailY +  payBailUp.getHeight();
		payBailBottom = payBailY;

		//Starts with the first player
		currentPlayer = 0;

		//Creates the game board
		initializeGame(numPlayers);

		//Sets the player's state to rolling
		playerRolling = true;

		//Disposes of font generators
		fontGeneratorBH.dispose();
		fontGeneratorBH2.dispose();
		fontGeneratorBM.dispose();
	}

	@Override
	public void handleInput() {

		//Grabs x and y coords of mouse
		coords.x = Gdx.input.getX();
		coords.y = Gdx.input.getY();
		camera.unproject(coords);

		if(Gdx.input.justTouched()){

			//If the player touched the roll button
			if(playerRolling && (coords.x >= rollLeft && coords.x <= rollRight) && (coords.y >= rollBottom && coords.y <= rollTop)){

				rollPressed = true;

				//Roll the dice
				currentPlayerRoll = board.rollDice(currentPlayer);
				System.out.println("Player " + currentPlayer + " rolled " + currentPlayerRoll);

				//Check for rolling doubles and to roll again
				if(board.getPlayers().get(currentPlayer).getRollAgain()){
					playerChoosing = false;
					playerRolling = true;
				}else{
					playerChoosing = true;
					playerRolling = false;
				}

			//If the player touched the end turn button
			}else if(playerChoosing && (coords.x >= endTurnLeft && coords.x <= endTurnRight) && (coords.y >= endTurnBottom && coords.y <= endTurnTop)){

				endTurnPressed = true;

				//If they touched the pressed button when in debt
				if(endTurnPressed && currentPlayerInDebt){

					board.getPlayers().get(currentPlayer).setBankrupt(true);
					new DialogWindow("Bankrupt", "Player " + (currentPlayer + 1) + " is now bankrupt.");
					System.out.println("Player " + (currentPlayer + 1) + " is now bankrupt");

					//If in debt then check for if they owe a player
					if(board.getPlayers().get(currentPlayer).getOwesPlayer()){

						board.giveAll(board.getPlayers().get(currentPlayer), board.getPlayers().get(currentPlayer).getPlayerOwed());
						System.out.println("Player " + (currentPlayer + 1) + " giving all to " + board.getPlayers().get(currentPlayer).getPlayerOwed().getName());

					}

				}

				playerChoosing = false;
				playerRolling = true;
				nextPlayer();
				System.out.println("Player " + currentPlayer + " is now rolling");

			//If the player touched the pay debt button
			}else if(playerChoosing && (coords.x >= payDebtLeft && coords.x <= payDebtRight) && (coords.y >= payDebtBottom && coords.y <= payDebtTop)){

				board.payDebt(board.getPlayers().get(currentPlayer));
				playerChoosing = true;
				playerRolling = false;
				board.getPlayers().get(currentPlayer).setInDebt(false);

			//If the player touched the upgrade button
			}else if(playerChoosing && playerCanUpgrade && (coords.x >= upgradeLeft && coords.x <= upgradeRight) && (coords.y >= upgradeBottom && coords.y <= upgradeTop)){

				upgradePressed = true;
				new UpgradeWindow(board.getPlayers().get(currentPlayer), board.canUpgradeList(board.getPlayers().get(currentPlayer)), board);

			//If the player touched the mortgage button
			}else if(playerChoosing && playerCanMortgage && (coords.x >= mortgageLeft && coords.x <= mortgageRight) && (coords.y >= mortgageBottom && coords.y <= mortgageTop)){

				mortgagePressed = true;
				new MortgageWindow(board.getPlayers().get(currentPlayer), board.canMortgageList(board.getPlayers().get(currentPlayer)), board);

			//If the player touched the trade button
			}else if(playerChoosing && (coords.x >= tradeLeft && coords.x <= tradeRight) && (coords.y >= tradeBottom && coords.y <= tradeTop)){

				tradePressed = true;
				board.initiateTrade(board.getPlayers().get(currentPlayer));

			//If the player touched the pay bail button
			}else if(playerChoosing && currentPlayerInJail && (coords.x >= payBailLeft && coords.x <= payBailRight) && (coords.y >= payBailBottom && coords.y <= payBailTop)){

				payBailPressed = true;
				board.payJail(board.getPlayers().get(currentPlayer));

			}


		}

	}

	@Override
	public void update(float dt) {

		handleInput();

		//Show the second image to the button
		animateButtons(dt);

		//Check for all current player flags
		currentPlayerInDebt = board.getPlayers().get(currentPlayer).getInDebt();
		playerCanUpgrade = board.canUpgrade(board.getPlayers().get(currentPlayer));
		playerCanMortgage = board.canMortgage(board.getPlayers().get(currentPlayer));
		currentPlayerInJail = board.getPlayers().get(currentPlayer).getInJail();

		//Check for game over
		if(board.gameOver() && !endGame){
			board.winner();
			endGame = true;
		}

	}

	@Override
	public void render() {

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//Draw the board background
		batch.draw(gameBoard, 0, 0);

		//Draw colored overlays for the properties owned
		drawOwnedProperties();

		//Draw all upgraded houses and hotels
		drawHousesAndHotels();

		//Draw all player pieces
		drawPlayerPieces();

		//Render out roll button if they're rolling
		if(playerRolling){
			if(rollPressed){
				batch.draw(rollDown, rollX, rollY);
			}else{
				batch.draw(rollUp, rollX, rollY);
			}
		}

		//Render out all buttons needed for the turn
		if(playerChoosing){
			fontRoll.draw(batch, currentPlayerRoll + " ", 450, 500);

			//Draw pay debt button
			if(board.getPlayers().get(currentPlayer).getInDebt()){

				if(payDebtPressed){
					batch.draw(payDebtDown, payDebtX, payDebtY);
				}else{
					batch.draw(payDebtUp, payDebtX, payDebtY);
				}

			}

			//Draw upgrade button
			if(playerCanUpgrade){

				if(upgradePressed){
					batch.draw(upgradeDown, upgradeX, upgradeY);
				}else{
					batch.draw(upgradeUp, upgradeX, upgradeY);
				}

			}

			//Draw pay bail button
			if(currentPlayerInJail){

				if(payBailPressed){
					batch.draw(payBailDown, payBailX, payBailY);
				}else{
					batch.draw(payBailUp, payBailX, payBailY);
				}

			}

			//Draw trade button
			if(tradePressed){
				batch.draw(tradeDown, tradeX, tradeY);
			}else{
				batch.draw(tradeUp, tradeX, tradeY);
			}

			//Draw mortgage button
			if(mortgagePressed){
				batch.draw(mortgageDown, mortgageX, mortgageY);
			}else{
				batch.draw(mortgageUp, mortgageX, mortgageY);
			}

			//Draw end turn button
			if(endTurnPressed){
				batch.draw(endTurnDown, endTurnX, endTurnY);
			}else{
				batch.draw(endTurnUp, endTurnX, endTurnY);
			}

		}

		//Write out all players
		fontTitle.draw(batch, "Players", 955, 650);
		for(int x = 0; x < numPlayers; x++){
			fontDescription.draw(batch, "Player " + (x+1) + ":  ", 880, 600 - (30 * x));
			fontDescription.draw(batch, "$" + board.getPlayers().get(x).getCash(), 1050, 600 - (30 * x));
		}

		//Write out the current space's information
		fontTitle.draw(batch, "INFO", 980, 450);
		fontDescription.draw(batch, "Current Space: " + board.getSpaces()[board.getPlayers().get(currentPlayer).getPosition()].getName(), 800, 400);
		fontDescription.draw(batch, "Owner: ", 800, 370);

		if(board.getSpaces()[board.getPlayers().get(currentPlayer).getPosition()] instanceof Property && ((Property)board.getSpaces()[board.getPlayers().get(currentPlayer).getPosition()]).getOwner() != null){
			fontDescription.draw(batch, ((Property)board.getSpaces()[board.getPlayers().get(currentPlayer).getPosition()]).getOwner().getName(), 900, 370);
		}else{
			fontDescription.draw(batch, " Unowned", 900, 370);
		}

		//Draw the icons for current player's turn
		drawIcons();

		batch.end();
	}


	public void animateButtons(float dt){

		if(rollPressed || endTurnPressed || mortgagePressed || upgradePressed || tradePressed || payDebtPressed || payBailPressed) timer += dt;

		if(timer > .1){

			timer = 0;
			rollPressed = false;
			endTurnPressed = false;
			mortgagePressed = false;
			tradePressed = false;
			upgradePressed = false;
			payDebtPressed = false;
			payBailPressed = false;

		}
	}

	public void drawIcons(){

		if(currentPlayer == 0){

			batch.draw(dogIcon, dogIconX, dogIconY);

		}else if(currentPlayer == 1){

			batch.draw(carIcon, carIconX, carIconY);

		}else if(currentPlayer == 2){

			batch.draw(ironIcon, ironIconX, ironIconY);

		}else{

			batch.draw(bootIcon, bootIconX, bootIconY);
		}

	}

	public void initializeGame(int numPlayers){

		board = new Board();

		for(int x = 1; x <= numPlayers; x++){

			board.addPlayer(("Player " + x), x);

		}

	}

	public void drawPlayerPieces(){

		for(Player p : board.getPlayers()){

			batch.draw(getPlayerPiece(p.getToken()), getPlayerPositionX(p), getPlayerPositionY(p));

		}

	}

	public Texture getPlayerPiece(int number){

		if(number == 1){

			return dog;

		}else if(number == 2){

			return car;

		}else if(number == 3){

			return iron;

		}else{

			return boot;

		}

	}

	public float getPlayerPositionX(Player p){

		int position = p.getPosition();
		int X = 0;

		if(position >= 10 && position <= 20){
			X = 15;
		}else if(position >= 30 && position <= 39){
			X = 655;
		}else if(position == 0){
			X = 640;
		}else if(position == 1 || position == 29){
			X = 565;
		}else if(position == 2 || position == 28){
			X = 510;
		}else if(position == 3 || position == 27){
			X = 450;
		}else if(position == 4 || position == 26){
			X = 390;
		}else if(position == 5 || position == 25){
			X = 335;
		}else if(position == 6 || position == 24){
			X = 275;
		}else if(position == 7 || position == 23){
			X = 220;
		}else if(position == 8 || position == 22){
			X = 160;
		}else if(position == 9 || position == 21){
			X = 100;
		}

		return X;
	}

	public float getPlayerPositionY(Player p){

		int position = p.getPosition();
		int Y = 0;

		if(position >= 0 && position <= 10){
			Y = 25;
		}else if(position >= 20 && position <= 30){
			Y = 640;
		}else if(position == 11 || position == 39){
			Y = 100;
		}else if(position == 12 || position == 38){
			Y = 160;
		}else if(position == 13 || position == 37){
			Y = 210;
		}else if(position == 14 || position == 36){
			Y = 275;
		}else if(position == 15 || position == 35){
			Y = 335;
		}else if(position == 16 || position == 34){
			Y = 390;
		}else if(position == 17 || position == 33){
			Y = 450;
		}else if(position == 18 || position == 32){
			Y = 510;
		}else if(position == 19 || position == 31){
			Y = 565;
		}

		return Y;

	}

	public void nextPlayer(){

		if(currentPlayer == (numPlayers - 1)){
			currentPlayer = 0;
		}else{
			currentPlayer++;
		}

		if(board.getPlayers().get(currentPlayer).getBankrupt()){
			nextPlayer();
		}
	}

	public void drawOwnedProperties(){

		for(int property : board.getPropertyIndex()){

			if((numPlayers == 2 || numPlayers == 3 || numPlayers == 4) && ((Property)board.getSpaces()[property]).getOwner() == board.getPlayers().get(0)){

				drawProperty(0, property);

			}else if((numPlayers == 2 || numPlayers == 3 || numPlayers == 4) && ((Property)board.getSpaces()[property]).getOwner() == board.getPlayers().get(1)){

				drawProperty(1, property);

			}else if((numPlayers == 3 || numPlayers == 4) && ((Property)board.getSpaces()[property]).getOwner() == board.getPlayers().get(2)){

				drawProperty(2, property);

			}else if(numPlayers == 4 && ((Property)board.getSpaces()[property]).getOwner() == board.getPlayers().get(3)){

				drawProperty(3, property);

			}

		}

	}

	public void drawProperty(int player, int property){

		if(property == 1){
			batch.draw(getOwnedTextureVert(player), 565, 7);
		}else if(property == 3){
			batch.draw(getOwnedTextureVert(player), 447, 7);
		}else if(property == 5){
			batch.draw(getOwnedTextureVert(player), 330, 7);
		}else if(property == 6){
			batch.draw(getOwnedTextureVert(player), 272, 7);
		}else if(property == 8){
			batch.draw(getOwnedTextureVert(player), 155, 7);
		}else if(property == 9){
			batch.draw(getOwnedTextureVert(player), 97, 7);
		}else if(property == 11){
			batch.draw(getOwnedTextureHoriz(player), 7, 97);
		}else if(property == 12){
			batch.draw(getOwnedTextureHoriz(player), 7, 155);
		}else if(property == 13){
			batch.draw(getOwnedTextureHoriz(player), 7, 212);
		}else if(property == 14){
			batch.draw(getOwnedTextureHoriz(player), 7, 273);
		}else if(property == 15){
			batch.draw(getOwnedTextureHoriz(player), 7, 330);
		}else if(property == 16){
			batch.draw(getOwnedTextureHoriz(player), 7, 389);
		}else if(property == 18){
			batch.draw(getOwnedTextureHoriz(player), 7, 506);
		}else if(property == 19){
			batch.draw(getOwnedTextureHoriz(player), 7, 563);
		}else if(property == 21){
			batch.draw(getOwnedTextureVert(player), 97, 623);
		}else if(property == 23){
			batch.draw(getOwnedTextureVert(player), 214, 623);
		}else if(property == 24){
			batch.draw(getOwnedTextureVert(player), 272, 623);
		}else if(property == 25){
			batch.draw(getOwnedTextureVert(player), 331, 623);
		}else if(property == 26){
			batch.draw(getOwnedTextureVert(player), 389, 623);
		}else if(property == 27){
			batch.draw(getOwnedTextureVert(player), 447, 623);
		}else if(property == 28){
			batch.draw(getOwnedTextureVert(player), 506, 623);
		}else if(property == 29){
			batch.draw(getOwnedTextureVert(player), 564, 623);
		}else if(property == 31){
			batch.draw(getOwnedTextureHoriz(player), 623, 564);
		}else if(property == 32){
			batch.draw(getOwnedTextureHoriz(player), 623, 506);
		}else if(property == 34){
			batch.draw(getOwnedTextureHoriz(player), 623, 388);
		}else if(property == 35){
			batch.draw(getOwnedTextureHoriz(player), 623, 330);
		}else if(property == 37){
			batch.draw(getOwnedTextureHoriz(player), 623, 214);
		}else if(property == 39){
			batch.draw(getOwnedTextureHoriz(player), 623, 98);
		}

	}

	public Texture getOwnedTextureVert(int player){

		if(player == 0){
			return dogOwnedVert;
		}else if(player == 1){
			return carOwnedVert;
		}else if(player == 2){
			return ironOwnedVert;
		}else{
			return bootOwnedVert;
		}

	}

	public Texture getOwnedTextureHoriz(int player){

		if(player == 0){
			return dogOwnedHoriz;
		}else if(player == 1){
			return carOwnedHoriz;
		}else if(player == 2){
			return ironOwnedHoriz;
		}else{
			return bootOwnedHoriz;
		}

	}

	public void drawHousesAndHotels(){

		for(int a : board.getPropertyIndex()){

			if(board.getSpaces()[a] instanceof Lot){

				int houses = ((Lot)board.getSpaces()[a]).getHouses();

				if(houses > 0 && houses < 5){

					fontHouse.draw(batch, houses + "", getHouseX(a), getHouseY(a));

				}else if(houses >= 5){

					batch.draw(house, getHotelX(a), getHotelY(a));

				}

			}

		}

	}


	public int getHotelX(int property){

		if(property == 1){
			return 586;
		}else if(property == 3){
			return 470;
		}else if(property == 6){
			return 295;
		}else if(property == 8){
			return 178;
		}else if(property == 9){
			return 115;
		}else if(property == 11){
			return 78;
		}else if(property == 13){
			return 78;
		}else if(property == 14){
			return 78;
		}else if(property == 16){
			return 78;
		}else if(property == 18){
			return 78;
		}else if(property == 19){
			return 78;
		}else if(property == 21){
			return 115;
		}else if(property == 23){
			return 235;
		}else if(property == 24){
			return 295;
		}else if(property == 26){
			return 406;
		}else if(property == 27){
			return 470;
		}else if(property == 29){
			return 586;
		}else if(property == 31){
			return 624;
		}else if(property == 32){
			return 624;
		}else if(property == 34){
			return 624;
		}else if(property == 37){
			return 624;
		}else{
			return 624;
		}

	}

	public int getHotelY(int property){

		if(property == 1){
			return 78;
		}else if(property == 3){
			return 78;
		}else if(property == 6){
			return 78;
		}else if(property == 8){
			return 78;
		}else if(property == 9){
			return 78;
		}else if(property == 11){
			return 120;
		}else if(property == 13){
			return 238;
		}else if(property == 14){
			return 292;
		}else if(property == 16){
			return 411;
		}else if(property == 18){
			return 525;
		}else if(property == 19){
			return 585;
		}else if(property == 21){
			return 624;
		}else if(property == 23){
			return 624;
		}else if(property == 24){
			return 624;
		}else if(property == 26){
			return 624;
		}else if(property == 27){
			return 624;
		}else if(property == 29){
			return 624;
		}else if(property == 31){
			return 582;
		}else if(property == 32){
			return 527;
		}else if(property == 34){
			return 411;
		}else if(property == 37){
			return 231;
		}else{
			return 120;
		}

	}

	public int getHouseX(int property){

		if(property == 1){
			return 585;
		}else if(property == 3){
			return 468;
		}else if(property == 6){
			return 295;
		}else if(property == 8){
			return 178;
		}else if(property == 9){
			return 118;
		}else if(property == 11){
			return 82;
		}else if(property == 13){
			return 82;
		}else if(property == 14){
			return 82;
		}else if(property == 16){
			return 82;
		}else if(property == 18){
			return 82;
		}else if(property == 19){
			return 82;
		}else if(property == 21){
			return 118;
		}else if(property == 23){
			return 235;
		}else if(property == 24){
			return 295;
		}else if(property == 26){
			return 406;
		}else if(property == 27){
			return 470;
		}else if(property == 29){
			return 586;
		}else if(property == 31){
			return 628;
		}else if(property == 32){
			return 628;
		}else if(property == 34){
			return 628;
		}else if(property == 37){
			return 628;
		}else{
			return 628;
		}

	}

	public int getHouseY(int property){

		if(property == 1){
			return 95;
		}else if(property == 3){
			return 95;
		}else if(property == 6){
			return 95;
		}else if(property == 8){
			return 95;
		}else if(property == 9){
			return 95;
		}else if(property == 11){
			return 128;
		}else if(property == 13){
			return 248;
		}else if(property == 14){
			return 302;
		}else if(property == 16){
			return 421;
		}else if(property == 18){
			return 535;
		}else if(property == 19){
			return 595;
		}else if(property == 21){
			return 640;
		}else if(property == 23){
			return 640;
		}else if(property == 24){
			return 640;
		}else if(property == 26){
			return 640;
		}else if(property == 27){
			return 640;
		}else if(property == 29){
			return 640;
		}else if(property == 31){
			return 592;
		}else if(property == 32){
			return 537;
		}else if(property == 34){
			return 421;
		}else if(property == 37){
			return 241;
		}else{
			return 130;
		}

	}

}
