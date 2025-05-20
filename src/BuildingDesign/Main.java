package BuildingDesign;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static JobManager jobManager = new JobManager();
    private static CrewManager crewManager = new CrewManager();
    private static PaymentManager paymentManager = new PaymentManager(jobManager);
    private static void printMainMenu() {
    	 System.out.println("\n--- House-Building Contract-Management Program ---");
         System.out.println("1. Manage Building Jobs");
         System.out.println("2. Manage Work Crews");
         System.out.println("3. Process Payments");
         System.out.println("4. Assign Crew to Job");
         System.out.println("5. Make Payments Towards Building Jobs");
         System.out.println("6. Update Job Stage");
         System.out.println("7. View Building Job Details");
         System.out.println("8. Exit");
        
    }
    public static void main(String[] args) {
        int choice;
        do {
            printMainMenu();
            choice = getInput("Choose an option (1-8): ", 1, 8);
            switch (choice) {
                case 1 -> manageBuildingJobs();
                case 2 -> manageCrews();
                case 3 -> processPayments();
                case 4 -> assignCrewToJob();
                case 5 -> makePaymentToJob();
                case 6 -> updateJobStage();
                case 7 -> viewBuildingJobDetails();
                case 8 -> System.out.println("Exiting system.");
            }
        } while (choice != 8);
        scanner.close();
    }

    private static int getInput(String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    scanner.nextLine(); // Consume newline
                    return input;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("That's not a number. Please enter a number.");
                scanner.next(); // consume the non-int input
            }
        }
    }

    
    private static void manageBuildingJobs() {
        boolean back = false;
        while (!back) {
            System.out.println("\nBuilding Job Management:");
            System.out.println("1. Add New Building Job");
            System.out.println("2. List All Active Building Jobs"); // Updated menu option
            System.out.println("3. List All Building Jobs");
            System.out.println("4. Return to Main Menu");
            int choice = getInput();
            switch (choice) {
                case 1:
                    addNewBuildingJob();
                    break;
                case 2:
                    jobManager.listActiveJobs(); // Call the new method here
                    break;
                case 3:
                    jobManager.listAllJobs();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageCrews() {
        boolean back = false;
        while (!back) {
            System.out.println("\nWork Crew Management:");
            System.out.println("1. Add Work Crew");
            System.out.println("2. List All Work Crews");
            System.out.println("3. List Crews by Stage");
            System.out.println("4. Assign Crew to Job");
            System.out.println("5. Return to Main Menu");
            int choice = getInput();
            switch (choice) {
                case 1:
                    addWorkCrew();
                    break;
                case 2:
                    crewManager.listAllCrews();
                    break;
                case 3:
                    listCrewsByStage();
                    break;  
                case 4:
                    assignCrewToJob();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void listCrewsByStage() {
        System.out.print("Enter the stage number (1-3) to list crews for: ");
        int stageNumber = getInput();
        if (stageNumber >= 1 && stageNumber <= 3) {
            Stage stage = Stage.values()[stageNumber - 1];
            crewManager.listCrewsByStage(stage);
        } else {
            System.out.println("Invalid stage number. Please enter a number between 1 and 3.");
        }
    }

    private static void processPayments() {
        System.out.println("\nPayment Processing:");
        System.out.println("1. Process Payment for a Job");
        System.out.println("2. Return to Main Menu");
        int choice = getInput();
        switch (choice) {
            case 1:
                processPayment();
                break;
            case 2:
                // Return to Main Menu
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static int getInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a number. Please enter a number.");
            scanner.next(); // consume the non-int input
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }
    private static void addNewBuildingJob() {
        scanner.nextLine(); // Clear the buffer before taking new line input
        System.out.print("Enter address of the new building job (street number,street name, suburb/town): ");
        String address = scanner.nextLine();
        System.out.print("Enter the total cost for the complete house build: ");
        double cost = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer after reading a number
        BuildingJob job = new BuildingJob(address, cost);
        jobManager.addBuildingJob(job);
        System.out.println("Building job added successfully.");
    }
    private static void addWorkCrew() {
        System.out.print("Enter name of the work crew: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact name: ");
        String contactName = scanner.nextLine();
        System.out.print("Enter contact phone: ");
        String contactPhone = scanner.nextLine();
        System.out.print("Enter stage number (1-3) the crew is qualified for: ");
        int stageNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left by nextInt()
        if (stageNumber < 1 || stageNumber > 3) {
            System.out.println("Invalid stage number. Please enter a number between 1 and 3.");
            return;
        }
        WorkCrew crew = new WorkCrew(name, contactName, contactPhone, Stage.values()[stageNumber - 1]);
        crewManager.addWorkCrew(crew);
        System.out.println("Work crew added successfully.");
    }
    
    private static void assignCrewToJob() {
        // List all eligible jobs that are ready for a crew to be assigned
        List<BuildingJob> eligibleJobs = jobManager.getEligibleJobsForCrewAssignment();
        
        if (eligibleJobs.isEmpty()) {
            System.out.println("No eligible jobs available for crew assignment.");
            return;
        }

        // Display eligible jobs to the user
        for (int i = 0; i < eligibleJobs.size(); i++) {
            BuildingJob job = eligibleJobs.get(i);
            System.out.println((i + 1) + ". " + job.getAddress() + " [" + job.getJobStageDescription() + "]");
        }

        System.out.print("Enter the number of the job to assign a crew to: ");
        int jobChoice = getInput() - 1; // Convert to 0-based index

        // Validate job choice
        if (jobChoice < 0 || jobChoice >= eligibleJobs.size()) {
            System.out.println("Invalid job selection.");
            return;
        }

        BuildingJob selectedJob = eligibleJobs.get(jobChoice);
        Stage currentStage = selectedJob.getCurrentStage();

        // Display eligible crews to the user
        List<WorkCrew> eligibleCrews = crewManager.getCrewsForStage(currentStage);
        if (eligibleCrews.isEmpty()) {
            System.out.println("No eligible work crews available for this stage.");
            return;
        }

        System.out.println("Please select a work crew to assign to the job at " + selectedJob.getAddress() + ":");
        for (int i = 0; i < eligibleCrews.size(); i++) {
            WorkCrew crew = eligibleCrews.get(i);
            System.out.println((i + 1) + ". " + crew.getCrewName() + " [contact: " + crew.getContactPhone() + " (" + crew.getContactName() + ")]");
        }

        System.out.print("Enter the number of the crew to assign: ");
        int crewChoice = getInput() - 1; // Convert to 0-based index

        // Validate crew choice
        if (crewChoice < 0 || crewChoice >= eligibleCrews.size()) {
            System.out.println("Invalid crew selection.");
            return;
        }

        WorkCrew selectedCrew = eligibleCrews.get(crewChoice);

        // Assign the selected crew to the job
        if (crewManager.assignCrewToJob(selectedCrew, selectedJob)) {
            System.out.println("Assigned " + selectedCrew.getCrewName() + " to job at " + selectedJob.getAddress() + ".");
        } else {
            System.out.println("Failed to assign crew. Either the job is not in the correct stage or another error occurred.");
        }
    }
    private static void processPayment() {
        scanner.nextLine(); // Clear the buffer
        System.out.print("Enter address of the job: ");
        String address = scanner.nextLine();
        System.out.print("Enter payment amount: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Payment amount must be greater than zero.");
            return;
        }
        paymentManager.processPayment(address, amount);
    }
    private static void makePaymentToJob() {
        List<BuildingJob> eligibleJobs = jobManager.listEligibleJobsForPayment();
        
        if (eligibleJobs.isEmpty()) {
            System.out.println("No jobs available for payment.");
            return;
        }

        System.out.println("Please select a building job:");
        for (int i = 0; i < eligibleJobs.size(); i++) {
            BuildingJob job = eligibleJobs.get(i);
            System.out.println((i + 1) + ". " + job.getAddress() + " [" + job.getJobStageDescription() + "]");
        }

        System.out.print("Enter your choice: ");
        int jobChoice = getInput() - 1;

        if (jobChoice < 0 || jobChoice >= eligibleJobs.size()) {
            System.out.println("Invalid job selection.");
            return;
        }

        BuildingJob selectedJob = eligibleJobs.get(jobChoice);
        Stage currentStage = selectedJob.getCurrentStage();

        double stageCost = currentStage.calculatePaymentForStage(selectedJob.getTotalCost());
        double amountOwing = stageCost - selectedJob.getPaymentsReceived();

        System.out.println("The total cost of " + currentStage.getDescription() + " for the building job at " + selectedJob.getAddress() + " is $" + stageCost);
        System.out.println("The amount owing is $" + amountOwing);

        System.out.print("How much is being paid now? ");
        double paymentAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Check if the payment is sensible
        if (paymentAmount <= 0 || paymentAmount > amountOwing + 1) {
            System.out.println("Invalid payment amount.");
            return;
        }

        jobManager.receivePaymentForJob(selectedJob.getAddress(), paymentAmount);
        amountOwing = stageCost - selectedJob.getPaymentsReceived(); // Recalculate owing amount after payment
        System.out.println("New amount owing is $" + amountOwing);
    }
    private static void updateJobStage() {
        List<BuildingJob> ongoingJobs = jobManager.getOngoingJobs();
        
        if (ongoingJobs.isEmpty()) {
            System.out.println("No ongoing jobs available for updating.");
            return;
        }

        System.out.println("Select a building job to update the stage:");
        for (int i = 0; i < ongoingJobs.size(); i++) {
            System.out.println((i + 1) + ". " + ongoingJobs.get(i).getAddress() + " [" + ongoingJobs.get(i).getCurrentStage().getDescription() + "]");
        }

        System.out.print("Enter the number of the job to update: ");
        int jobIndex = getInput() - 1;

        if (jobIndex < 0 || jobIndex >= ongoingJobs.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        BuildingJob selectedJob = ongoingJobs.get(jobIndex);
        System.out.println("You have selected the building job at " + selectedJob.getAddress() + ". Confirm update? (yes/no):");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            jobManager.moveToNextStage(selectedJob);
        } else {
            System.out.println("Job stage update canceled.");
        }
    }
    private static void viewBuildingJobDetails() {
        List<BuildingJob> incompleteJobs = jobManager.listIncompleteJobs(); // Method to list incomplete jobs

        if (incompleteJobs.isEmpty()) {
            System.out.println("There are no incomplete jobs to display.");
            return;
        }

        System.out.println("Please select a building job to view details:");
        for (int i = 0; i < incompleteJobs.size(); i++) {
            System.out.println((i + 1) + ". " + incompleteJobs.get(i).getAddress() + " [" + incompleteJobs.get(i).getJobStageDescription() + "]");
        }

        System.out.print("Enter your choice: ");
        int jobChoice = getInput() - 1; // Adjust for 0-based index

        if (jobChoice < 0 || jobChoice >= incompleteJobs.size()) {
            System.out.println("Invalid job selection.");
            return;
        }

        BuildingJob selectedJob = incompleteJobs.get(jobChoice);

        // Now, display the details for the selected job
        System.out.println("Report about building job at " + selectedJob.getAddress());
        System.out.println("Current stage: " + selectedJob.getCurrentStage().getDescription());
        System.out.println("Work crews assigned to this job:");
        selectedJob.getAssignedCrewsForStages().forEach((stage, crew) -> 
            System.out.println(stage.getDescription() + ": " + crew.getCrewName()));
        System.out.println("Total cost of job: $" + selectedJob.getTotalCost());
        System.out.println("Total paid: $" + selectedJob.getPaymentsReceived());
        System.out.println("Amount owing: $" + (selectedJob.getTotalCost() - selectedJob.getPaymentsReceived()));
    }
}