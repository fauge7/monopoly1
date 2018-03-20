package monopoly.core;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StateManager {

	private Stack<State> states;

	public StateManager(){

		states = new Stack<State>();

	}

	public void pushState(State state){

		states.push(state);

	}

	public void popState(){

		states.pop();

	}

	public void setState(State state){

		states.pop();
		states.push(state);

	}

	public void update(float delta){

		states.peek().update(delta);

	}

	public void render(SpriteBatch batch){

		states.peek().render();

	}

}
