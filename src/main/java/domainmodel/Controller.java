package domainmodel;
import DelfinenSwimTypes.Discipline;
import position.ActivityType;
import position.Cashier;
import position.Chairman;
import position.Member;
import java.time.LocalDate;
import DelfinenSwimTypes.Discipline;
import DelfinenSwimTypes.SwimTeam;
import datasource.FileHandler;
import position.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Controller {
    FileHandler fileHandler = new FileHandler("members.csv");
    SwimTeam swimTeam = new SwimTeam();
    private final Trainer trainer = new Trainer(swimTeam);
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

    public Map<Discipline, List<AthleteMember>> getTop5Senior() {return trainer.getTop5Senior();}

    public Map<Discipline, List<AthleteMember>> getTop5Junior() {return trainer.getTop5Junior();}

    public List<AthleteMember> getSeniorTeam(){
        return swimTeam.getSeniorTeam();
    }

    public List<AthleteMember> getJuniorTeam(){return swimTeam.getJuniorTeam();}

    public void registerTrainingResult(AthleteMember athlete, int newTime) {trainer.registerTrainingResult(athlete, newTime);}

    public void registerCompetitionResult(AthleteMember athlete, int newTime) {trainer.registerCompetitionResult(athlete, newTime);}

    public AthleteMember findAthleteByID(int iD) {
        for (AthleteMember athlete : trainer.getSeniorTeam()) {
            if (athlete.getiD() == iD) return athlete;
        }
        for (AthleteMember athlete : trainer.getJuniorTeam()) {
            if (athlete.getiD() == iD) return athlete;
        }
        return null;
    }
    public Member creatMember(int ID, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership, ActivityType activityType){
        return chairman.creatMember(ID, name, birthDate, address, email, phone, isActive, discipline, priceForMembership, activityType);
    }
}
