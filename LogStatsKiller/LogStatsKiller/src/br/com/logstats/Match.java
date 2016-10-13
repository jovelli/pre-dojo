package br.com.logstats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Match 
{
	private String sessionId;
	private LocalDateTime startSession;
	private LocalDateTime endSession;
	private List<Player> players;
	private List<Event> events;
	
	
	public Set<Player> getPlayers() 
	{
		return new HashSet<Player>( players );
	}
	
	public Player setPlayerIfAbsent(Player player)
	{
		if (null != player && !players.contains( player ) )
		{
			players.add( player );
		
		} else {
			
			player = players.get( players.indexOf(player) );
		}
		 
		return player;
	}	

	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) 
	{
		this.events = new ArrayList<Event>( events ) ;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public LocalDateTime getStartSession() {
		return startSession;
	}
	public void setStartSession(LocalDateTime startSession) {
		this.startSession = startSession;
	}
	public LocalDateTime getEndSession() {
		return endSession;
	}
	public void setEndSession(LocalDateTime endSession) {
		this.endSession = endSession;
	}
	
	public Match(String sessionId)
	{
		this();
		this.sessionId = sessionId;
	}
	
	public Match()
	{
		this.players = new ArrayList<>();
		this.events = new ArrayList<Event>();
	}	
	
}


