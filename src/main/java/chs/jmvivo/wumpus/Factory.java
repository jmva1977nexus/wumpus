package chs.jmvivo.wumpus;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import chs.jmvivo.wumpus.Configuration.Item;

/**
 * Factory interface.
 * 
 * This allow to configurable elements of game to be extensible. Also to provide
 * different implementations and configuration options.
 * 
 * @author jmvivo
 *
 * @param <T>
 */
public interface Factory<T> {
	
	/**
	 * @return asset identifier
	 */
	String getIdentifier();

	/**
	 * Register configuration needed for asset
	 * 
	 * @param config
	 * @see Configuration#addItem(String, chs.jmvivo.wumpus.Configuration.ItemType, Class, Object)
	 */
	void registerConfiguration(Configuration config);

	/**
	 * Validate configuration items already registered and set the values by user.
	 * 
	 * Return the problems from as allow user to change it.
	 * 
	 * @param config
	 * @return a list of Pairs with key a message of problem found and related item 
	 */
	List<Pair<String, Item>> validate(Configuration config);

	/** 
	 * Build the asset using configuration values
	 * 
	 * @param config
	 * @return
	 */
	T build(Configuration config);

}
