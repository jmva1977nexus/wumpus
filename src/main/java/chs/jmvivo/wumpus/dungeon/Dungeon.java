package chs.jmvivo.wumpus.dungeon;

import chs.jmvivo.wumpus.Factory;

/**
 * Dungeon Interface
 * 
 * @author jmvivo
 *
 */
public interface Dungeon {
	
	
	/**
	 * Initializes dungeon for player.
	 * 
	 * 
	 * @param player
	 */
	public void intialize(Player player);
	
	/**
	 * @return current player
	 */
	public Player getPlayer();
	

	/**
	 * Factory class for dungeon
	 * 
	 * @author jmvivo
	 *
	 */
	public interface DungeonFactory extends Factory<Dungeon> {

	}

}
