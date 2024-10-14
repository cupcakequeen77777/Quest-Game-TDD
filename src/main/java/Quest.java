import java.util.ArrayList;
import java.util.List;

public class Quest {
    List<Stage> stages;
    int numStages;
    boolean isCompleted;
    int currentStage;

    public Quest(int numStages) {
        this.numStages = numStages;
        stages = new ArrayList<>(numStages);
        isCompleted = false;
        currentStage = 0;
    }

    public void addStage(Stage stage) {
        if (stages.size() < numStages) {
            stages.add(stage);
        } else {
            throw new IllegalStateException("Cannot add more stages than the quest's maximum");
        }
    }


    public boolean isStageValid(int stageIndex) {
        if (stageIndex == 0 && stages.get(stageIndex).isComplete()) { // First stage
            return true; // Only check for non-emptiness
        } else if(stageIndex == 0){
            return false;
        }
        return !stages.get(stageIndex).isEmpty() && stages.get(stageIndex).getValue() > stages.get(stageIndex - 1).getValue();
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("-------------------\n");
        str.append("Quest\n");

        int counter = 1;
        for (Stage stage : stages) {
            str.append("Stage ").append(counter).append(": ").append(stage).append("\n");
            counter++;
        }
        str.append("-------------------\n");

        return str.toString();
    }
}

