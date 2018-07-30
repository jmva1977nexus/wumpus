package chs.jmvivo.wumpus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import chs.jmvivo.wumpus.dungeon.Dungeon;
import chs.jmvivo.wumpus.dungeon.Event;
import chs.jmvivo.wumpus.dungeon.Player;
import chs.jmvivo.wumpus.ui.Ui;

/**
 * Unit test for {@link Game} class
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {
	
	@Mock
	private Ui ui;
	
	@Mock
	private Dungeon dungeon;
	
	@Mock
	private Player player;
	
	@Mock
	private Configuration configuration;
	
	private Game target;
	
	@Before
	public void before(){
		target = new Game(ui, dungeon, player, configuration);
	}

	@Test(expected=IllegalStateException.class)
	public void testAlreadyConfigured() {
		target.configure();
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testPlayNotConfigured() {
		// prepare
		when(configuration.isDone()).thenReturn(false);
		
		// exec
		target.play();
	}
	
	@Test
	public void testPlayBaseWin() {
		// prepare
		Event emptyEvent = createEmptyEvent();
		Event winEvent = createWinEvent();
		when(configuration.isDone()).thenReturn(true);
		when(player.intialize()).thenReturn(emptyEvent);
		when(ui.waitForAction(player)).thenReturn(emptyEvent, winEvent);
		when(ui.askOption(any(String.class), eq(1), any(String.class), any(String.class))).thenReturn(1);
		
		// exec
		target.play();
		
		// verify
		
		verify(dungeon).intialize(player);
		verify(player).intialize();
		verify(ui, times(2)).waitForAction(player);
		verify(ui).showMessage("You WIN!!!");
		verify(ui).askOption(any(String.class), eq(1), any(String.class), any(String.class));
		
	}

	@Test
	public void testPlayBaseDie() {
		// prepare
		Event emptyEvent = createEmptyEvent();
		Event dieEvent = createDieEvent();
		when(configuration.isDone()).thenReturn(true);
		when(player.intialize()).thenReturn(emptyEvent);
		when(ui.waitForAction(player)).thenReturn(emptyEvent, emptyEvent, dieEvent);
		when(ui.askOption(any(String.class), eq(1), any(String.class), any(String.class))).thenReturn(1);
		
		// exec
		target.play();
		
		// verify
		
		verify(dungeon).intialize(player);
		verify(player).intialize();
		verify(ui, times(3)).waitForAction(player);
		verify(ui).showMessage("You die!!!");
		verify(ui).showMessage("GAME OVER");
		verify(ui).askOption(any(String.class), eq(1), any(String.class), any(String.class));
		
	}
	
	

	@Test
	public void testPlayRestart() {
		// prepare
		Event emptyEvent = createEmptyEvent();
		Event dieEvent = createDieEvent();
		when(configuration.isDone()).thenReturn(true);
		when(player.intialize()).thenReturn(emptyEvent,emptyEvent);
		when(ui.waitForAction(player)).thenReturn(emptyEvent, emptyEvent, dieEvent,dieEvent);
		when(ui.askOption(any(String.class), eq(1), any(String.class), any(String.class))).thenReturn(0,1);
		
		// exec
		target.play();
		
		// verify
		
		verify(dungeon, times(2)).intialize(player);
		verify(player, times(2)).intialize();
		verify(ui, times(4)).waitForAction(player);
		verify(ui, times(2)).showMessage("You die!!!");
		verify(ui, times(2)).showMessage("GAME OVER");
		verify(ui, times(2)).askOption(any(String.class), eq(1), any(String.class), any(String.class));
		
	}
	
	/**
	 * @return new instance of Win event
	 */
	private Event createDieEvent() {
		return new Event() {
			
			@Override
			public boolean playerWin() {
				return false;
			}
			
			@Override
			public boolean playerDie() {
				return true;
			}
			
			@Override
			public boolean isGameFinished() {
				return true;
			}
			
			@Override
			public List<String> getPerceptions() {
				return null;
			}
		};
	}
	

	/**
	 * @return new instance of Win event
	 */
	private Event createWinEvent() {
		return new Event() {
			
			@Override
			public boolean playerWin() {
				return true;
			}
			
			@Override
			public boolean playerDie() {
				return false;
			}
			
			@Override
			public boolean isGameFinished() {
				return true;
			}
			
			@Override
			public List<String> getPerceptions() {
				return null;
			}
		};
	}

	/**
	 * @return a new instance of empty event
	 */
	private Event createEmptyEvent() {
		return new Event() {
			
			@Override
			public boolean playerWin() {
				return false;
			}
			
			@Override
			public boolean playerDie() {
				return false;
			}
			
			@Override
			public boolean isGameFinished() {
				return false;
			}
			
			@Override
			public List<String> getPerceptions() {
				return null;
			}
		};
	}
	
}
