
package br.com.logstats;

public class Event 
{
	private long id;
	private Player dead, killer;
	private String weapon;
	
	public String getWeapon() 
	{
		return weapon;
	}
	
	public void setWeapon(String weapon)
	{
		this.weapon = weapon;
	}
	
	public Player getDead() 
	{
		return dead;
	}
	
	public void setDead(Player dead) 
	{
		this.dead = dead;
	}
	
	public Player getKiller() 
	{
		return killer;
	}
	
	public void setKiller(Player killer) 
	{
		this.killer = killer;
	}
	
	public long getId() 
	{
		return id;
	}
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public Event(long id)
	{
		this.id = id;
	}
	
}
