package br.com.logstats.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.logstats.Match;
import br.com.logstats.Player;

public class MatchTest {

	@Test
	public void testSetPlayerIfAbsent() 
	{
		Match match = new Match("123456789"); 
		
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		Player p3 = new Player("p3");
		Player p4 = new Player("p4");
		Player p5 = new Player("p5");
		Player p6 = new Player("p6");
		
		match.setPlayerIfAbsent(p1);
		match.setPlayerIfAbsent(p1);
		match.setPlayerIfAbsent(p1);
		
		match.setPlayerIfAbsent(p2);
		match.setPlayerIfAbsent(p2);
		
		match.setPlayerIfAbsent(p3);
		
		match.setPlayerIfAbsent(p4);
		match.setPlayerIfAbsent(p4);
		match.setPlayerIfAbsent(p4);
		match.setPlayerIfAbsent(p4);
		
		match.setPlayerIfAbsent(p5);
		match.setPlayerIfAbsent(p5);
		match.setPlayerIfAbsent(p5);
		
		match.setPlayerIfAbsent(p6);
		match.setPlayerIfAbsent(p6);
		
		
		Match matchTest = new Match("123456789");
		matchTest.setPlayerIfAbsent(p1);
		matchTest.setPlayerIfAbsent(p2);
		matchTest.setPlayerIfAbsent(p3);
		matchTest.setPlayerIfAbsent(p4);
		matchTest.setPlayerIfAbsent(p5);
		matchTest.setPlayerIfAbsent(p6);
		
		
		//Players
		assertEquals( match.getPlayers(), matchTest.getPlayers() );
		
		//Events
		assertEquals( match.getEvents(), matchTest.getEvents() );
		
		
	}

}
