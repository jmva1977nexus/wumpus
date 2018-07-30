package chs.jmvivo.wumpus.ui.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import chs.jmvivo.wumpus.Configuration;
import chs.jmvivo.wumpus.Configuration.Item;
import chs.jmvivo.wumpus.Factory;
import chs.jmvivo.wumpus.dungeon.Dungeon;
import chs.jmvivo.wumpus.dungeon.Event;
import chs.jmvivo.wumpus.dungeon.Player;
import chs.jmvivo.wumpus.dungeon.Player.Action;
import chs.jmvivo.wumpus.ui.Ui;

/**
 * Simple {@link Ui} implementation
 * 
 * Use console for input output
 * 
 * @author jmvivo
 *
 */
public class SimpleUI implements Ui {

	private Scanner in;

	public SimpleUI() {
		in = new Scanner(System.in);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chs.jmvivo.wumpus.ui.Ui#askForConfiguration(chs.jmvivo.wumpus.
	 * Configuration)
	 */
	@Override
	public void askForConfiguration(Configuration configuration) {
		configuration.forEach((name, item) -> askForConfigurationItem(item, Optional.empty()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * chs.jmvivo.wumpus.ui.Ui#waitForAction(chs.jmvivo.wumpus.dungeon.Player)
	 */
	@Override
	public Event waitForAction(Player player) {
		print("What do you want to do?");

		List<Action> actions = player.getAvailableActions();

		for (int i = 0; i < actions.size(); i++) {
			Action action = actions.get(i);
			print(String.format("%s. %s", i, action.getName()));
		}

		int select = askForInt("Select the number of action: ", 0, actions.size() - 1);

		return player.doAnAction(actions.get(select));
	}

	private int askForInt(String prompt, int min, int max) {
		return askForInt(prompt, min, max, null);
	}

	/**
	 * Ask for a integer to user from command line
	 * 
	 * @param prompt
	 * @param min
	 * @param max
	 * @param defaultValue
	 * @return
	 */
	private int askForInt(String prompt, Integer min, Integer max, Integer defaultValue) {
		Integer value = null;
		do {
			if (defaultValue == null) {
				print(prompt);

			} else {
				print(String.format(" %s (default: %s)", prompt, defaultValue));
			}
			String input = in.nextLine();
			if (StringUtils.isBlank(input) && defaultValue != null) {
				return defaultValue.intValue();
			}
			try {
				value = Integer.valueOf(input);
			} catch (NumberFormatException ex) {
				print(String.format("Invalid value for numer '%s'", input));
			}
			if (value != null) {
				if (min != null && value.intValue() < min) {
					print(String.format("Must be greater or equal than %s ", min));
					value = null;
				} else if (max != null && value.intValue() > max) {
					print(String.format("Must be lower or equal %s", max));
					value = null;
				}
			}
		} while (value == null);
		return value;

	}

	/**
	 * Shows a message in command line
	 * 
	 * @param string
	 */
	private void print(String string) {
		System.out.println(string);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chs.jmvivo.wumpus.ui.Ui#askForFactory(java.lang.String,
	 * java.util.Collection)
	 */
	@Override
	public <F extends Factory<?>> F askForFactory(String description, Collection<F> factories) {
		String prompt = String.format("What kind of %s do you want to use to play?", description);

		List<F> factoryList = new ArrayList<>(factories);

		int selected = askOption(prompt, null,
				factoryList.stream().map(F::getIdentifier).collect(Collectors.toList()).toArray(new String[0]));

		// for (int i = 0; i < factoryList.size(); i++) {
		// F factory = factoryList.get(i);
		// print(String.format("%s. %s", i, factory.getIdentifier()));
		// }
		//
		// int select = askForInt("Select the number of action: ", 0,
		// factoryList.size() - 1);
		return factoryList.get(selected);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chs.jmvivo.wumpus.ui.Ui#askForConfigurationItem(chs.jmvivo.wumpus.
	 * Configuration.Item, java.util.Optional)
	 */
	@Override
	public void askForConfigurationItem(Item<?> item, Optional<String> aditonalMessage) {

		if (aditonalMessage.isPresent()) {
			print(aditonalMessage.get());
		}
		switch (item.getItemType()) {
		case INTEGER: {
			// TODO add min and max value in items
			Item<Integer> intItem = (Item<Integer>) item;
			Integer value = askForInt("Select value for: " + item.getName(), null, null,
					(Integer) item.getDefaultValue());
			intItem.setValue(value);
			break;
		}
		case STRING: {
			Item<String> strItem = (Item<String>) item;
			String value = askForString("Select value for: " + item.getName(), (String) item.getDefaultValue());
			strItem.setValue(value);
			break;
		}

		default:
			throw new IllegalStateException("Unsupporte item type: " + item.getName());
		}

	}

	/**
	 * Ask user for a String in command line
	 * 
	 * @param prompt
	 * @param defaultValue
	 * @return
	 */
	private String askForString(String prompt, String defaultValue) {
		String value = null;
		do {
			if (defaultValue == null) {
				print(prompt);

			} else {
				print(String.format(" %s (default: %s)", prompt, defaultValue));
			}
			String input = in.nextLine();
			if (StringUtils.isBlank(input) && defaultValue != null) {
				return defaultValue;
			}
		} while (value == null);
		return value;
	}

	@Override
	public int askOption(String prompt, Integer defaultOption, String... options) {
		print(prompt);

		for (int i = 0; i < options.length; i++) {
			print(String.format("%s. %s", i, options[i]));
		}

		return askForInt("Select the number of action: ", 0, options.length - 1, defaultOption);
	}

	@Override
	public void showPerceptions(List<String> perceptions) {
		perceptions.stream().map(s -> " ... " + s + " ... ").forEach(this::print);
	}

	@Override
	public void showMessage(String string) {
		print(string);
	}

	public static class SimpleUIFactory implements UiFactory {

		@Override
		public String getIdentifier() {
			return "SimpleUI";
		}

		@Override
		public void registerConfiguration(Configuration config) {
			// Nothing to do
		}

		@Override
		public List<Pair<String, Item>> validate(Configuration config) {
			return Collections.emptyList();
		}

		@Override
		public Ui build(Configuration config) {
			return new SimpleUI();
		}

	}

	@Override
	public void intialize() {
		// Nothing to do
	}
	
	@Override
	public void shutdown() {
		// Nothing to do
	}

}
