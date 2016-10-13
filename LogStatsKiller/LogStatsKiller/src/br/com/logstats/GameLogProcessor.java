package br.com.logstats;

import java.util.Set;

import br.com.logstats.utils.GameLogReader;
import br.com.logstats.utils.GameLogWriter;

public class GameLogProcessor 
{
	public static void main ( String [] args )
	{
		GameLogProcessor game = new GameLogProcessor();
		
		game.gogogo(); 
	}
	
	private void gogogo ()
	{
		try 
		{
			//==============================================================
			//=====Read Log File and process contents to Match structure====
			//==============================================================
			Set<Match> listMatches = GameLogReader.readGameLog();
			
			//==============================================================
			//=========Process Match structures to calculate Stats==========
			//==============================================================
			GameLogStats logStats = new GameLogStats( listMatches );
			
			Set<GameLogResult> setResults = logStats.calculateStats();
					
			//==============================================================
			//==================Write Results to Outuput File===============
			//==============================================================
			GameLogWriter.writeGameLog( setResults );
			
			
		} catch(Exception ex)
		{
			System.out.println("Error [" + ex.getMessage() + "]");
		}
			
	}
	
}

