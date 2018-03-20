package monopoly.core;


import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Content {

	private HashMap<String, TextureAtlas> atlases;

	public Content(){

		atlases = new HashMap<String, TextureAtlas>();

	}

	public void loadContentAtlas(String path, String key){

		atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));

	}

	public TextureAtlas getContentAtlas(String key){

		return atlases.get(key);

	}

}
