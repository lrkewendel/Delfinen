package domainmodel;
import position.*;

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
}
