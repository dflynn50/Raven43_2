package raven.game;

import raven.game.interfaces.ITeam;
import java.awt.Color;
import raven.game.messaging.Telegram;
//import raven.game.RavenBot;
import raven.game.interfaces.IRavenBot;
//import raven.goals.GoalThink;

import java.awt.Color;
import java.util.List;
import raven.math.*;
import raven.ui.GameCanvas;
import raven.utils.Log;

import java.util.ArrayList;


/* To Do
//List bots	
//List spawn points
//process team goal (make new goals for teams)
//Handle messages
 * Other stuff- kill entity manager
 * make "loose bots"? Ask
 * add to iraven interface (?) In Progress
 *
 * 
 * Teams are based on a captain
 * protect captain
 * Find Captain
 * Scatter(Leader is dead)
 * Kill enemy leader
 * Murder defenders
 * 
 * Just different flavors of pursuit, or evade.
 * Also there needs to be a way to tell the leader to move more slowly
 * have more hit points, etc.  
 */


public class Team extends BaseGameEntity implements ITeam
{
	//Private vars
	private static int currValidTeamID = 0;
	private static int teamID;
	private Color teamColor;
	private RavenBot teamCaptain;
	private boolean captainExist = false;
	 
	
	////A list of bots on the team, should be references, I'll ask
	private	List<IRavenBot> teamBots = new ArrayList<IRavenBot>();
	
	///We need a valid location for spawning
	private ArrayList<Vector2D> teamSpawnPoints;

	//public static Color teamColor;
	
	//Goal queue? 
	//private GoalThink teamBrain;
	
	public Team(int id)
	{
			super(id);
			//Can I just modify this constructor?
			
			//Just so we don't get some null point exception
			//Just for testing.
			//teamSpawnPoints.add( new Vector2D(0,0));
			setEntityType(RavenObject.TEAM);
			//teamBrain = new GoalThink(this);
			
			/////Setting team ID before we register with entity manager.
			teamID = id; 
			teamID = currValidTeamID;
			currValidTeamID++;
			setEntityType(RavenObject.TEAM);
			
			
			
			/////we want this to register a team by ID but let's
			/////just get the teams working
			EntityManager.registerEntity(this);
		}

	
	
	//We want a way to add a bot to the list of bots on this team
	//But, we're in the middle of gutting out the entity manager
	//so we should probably just accept a reference to a bot.
	//Also using "draft" and drop instead of "add/remove"
	//to avoid confusion
	public void draftBot(IRavenBot draftee) {
	//Ask if this works as a reference
		if (teamBots.size()==0){
			draftee.setAsCaptain();
			//draftee.getTeam().toggleCaptainExist();
			Log.info("TEAM", "Registered Captain of team " + draftee.getTeam().ID());
		}
		teamBots.add(draftee);
		
	}
	
	///We may want to add a clear/remove team association. 
	public void dropBot(IRavenBot draftee){
		teamBots.remove(draftee);
		/*if(teamBots.size() == 0) {
			draftee.getTeam().toggleCaptainNoExist();
		}*/
	}

	


	
	@Override
	public void render()
	{}
	
	@Override
	public boolean handleMessage(Telegram msg) {
		// We need to implement the ability to handle a 
		// broad or multicast.
		// we could treat every message to the team as multicast
		
	return false;
	}


	public Vector2D getTeamSpawnPoint(){
		return teamSpawnPoints.get(0);
	}
	
	////We need new goals/brains
	/*
	public GoalThink getBrain() {
		return teamBrain;
	}
	*/
	public Color getTeamColor(){
		//if (this.ID() == 101){
		//teamColor = 
		//}
		return teamColor;
	}
	
	public void setCaptain(RavenBot bot) {teamCaptain = bot;}
	
	public RavenBot getCaptain() { return teamCaptain;}
	
	public  boolean captainExist() {return captainExist;}
	public void toggleCaptainExist() { captainExist = true; }
	public void toggleCaptainNoExist() {captainExist = false;}
	
}


