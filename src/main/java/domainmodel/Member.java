package domainmodel;

import java.time.LocalDate;

public class Member {
    private String name;
    private LocalDate birthDate;
    private MembershipType membershipType;

    public Member(String name, LocalDate birthDate, MembershipType membershipType) {
        // Man skal skrive bogstaver, ellers kommer der exception
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Navn må ikke være tomt");
        }
        // Afviser kontroltegn i navnet
        if (name.chars().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("Navn må ikke indeholde tabulatorer eller linjeskift");
        }
        // Kontrollere fødselsdatoen (Man kan ikke have en fødselsdag i fremtiden)
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Fødselsdato skal være i dag eller tidligere.");
        }
        // Kontrollere medlemskabstypen
        if (membershipType == null) {
            throw new IllegalArgumentException(
                    "Vælg aktivt eller passivt medlemskab.");
        }
        // Gemmer de godkendte oplysninger
        this.name = name.strip();
        this.birthDate = birthDate;
        this.membershipType = membershipType;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }
}
