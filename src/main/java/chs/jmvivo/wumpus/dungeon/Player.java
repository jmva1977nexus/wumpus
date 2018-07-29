package chs.jmvivo.wumpus.dungeon;

import java.util.List;

import chs.jmvivo.wumpus.Factory;

public interface Player {
	
	public static enum Status {ALIVE,DEAD,WIN };

	public Event doAaction(Action action);

	public Dungeon getDungeon();

	public void setDungeon(Dungeon dungeon);

	public List<Action> getAvailableActions();

	public interface PlayerFactory extends Factory<Player> {
	}

	public Event intialize();

	public interface Action {
		public String getName();
	}

}
