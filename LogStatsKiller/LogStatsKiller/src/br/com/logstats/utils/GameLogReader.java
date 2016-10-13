package br.com.logstats.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Frame;
import java.awt.FileDialog;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.logstats.Event;
import br.com.logstats.Match;
import br.com.logstats.Player;

public class GameLogReader
{

	//==========================================================================
	//=================== Date Format - dd/MM/yyyyHH:mm:ss =====================
	//==========================================================================
	private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm:ss");
	
	
	//==========================================================================
	//=== Basic Format - REGEXP:  [dd/mm/yyyy hh:mi:ss - text text text... ] ===
	//==========================================================================
	private static final String REGEXP_DATETIME = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)\\s+([0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d";
	private static final String REGEXP_LINE_BASIC = REGEXP_DATETIME + "\\s-\\s.+$";
	private static final String REGEXP_NEW_MATCH = REGEXP_DATETIME + "\\s-\\sNew\\smatch.+$";
	private static final String REGEXP_NEW_EVENT = REGEXP_DATETIME + "\\s-\\s.+killed.+$";
	private static final String REGEXP_END_MATCH = REGEXP_DATETIME + "\\s-.+\\shas\\sended$";
	private static final String WORLD_PLAYER = "<WORLD>";
	
	
	public static Set<Match> readGameLog() throws Exception
	{
		//Open FileDialog, filters for *.log
		Frame frame = new Frame();
		
		FileDialog dialog = new FileDialog(frame, "Escolha um arquivo .log", FileDialog.LOAD );
		
		dialog.setFile("*.log");
		
		dialog.setFilenameFilter( (File dir, String name) -> name.endsWith(".log") );
		
		//Opens Dialog to user.  
		dialog.setVisible(true);
		
		Path file = Paths.get( dialog.getDirectory() + FileSystems.getDefault().getSeparator() + dialog.getFile() );
		
		//Dispose frame.
		frame.dispose();
		
		return logFileToMatches( file );
	}

	
	private static Set<Match> logFileToMatches(Path file) throws Exception
	{

		Set<Match> listMatches = null;
				
		try ( BufferedReader reader = Files.newBufferedReader((file)) )  
		{
			
	    	listMatches = new HashSet<Match>();
	    	List<Event> lstEvents = new ArrayList<>();
	    	
			String line = null;
	    	Match match = null;
	    	
	    	
		    while ((line = reader.readLine()) != null) 
		    {		 
		    
		    	//========================================================
		    	//=============Validates basic format for line.=========== 
		    	//========================================================
		    	
		    	if ( ! line.matches( REGEXP_LINE_BASIC ) )
		    	{
		    		throw new IllegalArgumentException("Arquivo com conteudo Invalido. Linha [" + line + "]");
		    	} 	
		    	
		    	
		    	String[] parts = line.split(" ");
		    	
		    	//========================================================
		    	//=================New Match identified===================
		    	//========================================================
		    	
		    	if ( line.matches( REGEXP_NEW_MATCH ) )
		    	{
		    		
		    		//Create Match with sessionId
		    		match = new Match( parts[5] );
		    		
		    		match.setStartSession(LocalDateTime.parse( parts[0]+parts[1], dateTimeFormat) );	
		    	}
		    	
		    	
		    	//========================================================
		    	//=================Event Line identified==================
		    	//========================================================		    	
		    	
		    	if ( line.matches( REGEXP_NEW_EVENT ) )
		    	{
		    		
		    		//Counts death and kill for players.
		    		Player killer = null;
		    				
		    		if ( ! parts[3].equals( WORLD_PLAYER ) )
		    		{
		    			killer = match.setPlayerIfAbsent( new Player( parts[3] ) );
		    			killer.incrementKillCount();
		    			killer.incrementDeathByWeapon( parts[7] );
		    		
		    		} else {
		    			
		    			killer = new Player( parts[3] ); 
		    		}
		    		
		    		Player dead = match.setPlayerIfAbsent( new Player(  parts[5] ) );
		    		
		    		dead.incrementDeathCount();


		    		//New event
		    		Event event = new Event( LocalDateTime.parse( parts[0]+parts[1], dateTimeFormat).atZone( ZoneId.systemDefault() ).toEpochSecond() );
		    		event.setKiller( killer );
		    		event.setDead( dead );
		    		event.setWeapon( parts[7] );
		    		
		    		lstEvents.add( event );
		    	}
		    	
		    	
		    	//========================================================
		    	//================End Match Line identified===============
		    	//========================================================		    	
		    	
		    	if ( line.matches( REGEXP_END_MATCH ) )
		    	{
		    		
		    		match.setEvents( lstEvents );	
		    		
		    		match.setEndSession(LocalDateTime.parse( parts[0]+parts[1], dateTimeFormat) );
		    		
		    		listMatches.add( match );
		    	}		    	
		    	
		    }
		
		} catch(IOException ex) {
					
			throw new IOException("==Nao foi possivel abrir o arquivo [" + file.getFileName() + "]===");
		  
		} 
		
		return listMatches;
	}
	
}

