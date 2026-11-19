package UI;

import domainmodel.Controller;
import position.*;
import datasource.FileHandler;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
            String choice = scanner.nextLine();

            if (chosenPosition.equalsIgnoreCase("Formand")){
                switch (choice){
                    case "Opret" -> System.out.println();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }

            if (chosenPosition.equalsIgnoreCase("Kasserer")){
                switch (choice){
                    case "Se" -> System.out.println();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }

            if (chosenPosition.equalsIgnoreCase("Træner")){
                switch (choice){
                    case "Se" -> System.out.println();
                    default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
                }
            }
        }
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
}
