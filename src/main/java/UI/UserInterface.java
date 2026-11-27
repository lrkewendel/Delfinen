package UI;

import DelfinenSwimTypes.Discipline;
import domainmodel.Controller;
import position.*;
import datasource.FileHandler;
import DelfinenSwimTypes.Discipline;
import java.lang.classfile.attribute.SourceDebugExtensionAttribute;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.io.IOException;

public class UserInterface {

    private Scanner scanner;
    private Controller controller;

    public UserInterface() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.controller = new Controller();
    }

    //Starter programmet:
    public void startProgram() {
        boolean running = true;
        welcomeMessage();
        try {
            controller.loadMembers();
        }
        catch (FileNotFoundException e) {
            System.out.println("Ingen medlemsfil fundet. Tom medlemsliste kører.");
        } catch (IOException e) {
            System.out.println("Medlemmerne kunne ikke indlæses: " + e.getMessage());
            return;
        }

        while (running) {

            System.out.println("Vælg mellem en af de følgende stillinger ved at indtaste stillingsbetegnelsen: ");
            System.out.println("| Formand | Kasserer | Træner |");

            String chosenPosition = scanner.nextLine();

            if (chosenPosition.equalsIgnoreCase("Formand")) {
                System.out.println("Vælge opret eller se som kasserer / træner ");
                String choice = scanner.nextLine();
                switch (choice) {
                    // NYT: Starter medlemsoprettelsen
                    case "Opret" -> createMember();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }

            if (chosenPosition.equalsIgnoreCase("Kasserer")){
                System.out.println("Vælg en af de følgende handlinger: ");
                System.out.println("| Se kontingenter | Registrer betaling |");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "Se" -> showMembershipFees();
                    case "registrer", "registrer betaling" -> registerPayment();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }

            if (chosenPosition.equalsIgnoreCase("Træner")) {
                System.out.println("Vælg en af følgende handlinger: ");
                System.out.println("| Se oversigt over Top 5 | Registrer en svømmetid |");
                String choice = scanner.nextLine();

                switch (choice.toLowerCase()) {
                    case "se", "se oversigt over top 5" , "top 5" -> showTop5();
                    case "registrer", "registrer en svømmetid" -> registerSwimResult();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }
        }
    }
    private void registerPayment() {
        System.out.println("Indtast medlemsnummer:");
        int memberId;

        try {
            memberId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Medlemsnummer skal være helt tal...");
            return;
        }
        try {
            boolean registered = controller.registerPayment(memberId);

            if (registered) {
                System.out.println("Betalingen er registreret for medlem " + memberId);
            } else {
                System.out.println("Der findes ikke et medlem med det nummer " + memberId);
            }
        } catch(IOException e){
                System.out.println("Betalingen er registreret, men kunne ikke gemmes i filen");
        }
    }

    // NYT: Indsamler oplysninger og opretter medlem
    private void createMember() {
        while (true) {
            System.out.println("Opret nyt medlem");

            int iD = readInt("Indtast medlemsnummer:", Member::validateID);
            String name = readText("Indtast medlemmets navn:", Member::validateName);
            LocalDate birthDate = readBirthDate();
            String address = readText("Indtast adresse:", Member::validateAddress);
            String email = readText("Indtast email:", Member::validateEmail);
            int phone = readInt("Indtast telefonnummer:", Member::validatePhone);

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

            } catch (IOException e) {
                System.out.println("Medlemmet er oprettet, men kunne ikke gemmes i filen");
                return;
            }
        }
    }

    // Gentager kun det aktuelle felt, indtil Members validering accepterer det.
    private String readText(String message, Consumer<String> validator) {
        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();
            try {
                validator.accept(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // NYT: Omdanner brugerens input til et heltal
    private int readInt(String message, IntConsumer validator) {
        while (true) {
            System.out.println(message);

            try {
                int input = Integer.parseInt(scanner.nextLine().strip());
                validator.accept(input);
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Indtast et gyldigt heltal.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // NYT: Omdanner brugerens input til en dato
    private LocalDate readBirthDate() {
        while (true) {
            System.out.println("Indast fødselsdato (åååå-mm-dd): ");
            try {
                LocalDate birthDate = LocalDate.parse(scanner.nextLine().strip());
                Member.validateBirthDate(birthDate);
                return birthDate;
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig dato. Skriv eksempelvis 2003-03-03");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // NYT Vælger aktivt eller passivt medlemskab
    private boolean readMembershipType() {
        while (true) {
            System.out.println("Vælg medlemskabstype: ");
            System.out.println("1. Aktiv");
            System.out.println("2. Passiv");

            switch (scanner.nextLine().strip()) {
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
    }

    private void printTop5Team(String teamTitle, Map<Discipline, List<AthleteMember>> top5Map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("\n=== Top 5 over: " + teamTitle + " ===");

        for (Discipline discipline : Discipline.values()) {
            System.out.println("\nDisciplin: " + discipline);
            List<AthleteMember> swimmers = top5Map.get(discipline);

            if (swimmers == null || swimmers.isEmpty()) {
                System.out.println("Der er ingen træningstid registreret for svømmerne i denne disciplin.");
            } else {
                for (int i = 0; i < swimmers.size(); i++) {
                    AthleteMember s = swimmers.get(i);
                    System.out.println(" " + (i + 1) + ". " + s.getName() + " (iD nummer: " + s.getiD() + "). Tid: " + s.getTrainingResult() + " sekunder.");
                }
            }
        }
    }

    private void registerSwimResult() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            controller.sortAthletes();
        } catch (FileNotFoundException e) {
            System.out.println("Medlemmer kunne ikke indlæses.");
            return;
        }

        System.out.println("Indtast svømmer iD: ");
        int iD;
        try {
            iD = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("iD skal være et tal.");
            return;
        }

        AthleteMember athleteMember = controller.findAthleteByID(iD);
        if (athleteMember == null) {
            System.out.println("Ingen iD match for " + iD + " fundet.");
            return;
        }

        System.out.println("Du har valgt: " + athleteMember.getName() + ". (Aktiv i disciplin: " + athleteMember.getDiscipline() + ").");
        System.out.println("Vælg en disciplin: | BUTTERFLY | CRAWL | BACKSTROKE | BREASTSTROKE |.");
        Discipline chosenDiscipline;
        try {
            chosenDiscipline = Discipline.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Det er ikke en gyldig disciplin.");
            return;
        }

        if (!athleteMember.isActiveInDiscipline(chosenDiscipline)) {
            System.out.println("Afvist! Svømmer er ikke aktiv i den disciplin.");
            return;
        }

        System.out.println("Er det træningstiden eller stævnetiden, der skal ændres?");
        String type = scanner.nextLine();

        try {
            if (type.equalsIgnoreCase("Træningstiden")) {
                System.out.println("Indtast tid i 100 sekund (fx 6532 for 01:05.32): ");
                int newTime = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("Indtast dato (dd-MM-yyyy): ");
                LocalDate date = LocalDate.parse(scanner.nextLine().trim(), formatter);

                boolean isBetter = controller.registerTrainingResult(athleteMember, newTime, date);

                if (isBetter) {
                    System.out.println("Ny træningstid er registreret: " + formatTime(athleteMember.getTrainingResult()) + " (" + date.format(formatter) + ").");
                } else {
                    String oldDate = (athleteMember.getTrainingDate() != null) ? athleteMember.getTrainingDate().format(formatter) : "ukendt dato";
                    System.out.println("Tiden er ikke bedre end svømmerens nuværende bedste tid (" + formatTime(athleteMember.getTrainingResult()) + " fra " + oldDate + "). Den gamle tid beholdes.");
                }

            } else if (type.equalsIgnoreCase("Stævnetiden")) {
                System.out.println("Indtast tid i 100 sekund (fx 6532 for 01:05.32): ");
                int newTime = Integer.parseInt(scanner.nextLine().trim());

                controller.registerCompetitionResult(athleteMember, newTime);
                System.out.println("Tiden er registreret.");

            } else {
                System.out.println("Ugyldigt valg! Indtast (træningstiden) eller (stævnetiden).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Tiden skal være et helt tal.");
        } catch (DateTimeParseException e) {
            System.out.println("Ugyldig dato! Brug formatet dd-MM-yyyy (fx 27-11-2026).");
        }
    }

    private void showTop5() {
        try {
            controller.sortAthletes();
        } catch (FileNotFoundException e) {
            System.out.println("Medlemmer kunne desvære ikke indlæses.");
            return;
        }

        System.out.println("Vælg en disciplin: | BUTTERFLY | CRAWL | BACKSTROKE | BREASTSTROKE |.");
        Discipline chosenDiscipline;
        try {
            chosenDiscipline = Discipline.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Det er ikke en gyldig disciplin.");
            return;
        }


        printTop5Team("Juniorholdet", controller.getTop5Junior());
        printTop5Team("Seniorholdet", controller.getTop5Senior());
    }

    private String formatTime(int timeInHundredths) {
        if (timeInHundredths <= 0) return "Ingen tid registreret";
        int minutes = timeInHundredths / 6000;
        int seconds = (timeInHundredths % 6000) / 100;
        int hundredths = timeInHundredths % 100;
        return String.format("%02d:%02d.%02d", minutes, seconds, hundredths);
    }

    private void welcomeMessage() {
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
            String paymentStatus = member.isPaid() ? "Betalt" : "Ikke betalt";
            System.out.println(member.getiD() + " | " + member.getName() + " | " + fee + " kr." + " | " +  paymentStatus);
        }
    }
}
