package chs.jmvivo.wumpus.dungeon.impl;

import chs.jmvivo.wumpus.dungeon.CellContent;

/**
 * Standard game cell content:
 * 
 * Wumpus, Well and Gold
 * 
 * @author jmvivo
 *
 */
public enum StandardCellContent implements CellContent {
	// @formatter:off
	EMPTY(), START(), WELL(true, false, false, "You fall into a well!!!!", null), WUMPUS_DEAD(), WUMPUS_ALIVE(
			"The Wumpus kill you!!!", "You kill the Wumpus!", "The Wumpus survived!!",
			WUMPUS_DEAD), GOLD(false, true, false, null, "You found the GOLD!!");
	// @formatter:on

	private final boolean mortal;

	private final boolean win;

	private final boolean canBeKilled;

	private String dieMessage;

	private String winMessage;

	private String killedContentMessage;

	private String missKilledContentMessage;

	private StandardCellContent killedConent;

	private StandardCellContent() {
		this(false, false, false, null, null, null, null, null);
	}

	private StandardCellContent(boolean mortal, boolean win, boolean canBeKilled, String dieMessage,
			String winMessage) {
		this(mortal, win, false, dieMessage, winMessage, null, null, null);
	}

	private StandardCellContent(String dieMessage, String killedContentMessage, String missKilledContentMessage,
			StandardCellContent killedContent) {
		this(true, false, true, dieMessage, null, killedContentMessage, missKilledContentMessage, killedContent);
	}

	private StandardCellContent(boolean mortal, boolean win, boolean canBeKilled, String dieMessage, String winMessage,
			String killedContentMessage, String missKilledContentMessage, StandardCellContent killedContent) {
		this.mortal = mortal;
		this.win = win;
		this.canBeKilled = canBeKilled;
		this.dieMessage = dieMessage;
		this.winMessage = winMessage;
		this.killedContentMessage = killedContentMessage;
		this.missKilledContentMessage = missKilledContentMessage;
		if (canBeKilled) {
			this.killedConent = killedContent;
		} else {
			this.killedConent = this;
		}
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public boolean isMortal() {
		return mortal;
	}

	@Override
	public boolean isWinCondition() {
		return win;
	}

	@Override
	public boolean canBeKilled() {
		return canBeKilled;
	}

	@Override
	public String getDieMessage() {
		return dieMessage;
	}

	@Override
	public String getWinMessage() {
		return winMessage;
	}

	@Override
	public String getKilledContentMessage() {
		return killedContentMessage;
	}

	@Override
	public String getMissKilledContentMessage() {
		return missKilledContentMessage;
	}

	@Override
	public CellContent getKilledContent() {
		return killedConent;
	}

}