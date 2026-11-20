package datasource;

import DelfinenSwimTypes.Discipline;
import position.AthleteMember;
import position.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    private String fileName;
    private static final String HEADER = "name;birthDate;address;email;phone;isActive;discipline;priceForMembership;trainingResult;competitionResult";
    private int skippedLineCount;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public void saveMembers(List<Member> members)
            throws FileNotFoundException, java.io.UnsupportedEncodingException {
        try (PrintStream out = new PrintStream(new File(fileName), "UTF-8")) {
            out.println(HEADER);

            for (Member member : members) {
                out.println(toCsvLine(member));
            }
            out.close();
        }
    }

    private String toCsvLine(Member member){

        String trainingResult = "";
        String competitionResult = "";

        if(member instanceof AthleteMember athleteMember) {
            trainingResult = String.valueOf(athleteMember.getTrainingResult());
            competitionResult = String.valueOf(athleteMember.getCompetitionResult());
        }

        return member.getName() + ";"
                +member.birthDate() + ";"
                +member.getAddress() + ";"
                +member.getEmail() + ";"
                +member.getPhone() + ";"
                +member.isActive() + ";"
                +member.getDiscipline() + ";"
                +member.getPriceForMembership() + ";"
                +trainingResult + ";"
                +competitionResult;
    }

    public int getSkippedLineCount() {
        return skippedLineCount;
    }

    public List<Member>loadMembers()
        throws FileNotFoundException{

        List<Member> members = new ArrayList<>();
        skippedLineCount = 0;

        Scanner fileScanner = new Scanner(new File(fileName), "UTF-8");
        boolean firstLine = true;

        while (fileScanner.hasNextLine()){
            String line = fileScanner.nextLine();

            if(firstLine && line.equals(HEADER)){
                firstLine = false;
                continue;
            }

            firstLine = false;
            Member member = parseMember(line);

            if(member != null){
                members.add(member);
            } else {
                skippedLineCount++;
            }
        }
        fileScanner.close();
        return members;
    }

    private Member parseMember(String line){
        String[] fields = line.split(";", -1);

        if(fields.length != 10){
            return null;
        }

        try {
            String name = fields[0];
            LocalDate birthDate = LocalDate.parse(fields[1]);
            String address = fields[2];
            String email = fields[3];
            int phone = Integer.parseInt(fields[4]);
            boolean isActive = Boolean.parseBoolean(fields[5]);
            Discipline discipline = Discipline.valueOf(fields[6]);
            int priceForMembership = Integer.parseInt(fields[7]);

            if(!fields[8].isEmpty() && !fields[9].isEmpty()){

                int trainingResult = Integer.parseInt(fields[8]);
                int competitionResult = Integer.parseInt(fields[9]);

                return new AthleteMember(name, birthDate, address, email, phone, isActive, discipline, priceForMembership, null, trainingResult, competitionResult, null);
            } else {
                return new Member(name, birthDate, address, email, phone, isActive, discipline, priceForMembership);
            }
        }

        catch (IllegalArgumentException e){
            return null;
        }

        catch (DateTimeParseException e){
            return null;
        }
    }
}