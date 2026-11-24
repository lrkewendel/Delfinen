package position;

import DelfinenSwimTypes.Discipline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private Member youngMember;
    private Member olderMember;
    private Member passiveYoungMember;
    private Member passiveAdultMember;
    private Member passiveOlderMember;

    @BeforeEach
    void setUp() {
        youngMember = new Member(18,"Ung aktiv", LocalDate.of(2008, 11, 22), "Testvej 1", "ung@example.com", 12345678, true, Discipline.CRAWL,0);

        olderMember = new Member(60,"Ældre aktiv", LocalDate.of(1966, 11, 22), "Testvej 2", "ældre@example.com", 12345678, true, Discipline.CRAWL,0);

        passiveYoungMember = new Member(17,"Ung passiv", LocalDate.of(2009, 11, 22), "Testvej 3", "passivung@example.com", 12345678, false, Discipline.CRAWL,0);

        passiveAdultMember = new Member(30,"Voksen passiv", LocalDate.of(1996, 11, 22), "Testvej 4", "passivvoksen@example.com", 12345678, false, Discipline.CRAWL,0);

        passiveOlderMember = new Member(60,"Ældre passiv", LocalDate.of(1966, 11, 22), "Testvej 5", "passivældre@example.com", 12345678, false, Discipline.CRAWL,0);
    }

    @Test
    void passiveMemberUnder18Pays500() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 11, 22);

        // Act
        int actualFee = passiveYoungMember.calculateFee(date);

        // Assert
        assertEquals(500, actualFee);
    }

    @Test
    void passiveMemberBetween18And59Pays500() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 11, 22);

        // Act
        int actualFee = passiveAdultMember.calculateFee(date);

        // Assert
        assertEquals(500, actualFee);
    }

    @Test
    void passiveMemberAt60Pays500() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 11, 22);

        // Act
        int actualFee = passiveOlderMember.calculateFee(date);

        // Assert
        assertEquals(500, actualFee);
    }

    @Test
    void activeMemberDayBeforeEighteenthBirthdayPays1000() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 11, 21);

        // Act
        int actualFee = youngMember.calculateFee(date);

        // Assert
        assertEquals(1000, actualFee);
    }

    @Test
    void activeMemberOnEighteenthBirthdayPays1600() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 11, 22);

        // Act
        int actualFee = youngMember.calculateFee(date);

        // Assert
        assertEquals(1600, actualFee);
    }

    @Test
    void activeMemberDayBeforeSixtiethBirthdayPays1600() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 11, 21);

        // Act
        int actualFee = olderMember.calculateFee(date);

        // Assert
        assertEquals(1600, actualFee);
    }

    @Test
    void activeMemberOnSixtiethBirthdayPays1200() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 11, 22);

        // Act
        int actualFee = olderMember.calculateFee(date);

        // Assert
        assertEquals(1200, actualFee);
    }

    @Test
    void futureBirthDateIsRejected() {
        // Arrange
        LocalDate futureBirthDate = LocalDate.now().plusDays(1);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new Member(0,"Test", futureBirthDate, "Testvej 1", "test@example.com", 12345678, true, Discipline.BUTTERFLY,0));
    }
}