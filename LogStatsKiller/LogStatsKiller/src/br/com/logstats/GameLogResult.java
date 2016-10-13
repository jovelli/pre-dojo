package br.com.logstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLogResult 
{
	private List<Player> orderedPlayersByKillsAndDeaths;
	private Map<Player, String> preferredWeaponByPlayer;
	private Map<Player, Integer> biggestStreakPlayer;
	private List<Player> awardedNotKilledPlayers;
	private List<Player> awardedFiveKillsMinutePlayers;
	
	private Match match;

	public Match getMatch()
	{
		return match;
	}
	
	public List<Player> getOrderedPlayersByKillsAndDeaths() 
	{
		return new ArrayList<Player>( orderedPlayersByKillsAndDeaths );
	}

	public void setOrderedPlayersByKillsAndDeaths(List<Player> orderedPlayersByKillsAndDeaths) 
	{
		this.orderedPlayersByKillsAndDeaths = new ArrayList<Player>(orderedPlayersByKillsAndDeaths);
	}
	
	
	public Map<Player, String> getPreferredWeaponByPlayer() 
	{
		return new HashMap<Player, String>( preferredWeaponByPlayer );
	}
	
	public void setPreferredWeaponByPlayer(Map<Player, String> preferredWeaponByPlayer) 
	{
		this.preferredWeaponByPlayer = new HashMap<Player, String>(preferredWeaponByPlayer);
	}	
	
	public Map<Player, Integer> getBiggestStreakPlayer()
	{
		return biggestStreakPlayer;
	}

	public void setBiggestStreakPlayer(Map<Player, Integer> biggestStreakPlayer) 
	{
		this.biggestStreakPlayer = biggestStreakPlayer;
	}
	
	public List<Player> getAwardedNotKilledPlayers() 
	{
		return awardedNotKilledPlayers;
	}

	public void setAwardedNotKilledPlayers(List<Player> awardedNotKilledPlayers) 
	{
		this.awardedNotKilledPlayers = awardedNotKilledPlayers;
	}	
	
	public List<Player> getAwardedFiveKillsMinutePlayers() 
	{
		return awardedFiveKillsMinutePlayers;
	}

	public void setAwardedFiveKillsMinutePlayers(List<Player> awardedFiveKillsMinutePlayers) 
	{
		this.awardedFiveKillsMinutePlayers = awardedFiveKillsMinutePlayers;
	}
	
	public GameLogResult(Match match)
	{
		this.match = match;
	}
	
	
}



