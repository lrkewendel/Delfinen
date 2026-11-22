package position;

import DelfinenSwimTypes.Discipline;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Chairman {

    private List<Member> listOfMembers = new ArrayList<>();

    public void creatMember(int iD, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership) {
        Member member = new Member(iD, name, birthDate, address, email, phone, isActive, discipline, priceForMembership);
        listOfMembers.add(member);
    }

    public List<Member> getListOfMembers(){
        return listOfMembers;
    }
}
