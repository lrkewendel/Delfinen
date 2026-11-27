package position;

import DelfinenSwimTypes.Discipline;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Chairman {
    private List<Member> listOfMembers = new ArrayList<>();

    public Member creatMember(int iD, String name, LocalDate birthDate,
                              String address, String email, int phone,
                              boolean isActive, Discipline discipline,
                              int priceForMembership, ActivityType activityType) {

        Member member = new Member(
                iD, name, birthDate, address, email, phone,
                isActive, discipline, priceForMembership, activityType
        );
        listOfMembers.add(member);
        return member;
    }

    public List<Member> getListOfMembers() {
        return listOfMembers;
    }
}


