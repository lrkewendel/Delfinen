package UI;

import DelfinenSwimTypes.Discipline;
import domainmodel.Controller;
import position.*;
import datasource.FileHandler;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

            System.out.println("Vælg mellem en af de følgende stillinger ved at indtaste stillingsbetegnelsen: ");
            System.out.println("| Formand | Kasserer | Træner |");

            String chosenPosition = scanner.nextLine();

            if (chosenPosition.equalsIgnoreCase("Formand")){
                String choice = scanner.nextLine();
                switch (choice){
                    case "Opret" -> System.out.println();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
                ////Man skal skrive bogstaver, ellers kommer der exception:
                //        if (name == null || name.isBlank()) {
                //            throw new IllegalArgumentException("Navn må ikke være tomt.");
                //        }
                //
                //        //Afviser kontroltegn i navnet:
                //        if (name.chars().anyMatch(Character::isISOControl)) {
                //            throw new IllegalArgumentException("Navn må ikke indeholde tabulatorer eller linjeskift.");
                //        }
                //
                //        //Kontrollere fødselsdatoen (Man kan ikke have en fødselsdag i fremtiden):
                //        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
                //            throw new IllegalArgumentException("Indtast en gyldig fødselsdagsdato.");
                //        }
                //
                //        //Kontrollere disciplintypen:
                //        if (discipline == null) {
                //            throw new IllegalArgumentException("Vælg en gyldig disciplin.");
                //        }
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
