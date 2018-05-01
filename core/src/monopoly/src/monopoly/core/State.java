package monopoly.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

	protected StateManager manager;
	protected OrthographicCamera camera;
	protected Vector3 coords;

	public State(StateManager manager){

		this.manager = manager;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Monopoly.WIDTH, Monopoly.HEIGHT);
		coords = new Vector3();

	}

	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	//public abstract void render(SpriteBatch batch);

}
