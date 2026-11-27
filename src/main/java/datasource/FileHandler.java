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
    private static final String HEADER = "iD;name;birthDate;address;email;phone;isActive;discipline;priceForMembership;trainingResult;trainingDate;competitionResult;paid";
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
        String trainingDate = "";
        String competitionResult = "";

        if(member instanceof AthleteMember athleteMember) {
            trainingResult = String.valueOf(athleteMember.getTrainingResult());
            trainingDate = (athleteMember.getTrainingDate() !=null) ? athleteMember.getTrainingDate().toString() : "";
            competitionResult = String.valueOf(athleteMember.getCompetitionResult());
        }

        return member.getiD() + ";"
                +member.getName() + ";"
                +member.birthDate() + ";"
                +member.getAddress() + ";"
                +member.getEmail() + ";"
                +member.getPhone() + ";"
                +member.isActive() + ";"
                +member.getDiscipline() + ";"
                +member.getPriceForMembership() + ";"
                +trainingResult + ";"
                +trainingDate + ";"
                +competitionResult + ";"
                +member.isPaid();
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

    private Member parseMember(String line) {
        String[] fields = line.split(";", -1);

        if (fields.length != 13) {
            return null;
        }

        try {
            int iD = Integer.parseInt(fields[0]);
            String name = fields[1];
            LocalDate birthDate = LocalDate.parse(fields[2]);
            String address = fields[3];
            String email = fields[4];
            int phone = Integer.parseInt(fields[5]);
            boolean isActive = Boolean.parseBoolean(fields[6]);
            Discipline discipline = Discipline.valueOf(fields[7]);
            int priceForMembership = Integer.parseInt(fields[8]);

            if (!fields[12].equalsIgnoreCase("true") && !fields[12].equalsIgnoreCase("false")) {
                return null;
            }
            boolean paid = Boolean.parseBoolean(fields[12]);
            Member member;

            if (!fields[9].isEmpty() && !fields[11].isEmpty()) {
                int trainingResult = Integer.parseInt(fields[9]);
                LocalDate trainingDate = fields[10].isEmpty() ? null : LocalDate.parse(fields[10]);
                int competitionResult = Integer.parseInt(fields[11]);

                member = new AthleteMember(iD, name, birthDate, address, email, phone, isActive, discipline, priceForMembership, trainingResult, trainingDate, competitionResult);
            }
            else {
                member = new Member(iD, name, birthDate, address, email, phone, isActive, discipline, priceForMembership);
            }
            if (paid) {
                member.registerPayment();
            }
            return member;
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return null;
        }
    }
}