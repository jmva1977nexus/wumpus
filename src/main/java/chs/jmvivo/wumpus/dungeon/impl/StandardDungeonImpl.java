package chs.jmvivo.wumpus.dungeon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import chs.jmvivo.wumpus.Configuration;
import chs.jmvivo.wumpus.Configuration.Item;
import chs.jmvivo.wumpus.Configuration.ItemType;
import chs.jmvivo.wumpus.Game;
import chs.jmvivo.wumpus.dungeon.Dungeon;
import chs.jmvivo.wumpus.dungeon.Player;
import chs.jmvivo.wumpus.dungeon.spi.DungeonForPlayer;

/**
 * Class which holds the Standard implementation of Dungeon
 * 
 * @author jmvivo
 *
 */
public class StandardDungeonImpl extends BaseDungeon<StandardCell,StandardCellContent> implements Dungeon, DungeonForPlayer<StandardCell,StandardCellContent> {

	private static final String DUNGEON_SIZE = "dungeon.size";
	private static final String DUNGEON_WUMPUS = "dungeon.wumpus";
	private static final String DUNGEON_WELLS = "dungeon.wells";
	private static final String DUNGEON_GOLD = "dungeon.gold";

	private static final String NAME = "Standard";


	private final int wells;

	private final int wumpus;

	private final int gold;

	/**
	 * @param configuration
	 */
	public StandardDungeonImpl(Configuration configuration) {

		super((Integer) configuration.get(DUNGEON_SIZE).getValue());
		this.wells = (Integer) configuration.get(DUNGEON_WELLS).getValue();
		this.wumpus = (Integer) configuration.get(DUNGEON_WUMPUS).getValue();
		this.gold = (Integer) configuration.get(DUNGEON_GOLD).getValue();

		this.cells = null;
	}

	@Override
	public void intialize(Player player) {
		super.intialize(player);

		cells[0][0].setContent(StandardCellContent.START);

		generateObjects(StandardCellContent.WELL, wells, Optional.of("Perceive a breeze"));
		generateObjects(StandardCellContent.WUMPUS_ALIVE, wumpus, Optional.of("Perceive a stench"));
		generateObjects(StandardCellContent.GOLD, gold, Optional.of("Perceive a brightness"));
	}

	/**
	 * Creates and fills the cell array. Cells will create as empty
	 */
	@Override
	protected void createEmptyCells() {
		cells = new StandardCell[size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				cells[row][col] = new StandardCell(row, col, StandardCellContent.EMPTY);
			}
		}
	}



	/**
	 * Factory of {@link StandardDungeonImpl}.
	 * 
	 * Should be register in {@link Game#registerDungeon(chs.jmvivo.wumpus.dungeon.Dungeon.DungeonFactory)}
	 * 
	 * @author jmvivo
	 *
	 */
	public static class StandardDungeonImplFactory implements DungeonFactory {

		@Override
		public String getIdentifier() {
			return NAME;
		}

		@Override
		public void registerConfiguration(Configuration config) {
			config.addItem(DUNGEON_SIZE, ItemType.INTEGER, Integer.class, 5);
			config.addItem(DUNGEON_WELLS, ItemType.INTEGER, Integer.class, 4);
			config.addItem(DUNGEON_WUMPUS, ItemType.INTEGER, Integer.class, 1);
			config.addItem(DUNGEON_GOLD, ItemType.INTEGER, Integer.class, 1);
		}

		@Override
		public List<Pair<String, Item>> validate(Configuration config) {
			List<Pair<String, Item>> problems = new ArrayList<>();

			// Check size
			Item size = config.get(DUNGEON_SIZE);
			int sizeInt = (Integer) size.getValue();
			if (sizeInt < 3) {
				problems.add(Pair.of("Too small", size));
				return problems;
			} else if (sizeInt > 20) {
				problems.add(Pair.of("Too Big", size));
				return problems;
			}
			int cells = sizeInt * sizeInt;

			// Check wells
			Item wells = config.get(DUNGEON_WELLS);
			int wellsInt = (Integer) wells.getValue();
			if (wellsInt > cells / 2) {
				problems.add(Pair.of(String.format("Too much well (try with %s)", cells / 5), wells));
			} else if (wellsInt < 0) {
				problems.add(Pair.of("Invalid well number", wells));
			}

			// Check wumpus
			Item wumpus = config.get(DUNGEON_WUMPUS);
			int wumpusInt = (Integer) wumpus.getValue();
			if (wumpusInt > cells / 4) {
				problems.add(Pair.of(String.format("Too much wumpus (try with %s)", cells / 10), wumpus));
			} else if (wumpusInt < 0) {
				problems.add(Pair.of("Invalid wumpus number", wumpus));
			}

			// Check gold
			Item gold = config.get(DUNGEON_WUMPUS);
			int goldInt = (Integer) gold.getValue();
			if (goldInt > cells / 10) {
				problems.add(Pair.of(String.format("Too much gold (try with %s)", cells / 20), wumpus));
			} else if (goldInt < 1) {
				problems.add(Pair.of("Invalid gold number", gold));
			}

			return problems;
		}

		@Override
		public Dungeon build(Configuration configuration) {
			return new StandardDungeonImpl(configuration);
		}

	}

}
