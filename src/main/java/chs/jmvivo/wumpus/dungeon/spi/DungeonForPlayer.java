package chs.jmvivo.wumpus.dungeon.spi;

import chs.jmvivo.wumpus.dungeon.CellContent;
import chs.jmvivo.wumpus.dungeon.Dungeon;

public interface DungeonForPlayer<CELL extends CellForDungeon<CONTENT>, CONTENT extends CellContent> extends Dungeon{
	
	CELL getCell(int row, int col);
	

}
