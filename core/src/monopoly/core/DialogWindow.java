package monopoly.core;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class DialogWindow {
	public String title;
	public String message;
	public float deltaTime = 0f;
	public final float MAXTIME = 7f;
	Color color;
	//Constructor with no color
	public DialogWindow(String title,String message,int i){
		GameState.addDiagWindow(title, message, Color.WHITE);
	}
	//Constructor with color
	public DialogWindow(String title,String message,Color color,int i){
		GameState.addDiagWindow(title, message, color);
	}
	public DialogWindow(String title, String message, Color color, boolean b) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.message = message;
		this.color = color;
		deltaTime = 0f;		
	}
	public void update(){
		deltaTime+=Gdx.graphics.getDeltaTime();
	}
}
