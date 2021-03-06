package raven.goals;

import raven.game.RavenBot;
import raven.math.Vector2D;
import raven.ui.GameCanvas;

public class Goal_SeekToPosition extends GoalComposite<RavenBot> {
	Vector2D  m_vPosition;

	//the approximate time the bot should take to travel the target location
	double     m_dTimeToReachPos;

	//this records the time this goal was activated
	double     elapsedTime;

	//returns true if a bot gets stuck
	// dont know how to detect this atm.
	boolean isStuck(){
		if (elapsedTime > m_dTimeToReachPos) {
			System.out.println("BOT "  + m_pOwner.ID() + " IS STUCK!!");
			return true;
		}
		return false;
	}
	
	public Goal_SeekToPosition(RavenBot rbot, Vector2D target) {
		super(rbot, Goal.GoalType.goal_seek_to_position);

		if(rbot.isCaptain())
			m_vPosition = target;
		else
			m_vPosition = rbot.getTeam().getCaptain().pos();
		m_dTimeToReachPos = 0.0;

	}

	@Override
	public void activate() {
		m_iStatus = Goal.CurrentStatus.active;

		//record the time the bot starts this goal
		elapsedTime = 0;    

		//This value is used to determine if the bot becomes stuck 
		m_dTimeToReachPos = m_pOwner.calculateTimeToReachPosition(m_vPosition);

		//factor in a margin of error for any reactive behavior
		double MarginOfError = 1.0;
		m_dTimeToReachPos += MarginOfError;
		m_pOwner.getSteering().setTarget(m_vPosition);
		m_pOwner.getSteering().seekOn();
	}

	@Override
	public raven.goals.Goal.CurrentStatus process(double delta){
		//if status is inactive, call Activate()
		activateIfInactive();

		//test to see if the bot has become stuck
		elapsedTime += delta;
		if (isStuck()) {
			m_iStatus = Goal.CurrentStatus.failed;
		}

		//test to see if the bot has reached the waypoint. If so terminate the goal
		else {
			if (m_pOwner.isAtPosition(m_vPosition)) {
				m_iStatus = Goal.CurrentStatus.completed;
			}
		}
		return m_iStatus;
	}

	@Override
	public void terminate(){
		m_pOwner.getSteering().seekOff();
		m_pOwner.getSteering().arriveOff();
		m_iStatus = Goal.CurrentStatus.completed;
	}

	@Override
	public void render(){
		if (m_iStatus == Goal.CurrentStatus.active)
		{
			GameCanvas.greenBrush();
			GameCanvas.blackPen();
			GameCanvas.circle(m_vPosition, 3);
		}

		else if (m_iStatus == Goal.CurrentStatus.inactive)
		{

			GameCanvas.redBrush();
			GameCanvas.blackPen();
			GameCanvas.circle(m_vPosition, 3);
		}
	}



}
