package chs.jmvivo.wumpus.dungeon;

/**
 * Interface for cell content
 * 
 * @author jmvivo
 *
 */
public interface CellContent {

	/**
	 * @return true if content is mortal for player (player dies if go into a
	 *         cell with this content)
	 */
	boolean isMortal();
	
	String getDieMessage();

	/**
	 * @return true if content makes player wins the game
	 */
	boolean isWinCondition();
	
	
	/**
	 * 
	 * @return
	 */
	String getWinMessage();

	/**
	 * @return true if content can be Killed by player
	 */
	boolean canBeKilled();
	
	/**
	 * @return
	 */
	String getKilledContentMessage();

	/**
	 * 
	 * @return
	 */
	String getMissKilledContentMessage();

}
