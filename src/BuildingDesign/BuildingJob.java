package BuildingDesign;

import java.util.HashMap;
import java.util.Map;

public class BuildingJob {
    private String address;
    private Stage currentStage;
    private double totalCost;
    private double paymentsReceived;
    private Map<Stage, WorkCrew> assignedCrewsForStages;

    // Constructor
    public BuildingJob(String address, double totalCost) {
        this.address = address;
        this.currentStage = Stage.STAGE1; // Initial stage, assuming all jobs start from Stage 1
        this.totalCost = totalCost;
        this.paymentsReceived = 0.0; // Initially, no payments have been received
        this.assignedCrewsForStages = new HashMap<>();
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getPaymentsReceived() {
        return paymentsReceived;
    }

    public void receivePayment(double amount) {
        if (isComplete()) {
            System.out.println("This job is already fully paid and completed.");
            return;
        }
        
        this.paymentsReceived += amount;
        
        if (paymentsReceived > totalCost) {
            System.out.println("Overpayment detected. Returning excess amount: $" + (paymentsReceived - totalCost));
            paymentsReceived = totalCost; // Adjust to not exceed total cost
        }
    }
    public Map<Stage, WorkCrew> getAssignedCrewsForStages() {
        return assignedCrewsForStages;
    }
    private boolean isPaymentSufficientForNextStage() {
        double requiredPayment = currentStage.calculatePaymentForStage(totalCost);
        return paymentsReceived >= requiredPayment;
    }

    public boolean canAssignCrew(Stage qualifiedStage) {
        String stageDescription = getJobStageDescription().toLowerCase();
        String preparingPrefix = "preparing for " + qualifiedStage.getDescription().toLowerCase();
        String workingPrefix = "working on " + qualifiedStage.getDescription().toLowerCase();

        boolean canAssign = stageDescription.contains(preparingPrefix) || stageDescription.contains(workingPrefix);
        System.out.println("Checking if can assign crew to " + qualifiedStage + ": " + canAssign + " (Current stage description: " + stageDescription + ")");
        return canAssign;
    }
    public void assignCrewToStage(WorkCrew crew, Stage stage) {
        if (canAssignCrew(stage)) {
            assignedCrewsForStages.put(stage, crew);
            System.out.println("Crew " + crew.getCrewName() + " assigned to " + stage.getDescription() + " at " + address);
        } else {
            System.out.println("This crew is not qualified for this stage or the stage is not ready for assignment.");
        }
    }
    public void advanceToNextStage() {
        if (currentStage != Stage.COMPLETED && isPaymentSufficientForNextStage()) {
            currentStage = currentStage.next();
            System.out.println("Building job at " + address + " has advanced to " + currentStage.getDescription());
        } else {
            System.out.println("Cannot advance to the next stage. Check if payment is sufficient and the current stage is not completed.");
        }
    }
    
    		
    public void advanceToNextStageIfNeeded() {
        String currentDescription = getJobStageDescription();
        if (currentDescription.contains("Preparing") || currentDescription.contains("Working")) {
           
        }
    }  
    
 // Method to get the stage description based on the payments received
    public String getJobStageDescription() {
        double paymentPercentage = (paymentsReceived / totalCost) * 100;

        if (paymentPercentage >= 100) {
            return "The owner is allowed to take possession and move in";
        } else if (paymentPercentage >= 65) {
            return "Working on Stage 3";
        } else if (paymentPercentage > 35) {
            return "Preparing for Stage 3";
        } else if (paymentPercentage >= 35) { 
            return "Working on Stage 2";
        } else if (paymentPercentage > 15) {
            return "Preparing for Stage 2";
        } else if (paymentPercentage >=15) { 
            return "Working on Stage 1";
        } else {
            return "Preparing for Stage 1";
        }
    }

    // Reporting the progress of the building job
    public void reportProgress() {
        System.out.println("Building Job at " + address);
        System.out.println("Current Stage: " + currentStage.getDescription());
        System.out.println("Total Cost: $" + totalCost);
        System.out.println("Payments Received: $" + paymentsReceived);
        System.out.println("Remaining Balance: $" + (totalCost - paymentsReceived));
        if (!assignedCrewsForStages.isEmpty()) {
            System.out.println("Crews Assigned:");
            assignedCrewsForStages.forEach((stage, crew) ->
                System.out.println("Stage " + stage.getStageNumber() + ": " + crew.getCrewName() + " (Contact: " + crew.getContactName() + ")"));
        }
    }

    // Check if the building job is complete, meaning all stages finished and full payment received
    public boolean isComplete() {
        return currentStage == Stage.COMPLETED && paymentsReceived >= totalCost;
    }

    // Check if the building job is active, meaning it is still in progress or not fully paid
    public boolean isActive() {
        return currentStage != Stage.COMPLETED || paymentsReceived < totalCost;
    }
    // Checks if payment for the current stage is complete
    public boolean isPaymentCompleteForStage() {
        double requiredPayment = currentStage.calculatePaymentForStage(totalCost);
        return paymentsReceived >= requiredPayment;
    }
    // Checks if a work crew has been assigned for the current stage
    public boolean isCrewAssignedForStage() {
        return assignedCrewsForStages.containsKey(currentStage);
    }

}
