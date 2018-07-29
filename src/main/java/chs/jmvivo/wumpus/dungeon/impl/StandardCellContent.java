package chs.jmvivo.wumpus.dungeon.impl;

import chs.jmvivo.wumpus.dungeon.CellContent;

public enum StandardCellContent implements CellContent {
	// @formatter:off
	EMPTY(false, false, false, null, null, null, null, null), 
	START(false, false, false, null, null, null, null, null), 
	WELL(true, false, false, "You fall into a well!!!!", null, null, null, null), 
	WUMPUS_DEAD(false, false, false, null, null, null, null, null), 
	WUMPUS_ALIVE(true, false, true, "The Wumpus kill you!!!", null, 
			"You kill the Wumpus!", "The Wumpus survived!!", WUMPUS_DEAD), 
	GOLD(false, false, true, null, "You found the GOLD!!", null, null, null);
    // @formatter:on

	private final boolean mortal;

	private final boolean win;

	private final boolean canBeKilled;

	private String dieMessage;

	private String winMessage;

	private String killedContentMessage;

	private String missKilledContentMessage;

	private StandardCellContent killedConent;

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
		return null;
	}

	@Override
	public String getWinMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKilledContentMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMissKilledContentMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}