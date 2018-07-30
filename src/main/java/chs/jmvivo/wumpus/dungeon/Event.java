package chs.jmvivo.wumpus.dungeon;

import java.util.List;

/**
 * Interface for event generated inside de dungeon
 * 
 * @author jmvivo
 *
 */
public interface Event {
	
	/**
	 * @return true if game has been finished
	 */
	boolean isGameFinished();
	
	boolean playerWin();
	
	boolean playerDie();
	
	/**
	 * @return list of messages to be show to user
	 */
	List<String> getPerceptions();

}
