package br.com.logstats.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import br.com.logstats.Event;
import br.com.logstats.GameLogResult;
import br.com.logstats.GameLogStats;
import br.com.logstats.Match;
import br.com.logstats.Player;

public class GameLogStatsTest {

	@Test
	public void testCalculateStats() 
	{
		Match match1 = new Match("123456");
		
		//Add Players
		Player p1 = new Player("p1");
		p1.incrementKillCount();
		p1.incrementKillCount();
		p1.incrementKillCount();
		p1.incrementDeathCount();
		p1.incrementDeathCount();
		p1.incrementDeathCount();
		p1.incrementDeathByWeapon("AK-47");
		p1.incrementDeathByWeapon("AK-47");
		p1.incrementDeathByWeapon("AK-47");
		
		Player p2 = new Player("p2");
		p2.incrementKillCount();
		p2.incrementDeathByWeapon("MG16");
		p2.incrementDeathCount();
		p2.incrementDeathCount();
		
		Player p3 = new Player("p3");
		p3.incrementKillCount();
		p3.incrementKillCount();
		p3.incrementKillCount();
		p3.incrementKillCount();
		p3.incrementDeathByWeapon("M14");
		p3.incrementDeathByWeapon("KNIFE");
		p3.incrementDeathByWeapon("AK-47");
		p3.incrementDeathByWeapon("M14");
		p3.incrementDeathCount();
		
		Player p4 = new Player("p4");
		p4.incrementKillCount();
		p4.incrementDeathByWeapon("AK-47");
		p4.incrementDeathCount();
		p4.incrementDeathCount();
		p4.incrementDeathCount();
		p4.incrementDeathCount();
		p4.incrementDeathCount();
		
		Player p5 = new Player("p5");
		p5.incrementKillCount();
		p5.incrementKillCount();
		p5.incrementKillCount();
		p5.incrementDeathByWeapon("KNIFE");
		p5.incrementDeathByWeapon("KNIFE");
		p5.incrementDeathByWeapon(".40");	
		p5.incrementDeathCount();
		p5.incrementDeathCount();
		
		Player world = new Player("<WORLD>");
		
		match1.setPlayerIfAbsent(p1);
		match1.setPlayerIfAbsent(p2);
		match1.setPlayerIfAbsent(p3);
		match1.setPlayerIfAbsent(p4);
		match1.setPlayerIfAbsent(p5);
		
		//Add Events
		//Events p1
		Event event1 = new Event(123456789);
		event1.setKiller(p1);
		event1.setDead(p2);
		event1.setWeapon("AK-47");
		
		Event event2 = new Event(123456790);
		event2.setKiller(p1);
		event2.setDead(p3);
		event2.setWeapon("AK-47");
		
		Event event3 = new Event(123456791);
		event3.setKiller(p1);
		event3.setDead(p4);
		event3.setWeapon("AK-47");  
		
		
		//Events p2
		Event event4 = new Event(123456792);
		event4.setKiller(p2);
		event4.setDead(p1);
		event4.setWeapon("MG16");
		
		
		//Events p3
		Event event5 = new Event(123456793);
		event5.setKiller(p3);
		event5.setDead(p1);
		event5.setWeapon("M14");
		
		Event event6 = new Event(123456794);
		event6.setKiller(p3);
		event6.setDead(p5);
		event6.setWeapon("M14");
		
		Event event7 = new Event(123456795);
		event7.setKiller(p3);
		event7.setDead(p2);
		event7.setWeapon("AK-47");
		
		Event event8 = new Event(123456796);
		event8.setKiller(p3);
		event8.setDead(p4);
		event8.setWeapon("KNIFE");
		
		
		//Events p4
		Event event9 = new Event(123456797);
		event9.setKiller(p4);
		event9.setDead(p5);
		event9.setWeapon("AK-47");
		
		
		//Events p5
		Event event10 = new Event(123456798);
		event10.setKiller(p5);
		event10.setDead(p1);
		event10.setWeapon("M14");
		
		Event event11 = new Event(123456799);
		event11.setKiller(p5);
		event11.setDead(p4);
		event11.setWeapon("AK-47");
		
		Event event12 = new Event(123456800);
		event12.setKiller(p5);
		event12.setDead(p4);
		event12.setWeapon("AK-47");		
		
		//Events WORLD
		Event event13 = new Event(123456801);
		event13.setKiller(world);
		event13.setDead(p4);
		event13.setWeapon("DROWN");		
				
		
		List<Event> lstEvents = new ArrayList<>();
		lstEvents.add(event1);
		lstEvents.add(event2);
		lstEvents.add(event3);
		lstEvents.add(event4);
		lstEvents.add(event5);
		lstEvents.add(event6);
		lstEvents.add(event7);
		lstEvents.add(event8);
		lstEvents.add(event9);
		lstEvents.add(event10);
		lstEvents.add(event11);
		lstEvents.add(event12);
		
		match1.setEvents( lstEvents );
		
		Set<Match> setMatches = new HashSet<>();
		setMatches.add( match1 );
		
		GameLogStats gameLogStats = new GameLogStats( setMatches );
		
		
		//GameLogResults
		GameLogResult gameLogResults =  new GameLogResult( match1 );
		
		//OrderedPlayers
		List<Player> lstOrderedPlayers = new ArrayList<>();
		lstOrderedPlayers.add(p3);
		lstOrderedPlayers.add(p5);
		lstOrderedPlayers.add(p1);
		lstOrderedPlayers.add(p2);
		lstOrderedPlayers.add(p4);
		
		gameLogResults.setOrderedPlayersByKillsAndDeaths( Collections.unmodifiableList(lstOrderedPlayers) );
		
		//OrderedPlayers
		Map<Player, Integer> mapBigStreakPlayer = new HashMap<>();
		mapBigStreakPlayer.put( p3 , 4 );
		
		gameLogResults.setBiggestStreakPlayer( mapBigStreakPlayer ); 
				
		
		//PreferredGuns
		Map<Player, String> mapPrefferedGuns = new HashMap<>();
		mapPrefferedGuns.put(p1, "AK-47");
		mapPrefferedGuns.put(p2, "MG16");
		mapPrefferedGuns.put(p3, "M14");
		mapPrefferedGuns.put(p4, "AK-47");
		mapPrefferedGuns.put(p5, "KNIFE");
		
		gameLogResults.setPreferredWeaponByPlayer( mapPrefferedGuns );
		
		gameLogResults.setAwardedFiveKillsMinutePlayers( Collections.unmodifiableList( new ArrayList<Player>()));
		gameLogResults.setAwardedNotKilledPlayers(( Collections.unmodifiableList( new ArrayList<Player>())));
		

		//Match
		assertEquals(gameLogResults.getMatch(), gameLogStats.calculateStats().iterator().next().getMatch());

		//OrderedPlayersUnitTest
		assertEquals(gameLogResults.getOrderedPlayersByKillsAndDeaths(), gameLogStats.calculateStats().iterator().next().getOrderedPlayersByKillsAndDeaths());
				
		//AwardedFiveKillsMinutePlayers
		assertEquals(gameLogResults.getAwardedFiveKillsMinutePlayers(), gameLogStats.calculateStats().iterator().next().getAwardedFiveKillsMinutePlayers());
		
		//AwardedNotKilledPlayers
		assertEquals(gameLogResults.getAwardedNotKilledPlayers(), gameLogStats.calculateStats().iterator().next().getAwardedNotKilledPlayers());
			
		//BiggestStreakPlayer
		assertEquals(gameLogResults.getBiggestStreakPlayer(), gameLogStats.calculateStats().iterator().next().getBiggestStreakPlayer());
				
		//PreferredWeaponByPlayer
		assertEquals(gameLogResults.getPreferredWeaponByPlayer(), gameLogStats.calculateStats().iterator().next().getPreferredWeaponByPlayer());
			
		
	}

}
