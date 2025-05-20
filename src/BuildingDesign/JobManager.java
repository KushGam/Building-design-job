package BuildingDesign;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobManager {
    private List<BuildingJob> jobs;

    // Constructor
    public JobManager() {
        this.jobs = new ArrayList<>();
    }
    

    // Method to add a new BuildingJob to the list
    public void addBuildingJob(BuildingJob job) {
        if (job != null && findJobByAddress(job.getAddress()) == null) {
            jobs.add(job);
            System.out.println("New building job added at address: " + job.getAddress());
        } else {
            System.out.println("A job at this address already exists or job is null.");
        }
    }
 // Method to list all active jobs (not completed or not fully paid)
    public void listActiveJobs() {
        System.out.println("Active Building Jobs:");
        for (BuildingJob job : jobs) {
            if (job.isActive()) {
                String stageDescription = job.getJobStageDescription();
                System.out.println(job.getAddress() + " [" + stageDescription + "]");
            }
        }
    }
    // Method to receive a payment for a specific job and advance the stage if appropriate
    public void receivePaymentForJob(String address, double amount) {
        BuildingJob job = findJobByAddress(address);
        if (job != null && amount > 0) {
            job.receivePayment(amount);
            System.out.println("Payment of $" + amount + " received for job at: " + address);
            if (job.getCurrentStage().isPaymentSufficient(job.getTotalCost(), job.getPaymentsReceived())) {
                job.advanceToNextStage();
            }
        } else {
            System.out.println("No job found at this address: " + address);
        }
    }


    // Method to advance the stage of a building job, if conditions are met
    public void advanceJobStage(String address) {
        BuildingJob job = findJobByAddress(address);
        if (job != null) {
            job.advanceToNextStage();
            System.out.println("Job at address " + address + " advanced to " + job.getCurrentStage().getDescription());
        } else {
            System.out.println("No job found at this address: " + address);
        }
    }

    // Method to find a job by its address
   
    public BuildingJob findJobByAddress(String address) {
        return jobs.stream()
                   .filter(job -> job.getAddress().equalsIgnoreCase(address.trim()))
                   .findFirst()
                   .orElse(null);
    }
    // Method to list all jobs with their current status
    public void listAllJobs() {
        if (jobs.isEmpty()) {
            System.out.println("No building jobs currently managed.");
        } else {
            System.out.println("Current building jobs:");
            jobs.forEach(job -> System.out.println("Address: " + job.getAddress() +
                ", Stage: " + job.getCurrentStage().getDescription() + 
                ", Payments Received: $" + job.getPaymentsReceived()));
        }
    }

    // Method to get a detailed report on a specific job
    public void getJobReport(String address) {
        BuildingJob job = findJobByAddress(address);
        if (job != null) {
            job.reportProgress();
        } else {
            System.out.println("No job found at this address: " + address);
        }
    }
    public void listSummaryOfJobStages() {
        System.out.println("Summary of Building Jobs:");
        for (BuildingJob job : jobs) {
            // Check if the job is not completed
            if (!job.isComplete()) {
                Stage currentStage = job.getCurrentStage();
                String stageDescription = currentStage.getDescription(); // This gets the stage description according to payment received.
                System.out.println(job.getAddress() + " [" + stageDescription + "]");
            }
        }
    }
   
    public List<BuildingJob> getEligibleJobsForCrewAssignment() {
        return jobs.stream()
                   .filter(job -> !job.isComplete() && job.getJobStageDescription().contains("Preparing"))
                   .collect(Collectors.toList());
    }
    public List<BuildingJob> listEligibleJobsForPayment() {
        return jobs.stream()
                    .filter(job -> !job.isComplete())
                    .collect(Collectors.toList());
    }
 // Retrieves a list of ongoing jobs that are in progress and not yet completed
    public List<BuildingJob> getOngoingJobs() {
        return jobs.stream()
                   .filter(job -> !job.isComplete() && (job.isPaymentCompleteForStage() || job.isCrewAssignedForStage()))
                   .collect(Collectors.toList());
    }

    // Check if a job can progress to "doing" the work
    public boolean canProgressToDoing(BuildingJob job) {
        return !job.isComplete() &&
               job.isPaymentCompleteForStage() && 
               job.isCrewAssignedForStage();
    }

    // Move a job to the next stage
    public void moveToNextStage(BuildingJob job) {
        if (canProgressToDoing(job)) {
            job.advanceToNextStage();
            System.out.println("Building job at " + job.getAddress() + " has moved to the next stage: " + job.getCurrentStage().getDescription());
        } else {
            System.out.println("Building job at " + job.getAddress() + " cannot progress to 'doing' work due to incomplete prerequisites.");
        }
    }
    public List<BuildingJob> listIncompleteJobs() {
        return jobs.stream()
                   .filter(job -> !job.isComplete())
                   .collect(Collectors.toList());
    }
}
