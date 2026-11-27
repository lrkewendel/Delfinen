package DelfinenSwimTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import position.AthleteMember;
import position.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwimTeamTest {

    private SwimTeam swimTeam;

    @BeforeEach
    void setUp() {
        swimTeam = new SwimTeam();
    }

    @Test
    void testGetTop5ForDiscipline_SortsAndLimitsTo5() {

        //Arrange:
        List<AthleteMember> team = List.of(
                createAthleteMember(1, "A", 20, Discipline.CRAWL, 55, 0),
                createAthleteMember(2, "B", 21, Discipline.CRAWL, 48, 50),
                createAthleteMember(3, "C", 22, Discipline.CRAWL, 62, 65),
                createAthleteMember(4, "D", 23, Discipline.CRAWL, 52, 50),
                createAthleteMember(5, "E", 24, Discipline.CRAWL, 53, 54),
                createAthleteMember(6, "F", 25, Discipline.CRAWL, 51, 0)
        );

        //Act:
        List<AthleteMember> top5 = swimTeam.getTop5ForDiscipline(team, Discipline.CRAWL);

        //Assert:
        assertEquals(5, top5.size(), "Listen må max indeholde 5 svømmere");
        assertEquals(48, top5.get(0).getResults());
        assertEquals(50, top5.get(1).getResults());
        assertEquals(51, top5.get(2).getResults());
        assertEquals(53, top5.get(3).getResults());
        assertEquals(55, top5.get(4).getResults());
    }

    @Test
    void testGetTop5ForDiscipline_OutfilterInvalidAndWrongResultsForDiscipline() {

        //Arrange:
        List<AthleteMember> team = List.of(
                createAthleteMember(1, "Ingen tider", 20, Discipline.CRAWL, 0, 0),
                createAthleteMember(2, "Negative tider", 20, Discipline.CRAWL, -5, -10),
                createAthleteMember(3, "Forkert disciplin", 20, Discipline.BACKSTROKE, 40, 42),
                createAthleteMember(4, "Kun konkurrencetid", 20, Discipline.CRAWL, 0, 52),
                createAthleteMember(5, "Kun træningstid", 20, Discipline.CRAWL, 49, 0)
        );

        //Act:
        List<AthleteMember> top5 = swimTeam.getTop5ForDiscipline(team, Discipline.CRAWL);

        //Assert:
        assertEquals(2, top5.size());
        assertEquals(49, top5.get(0).getResults());
        assertEquals(52, top5.get(1).getResults());
    }

    @Test
    void testGetTop5SeniorAndJunior_SeparatesTeamsCorrectly() {

        //Arrange:
        List<Member> membersFromFile = new ArrayList<>();
        membersFromFile.add(createAthleteMember(1, "Junior 1", 17, Discipline.BUTTERFLY, 50, 52));
        membersFromFile.add(createAthleteMember(2, "Junior 2", 15, Discipline.BUTTERFLY, 49, 0));
        membersFromFile.add(createAthleteMember(3, "Senior 1 (grænse 18 år)", 18, Discipline.BUTTERFLY, 47, 48));
        membersFromFile.add(createAthleteMember(4, "Senior 2", 25, Discipline.BUTTERFLY, 55, 46));

        //Act:
        swimTeam.sortMembers(membersFromFile);
        Map<Discipline, List<AthleteMember>> top5Senior = swimTeam.getTop5Senior();
        Map<Discipline, List<AthleteMember>> top5Junior = swimTeam.getTop5Junior();

        //Assert Senior:
        List<AthleteMember> seniorButterfly = top5Senior.get(Discipline.BUTTERFLY);
        assertEquals(2, seniorButterfly.size());
        assertEquals(46, seniorButterfly.get(0).getResults());
        assertEquals(47, seniorButterfly.get(1).getResults());

        //Assert Junior:
        List<AthleteMember> juniorButterfly = top5Junior.get(Discipline.BUTTERFLY);
        assertEquals(2, juniorButterfly.size());
        assertEquals(49, juniorButterfly.get(0).getResults());
        assertEquals(50, juniorButterfly.get(1).getResults());

        // Assert at discipliner uden svømmere returnerer en tom liste:
        assertTrue(top5Senior.get(Discipline.BREASTSTROKE).isEmpty());
    }

    //Hjælpemetode til oprettelse af medlemmer:
    private AthleteMember createAthleteMember(int id, String name, int age, Discipline discipline, int trainingResult, int competitionResult) {
        LocalDate birthDate = LocalDate.now().minusYears(age);
        return new AthleteMember(
                id,
                name,
                birthDate,
                "Testvej 1",
                "test@mail.dk",
                12345678,
                true,
                discipline,
                1600,
                trainingResult,
                competitionResult
        );
    }
}
