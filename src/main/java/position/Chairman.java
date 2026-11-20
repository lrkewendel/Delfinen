package position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Chairman {

    private List<Member> listOfMembers = new ArrayList<>();

    public void creatMember(String name, LocalDate birthDate, String address, String email, int phone, String activityForm, int priceForMembership) {
        Member member = new Member(name, birthDate, address, email, phone, activityForm, priceForMembership);
        listOfMembers.add(member);
    }

    public List<Member> getListOfMembers(){
        return listOfMembers;
    }
}
