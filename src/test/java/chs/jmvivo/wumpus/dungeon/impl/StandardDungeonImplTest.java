package chs.jmvivo.wumpus.dungeon.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import chs.jmvivo.wumpus.Configuration;
import chs.jmvivo.wumpus.Configuration.Item;
import chs.jmvivo.wumpus.dungeon.Dungeon;
import chs.jmvivo.wumpus.dungeon.Player;
import chs.jmvivo.wumpus.dungeon.impl.StandardDungeonImpl.StandardDungeonImplFactory;

/**
 * 
 * @author jmvivo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StandardDungeonImplTest {
	
	@Mock
	private Item mockItem;


	@Mock
	private Configuration mockConfig;

	private Configuration configuration;
	
	@Mock
	private Player player;
	
	private StandardDungeonImpl target;
	
	@Before
	public void before(){
		configuration = new Configuration();
		StandardDungeonImplFactory factory = new StandardDungeonImplFactory();
		factory.registerConfiguration(configuration);
	}
	
	@Test
	public void testConstructor() {
		when(mockItem.getValue()).thenReturn(Integer.valueOf(5),Integer.valueOf(1),Integer.valueOf(1), Integer.valueOf(1) );
		when(mockConfig.get(StandardDungeonImpl.DUNGEON_SIZE)).thenReturn(mockItem);
		when(mockConfig.get(StandardDungeonImpl.DUNGEON_WUMPUS)).thenReturn(mockItem);
		when(mockConfig.get(StandardDungeonImpl.DUNGEON_WELLS)).thenReturn(mockItem);
		when(mockConfig.get(StandardDungeonImpl.DUNGEON_GOLD)).thenReturn(mockItem);
		StandardDungeonImpl dungeon = new StandardDungeonImpl(mockConfig);
		
		verify(mockItem, times(4)).getValue();
		verify(mockConfig).get(StandardDungeonImpl.DUNGEON_SIZE);
		verify(mockConfig).get(StandardDungeonImpl.DUNGEON_WUMPUS);
		verify(mockConfig).get(StandardDungeonImpl.DUNGEON_WELLS);
		verify(mockConfig).get(StandardDungeonImpl.DUNGEON_GOLD);
		
	}
	
}
