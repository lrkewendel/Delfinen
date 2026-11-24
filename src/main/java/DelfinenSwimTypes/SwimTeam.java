package DelfinenSwimTypes;

import position.AthleteMember;
import position.Member;

import java.util.ArrayList;
import java.util.List;

public class SwimTeam {

    private List<AthleteMember> seniorTeam = new ArrayList<>();
    private List<AthleteMember> juniorTeam = new ArrayList<>();

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

    public List<AthleteMember> getSeniorTeam(){
        return seniorTeam;
    }

    public List<AthleteMember> getJuniorTeam(){
        return juniorTeam;
    }
}
