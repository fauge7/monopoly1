package monopoly.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Monopoly extends ApplicationAdapter {

	public static final String TITLE = "Monopoly";
	public static final int HEIGHT = 720;
	public static final int WIDTH = 1280;

	private StateManager manager;
	private SpriteBatch batch;
	private static Content resources;

	@Override
	public void create () {
		resources = new Content();
		batch = new SpriteBatch();
		manager = new StateManager();
		manager.pushState(new MenuState(manager, batch));
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		manager.update(Gdx.graphics.getDeltaTime());
		manager.render(batch);

	}
}
