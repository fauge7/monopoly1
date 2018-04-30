package monopoly.test;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import monopoly.core.Board;
import monopoly.core.Player;

public class TestBoard {

	Board b;
	Player Ely,Gurav;

	@Before
	public void setUp() throws Exception {
		b = new Board();
		Ely = new Player("Ely", 1);
		Gurav = new Player("Gurav", 2);

		b.addPlayer(Ely.getName(),Ely.getToken());
		b.addPlayer(Gurav.getName(),Gurav.getToken());
	}

	@Test
	public void testRoll() {
		for(int i = 0; i < 100;i++){
			b.rollDice(Ely);
			assert(Ely.getLastRoll() >= 2 && Ely.getLastRoll() <= 12);
		}
	}
	@Test
	public void testPlayerCash() {
		Ely.addCash(21000);
		assert(Ely.getCash() < 20580);
		assert(Gurav.getCash() == 0);
	}

}
