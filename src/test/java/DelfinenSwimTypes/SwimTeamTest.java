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
        List<AthleteMember> team = List.of(
                createAthleteMember(1, "A", 20, Discipline.CRAWL, 5500, 0),
                createAthleteMember(2, "B", 21, Discipline.CRAWL, 4800, 5000),
                createAthleteMember(3, "C", 22, Discipline.CRAWL, 6200, 6500),
                createAthleteMember(4, "D", 23, Discipline.CRAWL, 5000, 5200),
                createAthleteMember(5, "E", 24, Discipline.CRAWL, 5300, 5400),
                createAthleteMember(6, "F", 25, Discipline.CRAWL, 5100, 0)
        );

        List<AthleteMember> top5 = swimTeam.getTop5ForDiscipline(team, Discipline.CRAWL);

        assertEquals(5, top5.size(), "Listen må max indeholde 5 svømmere");
        assertEquals(4800, top5.get(0).getTrainingResult());
        assertEquals(5000, top5.get(1).getTrainingResult());
        assertEquals(5100, top5.get(2).getTrainingResult());
        assertEquals(5300, top5.get(3).getTrainingResult());
        assertEquals(5500, top5.get(4).getTrainingResult());
    }

    @Test
    void testGetTop5ForDiscipline_OutfilterInvalidAndWrongResultsForDiscipline() {
        List<AthleteMember> team = List.of(
                createAthleteMember(1, "Ingen tider", 20, Discipline.CRAWL, 0, 0),
                createAthleteMember(2, "Negative tider", 20, Discipline.CRAWL, -500, -1000),
                createAthleteMember(3, "Forkert disciplin", 20, Discipline.BACKSTROKE, 4000, 4200),
                createAthleteMember(4, "Kun konkurrencetid", 20, Discipline.CRAWL, 0, 5200),
                createAthleteMember(5, "Kun træningstid", 20, Discipline.CRAWL, 4900, 0)
        );

        List<AthleteMember> top5 = swimTeam.getTop5ForDiscipline(team, Discipline.CRAWL);

        assertEquals(1, top5.size());
        assertEquals(4900, top5.get(0).getTrainingResult());
    }

    @Test
    void testGetTop5SeniorAndJunior_SeparatesTeamsCorrectly() {
        List<Member> membersFromFile = new ArrayList<>();
        membersFromFile.add(createAthleteMember(1, "Junior 1", 17, Discipline.BUTTERFLY, 5000, 5200));
        membersFromFile.add(createAthleteMember(2, "Junior 2", 15, Discipline.BUTTERFLY, 4900, 0));
        membersFromFile.add(createAthleteMember(3, "Senior 1 (grænse 18 år)", 18, Discipline.BUTTERFLY, 4700, 4800));
        membersFromFile.add(createAthleteMember(4, "Senior 2", 25, Discipline.BUTTERFLY, 4600, 5500));

        swimTeam.sortMembers(membersFromFile);
        Map<Discipline, List<AthleteMember>> top5Senior = swimTeam.getTop5Senior();
        Map<Discipline, List<AthleteMember>> top5Junior = swimTeam.getTop5Junior();

        List<AthleteMember> seniorButterfly = top5Senior.get(Discipline.BUTTERFLY);
        assertEquals(2, seniorButterfly.size());
        assertEquals(4600, seniorButterfly.get(0).getTrainingResult());
        assertEquals(4700, seniorButterfly.get(1).getTrainingResult());

        List<AthleteMember> juniorButterfly = top5Junior.get(Discipline.BUTTERFLY);
        assertEquals(2, juniorButterfly.size());
        assertEquals(4900, juniorButterfly.get(0).getTrainingResult());
        assertEquals(5000, juniorButterfly.get(1).getTrainingResult());

        assertTrue(top5Senior.get(Discipline.BREASTSTROKE).isEmpty());
    }

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
                LocalDate.now(),
                competitionResult
        );
    }
}
