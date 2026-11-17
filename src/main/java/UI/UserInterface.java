package UI;

import domainmodel.Controller;

import java.nio.charset.StandardCharsets;
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
            //metode til hvad brugerens muligheder er
            System.out.println("Hvad kunne du tænke dig at gøre?");
            String choice = scanner.nextLine();

            switch (choice){
                // oprette muligheder
                default -> System.out.println("Den handling er desværre ikke mulig. Prøv igen!");
            }
        }
    }

    private void welcomeMessage(){
        String blue = "\u001B[1;34m";
        String dolphin = "\uD83D\uDC2C";
        String reset = "\u001B[0m";

        System.out.println(blue + "Velkommen til svømmeklubben Delfinen " + dolphin + reset + "\n");
    }
}
