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
 * @author jmvivo
 *
 */
public class DefaultEvent implements Event {

	private final boolean gameFinished;
	private final boolean win;
	private final boolean die;
	private final List<String> perceptions;

	public static DefaultEvent createPlayerDie(String message) {
		return new DefaultEvent(true, false, true, message);
	}

	public static DefaultEvent createPlayerWin(String message) {
		return new DefaultEvent(true, true, false, message);
	}

	public static DefaultEvent createNothingHappends() {
		return new DefaultEvent(false, false, false);
	}

	public static DefaultEvent createMessage(String message) {
		return new DefaultEvent(false, false, false, message);
	}

	public static DefaultEvent createPerceptions(List<String> perceptions) {
		return new DefaultEvent(false, false, false, perceptions);
	}

	public DefaultEvent(boolean gameFinished, boolean win, boolean die) {
		this(gameFinished, win, die, Collections.emptyList());
	}

	public DefaultEvent(boolean gameFinished, boolean win, boolean die, String... perceptions) {
		this(gameFinished, win, die, Arrays.asList(perceptions));
	}

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
