package chs.jmvivo.wumpus.dungeon.spi;

import chs.jmvivo.wumpus.dungeon.CellContent;
import chs.jmvivo.wumpus.dungeon.Dungeon;
import chs.jmvivo.wumpus.dungeon.Player;

/**
 * Extends {@link Dungeon} to add SPI for {@link Player} implementations
 * @author jmvivo
 *
 * @param <CELL>
 * @param <CONTENT>
 */
public interface DungeonForPlayer<CELL extends CellForDungeon<CONTENT>, CONTENT extends CellContent> extends Dungeon{
	
	CELL getCell(int row, int col);
	

}
