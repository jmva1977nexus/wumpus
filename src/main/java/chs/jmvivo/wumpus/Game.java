package chs.jmvivo.wumpus;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chs.jmvivo.wumpus.Configuration.Item;
import chs.jmvivo.wumpus.Configuration.ItemType;
import chs.jmvivo.wumpus.dungeon.Dungeon;
import chs.jmvivo.wumpus.dungeon.Dungeon.DungeonFactory;
import chs.jmvivo.wumpus.dungeon.Event;
import chs.jmvivo.wumpus.dungeon.Player;
import chs.jmvivo.wumpus.dungeon.Player.PlayerFactory;
import chs.jmvivo.wumpus.dungeon.impl.StandardDungeonImpl;
import chs.jmvivo.wumpus.dungeon.impl.StandardPlayer;
import chs.jmvivo.wumpus.ui.Ui;
import chs.jmvivo.wumpus.ui.Ui.UiFactory;
import chs.jmvivo.wumpus.ui.impl.SimpleUI;

/**
 * Main game class
 * 
 * Allows statically register factories for Dungeon player and UI
 *
 */
public class Game {
	
	private static final Logger LOG = LoggerFactory.getLogger(Game.class);

	private static Set<DungeonFactory> dungeonsFactories = new LinkedHashSet<>();
	private static Set<PlayerFactory> playerFactories = new LinkedHashSet<>();
	private static Set<UiFactory> uiFactories = new LinkedHashSet<>();

	private Configuration configuration;

	private Ui ui;

	private Dungeon dungeon;

	private Player player;

	/**
	 * Main method: Creates a new Game instance, calls game configuration and
	 * starts the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO move initialization to ServieLocator class
		registerDungeon(new StandardDungeonImpl.StandardDungeonImplFactory());
		registerPlayer(new StandardPlayer.StandardPlayerFactory());
		registerUi(new SimpleUI.SimpleUIFactory());
		
		Game game = new Game();

		game.configure();
		game.play();
	}

	public static void registerDungeon(DungeonFactory factory) {
		dungeonsFactories.add(factory);
		LOG.info("Register dungeon: {}", factory.getIdentifier());
	}

	public static void registerPlayer(PlayerFactory factory) {
		playerFactories.add(factory);
		LOG.info("Register player: {}", factory.getIdentifier());
	}

	public static void registerUi(UiFactory factory) {
		uiFactories.add(factory);
		LOG.info("Register UI: {}", factory.getIdentifier());
	}

	/**
	 * Default constructor
	 */
	public Game() {
		this(null, null, null, null);
	}

	/**
	 * Create a new game instance with predefined configuration and assets
	 * 
	 * (useful for test or predefined parameters)
	 * 
	 * @param ui
	 * @param dungeon
	 * @param player
	 * @param configuration
	 */
	public Game(Ui ui, Dungeon dungeon, Player player, Configuration configuration) {
		this.ui = ui;
		this.dungeon = dungeon;
		this.player = player;
		this.configuration = configuration;
	}

	/**
	 * Configure the game preparing and check factories and asking to user
	 * configuration values.
	 */
	public void configure() {

		if (configuration != null) {
			throw new IllegalStateException("Game is already configured!!!");
		}
		configuration = new Configuration();

		// Prepare UI
		UiFactory uiFactory = null;
		if (ui == null) {
			Ui simpleUi = new SimpleUI();
			simpleUi.intialize();

			uiFactory = configureFactory("User Interface", uiFactories, simpleUi);
			uiFactory.registerConfiguration(configuration);
			simpleUi.askForConfiguration(configuration);
			if (!configuration.isEmpty()) {
				simpleUi.askForConfiguration(configuration);
				validateConfigurationOf(uiFactory, simpleUi);
			}
			ui = uiFactory.build(configuration);
			ui.intialize();
		}

		// Pre
		DungeonFactory dungeonFactory = null;
		if (dungeon == null) {
			dungeonFactory = configureFactory("Dungeon", dungeonsFactories, ui);
			dungeonFactory.registerConfiguration(configuration);
		}

		PlayerFactory playerFactory = null;
		if (player == null) {
			playerFactory = configureFactory("Player", playerFactories, ui);
			playerFactory.registerConfiguration(configuration);
		}

		ui.askForConfiguration(configuration);
		if (dungeonFactory != null) {
			validateConfigurationOf(dungeonFactory);
			dungeon = dungeonFactory.build(configuration);
		}
		if (playerFactory != null) {
			validateConfigurationOf(playerFactory);
			player = playerFactory.build(configuration);
		}
		configuration.done();
	}

	/**
	 * Starts the game.
	 * 
	 * This method contains the main game loop
	 * 
	 * Requires already {@link #configure()}
	 * 
	 */
	public void play() {
		// Check if already configured
		if (!configuration.isDone()) {
			throw new IllegalStateException("Not configured");
		}
		int restart;
		do {
			// Intialize player and dungeon
			dungeon.intialize(player);
			Event event = player.intialize();
			if (CollectionUtils.isNotEmpty(event.getPerceptions())) {
				ui.showPerceptions(event.getPerceptions());
			}
			
			// Main game loop
			while (!event.isGameFinished()) {
				event = ui.waitForAction(player);
				if (CollectionUtils.isNotEmpty(event.getPerceptions())) {
					ui.showPerceptions(event.getPerceptions());
				}
			} 

			// Check finish condition
			if (event.playerWin()) {
				ui.showMessage("You WIN!!!");
			} else {
				if (event.playerDie()) {
					ui.showMessage("You die!!!");
				}
				ui.showMessage("GAME OVER");
			}

			// Ask for play again
			restart = ui.askOption("Do you want play again?", 1, "Yes", "No");
		} while (restart == 0);
		ui.showMessage("Bye!!!");
		ui.shutdown();
	}

	/**
	 * Configure a kind of factory. If there is only one option user will not be asked
	 * 
	 * @param option description of asset
	 * @param factories which can be selected
	 * @param uiToUse to ask the user
	 * @return
	 */
	private <T, F extends Factory<T>> F configureFactory(String option, Collection<F> factories, Ui uiToUse) {
		if (uiToUse == null) {
			throw new IllegalStateException("Ui is required!!");
		}
		if (factories.isEmpty()) {
			throw new IllegalStateException("No factory provided for ".concat(option).concat("!!"));
		}
		if (factories.size() == 1) {
			return factories.iterator().next();
		}
		F factory = ui.askForFactory(option, factories);

		if (factory == null) {
			throw new IllegalStateException("No factory selecte for ".concat(option).concat("!!"));
		}
		return factory;
	}

	/**
	 * Configure all assets
	 * 
	 * @param dungeonFactory
	 * @param playerFactory
	 */
	private void configureItems(DungeonFactory dungeonFactory, PlayerFactory playerFactory) {
		if (dungeonFactory != null) {
			validateConfigurationOf(dungeonFactory);
		}
		if (playerFactory != null) {
			validateConfigurationOf(playerFactory);
		}
		configuration.done();
	}

	/**
	 * Validate factory configuration
	 * 
	 * @param factory
	 */
	private void validateConfigurationOf(Factory<?> factory) {
		validateConfigurationOf(factory,ui);
	}

	/**
	 * Validate factory configuration 
	 * 
	 * @param factory
	 * @param uiToUse
	 */
	private void validateConfigurationOf(Factory<?> factory, final Ui uiToUse) {
		List<Pair<String, Item>> errors = Collections.emptyList();
		do {
			errors.forEach(info -> {
				uiToUse.askForConfigurationItem(info.getValue(), Optional.of(info.getKey()));
			});
			errors = factory.validate(configuration);

		} while (CollectionUtils.isNotEmpty(errors));
	}
	
}
