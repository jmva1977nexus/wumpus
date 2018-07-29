package chs.jmvivo.wumpus.dungeon.spi;

public enum Orientation {
	NORTH (+1,0), SOUTH(-1,0), EAST(0,+1), WEST(0,-1);
	
	private final int rowModifier;
	private final int colModifier;

	private Orientation(int rowModifier, int colModifier) {
		this.rowModifier = rowModifier;
		this.colModifier = colModifier;
	}
	
	public int getRowModifier(){
		return rowModifier;
	}
	
	public int getColModifier() {
		return colModifier;
	}
}
