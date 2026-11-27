package UI;

import DelfinenSwimTypes.Discipline;
import domainmodel.Controller;
import position.ActivityType;
import datasource.FileHandler;
import position.Member;
import DelfinenSwimTypes.Discipline;

import java.lang.classfile.attribute.SourceDebugExtensionAttribute;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

public class UserInterface {

    private Scanner scanner;
    private Controller controller;

    public UserInterface(){
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.controller = new Controller();
    }

    //Starter programmet:
    public void startProgram(){
        boolean running = true;
        welcomeMessage();

        while (running){
            System.out.println("Vælg mellem en af den følgende stillinger: ");
            System.out.println("| Formand | Kasserer | Træner |");

            String chosenPosition = scanner.nextLine().strip();

            // NYT: Brugeren får en besked før næste indtastning
            System.out.println("Opret som formand eller Se som kasserer/træner");
            String choice = scanner.nextLine().strip();
            String chosenPosition = scanner.nextLine();

            if (chosenPosition.equalsIgnoreCase("Formand")){
                String choice = scanner.nextLine();
                switch (choice){
                    // NYT: Starter medlemsoprettelsen
                    case "Opret" -> createMember();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }

            if (chosenPosition.equalsIgnoreCase("Kasserer")){
                String choice = scanner.nextLine();
                switch (choice){
                    case "Se" -> showMembershipFees();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }

            if (chosenPosition.equalsIgnoreCase("Træner")){

                System.out.println("Vælg en af følgende handlinger: ");
                System.out.println("| Se oversigt over Top 5 | Registrer en svømmetid |");
                String choice = scanner.nextLine();

                switch (choice.toLowerCase()){
                    case "se", "se oversigt over top 5" -> showTop5();
                    case "registrer", "registrer en svømmetid" -> registerSwimResult();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }
        }
    }

    // NYT: Indsamler oplysninger og opretter medlem
    private void createMember(){
        while (true){
            System.out.println("Opret nyt medlem");

            int iD = readInt("Indtast medlemsnummer:");
            String name = readText("Indtast medlemmets navn:");
            LocalDate birthDate = readBirthDate();
            String address = readText("Indtast adresse:");
            String email = readText("Indtast email:");
            int phone = readInt("Indtast telefonnummer:");

            boolean isActive = readMembershipType();
            ActivityType activityType = readActivityType();
            Discipline discipline = readDiscipline();

            // Member beregner kontigent  i getPriceForMembership()
            int priceForMembership = 0;

            try {
                Member member = controller.creatMember(iD, name, birthDate, address, email, phone, isActive, discipline, priceForMembership, activityType);
                System.out.println("Medlemmet " + member.getName() + " er oprettet med medlemsnummer " + member.getiD() + ".");
                //Afslutter metoden og vender tilbage til menuen
                return;

            } catch (IllegalArgumentException e) {
                // Viser fejlbeskeden fra Member
                System.out.println("Medlemmet blev ikke oprettet: " + e.getMessage());
                System.out.println("Indtast oplysningerne igen.");

            }
        }
    }

    // NYT: Læser tekst. Member validere indholdet
    private String readText(String message){
        System.out.println(message);
        return scanner.nextLine();
    }
    // NYT: Omdanner brugerens input til et heltal
    private int readInt(String message){
        while(true){
            System.out.println(message);

            try {
                return Integer.parseInt(scanner.nextLine().strip());
            }  catch (NumberFormatException e){
                System.out.println("Indast et gyldigt heltal.");
            }
        }
    }

    // NYT: Omdanner brugerens input til en dato
    private LocalDate readBirthDate(){
        while(true){
            System.out.println("Indast fødselsdato (åååå-mm-dd): ");
            try {
                return LocalDate.parse(scanner.nextLine().strip());
            }  catch (DateTimeParseException e){
                System.out.println("Ugyldig dato. Skriv eksempelvis 2003-03-03");
            }
        }
    }

    // NYT Vælger aktivt eller passivt medlemskab
    private boolean readMembershipType(){
        while (true){
            System.out.println("Vælg medlemskabstype: ");
            System.out.println("1. Aktiv");
            System.out.println("2. Passiv");

            switch (scanner.nextLine().strip()){
                case "1":
                    return true;
                case "2":
                    return false;
                default:
                    System.out.println("Du skal vælge 1 eller 2");
            }
        }
    }

    // NYT: Vælger motionist eller konkurrencesvømmer
    private ActivityType readActivityType() {
        while (true) {
            System.out.println("Vælg aktivitetstype:");
            System.out.println("1. Motionist");
            System.out.println("2. Konkurrencesvømmer");

            switch (scanner.nextLine().strip()) {
                case "1":
                    return ActivityType.RECREATIONAL;
                case "2":
                    return ActivityType.COMPETITIVE;
                default:
                    System.out.println("Du skal vælge 1 eller 2.");

            }
        }
    }
    // NYT: Vælger én disciplin til jeres nuværende Member-konstruktør
    private Discipline readDiscipline() {
        while (true) {
            System.out.println("Vælg disciplin:");
            System.out.println("1. Butterfly");
            System.out.println("2. Crawl");
            System.out.println("3. Rygcrawl");
            System.out.println("4. Brystsvømning");

            switch (scanner.nextLine().strip()) {
                case "1":
                    return Discipline.BUTTERFLY;
                case "2":
                    return Discipline.CRAWL;
                case "3":
                    return Discipline.BACKSTROKE;
                case "4":
                    return Discipline.BREASTSTROKE;
                default:
                    System.out.println("Du skal vælge et tal fra 1 til 4.");
            }
        }
    private void printTop5Team(String teamTitle, Map<Discipline, List<AthleteMember>> top5Map){
        System.out.println("\n=== Top 5 over: " + teamTitle + " ===");

        for(Discipline discipline : Discipline.values()){
            System.out.println("\nDisciplin: " + discipline);
            List<AthleteMember> swimmers = top5Map.get(discipline);

            if (swimmers == null || swimmers.isEmpty()){
                System.out.println("Der er ingen træningstid registreret for svømmerne i denne disciplin.");
            } else {
                for (int i = 0; i < swimmers.size(); i++){
                    AthleteMember s = swimmers.get(i);
                    System.out.println(" " + (i + 1) + ". " + s.getName() + " (iD nummer: " + s.getiD() + "). Tid: " + s.getTrainingResult() + " sekunder.");
                }
            }
        }
    }

    private void registerSwimResult(){
        try{
            controller.sortAthletes();
        } catch (FileNotFoundException e){
            System.out.println("Medlemmer kunne desvære ikke indlæses.");
            return;
        }

        System.out.println("Indtast svømmer iD: ");
        int iD;
        try{
            iD = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("iD skal være et tal.");
            return;
        }

        AthleteMember athleteMember = controller.findAthleteByID(iD);
        if(athleteMember == null){
            System.out.println("Ingen iD match for " + iD + " fundet.");
            return;
        }

        System.out.println("Du har valgt: " + athleteMember.getName() + ". (Aktiv i disciplin: " + athleteMember.getDiscipline() + ").");
        System.out.println("Vælg en disciplin (BUTTERFLY, CRAWL, BACKSTROKE, BREASTSTROKE): ");
        Discipline chosenDiscipline;
        try{
            chosenDiscipline = Discipline.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e){
            System.out.println("Det er ikke en gyldig disciplin.");
            return;
        }

        if(!athleteMember.isActiveInDiscipline(chosenDiscipline)){
            System.out.println("Afvist! Svømmer er ikke aktiv i den disciplin.");
            return;
        }

        System.out.println("Er det træningstiden eller stævnetiden, der skal ændres?");
        String type = scanner.nextLine();

        try{
            if(type.equalsIgnoreCase("Træningstiden")){
                System.out.println("Indtast tid i sekunder: ");
                int newTime = Integer.parseInt(scanner.nextLine());

                controller.registerTrainingResult(athleteMember, newTime);
                System.out.println("Tiden er registreret.");
            } else if(type.equalsIgnoreCase("Stævnetiden")) {
                System.out.println("Indtast tid i sekunder: ");
                int newTime = Integer.parseInt(scanner.nextLine());

                controller.registerCompetitionResult(athleteMember, newTime);
                System.out.println("Tiden er registreret.");
            } else {
                System.out.println("Ugyldigt valg! Indtast (træningstiden) eller (stævnetiden).");
            }
        } catch (NumberFormatException e){
            System.out.println("Tiden skal være et helt tal.");
        }
    }

    private void showTop5(){
        try{
            controller.sortAthletes();
        } catch (FileNotFoundException e){
            System.out.println("Medlemmer kunne desvære ikke indlæses.");
            return;
        }

        printTop5Team("Juniorholdet", controller.getTop5Junior());
        printTop5Team("Seniorholdet", controller.getTop5Senior());
    }

    private void welcomeMessage(){
        //Farver og figur til pynt:
        String LIGHTBLUE = "\u001B[38;5;39m";
        String DARKBLUE = "\u001B[38;5;21m";
        String RESET = "\u001B[0m";
        String dolphin = "\uD83D\uDC2C";

        //Printer pæn overskrift:
        String[] HeadLine = {
                "██████╗ ███████╗██╗     ███████╗██╗███╗   ██╗███████╗███╗   ██╗",
                "██╔══██╗██╔════╝██║     ██╔════╝██║████╗  ██║██╔════╝████╗  ██║",
                "██║  ██║█████╗  ██║     █████╗  ██║██╔██╗ ██║█████╗  ██╔██╗ ██║",
                "██║  ██║██╔══╝  ██║     ██╔══╝  ██║██║╚██╗██║██╔══╝  ██║╚██╗██║",
                "██████╔╝███████╗███████╗██║     ██║██║ ╚████║███████╗██║ ╚████║",
                "╚═════╝ ╚══════╝╚══════╝╚═╝     ╚═╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═══╝"
        };

        for (String line : HeadLine) {
            for (char symbol : line.toCharArray()) {
                if (symbol == '█') {
                    System.out.print(LIGHTBLUE + symbol);
                } else if (symbol != ' ') {
                    System.out.print(DARKBLUE + symbol);
                } else {
                    System.out.print(symbol);
                }
            }
            System.out.println(RESET);
        }

        System.out.println("\n                Velkommen til svømmeklubben" + dolphin + "\n\n");
    }
    private void showMembershipFees() {
        List<Member> members = controller.getMembers();

        if (members.isEmpty()) {
            System.out.println("Der er ingen medlemmer endnu");
            return;
        }
        System.out.println("Medlemmernes årlige kontingent: ");

        for (Member member : members) {
            int fee = controller.getMembershipFee(member);

            System.out.println(member.getName() + ":" + fee);
        }
    }
}
