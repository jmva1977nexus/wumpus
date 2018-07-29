package chs.jmvivo.wumpus.dungeon.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chs.jmvivo.wumpus.dungeon.spi.CellForDungeon;

/**
 * Representation of cell
 * 
 * @author jmvivo
 */
public class StandardCell implements CellForDungeon<StandardCellContent> {

	private final int row;
	private final int col;
	private StandardCellContent content;
	private List<String> perceptions;

	StandardCell(int row, int col, StandardCellContent content) {
		this.row = row;
		this.col = col;
		this.content = content;
		this.perceptions = new ArrayList<>();
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCol() {
		// TODO Auto-generated method stub
		return col;
	}

	@Override
	public StandardCellContent getContent() {
		return content;
	}

	@Override
	public List<String> getPerceptions() {
		return Collections.unmodifiableList(perceptions);
	}

	public void addPerception(String perception) {
		this.perceptions.add(perception);
	}

	public void setContent(StandardCellContent newContent) {
		this.content = newContent;
	}

	@Override
	public boolean killContent() {
		if (content.canBeKilled()) {
			return true;
		}
		return false;
	}

}