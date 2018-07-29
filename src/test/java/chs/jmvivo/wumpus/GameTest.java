package chs.jmvivo.wumpus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class GameTest {

	@Test
	public void testApp() {
		Game game = new Game();
		assertThat(game).isNotNull();
	}
}
