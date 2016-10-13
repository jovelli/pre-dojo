package br.com.logstats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Map.Entry;

public class GameLogStats 
{
	private Set<Match> setMatches;

	
	//==========================================================================================
	//===============================Calculate the stats from Match=============================
	//==========================================================================================		
	public Set<GameLogResult> calculateStats()
	{
		HashSet<GameLogResult> gameLogResults = new HashSet<>();
		
		for ( Match match : setMatches )
		{
			
			GameLogResult gameLogResult = new GameLogResult( match );
			
			//Sort Players and store result in gameLogResult
			List<Player> orderedPlayer = this.getOrderedPlayers( match );
			
			gameLogResult.setOrderedPlayersByKillsAndDeaths( orderedPlayer );
			
			
			//Get most used weapons by Players
			gameLogResult.setPreferredWeaponByPlayer( this.getPreferredWeaponByPlayer( match ) );
			
			
			//Get biggest streak in Match
			gameLogResult.setBiggestStreakPlayer( this.getBiggestStreak( match ) );


			//Get not killed Players
			gameLogResult.setAwardedNotKilledPlayers(  this.getNotKilledPlayers( match ) );
			
			
			//Players killed 5 times in a minute
			gameLogResult.setAwardedFiveKillsMinutePlayers(  this.getFiveKillsMinutePlayers( match ) );

			gameLogResults.add( gameLogResult );
		}
		
		return gameLogResults;
	}
	
	
	//==========================================================================================
	//===============================Get most used weapon by Player=============================
	//==========================================================================================	
	private Map<Player, String> getPreferredWeaponByPlayer(Match match)
	{	
		Map<Player, String> preferredWeaponByPlayer = new HashMap<Player, String>();
		
		for (Player player : match.getPlayers())
		{
			Map<String, Integer> weaponsByPlayer = player.getWeapons();
			
			if (0 != weaponsByPlayer.size())
			{
				Entry<String, Integer> mostUsedWeaponsByPlayer = weaponsByPlayer.entrySet()
																				.stream()
																				.max(  Map.Entry.comparingByValue(Integer::compareTo) )
																				.get();
				
				preferredWeaponByPlayer.put( player, mostUsedWeaponsByPlayer.getKey() );
					
			}
			
		}
		
		return preferredWeaponByPlayer;
	}
	
	
	//==========================================================================================
	//====================Sort Players by Kills DESC and Deaths ASC ============================
	//==========================================================================================	
	private List<Player> getOrderedPlayers(Match match)
	{
		List<Player> orderedPlayers = match.getPlayers()
												 .stream()
												 .sorted(
														Collections.reverseOrder(
												    		Comparator.comparing((Player p) -> p.getKillCount())
												    	).thenComparing(
												    		Comparator.comparing((Player p) -> p.getDeathCount())
												    	)
												    )
												 .collect(Collectors.toList());
		
		return Collections.unmodifiableList( orderedPlayers );
	}
	
	
	//==========================================================================================
	//==========================Get Biggest Streak in the Match ================================
	//==========================================================================================		
	private Map<Player, Integer> getBiggestStreak(Match match)
	{	
		
		@SuppressWarnings("unused")
		Player biggestStreakPlayer = null;
		int biggestStreakCount = 0;
		List<Event> eventsByPlayer; 
		int kills;
		
		for ( Player player : match.getPlayers() )
		{
			//Get events by player
			eventsByPlayer = match.getEvents().stream()
									 			.filter((Event e) -> player.equals(e.getKiller()) 
									 					|| player.equals(e.getDead()) )
									 			.sorted( Comparator.comparing(e -> e.getId()))
									 			.collect(Collectors.toList());
			
			//Set kills to 0
			kills = 0;
			
			for (int i = 0; i < eventsByPlayer.size(); i++)
			{
				if (eventsByPlayer.get(i).getKiller().equals( player ))
				{
					kills++;
				} 
				
				//Compare current streak to streak registered 
				//if player is dead or if it is last event
				if ( eventsByPlayer.get(i).getDead().equals( player ) 
					 ||	(i == eventsByPlayer.size() - 1))
				{
					if ( kills > biggestStreakCount )
					{	
						biggestStreakPlayer = player;
						biggestStreakCount = kills;	
					}
					
					kills = 0;
				}
			}
		}
		
		Map<Player, Integer> resultPlayer = new HashMap<>();
		resultPlayer.put(biggestStreakPlayer, biggestStreakCount);
		
		return resultPlayer;
	}	
	

	//==========================================================================================
	//================================Get Not Killed Players====================================
	//==========================================================================================		
	private List<Player> getNotKilledPlayers(Match match)
	{	
		List<Player> notKilledPlayers = match.getPlayers()
											 .stream()
											 .filter((Player p) -> p.getDeathCount() == 0)
											 .collect(Collectors.toList());
				 
		return Collections.unmodifiableList( notKilledPlayers );
	}
	
	//==========================================================================================
	//===========================Get Players that killed 5 in a minute==========================
	//==========================================================================================		
	private List<Player> getFiveKillsMinutePlayers(Match match)
	{	
		List<Player> resPlayers = new ArrayList<>();
		
		outer:
		for ( Player player : match.getPlayers() )
		{
			//Get kill events by player
			List<Event> eventKillsByPlayer = match.getEvents().stream()
								 			.filter((Event e) -> player.equals(e.getKiller()))
								 			.sorted( Comparator.comparing(e -> e.getId()))
								 			.collect(Collectors.toList());
			
			//Compare time occurred event at index i to event at index i + 4
			for (int i = 0; i < eventKillsByPlayer.size(); i++)
			{
				if (i + 4 >= eventKillsByPlayer.size())
				{
					break;
				}
				
				if ( ( eventKillsByPlayer.get(i + 4).getId() - eventKillsByPlayer.get(i).getId() ) <= 60 ) 
				{ 
					resPlayers.add( player );
					continue outer;
				}
				
			}
		}
				 
		return Collections.unmodifiableList( resPlayers );
	}	
	
	
	public GameLogStats(Set<Match> setMatches )
	{
		this.setMatches = setMatches;
	}
	
}
