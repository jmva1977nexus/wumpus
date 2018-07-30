/**
 * 
 */
package chs.jmvivo.wumpus.dungeon.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import chs.jmvivo.wumpus.dungeon.Event;

/**
 * Default implementation of Event
 * 
 * @author jmvivo
 *
 */
public class DefaultEvent implements Event {

	private final boolean gameFinished;
	private final boolean win;
	private final boolean die;
	private final List<String> perceptions;

	/**
	 * Create an event when player die
	 * 
	 * @param message
	 * @return
	 */
	public static DefaultEvent createPlayerDie(String message) {
		return new DefaultEvent(true, false, true, message);
	}

	/**
	 * 
	 * Create an event when player win
	 * 
	 * @param message
	 * @return
	 */
	public static DefaultEvent createPlayerWin(String message) {
		return new DefaultEvent(true, true, false, message);
	}

	/**
	 * Create an event when nothing happens
	 * 
	 * @return
	 */
	public static DefaultEvent createNothingHappends() {
		return new DefaultEvent(false, false, false);
	}

	/**
	 * Create an event when message should be notify
	 * @param message
	 * @return
	 */
	public static DefaultEvent createMessage(String message) {
		return new DefaultEvent(false, false, false, message);
	}

	/**
	 * Create an event when perceptions found
	 * 
	 * @param perceptions
	 * @return
	 */
	public static DefaultEvent createPerceptions(List<String> perceptions) {
		return new DefaultEvent(false, false, false, perceptions);
	}

	/**
	 * Constructor without perceptions
	 * 
	 * @param gameFinished
	 * @param win
	 * @param die
	 */
	public DefaultEvent(boolean gameFinished, boolean win, boolean die) {
		this(gameFinished, win, die, Collections.emptyList());
	}

	/**
	 * Constructor 
	 * 
	 * @param gameFinished
	 * @param win
	 * @param die
	 * @param perceptions
	 */
	public DefaultEvent(boolean gameFinished, boolean win, boolean die, String... perceptions) {
		this(gameFinished, win, die, Arrays.asList(perceptions));
	}

	/** 
	 * Default constructor
	 * 
	 * @param gameFinished
	 * @param win
	 * @param die
	 * @param perceptions
	 */
	public DefaultEvent(boolean gameFinished, boolean win, boolean die, List<String> perceptions) {
		this.gameFinished = gameFinished;
		this.win = win;
		this.die = die;
		this.perceptions = new ArrayList<>(perceptions);
	}

	@Override
	public boolean isGameFinished() {
		return gameFinished;
	}

	@Override
	public boolean playerWin() {
		return win;
	}

	@Override
	public boolean playerDie() {
		return die;
	}

	@Override
	public List<String> getPerceptions() {
		return Collections.unmodifiableList(perceptions);
	}

	public void addPerceptions(List<String> perceptions) {
		if (CollectionUtils.isNotEmpty(perceptions)) {
			this.perceptions.addAll(perceptions);
		}
	}

	public void addPerception(String perception) {
		this.perceptions.add(perception);
	}

}
