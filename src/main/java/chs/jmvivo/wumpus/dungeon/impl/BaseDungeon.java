/**
 * 
 */
package chs.jmvivo.wumpus.dungeon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chs.jmvivo.wumpus.dungeon.CellContent;
import chs.jmvivo.wumpus.dungeon.Player;
import chs.jmvivo.wumpus.dungeon.spi.CellForDungeon;
import chs.jmvivo.wumpus.dungeon.spi.DungeonForPlayer;

/**
 * Base class for Dungeons
 * 
 * @author jmvivo
 *
 */
public abstract class BaseDungeon<CELL extends CellForDungeon<CONTENT>, CONTENT extends CellContent>
		implements DungeonForPlayer<CELL, CONTENT> {

	private static final Logger LOG = LoggerFactory.getLogger(BaseDungeon.class);

	protected final int size;

	private Player player;
	
	protected CELL[][] cells;

	/**
	 * Constructor
	 * 
	 * @param size
	 */
	protected BaseDungeon(int size) {
		this.size = size;
		LOG.trace("Dungeon size {}", size);
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	/**
	 * Creates the cells and fill its as "empty"
	 */
	protected abstract void createEmptyCells();

	@Override
	public void intialize(Player player) {
		this.player = player;
		this.player.setDungeon(this);

		createEmptyCells();
	}

	/**
	 * Fills "number" cells with "type" content
	 * 
	 * @param type
	 * @param number
	 * @param perception
	 *            if is present add this perception to relative cells
	 */
	protected void generateObjects(CONTENT type, final int number, Optional<String> perception) {
		Random rnd = new Random(System.currentTimeMillis());
		LOG.debug("Generating Objects: {} [{}]", type.getName(), number);
		int count = 0;
		while (count < number) {
			int row = rnd.nextInt(size);
			int col = rnd.nextInt(size);

			CELL cell = cells[row][col];
			if (cell.getContent() != StandardCellContent.EMPTY) {
				// already something there
				continue;
			}
			cell.setContent(type);
			LOG.debug("Cell[{},{}} <- {}", cell.getRow(), cell.getCol(), type.getName());

			if (perception.isPresent()) {
				addPerceptionForRelative(cell, perception.get());
			}
			count++;
		}
	}

	/**
	 * Get a cell by coordinates
	 * 
	 * @param row
	 * @param col
	 * @return the cell or null if not exists
	 */
	public CELL getCell(int row, int col) {
		if (row < 0 || row >= size) {
			return null;
		} else if (col < 0 || col >= size) {
			return null;
		}
		return cells[row][col];
	}

	/**
	 * Add perceptions to empty relative cells.
	 * 
	 * Relative cell are locate at north, south, east and west of referenced
	 * cell.
	 * 
	 * @param cell
	 * @param perception
	 */
	protected void addPerceptionForRelative(CELL cell, String perception) {
		List<CELL> relatives = new ArrayList<>();
		relatives.add(getCell(cell.getRow() + 1, cell.getCol()));
		relatives.add(getCell(cell.getRow() - 1, cell.getCol()));
		relatives.add(getCell(cell.getRow(), cell.getCol() + 1));
		relatives.add(getCell(cell.getRow(), cell.getCol() - 1));
		relatives.stream().filter(c -> c != null).forEach(c -> {
			c.addPerception(perception);
			LOG.trace("Cell[{},{}].perception += {}", c.getRow(), c.getCol(), perception);
		});
	}

}
