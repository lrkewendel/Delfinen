package domainmodel;
import DelfinenSwimTypes.Discipline;
import position.ActivityType;
import position.Cashier;
import position.Chairman;
import position.Member;
import java.time.LocalDate;
import DelfinenSwimTypes.SwimTeam;
import datasource.FileHandler;
import position.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;


public class Controller {
    FileHandler fileHandler = new FileHandler("members.csv");
    SwimTeam swimTeam = new SwimTeam();
    private final Trainer trainer = new Trainer(swimTeam);
    private final Cashier cashier = new Cashier();
    private final Chairman chairman = new Chairman();
    private List<Member> loadedMembers = chairman.getListOfMembers();

    public List<Member> getMembers() {
        return chairman.getListOfMembers();
    }

    public int getMembershipFee(Member member) {
        return cashier.calculateMembershipFee(member, LocalDate.now());
    }
    public Member creatMember(int ID, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership, ActivityType activityType) throws IOException {
        Member member = chairman.creatMember(ID, name, birthDate, address, email, phone, isActive, discipline, priceForMembership, activityType);
        saveMembers();
        return member;
        }

    public boolean registerPayment (int memberId) throws IOException {
        for (Member member : loadedMembers) {
            if (member.getiD() == memberId) {
                cashier.managePayment(member);
                saveMembers();
                return true;
            }
        }
        return false;
    }

    //Indlæser medlemmerne fra filen og inddeler AthleteMembers i hold efter alder:
    public void sortAthletes()
            throws FileNotFoundException {
        swimTeam.sortMembers(loadedMembers);
    }

    public Map<Discipline, List<AthleteMember>> getTop5Senior() {return trainer.getTop5Senior();}

    public Map<Discipline, List<AthleteMember>> getTop5Junior() {return trainer.getTop5Junior();}

    public List<AthleteMember> getSeniorTeam(){
        return swimTeam.getSeniorTeam();
    }

    public List<AthleteMember> getJuniorTeam(){return swimTeam.getJuniorTeam();}

    public boolean registerTrainingResult(AthleteMember athlete, int newTime, LocalDate date) {
        boolean isBetter = trainer.registerTrainingResult(athlete, newTime, date);
        if (isBetter) {
            saveLoadedMembers();
        }
        return isBetter;
    }

    public void registerCompetitionResult(AthleteMember athlete, int newTime) {trainer.registerCompetitionResult(athlete, newTime); saveLoadedMembers();}

    public void loadMembers() throws IOException {
        List<Member> members = fileHandler.loadMembers();

        if (fileHandler.getSkippedLineCount() > 0) {
            throw new IOException("Nogle medlemsdata kunne ikke indlæses. Kontroller CSV-filen");
        }
        loadedMembers.clear();
        loadedMembers.addAll(members);
    }

    public void saveMembers () throws IOException {
        fileHandler.saveMembers(loadedMembers);
    }

    private void saveLoadedMembers() {
        try {
            if (!loadedMembers.isEmpty()) {
                fileHandler.saveMembers(loadedMembers);
            }
        } catch (Exception e) {}
    }

    public AthleteMember findAthleteByID(int iD) {
        for (AthleteMember athlete : trainer.getSeniorTeam()) {
            if (athlete.getiD() == iD) return athlete;
        }
        for (AthleteMember athlete : trainer.getJuniorTeam()) {
            if (athlete.getiD() == iD) return athlete;
        }
        return null;
    }
}
