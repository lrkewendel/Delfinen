package position;

import DelfinenSwimTypes.Discipline;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Member {

    private int iD;
    private String name;
    private LocalDate birthDate;
    private String address;
    private String email;
    private int phone;
    private boolean isActive;
    private Discipline discipline;
    private int priceForMembership;
    private boolean paid = false;
    private ActivityType activityType;

    public Member(int iD, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership, ActivityType activityType) {

        validateName(name);
        validateBirthDate(birthDate);
        validateID(iD);
        validateAddress(address);
        validateEmail(email);
        validatePhone(phone);

        if (discipline == null) {
            throw new IllegalArgumentException("Vælg en disciplin.");
        }
        if (priceForMembership < 0) {
            throw new IllegalArgumentException("Kontingent må ikke være negativt.");
        }
        if (activityType == null) {
            throw new IllegalArgumentException("Vælg en aktivitetstype.");
        }

        this.iD = iD;
        this.name = name.strip();
        this.birthDate = birthDate;
        this.address = address.strip();
        this.email = email.strip();
        this.phone = phone;
        this.isActive = isActive;
        this.discipline = discipline;
        this.priceForMembership = priceForMembership;
        this.activityType = activityType;
    }

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Navn må ikke være tomt.");
        }
        String cleanedName = name.strip();
        if(!cleanedName.matches("\\p{L}+(?:[ '\\-’]\\p{L}+)*")){
            throw new IllegalArgumentException("Navnet skal bestå af bogstaver." + " Mellemrum, bindestreg og apostrof må kun stå mellem navnets dele, f.eks: Anne-Marie");
        }
    }

    public static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fødselsdato skal være i dag eller tidligere.");
        }
    }

    public static void validateID(int iD) {
        if (iD <= 0) {
            throw new IllegalArgumentException("Medlemsnummer skal være større end 0.");
        }
    }

    public static void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Adresse må ikke være tom.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email må ikke være tom.");
        }

        if (!email.strip().matches("[^\\s@]+@[^\\s@.]+(?:\\.[^\\s@.]+)+")){
            throw new IllegalArgumentException("Indast en gyldig email, fx Jens@gmail.com");
        }
    }

    public static void validatePhone(int phone) {
        if (phone <= 0) {
            throw new IllegalArgumentException("Telefonnummer skal være et positivt tal.");
        }

    }

    public int getiD() {
        return iD;
    }

    public String getName() {
        return name;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public int getPriceForMembership() {
        priceForMembership = calculateFee(LocalDate.now());
        return priceForMembership;
    }

    public int calculateFee(LocalDate date) {
        if (!isActive) {
            return 500;
        }
        long age = ChronoUnit.YEARS.between(birthDate, date);

        if (age < 18) {
            return 1000;
        }
        if (age >= 60) {
            return 1200;
        }
        return 1600;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

//     Denne konstruktør antages gamle kald uden aktivitetstype som motionister
    public Member(int iD, String name, LocalDate birthDate,
                  String address, String email, int phone,
                  boolean isActive, Discipline discipline,
                  int priceForMembership) {

        this(iD, name, birthDate, address, email, phone,
                isActive, discipline, priceForMembership,
                ActivityType.RECREATIONAL);
    }
    public boolean isPaid() {
        return paid;
    }
    public void registerPayment() {
        paid = true;
    }
}
