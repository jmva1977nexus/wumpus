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

public interface Ui {

	public void askForConfiguration(Configuration configuration);

	public <F extends Factory<?>> F askForFactory(String description, Collection<F> factories);

	public void draw(Dungeon dungeon);

	public Event waitForAction(Player player);

	public interface UiFactory extends Factory<Ui> {
	}

	public void askForConfigurationItem(Item<?> item, Optional<String> aditonalMessage);

	public void showPerceptions(List<String> perceptions);

	public int askOption(String prompt, Integer defaultOption, String...options);

	public void showMessage(String string);

}
