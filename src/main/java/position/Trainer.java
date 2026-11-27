package position;

import DelfinenSwimTypes.Discipline;
import DelfinenSwimTypes.SwimTeam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Trainer {

    private SwimTeam swimTeam;

    public Trainer(SwimTeam swimTeam){this.swimTeam = swimTeam;}

    public boolean registerTrainingResult(AthleteMember athleteMember, int newTime, LocalDate date) {
        if (newTime > 0 && (athleteMember.getTrainingResult() <= 0 || newTime < athleteMember.getTrainingResult())) {
            athleteMember.setTrainingResult(newTime);
            athleteMember.setTrainingDate(date);
            return true;
        }
        return false;
    }

    public void registerCompetitionResult(AthleteMember athleteMember, int newTime){
        if(newTime > 0 && (athleteMember.getCompetitionResult() <=0 || newTime < athleteMember.getCompetitionResult())){
            athleteMember.setCompetitionResult(newTime);
        }
    }

    public Map<Discipline, List<AthleteMember>> getTop5Senior(){
        return swimTeam.getTop5Senior();
    }

    public Map<Discipline, List<AthleteMember>> getTop5Junior(){
        return swimTeam.getTop5Junior();
    }

    public List<AthleteMember> getSeniorTeam(){
        return swimTeam.getSeniorTeam();
    }

    public List<AthleteMember> getJuniorTeam(){
        return swimTeam.getJuniorTeam();
    }
}
