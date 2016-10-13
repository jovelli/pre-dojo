package br.com.logstats;


import java.util.HashMap;
import java.util.Map;

public class Player
{
	private String playerId;
	private Integer killCount;
	private Integer deathCount; 
	private Map<String, Integer> weapons;
	
	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public Integer getKillCount() {
		return killCount;
	}

	public void incrementKillCount() {
		this.killCount++;
	}

	public Integer getDeathCount() {
		return deathCount;
	}

	public void incrementDeathCount() {
		this.deathCount++;
	}

	public void incrementDeathByWeapon(String weapon) 
	{
		this.weapons.put(weapon, ( ( this.weapons.containsKey( weapon ) ) ? this.weapons.get( weapon ) + 1 : 1 ) );	
	}
	
	
	public Map<String, Integer> getWeapons() 
	{
		return new HashMap<String, Integer>( this.weapons );
	}	
	
	public Player(String playerId)
	{
		this.playerId = playerId;
		this.killCount = 0;
		this.deathCount = 0;
		this.weapons = new HashMap<String, Integer>();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 13;
		int hash = 1;
		
		hash = prime * hash + (null != playerId ? playerId.hashCode() : 0 );
				
		return hash;
	}
	

	@Override
	public boolean equals(Object obj)
	{
		return (null != obj && this.playerId.equals( ((Player) obj).getPlayerId() )  ) ? true : false; 
	}

}
