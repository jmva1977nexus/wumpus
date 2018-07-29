package chs.jmvivo.wumpus;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import chs.jmvivo.wumpus.Configuration.Item;

/**
 * Factory interface.
 * 
 * This allow to configurable elements of game to be extensible. Also to provide
 * diferents implementations
 * 
 * @author jmvivo
 *
 * @param <T>
 */
public interface Factory<T> {
	String getIdentifier();

	void registerConfiguration(Configuration config);

	List<Pair<String, Item>> validate(Configuration config);

	T build(Configuration config);

}
