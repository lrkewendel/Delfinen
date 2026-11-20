package position;

import java.time.LocalDate;

public class AthleteMember extends Member{

    private Trainer trainer;
    private int trainingResult;
    private int competitionResult;
    private String succession;

    public AthleteMember(String name, LocalDate birthDate, String address, String email, int phone, String activityForm, int priceForMembership, Trainer trainer, int trainingResult, int competitionResult, String succession){
        super(name, birthDate, address, email, phone, activityForm, priceForMembership);
        this.trainer = trainer;
        this.trainingResult = trainingResult;
        this.competitionResult = competitionResult;
        this.succession = succession;
    }

    public int getTrainingResult(){
        return trainingResult;
    }

    public int getCompetitionResult() {
        return competitionResult;
    }

    public String getSuccession(){
        return succession;
    }
}
