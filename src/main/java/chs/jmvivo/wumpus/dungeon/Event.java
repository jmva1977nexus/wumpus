package chs.jmvivo.wumpus.dungeon;

import java.util.List;

/**
 * @author jmvivo
 *
 */
public interface Event {
	
	
	boolean isGameFinished();
	
	boolean playerWin();
	
	boolean playerDie();
	
	List<String> getPerceptions();

}
