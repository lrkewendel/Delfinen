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

    public Member(int iD, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership){
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fødselsdatoen må ikke være i fremtiden");
        }
        this.iD = iD;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.discipline = discipline;
        this.priceForMembership = priceForMembership;
    }

    public int getiD() {return iD;}

    public String getName(){
        return name;
    }

    public LocalDate birthDate(){
        return birthDate;
    }

    public int getAge(){
       return Period.between (birthDate, LocalDate.now()).getYears();
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {return phone;}

    public boolean isActive(){return isActive;}

    public Discipline getDiscipline() {
        return discipline;
    }

    public int getPriceForMembership(){
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
}
