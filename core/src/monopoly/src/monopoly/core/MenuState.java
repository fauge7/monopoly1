package monopoly.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State{

	private Texture background;
	private Texture playUp;
	private Texture playDown;
	private Texture quitUp;
	private Texture quitDown;
	private Texture aboutUp;
	private Texture aboutDown;

	//Int variables for the x and y coords of the buttons
	private int playX;
	private int playY;

	private int quitX;
	private int quitY;

	private int aboutX;
	private int aboutY;

	private int playLeft;
	private int playRight;
	private int playTop;
	private int playBottom;

	private int quitLeft;
	private int quitRight;
	private int quitTop;
	private int quitBottom;

	private int aboutLeft;
	private int aboutRight;
	private int aboutTop;
	private int aboutBottom;

	private boolean playPressed;
	private boolean quitPressed;
	private boolean aboutPressed;
	private boolean changingState;

	private SpriteBatch batch;

	private float timer;

	public MenuState(StateManager manager, SpriteBatch batch){

		super(manager);

		this.batch = batch;

		background = new Texture(Gdx.files.internal("assets/background.png"));
		playUp = new Texture(Gdx.files.internal("assets/play_up.png"));
		playDown = new Texture(Gdx.files.internal("assets/play_down.png"));
		quitUp = new Texture(Gdx.files.internal("assets/quit_up.png"));
		quitDown = new Texture(Gdx.files.internal("assets/quit_down.png"));
		aboutUp = new Texture(Gdx.files.internal("assets/about_up.png"));
		aboutDown = new Texture(Gdx.files.internal("assets/about_down.png"));

		playX = 300;
		playY = 100;

		quitX = 700;
		quitY = 100;

		aboutX = 50;
		aboutY = 50;

		playLeft = playX;
		playRight = playX + playUp.getWidth();
		playTop = playY + playUp.getHeight();
		playBottom = playY;

		quitLeft = quitX;
		quitRight = quitX + quitUp.getWidth();
		quitTop = quitY + quitUp.getHeight();
		quitBottom = quitY;

		aboutLeft = aboutX;
		aboutRight = aboutX + aboutUp.getWidth();
		aboutTop = aboutY + aboutUp.getHeight();
		aboutBottom = aboutY;

		timer = 0;

	}

	@Override
	public void handleInput() {

		coords.x = Gdx.input.getX();
		coords.y = Gdx.input.getY();
		camera.unproject(coords);

		if(Gdx.input.justTouched()){

			if((coords.x >= playLeft && coords.x <= playRight) && (coords.y >= playBottom && coords.y <= playTop)){

				playPressed = true;
				changingState = true;

			}else if((coords.x >= quitLeft && coords.x <= quitRight) && (coords.y >= quitBottom && coords.y <= quitTop)){

				quitPressed = true;
				changingState = true;

			}else if((coords.x >= aboutLeft && coords.x <= aboutRight) && (coords.y >= aboutBottom && coords.y <= aboutTop)){

				aboutPressed = true;
				changingState = true;

			}

		}

	}

	@Override
	public void update(float dt) {

		handleInput();

		if(changingState) timer += dt;

		if(changingState && (timer > .1)){

			if(aboutPressed){

				manager.setState(new AboutState(manager, batch));

			}else if(quitPressed){

				Gdx.app.exit();

			}else if(playPressed){

				manager.setState(new PlayerPickState(manager, batch));

			}

		}


	}

	@Override
	public void render() {

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(background, 0, 0);
		if(aboutPressed){
			batch.draw(aboutDown, aboutX, aboutY);
		}else{
			batch.draw(aboutUp, aboutX, aboutY);
		}

		if(playPressed){
			batch.draw(playDown, playX, playY);
		}else{
			batch.draw(playUp, playX, playY);
		}

		if(quitPressed){
			batch.draw(quitDown, quitX, quitY);
		}else{
			batch.draw(quitUp, quitX, quitY);
		}

		batch.end();

	}

}
