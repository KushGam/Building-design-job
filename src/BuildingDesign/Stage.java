package BuildingDesign;

/**
 * Enum representing the stages of a building job with descriptions and payment thresholds.
 */
public enum Stage {
    STAGE1(1, "Working on Stage 1", 0.15),
    STAGE2(2, "Working on Stage 2", 0.35),
    STAGE3(3, "Working on Stage 3", 0.65),
    COMPLETED(4, "Completed Stage 3", 1.0); // Assumes the last payment is 20% upon completion

    private final int stageNumber;
    private final String description;
    private final double paymentPercentage;

    Stage(int stageNumber, String description, double paymentPercentage) {
        this.stageNumber = stageNumber;
        this.description = description;
        this.paymentPercentage = paymentPercentage;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public String getDescription() {
        return description;
    }

    public double getPaymentPercentage() {
        return paymentPercentage;
    }

    /**
     * Calculates the payment required for this stage based on the total cost of the job.
     */
    public double calculatePaymentForStage(double totalCost) {
        return totalCost * paymentPercentage;
    }

    /**
     * Determines if the received payments are sufficient to cover this stage.
     */
    public boolean isPaymentSufficient(double totalCost, double paymentsReceived) {
        double requiredPayment = calculatePaymentForStage(totalCost);
        return paymentsReceived >= requiredPayment;
    }

    /**
     * Returns the next stage in the sequence, or the current stage if it is the last one.
     */
    public Stage next() {
        if (this.ordinal() + 1 < Stage.values().length) {
            return Stage.values()[this.ordinal() + 1];
        }
        return this; // No further progression beyond COMPLETED
    }
}