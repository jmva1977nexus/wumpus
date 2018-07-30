package chs.jmvivo.wumpus.dungeon;

import java.util.List;

import chs.jmvivo.wumpus.Factory;

/**
 * Interface for Player
 * 
 * @author jmvivo
 *
 */
public interface Player {
	
	/**
	 * Execute an action
	 * 
	 * @param action
	 * @return result event
	 * @see #getAvailableActions()
	 */
	public Event doAnAction(Action action);

	/**
	 * @return current dungeon
	 */
	public Dungeon getDungeon();

	/**
	 * Sets the dungeon
	 * 
	 * @param dungeon
	 */
	public void setDungeon(Dungeon dungeon);

	/**
	 * @return actions which user can perform with player
	 * @see #doAnAction(Action)
	 */
	public List<Action> getAvailableActions();

	/**
	 * Player factory
	 * 
	 * @author jmvivo
	 *
	 */
	public interface PlayerFactory extends Factory<Player> {
	}

	/**
	 * Initialize player in dungeon
	 * 
	 * @return Event result
	 */
	public Event intialize();

	/**
	 * Interface for Player action
	 * 
	 * @author jmvivo
	 *
	 */
	public interface Action {
		public String getName();
	}

}
