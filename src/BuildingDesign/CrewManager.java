package BuildingDesign;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CrewManager {
    private List<WorkCrew> crews;
    public List<WorkCrew> getCrews() {
        return new ArrayList<>(crews); // Return a copy of the crews list
    }
    
    // Constructor
    public CrewManager() {
        this.crews = new ArrayList<>();
       
        
    }

 

    // Method to add a work crew
    public void addWorkCrew(WorkCrew crew) {
        if (crew != null && !crewExists(crew)) {
            crews.add(crew);
            System.out.println("Work crew added: " + crew.getCrewName());
        } else {
            System.out.println("Work crew already exists or is invalid.");
        }
    }
    private boolean crewExists(WorkCrew crew) {
        return crews.stream().anyMatch(c -> c.getCrewName().equalsIgnoreCase(crew.getCrewName()));
    }

    public boolean assignCrewToJob(WorkCrew crew, BuildingJob job) {
        if (crew == null || job == null) {
            System.out.println("Invalid crew or job. Assignment cannot proceed.");
            return false;
        }

        if (job.canAssignCrew(crew.getStageQualifiedFor())) {
            job.assignCrewToStage(crew, crew.getStageQualifiedFor());
            System.out.println("Crew " + crew.getCrewName() + " successfully assigned to job at " + job.getAddress());
            return true;
        } else {
            System.out.println("Crew " + crew.getCrewName() + " not qualified for current stage: " + job.getCurrentStage().getDescription());
            return false;
        }
    }


    // Method to get all crews qualified for a particular stage
    public List<WorkCrew> getCrewsForStage(Stage stage) {
        return crews.stream()
                    .filter(crew -> crew.getStageQualifiedFor() == stage)
                    .collect(Collectors.toList());
    }
 // Method to print out the crews qualified for a particular stage
    public void listCrewsByStage(Stage stage) {
        List<WorkCrew> qualifiedCrews = crews.stream()
                                              .filter(crew -> crew.getStageQualifiedFor() == stage)
                                              .collect(Collectors.toList());

        if (qualifiedCrews.isEmpty()) {
            System.out.println("No crews are qualified to work on stage " + stage.getStageNumber() + ".");
        } else {
            System.out.println("Crews qualified for stage " + stage.getStageNumber() + ":");
            qualifiedCrews.forEach(crew -> System.out.println(crew.getCrewName() + 
                                                              " [" + crew.getContactPhone() + 
                                                              " (" + crew.getContactName() + ")]"));
        }
    }

    // List all work crews
    public void listAllCrews() {
        if (crews.isEmpty()) {
            System.out.println("No work crews are currently registered.");
        } else {
            System.out.println("Listing all registered work crews:");
            crews.forEach(crew -> System.out.println(crew.toString()));
        }
    }
  
}


