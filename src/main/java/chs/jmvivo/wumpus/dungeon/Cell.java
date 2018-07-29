package chs.jmvivo.wumpus.dungeon;

import java.util.List;

public interface Cell<CONTENT extends CellContent> {
	
	int getRow();
	
	int getCol();
	
	CONTENT getContent();
	
	List<String> getPerceptions();

	boolean killContent();

}
