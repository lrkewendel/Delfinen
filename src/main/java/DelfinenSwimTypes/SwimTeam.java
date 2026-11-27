package DelfinenSwimTypes;

import position.AthleteMember;
import position.Member;
import position.Trainer;

import java.util.*;

public class SwimTeam {

    private List<AthleteMember> seniorTeam = new ArrayList<>();
    private List<AthleteMember> juniorTeam = new ArrayList<>();

    public SwimTeam(){}

    public void sortMembers(List<Member> MembersFromFile) {

        for (Member member : MembersFromFile) {
            if (member instanceof AthleteMember athleteMember) {
                if (athleteMember.getAge() >= 18) {
                    seniorTeam.add(athleteMember);
                } else {
                    juniorTeam.add(athleteMember);
                }
            }
        }
    }

    public List<AthleteMember> getTop5ForDiscipline (List<AthleteMember> team, Discipline discipline){
        return team.stream()
                .filter(athleteMember -> athleteMember.getDiscipline() == discipline)
                .filter(athleteMember -> athleteMember.getResults() > 0)
                .sorted(Comparator.comparingInt(AthleteMember::getResults))
                .limit(5)
                .toList();
    }

    public Map<Discipline, List<AthleteMember>> getTop5Senior(){
        Map<Discipline, List<AthleteMember>> top5Map = new EnumMap<>(Discipline.class);
        for (Discipline discipline : Discipline.values()){
            top5Map.put(discipline, getTop5ForDiscipline(seniorTeam, discipline));
        } return top5Map;
    }

    public Map<Discipline, List<AthleteMember>> getTop5Junior(){
        Map<Discipline, List<AthleteMember>> top5Map = new EnumMap<>(Discipline.class);
        for (Discipline discipline : Discipline.values()){
            top5Map.put(discipline, getTop5ForDiscipline(juniorTeam, discipline));
        } return top5Map;
    }

    public List<AthleteMember> getSeniorTeam(){
        return seniorTeam;
    }

    public List<AthleteMember> getJuniorTeam(){
        return juniorTeam;
    }
}
