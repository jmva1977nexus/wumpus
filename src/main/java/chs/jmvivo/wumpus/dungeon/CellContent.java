package chs.jmvivo.wumpus.dungeon;

/**
 * Interface for cell content
 * 
 * @author jmvivo
 *
 */
public interface CellContent {
	
	
	/**
	 * @return description of the content
	 */
	String getName();
	
	/**
	 * @return true if content is mortal for player (player dies if go into a
	 *         cell with this content)
	 */
	boolean isMortal();
	
	/**
	 * @return message to show when player die by this content
	 */
	String getDieMessage();

	/**
	 * @return true if content makes player wins the game
	 */
	boolean isWinCondition();
	
	
	/**
	 * @return message to show when player win by this content
	 */
	String getWinMessage();

	/**
	 * @return true if content can be Killed by player
	 */
	boolean canBeKilled();
	
	/**
	 * @return message to show when player kill the content
	 */
	String getKilledContentMessage();

	/**
	 * @return message to show when player miss the shot
	 */
	String getMissKilledContentMessage();
	
	
	/**
	 * @return content to set when player kill this content
	 */
	CellContent getKilledContent();

}
