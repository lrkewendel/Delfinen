package position;

import DelfinenSwimTypes.Discipline;

import java.time.LocalDate;

public class AthleteMember extends Member{

    private Trainer trainer;
    private int trainingResult;
    private int competitionResult;
    private String succession;

    public AthleteMember(int iD, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership, Trainer trainer, int trainingResult, int competitionResult, String succession){
        super(iD, name, birthDate, address, email, phone, isActive, discipline, priceForMembership, ActivityType.COMPETITIVE);
        this.trainer = trainer;
        this.trainingResult = trainingResult;
        this.competitionResult = competitionResult;
        this.succession = succession;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public int getTrainingResult(){
        return trainingResult;
    }

    public int getCompetitionResult() {
        return competitionResult;
    }

    public void setTrainingResult(int trainingResult) {
        this.trainingResult = trainingResult;
    }

    public void setCompetitionResult(int competitionResult) {
        this.competitionResult = competitionResult;
    }

    public int getResults(){
        if (trainingResult <= 0) return competitionResult;
        if (competitionResult <= 0) return trainingResult;
        return Math.min(trainingResult, competitionResult);
    }

    public boolean isActiveInDiscipline(Discipline discipline){return getDiscipline() == discipline;}
}
