package br.com.logstats.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.logstats.GameLogResult;
import br.com.logstats.Player;


public class GameLogWriter 
{

	private static final String LINE_SEPARATOR = "============================================================================================================";
	private static final String OUTPUT_FILE_NAME = "LogStatsKillerResult_" + System.currentTimeMillis() + ".log";
	private static final String[] TITLE = {"JOGADOR", "SCORE", "MORTES", "ARMA PREFERIDA"} ;
	
	public static void writeGameLog(Set<GameLogResult> gameLogResults) throws Exception
	{
		String file = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + OUTPUT_FILE_NAME;
		
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get( file )))
		{
			String headerTitle = null;

			printHeader(writer, "****LOG STATS KILLER****");
			
			for (GameLogResult gameLogResult: gameLogResults)
			{
				headerTitle = "***PARTIDA [" + gameLogResult.getMatch().getSessionId() + "]***";
						
				printHeader( writer, headerTitle );
				writer.newLine();
				
				
				//Title for Match
				writer.write(String.format("%-15s", TITLE[0]));
				writer.write(String.format("%1$15s", TITLE[1]));
				writer.write(String.format("%1$15s", TITLE[2]));
				writer.write(String.format("%1$20s", TITLE[3]));
				
				//Players and details
				for (Player player : gameLogResult.getOrderedPlayersByKillsAndDeaths())
				{
					writer.newLine();
					writer.write( String.format("%-15s", player.getPlayerId() ) );
					writer.write( String.format("%1$15s", player.getKillCount() ) );
					writer.write( String.format("%1$15s", player.getDeathCount() ) );
					
					String weapon = gameLogResult.getPreferredWeaponByPlayer().get( player );
					writer.write( String.format("%1$15s", (null == weapon) ? "-" : weapon ));
				}
				
				//Awards
				writer.newLine();
				String awards = "***AWARDS PARTIDA [" + gameLogResult.getMatch().getSessionId() + "]***";
				printHeader( writer, awards );
				
				writer.newLine();
				writer.write( gameLogResult.getBiggestStreakPlayer().entrySet().stream().map(s -> "Maior sequencia (sem morrer) [ " + s.getKey().getPlayerId() + " ] score [ " + s.getValue()).findFirst().get() + " ]" );
				
				writer.newLine();
				writer.write( "Jogadores que nao morreram na partida[ " + gameLogResult.getAwardedNotKilledPlayers().stream().map( (Player p1) -> p1.getPlayerId() ).collect(Collectors.joining(", ")) + " ]");
				
				writer.newLine();
				writer.write( "Jogadores que mataram 5 em 1 minuto [ " + gameLogResult.getAwardedFiveKillsMinutePlayers().stream().map( (Player p1) -> p1.getPlayerId() ).collect(Collectors.joining(", ")) + " ]");
					
				writer.newLine();
				writer.newLine();
			}

			System.out.println("===Arquivo processado com sucesso===");
			System.out.println("Verfiique os resultados no arquivo [" + file + "]");
			
		} catch(IOException ex) {
			
			throw new IOException("==Nao foi possivel gravar o arquivo de saida [" + OUTPUT_FILE_NAME + "]===");
		  
		} catch(Exception ex) {
			
			throw new Exception( ex );
		}
	}
	
	private static void printSeparator(BufferedWriter writer, String separator, int times) throws IOException
	{
		for (int i = 0; i < times; i++ )
			writer.write( separator );
	}

	private static void printHeader(BufferedWriter writer, String headerTitle) throws IOException
	{
		writer.newLine();
		writer.write(LINE_SEPARATOR);
		writer.newLine();
		
		int side = (LINE_SEPARATOR.length() - (headerTitle.length())) / 2;
		
		printSeparator(writer, "=", side);
		writer.write( headerTitle );
		printSeparator(writer, "=", side);
		
		writer.newLine();
		writer.write(LINE_SEPARATOR);
		writer.newLine();
	}
	
}

