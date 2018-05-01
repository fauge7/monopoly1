package monopoly.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class AboutState extends State{

	private Texture aboutPage;
	private Texture backUp;
	private Texture backDown;

	private int backX;
	private int backY;

	private int backLeft;
	private int backRight;
	private int backTop;
	private int backBottom;

	private boolean backPressed;
	private boolean changingState;
	private float timer;

	private SpriteBatch batch;
	
	private BitmapFont fontTitle;
  private BitmapFont fontDescription;
  private BitmapFont fontRoll;
  private BitmapFont fontHouse;

	public AboutState(StateManager manager, SpriteBatch batch){
		super(manager);

		this.batch = batch;

		aboutPage = new Texture(Gdx.files.internal("assets/about_page.png"));
		backUp = new Texture(Gdx.files.internal("assets/back_up.png"));
		backDown = new Texture(Gdx.files.internal("assets/back_down.png"));

		FreeTypeFontGenerator fontGeneratorBH = new FreeTypeFontGenerator(Gdx.files.internal("assets/blue_highway_d.ttf"));
    FreeTypeFontGenerator fontGeneratorBH2 = new FreeTypeFontGenerator(Gdx.files.internal("assets/blue_highway_rg.ttf"));
    FreeTypeFontGenerator fontGeneratorBM = new FreeTypeFontGenerator(Gdx.files.internal("assets/pixel_emulator.otf"));
    
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
    
    

		backX = 50;
		backY = 50;

		backLeft = backX;
		backRight = backX + backUp.getWidth();
		backTop = backY + backUp.getHeight();
		backBottom = backY;

		timer = 0;

	}

	@Override
	public void handleInput() {

		coords.x = Gdx.input.getX();
		coords.y = Gdx.input.getY();
		camera.unproject(coords);

		if(Gdx.input.justTouched()){

			if((coords.x >= backLeft && coords.x <= backRight) && (coords.y >= backBottom && coords.y <= backTop)){

				backPressed = true;
				changingState = true;

			}

		}

	}

	@Override
	public void update(float dt) {

		handleInput();

		if(changingState) timer += dt;

		if(changingState && (timer > .1)){

			manager.setState(new MenuState(manager, batch));

		}


	}

	@Override
	public void render() {

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(aboutPage, 0, 0);
		if(backPressed){
			batch.draw(backDown, backX, backY);
		}else{
			batch.draw(backUp, backX, backY);
		}
		GlyphLayout lay = new GlyphLayout(fontDescription, "Win Monoploy by not going in debt and owning money!");
		fontDescription.draw(batch, "Win Monoploy by not going in debt and owning money!", Gdx.graphics.getWidth() / 2 - lay.width / 2, Gdx.graphics.getHeight() /2);
		
		batch.end();

	}

}
