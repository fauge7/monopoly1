package monopoly.core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerPickState extends State{

	private Texture background;
	private Texture twoPlayersUp;
	private Texture twoPlayersDown;
	private Texture threePlayersUp;
	private Texture threePlayersDown;
	private Texture fourPlayersUp;
	private Texture fourPlayersDown;

	private int twoPlayersX;
	private int twoPlayersY;
	private int threePlayersX;
	private int threePlayersY;
	private int fourPlayersX;
	private int fourPlayersY;

	private int twoPlayersLeft;
	private int twoPlayersRight;
	private int twoPlayersTop;
	private int twoPlayersBottom;

	private int threePlayersLeft;
	private int threePlayersRight;
	private int threePlayersTop;
	private int threePlayersBottom;

	private int fourPlayersLeft;
	private int fourPlayersRight;
	private int fourPlayersTop;
	private int fourPlayersBottom;

	private boolean twoPlayers;
	private boolean threePlayers;
	private boolean fourPlayers;
	private boolean changingState;
	private float timer;

	private SpriteBatch batch;

	public PlayerPickState(StateManager manager, SpriteBatch batch){
		super(manager);

		this.batch = batch;

		background = new Texture(Gdx.files.internal("assets/playerpick_background.png"));
		twoPlayersUp = new Texture(Gdx.files.internal("assets/two_players_up.png"));
		twoPlayersDown = new Texture(Gdx.files.internal("assets/two_players_down.png"));
		threePlayersUp = new Texture(Gdx.files.internal("assets/three_players_up.png"));
		threePlayersDown = new Texture(Gdx.files.internal("assets/three_players_down.png"));
		fourPlayersUp = new Texture(Gdx.files.internal("assets/four_players_up.png"));
		fourPlayersDown = new Texture(Gdx.files.internal("assets/four_players_down.png"));

		twoPlayersX = 20;
		twoPlayersY = 200;

		threePlayersX = 440;
		threePlayersY = 200;

		fourPlayersX = 860;
		fourPlayersY = 200;

		twoPlayersLeft = twoPlayersX;
		twoPlayersRight = twoPlayersX + twoPlayersUp.getWidth();
		twoPlayersTop = twoPlayersY + twoPlayersUp.getHeight();
		twoPlayersBottom = twoPlayersY;

		threePlayersLeft = threePlayersX;
		threePlayersRight = threePlayersX + threePlayersUp.getWidth();
		threePlayersTop = threePlayersY + threePlayersUp.getHeight();
		threePlayersBottom = threePlayersY;

		fourPlayersLeft = fourPlayersX;
		fourPlayersRight = fourPlayersX + fourPlayersUp.getWidth();
		fourPlayersTop = fourPlayersY + fourPlayersUp.getHeight();
		fourPlayersBottom = fourPlayersY;
	}

	@Override
	public void handleInput() {

		coords.x = Gdx.input.getX();
		coords.y = Gdx.input.getY();
		camera.unproject(coords);

		if(Gdx.input.justTouched()){

			if((coords.x >= twoPlayersLeft && coords.x <= twoPlayersRight) && (coords.y >= twoPlayersBottom && coords.y <= twoPlayersTop)){

				twoPlayers = true;
				changingState = true;

			}else if((coords.x >= threePlayersLeft && coords.x <= threePlayersRight) && (coords.y >= threePlayersBottom && coords.y <= threePlayersTop)){

				threePlayers = true;
				changingState = true;

			}else if((coords.x >= fourPlayersLeft && coords.x <= fourPlayersRight) && (coords.y >= fourPlayersBottom && coords.y <= fourPlayersTop)){

				fourPlayers = true;
				changingState = true;

			}

		}

	}

	@Override
	public void update(float dt) {

		handleInput();

		if(changingState) timer += dt;

		if(changingState && (timer > .1)){

			if(twoPlayers){

				manager.setState(new GameState(manager, batch, 2));

			}else if(threePlayers){

				manager.setState(new GameState(manager, batch, 3));

			}else if(fourPlayers){

				manager.setState(new GameState(manager, batch, 4));

			}

		}


	}

	@Override
	public void render() {

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(background, 0, 0);

		if(twoPlayers){
			batch.draw(twoPlayersDown, twoPlayersX, twoPlayersY);
		}else{
			batch.draw(twoPlayersUp, twoPlayersX, twoPlayersY);
		}

		if(threePlayers){
			batch.draw(threePlayersDown, threePlayersX, threePlayersY);
		}else{
			batch.draw(threePlayersUp, threePlayersX, threePlayersY);
		}

		if(fourPlayers){
			batch.draw(fourPlayersDown, fourPlayersX, fourPlayersY);
		}else{
			batch.draw(fourPlayersUp, fourPlayersX, fourPlayersY);
		}
		batch.end();

	}



}
