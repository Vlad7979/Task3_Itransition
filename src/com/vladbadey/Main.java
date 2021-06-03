package com.vladbadey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    class A {

    }

    static String[] arguments;
    static Map<Integer, String> map = new HashMap<>();
    static int userMove;
    public static void main(String[] args) throws NoSuchAlgorithmException {
        arguments = args;
        if(args.length >= 3 && !(args.length % 2 == 0)){
            System.out.println("HMAC:");
            GenerateHMAC hmacAndKey = new GenerateHMAC(args);
            System.out.println(hmacAndKey.bytesToHex(hmacAndKey.generateHMAC()));
            menu();
            if (userMove == 0) {
                return;
            }
            System.out.println("Your move: " + map.get(userMove));
            int pcMove = hmacAndKey.getChoice() + 1;
            System.out.println("Computer move: " + pcMove);
            try {
                findWinner(userMove, pcMove);
            } catch (NullPointerException e) {
                System.out.println("Draws!");
            }
            GenerateHMAC hmacAndKey2 = new GenerateHMAC(args);
            System.out.println("HMAC key: " + hmacAndKey2.bytesToHex(hmacAndKey2.generateHMAC()));
        } else if(args.length % 2 == 0 && args.length != 0){
            System.out.println("Enter an odd amount.");
        } else {
            System.out.println("Error, enter correctly.");
            System.out.println("Example: java *.java Rock, Paper, Shotgun or Rock, Paper, Shotgun, Lizard, Spock");
        }

    }

    public static void menu() {
        System.out.println("Available moves: ");
        AtomicInteger i = new AtomicInteger(1);
        Arrays.stream(arguments)
                .forEach(arg -> {
                    map.put(i.get(), arg);
                    System.out.println(i + " - " + arg);
                    i.getAndIncrement();
                });
        System.out.println("0 - exit");
        System.out.print("Enter your move: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            userMove = Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            menu();
        }
        if (userMove > map.size() || userMove < 0)
            menu();
    }

    public static void findWinner(int value, int pcMove) {
        List<Integer> listWithNums = new ArrayList<>();
        Map<Integer, Boolean> mapa = new HashMap<>();
        for (Map.Entry<Integer, String> m : map.entrySet()) {
            listWithNums.add(m.getKey());
        }
        int avgValue = listWithNums.size() / 2;
        int cP = 0;
        int cM = 0;
        for (int i = value + 1; i <= listWithNums.size(); i++) {
            if (cP < avgValue) {
                mapa.put(i, true);
                cP++;
            } else {
                mapa.put(i, false);
                cM++;
            }
        }
        if (value - 1 > 0) {
            for (int i = value - 1; i > 0; i--) {
                if (cM < avgValue) {
                    mapa.put(i, false);
                    cM++;
                } else {
                    mapa.put(i, true);
                    cP++;
                }
            }
        }
        if (mapa.get(pcMove)) {
            System.out.println("You lose!");
        }
        else {
            System.out.println("You win!");
        }
    }
}