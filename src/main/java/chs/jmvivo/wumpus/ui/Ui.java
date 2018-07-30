package chs.jmvivo.wumpus.ui;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import chs.jmvivo.wumpus.Configuration;
import chs.jmvivo.wumpus.Configuration.Item;
import chs.jmvivo.wumpus.Factory;
import chs.jmvivo.wumpus.dungeon.Dungeon;
import chs.jmvivo.wumpus.dungeon.Event;
import chs.jmvivo.wumpus.dungeon.Player;

/**
 * Interface to implement an UI for the game
 * 
 * @author jmvivo
 *
 */
public interface Ui {

	/**
	 * Ask to user for a list of values of configuration
	 * 
	 * @param configuration
	 */
	public void askForConfiguration(Configuration configuration);

	/**
	 * Ask to user which implementation to use
	 * 
	 * @param description of implementation to select
	 * @param factories available to select
	 * @return selected implementation
	 */
	public <F extends Factory<?>> F askForFactory(String description, Collection<F> factories);

	/**
	 * Wait to user action
	 * 
	 * @param player
	 * @return
	 */
	public Event waitForAction(Player player);


	/**
	 * Ask user for a configuration
	 * 
	 * @param item
	 * @param aditonalMessage if there is any additional information about asked value
	 */
	public void askForConfigurationItem(Item<?> item, Optional<String> aditonalMessage);

	/**
	 * Shows perceptions found by player
	 * 
	 * @param perceptions
	 */
	public void showPerceptions(List<String> perceptions);

	/**
	 * Ask to user for an option
	 * 
	 * @param prompt
	 * @param defaultOption
	 * @param options
	 * @return
	 */
	public int askOption(String prompt, Integer defaultOption, String...options);

	/**
	 * Show a message to user
	 * 
	 * @param string
	 */
	public void showMessage(String string);

	/**
	 * Initializes UI
	 */
	public void intialize();

	/**
	 * Shutdown 
	 */
	public void shutdown();

	/**
	 * UI factory
	 * 
	 * @author jmvivo
	 *
	 */
	public interface UiFactory extends Factory<Ui> {
	}
}
