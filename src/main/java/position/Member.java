package position;

import java.time.LocalDate;
import java.time.Period;

public class Member {

    private String name;
    private LocalDate birthDate;
    private String address;
    private String email;
    private int phone;
    private String activityForm;
    private int priceForMembership;

    public Member(String name, LocalDate birthDate, String address, String email, int phone, String activityForm, int priceForMembership){
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.activityForm = activityForm;
        this.priceForMembership = priceForMembership;
    }

    public String getName(){
        return name;
    }

    public LocalDate birthDate(){
        return birthDate;
    }

    public int getAge(){
       return Period.between (birthDate, LocalDate.now()).getYears();
    }

    public String getActivityForm(){
        return activityForm;
    }

    public int getPriceForMembership(){
        return priceForMembership;
    }

}
