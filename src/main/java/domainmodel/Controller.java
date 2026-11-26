package domainmodel;
import DelfinenSwimTypes.SwimTeam;
import datasource.FileHandler;
import position.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    FileHandler fileHandler = new FileHandler("members.csv");
    SwimTeam swimTeam = new SwimTeam();
    private Trainer trainer = new Trainer("Bo", swimTeam);
    private final Cashier cashier = new Cashier();
    private final Chairman chairman = new Chairman();

    public List<Member> getMembers() {
        return chairman.getListOfMembers();
    }

    public int getMembershipFee(Member member) {
        return cashier.calculateMembershipFee(member, LocalDate.now());
    }

    //Indlæser medlemmerne fra filen og inddeler AthleteMembers i hold efter alder:
    public void sortAthletes()
        throws FileNotFoundException{
        List<Member> members = fileHandler.loadMembers();
        swimTeam.sortMembers(members);
    }

    public List<AthleteMember> getSeniorTeam(){
        return swimTeam.getSeniorTeam();
    }

    public List<AthleteMember> getJuniorTeam(){
        return swimTeam.getJuniorTeam();
    }
}
