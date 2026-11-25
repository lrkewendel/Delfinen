package domainmodel;
import DelfinenSwimTypes.Discipline;
import position.ActivityType;
import position.Cashier;
import position.Chairman;
import position.Member;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;


public class Controller {
    private final Cashier cashier = new Cashier();
    private final Chairman chairman = new Chairman();

    public List<Member> getMembers() {
        return chairman.getListOfMembers();
    }

    public int getMembershipFee(Member member) {
        return cashier.calculateMembershipFee(member, LocalDate.now());
    }
    public Member creatMember(int ID, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership, ActivityType activityType){
        return chairman.creatMember(ID, name, birthDate, address, email, phone, isActive, discipline, priceForMembership, activityType);
    }
}
