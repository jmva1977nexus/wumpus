package chs.jmvivo.wumpus.dungeon.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import chs.jmvivo.wumpus.Configuration;
import chs.jmvivo.wumpus.Configuration.Item;
import chs.jmvivo.wumpus.Configuration.ItemType;
import chs.jmvivo.wumpus.dungeon.Cell;
import chs.jmvivo.wumpus.dungeon.CellContent;
import chs.jmvivo.wumpus.dungeon.Dungeon;
import chs.jmvivo.wumpus.dungeon.Event;
import chs.jmvivo.wumpus.dungeon.Player;
import chs.jmvivo.wumpus.dungeon.spi.CellForDungeon;
import chs.jmvivo.wumpus.dungeon.spi.DungeonForPlayer;
import chs.jmvivo.wumpus.dungeon.spi.Orientation;

/**
 * Standard implementation of Player
 * 
 * @author jmvivo
 *
 */
public class StandardPlayer implements Player {

	public static final String NAME = "Standar";
	public static final String PLAYER_ARROWS = "player.arrows";

	private enum PlayerAction implements Action {
		ADVANCE("Advance"), TURN_LEFT("Turn left"), TURN_RIGHT("Turn right"), THROW_ARROW("Throw an arrow"), EXIT(
				"Exit of dungeon");

		private final String description;

		private PlayerAction(String description) {
			this.description = description;
		}

		@Override
		public String getName() {
			return description;
		}
	}

	private DungeonForPlayer<?, ?> dungeon;

	private int maxArrows = 0;

	private int currentArrows = 0;

	private Orientation orientation = Orientation.NORTH;

	private int row = 0;

	private int col = 0;

	public StandardPlayer(Configuration config) {
		this.maxArrows = (Integer) config.get(PLAYER_ARROWS).getValue();
	}

	@Override
	public Event doAaction(Action action) {
		if (!getAvailableActions().contains(action)) {
			throw new IllegalArgumentException("Unsupported Action: " + action.toString());
		}
		PlayerAction pAction = (PlayerAction) action;
		switch (pAction) {
		case ADVANCE:
			return advance();

		case TURN_LEFT:
			return turnLeft();

		case TURN_RIGHT:
			return turnRight();

		case THROW_ARROW:
			return throwArrow();

		case EXIT:
			return exitFromDungeon();

		default:
			throw new IllegalArgumentException("Unimplemented Action: " + pAction);
		}
	}

	private Event exitFromDungeon() {
		return new DefaultEvent(true, false, false);
	}

	private Event throwArrow() {
		// decrease current arrows count
		currentArrows--;
		
		// Get the next cell in front of player
		int advance = 1;
		Cell nextCell = getCellInFont(advance);
		// Until arrows touch a wall (cell = null) or found a "killable" content
		while (nextCell != null) {
			CellContent content = nextCell.getContent();
			if (content.canBeKilled()) {
				// Try to kill cell content
				if (nextCell.killContent()) {
					// Content killed
					return DefaultEvent.createMessage(content.getKilledContentMessage());
				} else {
					// Miss the shot
					return DefaultEvent.createMessage(content.getMissKilledContentMessage());
				}
			}
			advance++;
			nextCell = getCellInFont(advance);
		}
		// Arrows found the wall
		return DefaultEvent.createMessage("The arrow broke against the wall");
	}

	private Event turnRight() {
		switch (orientation) {
		case NORTH:
			orientation = Orientation.EAST;
			break;
		case SOUTH:
			orientation = Orientation.WEST;
			break;
		case EAST:
			orientation = Orientation.SOUTH;
			break;
		case WEST:
			orientation = Orientation.NORTH;
			break;
		default:
			break;
		}

		return getPerceptions(getCurrentCell().getPerceptions());
	}

	private Event turnLeft() {
		switch (orientation) {
		case NORTH:
			orientation = Orientation.WEST;
			break;
		case SOUTH:
			orientation = Orientation.EAST;
			break;
		case EAST:
			orientation = Orientation.NORTH;
			break;
		case WEST:
			orientation = Orientation.SOUTH;
			break;
		default:
			break;
		}
		
		return getPerceptions(getCurrentCell().getPerceptions());
	}

	private Event advance() {
		// Get the next Cell
		Cell nextCell = getCellInFont();
		if (nextCell == null) {
			throw new IllegalStateException("Found a wall when advance"); 
		}
		this.row = nextCell.getRow();
		this.col = nextCell.getCol();

		// Check the cell content
		CellContent content = nextCell.getContent();

		if (content.isMortal()) {
			// Player die
			return DefaultEvent.createPlayerDie(content.getDieMessage());
		} else if (content.isWinCondition()) {
			// Player win
			return DefaultEvent.createPlayerWin(content.getWinMessage());
		}
		// Return perception
		return getPerceptions(nextCell.getPerceptions());
	}

	private Event getPerceptions(List<String> perceptions) {
		DefaultEvent event = DefaultEvent.createPerceptions(perceptions);
		if (isExitAhead()) {
			event.addPerception("The dungeon exit is in front of you");
		} else if (getCellInFont() == null) {
			// Inform that a wall found
			event.addPerception("You found a wall in front of you");
		}
		return event;
	}

	@Override
	public Dungeon getDungeon() {
		return dungeon;
	}

	@Override
	public void setDungeon(Dungeon dungeon) {
		this.dungeon = (DungeonForPlayer<?, ?>) dungeon;
	}

	@Override
	public List<Action> getAvailableActions() {
		List<Action> available = new ArrayList<>();
		if (getCellInFont() != null) {
			available.add(PlayerAction.ADVANCE);
		}
		if (isExitAhead()) {
			available.add(PlayerAction.EXIT);
		}
		available.add(PlayerAction.TURN_LEFT);
		available.add(PlayerAction.TURN_RIGHT);

		if (currentArrows > 0) {
			available.add(PlayerAction.THROW_ARROW);
		}

		return available;
	}

	private boolean isExitAhead() {
		return row == 0 && col == 0 && orientation == Orientation.SOUTH;
	}

	/**
	 * Return the "advance" cell from current position and orientation
	 * 
	 * @param advance
	 * @return
	 */
	private Cell getCellInFont(int advance) {
		return dungeon.getCell(row + (orientation.getRowModifier() * advance),
				col + (orientation.getColModifier() * advance));
	}

	/**
	 * Return next cell from current position and orientation
	 * 
	 * @return
	 */
	private Cell getCellInFont() {
		return getCellInFont(1);
	}
	
	private Cell getCurrentCell() {
		return dungeon.getCell(row, col);
	}

	@Override
	public Event intialize() {
		this.currentArrows = maxArrows;
		this.orientation = Orientation.NORTH;
		this.row = 0;
		this.col = 0;
		CellForDungeon<?> cell = dungeon.getCell(0, 0);
		DefaultEvent event = DefaultEvent.createMessage("You are inside of the dungeon");
		event.addPerceptions(cell.getPerceptions());
		return event;
	}

	public static class StandardPlayerFactory implements PlayerFactory {

		@Override
		public String getIdentifier() {
			return NAME;
		}

		@Override
		public void registerConfiguration(Configuration config) {
			config.addItem(PLAYER_ARROWS, ItemType.INTEGER, Integer.class, 3);
		}

		@Override
		public List<Pair<String, Item>> validate(Configuration config) {
			List<Pair<String, Item>> problems = new ArrayList<>();
			// Check arrows
			Item arrows = config.get(PLAYER_ARROWS);
			int arrowsInt = (Integer) arrows.getValue();
			if (arrowsInt < 0) {
				problems.add(Pair.of("Invalid arrows number", arrows));
			}
			return problems;
		}

		@Override
		public Player build(Configuration config) {
			return new StandardPlayer(config);
		}

	}
}
