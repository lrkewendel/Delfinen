package position;

import DelfinenSwimTypes.Discipline;

import java.time.LocalDate;

public class AthleteMember extends Member{

    private int trainingResult;
    private int competitionResult;

    public AthleteMember(int iD, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership, int trainingResult, int competitionResult){
        super(iD, name, birthDate, address, email, phone, isActive, discipline, priceForMembership);
        this.trainingResult = trainingResult;
        this.competitionResult = competitionResult;
    }

    public boolean isActiveInDiscipline(Discipline discipline){return getDiscipline() == discipline;}

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
}
