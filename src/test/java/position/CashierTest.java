package position;

import DelfinenSwimTypes.Discipline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class CashierTest {

    private Member member;
    private Cashier cashier;

    @BeforeEach
    void setUp() {
        member = new Member(1, "Test", LocalDate.of(2000, 1, 1), "Test 1", "test@test.dk", 12345678, true, Discipline.CRAWL, 0);
        cashier = new Cashier();
    }

    @Test
    void newMemberHasNotPaid() {
        // Arrange: medlem i setup

        // Act
        boolean actualPaid = member.isPaid();

        // Assert
        assertFalse(actualPaid);
    }

    @Test
    void registeringPaymentMarksMemberAsPaid() {
        // Arrange:  oprettet i setup

        // Act
        cashier.managePayment(member);

        // Assert
        assertTrue(member.isPaid());
    }
}
