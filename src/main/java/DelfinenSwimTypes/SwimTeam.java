package DelfinenSwimTypes;

import position.AthleteMember;

public class SwimTeam {

    private Discipline discipline;
    private boolean teamType;
    private AthleteMember athlete;

    public SwimTeam(Discipline discipline, boolean teamType, AthleteMember athlete){
        this.discipline = discipline;
        this.teamType = teamType;
        this.athlete = athlete;
    }

    public boolean isTeamType(){
        return teamType;
    }
}
