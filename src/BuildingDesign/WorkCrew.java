package BuildingDesign;

/**
 * Class representing a work crew with details about their qualification for job stages.
 */
public class WorkCrew {
    private String crewName;
    private String contactName;
    private String contactPhone;
    private Stage stageQualifiedFor;

    public WorkCrew(String crewName, String contactName, String contactPhone, Stage stageQualifiedFor) {
        setCrewName(crewName);
        setContactName(contactName);
        setContactPhone(contactPhone);
        setStageQualifiedFor(stageQualifiedFor);
    }

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        if (crewName == null || crewName.trim().isEmpty()) {
            throw new IllegalArgumentException("Crew name cannot be empty.");
        }
        this.crewName = crewName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        if (contactName == null || contactName.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name cannot be empty.");
        }
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        if (contactPhone == null || contactPhone.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact phone cannot be empty.");
        }
        this.contactPhone = contactPhone;
    }

    public Stage getStageQualifiedFor() {
        return stageQualifiedFor;
    }

    public void setStageQualifiedFor(Stage stageQualifiedFor) {
        if (stageQualifiedFor == null) {
            throw new IllegalArgumentException("Stage qualified for cannot be null.");
        }
        this.stageQualifiedFor = stageQualifiedFor;
    }

    @Override
    public String toString() {
        return String.format("WorkCrew Name: %s, Contact Name: %s, Contact Phone: %s, Qualified for: %s",
                crewName, contactName, contactPhone, stageQualifiedFor.getDescription());
    }
}