package BuildingDesign;

public class PaymentManager {
    private JobManager jobManager;

    public PaymentManager(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    public void processPayment(String address, double amount) {
        BuildingJob job = jobManager.findJobByAddress(address);
        if (job != null) {
            double totalCost = job.getTotalCost();
            double paymentReceived = job.getPaymentsReceived();
            double newTotalPayment = paymentReceived + amount;

            // Handle normal payment and overpayment scenarios
            if (newTotalPayment <= totalCost) {
                job.receivePayment(amount);
                System.out.println("Payment of $" + amount + " received for job at: " + address);
            } else {
                double amountToFullPayment = totalCost - paymentReceived;
                job.receivePayment(amountToFullPayment);
                System.out.println("Full payment received for job at: " + address);
                double overpayment = newTotalPayment - totalCost;
                if (overpayment > 0) {
                    System.out.println("Overpayment received for job at " + address + ". Please process a refund for the overpaid amount of $" + overpayment);
                }
            }

            // Update the job stage based on the current payment status
            job.advanceToNextStageIfNeeded();

            // Determine the current job stage description based on payments
            System.out.println("Building job at " + address + " is currently: " + job.getJobStageDescription());

            // Provide information on remaining balance or overpayment
            double remainingBalance = totalCost - job.getPaymentsReceived();
            if (remainingBalance > 0) {
                System.out.println("The building job at " + address + " is not fully paid yet. Remaining balance: $" + remainingBalance);
            } else {
                System.out.println("The building job at " + address + " is now fully paid.");
            }
        } else {
            System.out.println("No job found at this address: " + address);
        }
    }
}
