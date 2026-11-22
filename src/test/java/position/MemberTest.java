package position;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberTest {

    private Member createMember(LocalDate birthDate, String activityForm) {
        return new Member("Test", birthDate, "Testvej 1", "test@example.com", 12345678, activityForm, 0);
    }

    @Test
    void passiveMemberPays500() {
        // Arrange
        Member member = createMember(LocalDate.of(1966, 11, 22), "Passiv");
        LocalDate date = LocalDate.of(2026, 11, 22);

        // Act
        int actualFee = member.calculateFee(date);

        // Assert
        assertEquals(500, actualFee);
    }

    @Test
    void activeMemberUnder18Pays1000() {
        // Arrange
        Member member = createMember(LocalDate.of(2008, 11, 22), "Motionist");
        LocalDate date = LocalDate.of(2026, 11, 21);

        // Act
        int actualFee = member.calculateFee(date);

        // Assert
        assertEquals(1000, actualFee);
    }

    @Test
    void activeMemberPays1600On18sBirthday() {
        // Arrange
        Member member = createMember(LocalDate.of(2008, 11, 22), "Motionist");
        LocalDate date = LocalDate.of(2026, 11, 22);

        // Act
        int actualFee = member.calculateFee(date);

        // Assert
        assertEquals(1600, actualFee);
    }

    @Test
    void activeMemberPays1600Before60sBirthday() {
        // Arrange
        Member member = createMember(LocalDate.of(1966, 11, 22), "Motionist");
        LocalDate date = LocalDate.of(2026, 11, 21);

        // Act
        int actualFee = member.calculateFee(date);

        // Assert
        assertEquals(1600, actualFee);
    }

    @Test
    void activeMemberPays1200On60Birthday() {
        // Arrange
        Member member = createMember(LocalDate.of(1966, 11, 22), "Motionist");
        LocalDate date = LocalDate.of(2026, 11, 22);

        // Act
        int actualFee = member.calculateFee(date);

        // Assert
        assertEquals(1200, actualFee);
    }
}