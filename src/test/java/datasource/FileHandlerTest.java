package datasource;

import DelfinenSwimTypes.Discipline;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import position.AthleteMember;
import position.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileHandlerTest {

    private static final String TEST_FILE = "test-members.csv";

    @AfterEach
    void deleteTestFile(){ new File(TEST_FILE).delete();}

    @Test
    void savedMembersCanBeLoadedAgain()
        throws FileNotFoundException, UnsupportedEncodingException{

        //Arrange:
        FileHandler fileHandler = new FileHandler(TEST_FILE);
        List<Member> members = new ArrayList<>();

        Member member1 = new Member(1, "Asker", LocalDate.of(1997,12,12), "Kongensgade 1", "Asker12@hotmail.com", 78958900, true, Discipline.CRAWL, 1500);
        Member member2 = new Member(2, "Bo", LocalDate.of(1997, 12, 10), "Dronningegade 1", "Bobo@hotmail.com", 20445566, false, Discipline.BACKSTROKE, 500);
        Member member3 = new Member(3, "Maiken", LocalDate.of(2000, 12, 12), "Nørrebro 1", "Maik12en@hotmail.com", 23068900, false, Discipline.BACKSTROKE, 500);
        Member member4 = new AthleteMember(49, "Jens", LocalDate.of(1997, 05, 12), "Amagerbrogade 1", "Jens2@hotmail.com", 23556700, true, Discipline.BUTTERFLY, 2000, null, 10, 20, null);

        members.add(member1);
        members.add(member2);
        members.add(member3);
        members.add(member4);

        //Act:
        fileHandler.saveMembers(members);
        List<Member> loadedMembers = fileHandler.loadMembers();

        //Assert:
        assertEquals(4, loadedMembers.size());

        Member loadedFirst = loadedMembers.get(0);

        assertEquals(1, loadedFirst.getiD());
        assertEquals("Asker", loadedFirst.getName());
        assertEquals(LocalDate.of(1997, 12, 12), loadedFirst.birthDate());
        assertEquals("Kongensgade 1", loadedFirst.getAddress());
        assertEquals("Asker12@hotmail.com", loadedFirst.getEmail());
        assertEquals(78958900, loadedFirst.getPhone());
        assertEquals(true, loadedFirst.isActive());
        assertEquals(Discipline.CRAWL, loadedFirst.getDiscipline());
        // Kontingent beregnes ud fra alder og medlemsstatus, ikke konstruktørens beløb.
        assertEquals(member1.getPriceForMembership(), loadedFirst.getPriceForMembership());

        Member loadedSecond = loadedMembers.get(1);

        assertEquals(2, loadedSecond.getiD());
        assertEquals("Bo", loadedSecond.getName());
        assertEquals(LocalDate.of(1997, 12, 10), loadedSecond.birthDate());
        assertEquals("Dronningegade 1", loadedSecond.getAddress());
        assertEquals("Bobo@hotmail.com", loadedSecond.getEmail());
        assertEquals(20445566, loadedSecond.getPhone());
        assertEquals(false, loadedSecond.isActive());
        assertEquals(Discipline.BACKSTROKE, loadedSecond.getDiscipline());
        assertEquals(500, loadedSecond.getPriceForMembership());

        Member loadedThird = loadedMembers.get(2);

        assertEquals(3, loadedThird.getiD());
        assertEquals("Maiken", loadedThird.getName());
        assertEquals(LocalDate.of(2000, 12, 12), loadedThird.birthDate());
        assertEquals("Nørrebro 1", loadedThird.getAddress());
        assertEquals("Maik12en@hotmail.com", loadedThird.getEmail());
        assertEquals(23068900, loadedThird.getPhone());
        assertEquals(false, loadedThird.isActive());
        assertEquals(Discipline.BACKSTROKE, loadedThird.getDiscipline());
        assertEquals(500, loadedThird.getPriceForMembership());

        AthleteMember loadedFourth = (AthleteMember) loadedMembers.get(3);

        assertEquals(49, loadedFourth.getiD());
        assertEquals("Jens", loadedFourth.getName());
        assertEquals(LocalDate.of(1997, 05, 12), loadedFourth.birthDate());
        assertEquals("Amagerbrogade 1", loadedFourth.getAddress());
        assertEquals("Jens2@hotmail.com", loadedFourth.getEmail());
        assertEquals(23556700, loadedFourth.getPhone());
        assertEquals(true, loadedFourth.isActive());
        assertEquals(Discipline.BUTTERFLY, loadedFourth.getDiscipline());
        assertEquals(member4.getPriceForMembership(), loadedFourth.getPriceForMembership());
        assertEquals(null, loadedFourth.getTrainer());
        assertEquals(10, loadedFourth.getTrainingResult());
        assertEquals(20, loadedFourth.getCompetitionResult());
        assertEquals(null, loadedFourth.getSuccession());
    }

    @Test
    void savedEmptyListLoadAsEmpty()
        throws FileNotFoundException, UnsupportedEncodingException{

        //Arrange:
        FileHandler fileHandler = new FileHandler(TEST_FILE);
        List<Member> members = new ArrayList<>();

        //Act:
        fileHandler.saveMembers(members);
        List<Member> loadedMembers = fileHandler.loadMembers();

        //Assert:
        assertNotNull(loadedMembers);
        assertTrue(loadedMembers.isEmpty());
    }

    @Test
    void loadingMissingFileThrowsException(){
        //Arrange:
        FileHandler fileHandler = new FileHandler(TEST_FILE);
        new File(TEST_FILE).delete();

        //Act & Assert:
        assertThrows(FileNotFoundException.class, () -> fileHandler.loadMembers());
    }

    @Test
    void brokenLinesAreSkippedAndCounted()
        throws FileNotFoundException, UnsupportedEncodingException{

        //Arrange:
        FileHandler fileHandler = new FileHandler(TEST_FILE);
        PrintStream out = new PrintStream(new File(TEST_FILE), "UTF-8");
        out.println("iD;name;birthDate;address;email;phone;isActive;discipline;priceForMembership;trainingResult;competitionResult");

        out.println("1;Asker;1997-12-12;Kongensgade 1;Asker12@hotmail.com;78958900;true;CRAWL;1500;;");
        out.println("1;Bo;1997-12-10;Dronningegade 1;Bobo@hotmail.com;20445566;false;BACKSTROKE;500;;");
        out.println("1;Maiken;2000-12-12;Nørrebro 1;Maik12en@hotmail.com;23068900;false;BACKSTROKE;500;;");
        out.println("1;Jens;1997-05-12;Amagerbrogade 1;Jens2@hotmail.com;23556700;true;BUTTERFLY;2000;10;20");

        out.println("IKKE_ET_TAL;Bo;1997-12-10;Dronningegade 1;Bobo@hotmail.com;20445566;false;BACKSTROKE;500;;");
        out.println("3;Maiken;ugyldig-dato");

        out.close();

        //Act:
        List<Member> loadedMembers = fileHandler.loadMembers();

        //Assert:
        assertEquals(4, loadedMembers.size());
        assertEquals(1, loadedMembers.get(0).getiD());
        assertEquals(2, fileHandler.getSkippedLineCount());
    }
}
