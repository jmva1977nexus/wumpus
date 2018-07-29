package chs.jmvivo.wumpus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Configuration class
 * 
 * @author jmvivo
 *
 */
public class Configuration {
	
	public static enum ItemType {INTEGER(Integer.class), STRING(String.class);
		
		public final Class<?> classOfType;
		
		ItemType(Class<?> klass) {
			this.classOfType = klass;
		}
		
		public boolean isValidValue(Object value) {
			return isValidClass(value.getClass());
		}
		
		public boolean isValidClass(Class<?> otherClass) {
			return classOfType.isAssignableFrom(otherClass);
		}
	};
	
	/**
	 * Map which contains all values
	 */
	private final Map<String, Item<?>> items = new LinkedHashMap<>();
	private boolean done = false;
	
	public boolean isDone() {
		return done;
	}

	public void done() {
		this.done = true;
	}


	/**
	 * Register a new configuration value in map
	 * 
	 * @param name
	 * @param valueClass
	 * @param defaultValue
	 */
	public <T> void addItem(String name, ItemType itemType, Class<T> valueClass, T defaultValue) {
		if (!itemType.isValidValue(defaultValue)) {
			throw new IllegalArgumentException("Invalid default value!!");
		} else if (!itemType.isValidClass(valueClass)) {
			throw new IllegalArgumentException("Invalid value class!!");
		}
		items.put(name, new Item<T>(valueClass, name, itemType, defaultValue));
	}

	/**
	 * @param action
	 * @see Map#forEach(BiConsumer)
	 */
	public void forEach(BiConsumer<? super String, ? super Item> action) {
		items.forEach(action);
	}

	/**
	 * Gets an {@link Item} of configuration
	 * 
	 * @param name
	 * @return
	 */
	public Item<?> get(String name) {
		return items.get(name);
	}

	/**
	 * Contains a configuration value
	 * 
	 * @author jmvivo
	 *
	 * @param <T>
	 *            type of value
	 */
	public static class Item<T> {

		private final String name;
		private Class<T> valueClass;
		private final T defaultValue;
		private T value;
		private ItemType itemType;

		private Item(Class<T> valueClass, String name, ItemType itemType, T defaultValue) {
			this.name = name;
			this.defaultValue = defaultValue;
			this.valueClass = valueClass;
			this.value = defaultValue;
			this.itemType = itemType;
		}
		
		public ItemType getItemType() {
			return itemType;
		}

		/**
		 * @return current configuration value
		 */
		public T getValue() {
			return value;
		}

		/**
		 * Sets new value for configuration
		 * 
		 * @param value
		 */
		public void setValue(T value) {
			this.value = value;
		}

		/**
		 * @return value of class
		 */
		public Class<T> getValueClass() {
			return valueClass;
		}

		/**
		 * Name of item
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		public T getDefaultValue() {
			return defaultValue;
		}
		
		
	}

}
