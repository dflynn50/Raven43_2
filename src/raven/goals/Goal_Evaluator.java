package raven.goals;

import raven.game.RavenBot;
import raven.goals.Goal.GoalType;

public abstract class Goal_Evaluator {

	private Double bias;

	private GoalType goalTypeToAdd;

	public Goal_Evaluator(Double bias, GoalType type) {
		this.bias = bias;
		goalTypeToAdd = type;
		//goalTypeToAdd = Goal.GoalType.goal_pursuit;
	}


	public abstract double calculateDesirability(RavenBot m_pOwner);


	public abstract void setGoal(RavenBot m_pOwner);

	public GoalType getGoalType() { return goalTypeToAdd; }
	public void setGoalType(GoalType type) { goalTypeToAdd = type; }
	
	public Double getBias() {
		return bias;
	}


}
