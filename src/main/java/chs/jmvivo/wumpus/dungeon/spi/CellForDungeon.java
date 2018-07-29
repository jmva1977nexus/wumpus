package chs.jmvivo.wumpus.dungeon.spi;

import chs.jmvivo.wumpus.dungeon.Cell;
import chs.jmvivo.wumpus.dungeon.CellContent;

/**
 * Extends {@link Cell} to add SPI required for Dungeon instances
 * 
 * @author jmvivo
 *
 */
public interface CellForDungeon<CONTENT extends CellContent> extends Cell<CONTENT> {
	
	
	/**
	 * Set de cell content
	 * 
	 * @param content
	 */
	void setContent(CONTENT content);
	
	void addPerception(String perception);

}
