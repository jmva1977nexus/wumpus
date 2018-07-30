package chs.jmvivo.wumpus.dungeon;

import java.util.List;

/**
 * Interface for a cell of the dungeon
 * 
 * @author jmvivo
 *
 * @param <CONTENT>
 */
public interface Cell<CONTENT extends CellContent> {
	
	/**
	 * @return cell row
	 */
	int getRow();
	
	/**
	 * @return cell column
	 */
	int getCol();
	
	/**
	 * @return content of the cell
	 */
	CONTENT getContent();
	
	/**
	 * @return list of messages to be show when player go into the cell
	 */
	List<String> getPerceptions();

	/**
	 * @return notify that player has killed the content (only if {@link CellContent#canBeKilled()})
	 */
	boolean killContent();

}
