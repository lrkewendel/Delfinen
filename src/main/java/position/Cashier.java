package position;
import java.time.LocalDate;
public class Cashier {

    public int managePayment(){
        throw new UnsupportedOperationException("Ikke implementeret endnu");
    }

    public int manageDiscount(){
        throw new UnsupportedOperationException("Ikke implementeret endnu");
    }

    public int membersOverdue(){
        throw new UnsupportedOperationException("Ikke implementeret endnu");
    }

    public int annual(){
        throw new UnsupportedOperationException("Ikke implementeret endnu");
    }

    public int calculateMembershipFee(Member member, LocalDate date) {
        return member.calculateFee(date);
    }
}
