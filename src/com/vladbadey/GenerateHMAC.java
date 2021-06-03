package com.vladbadey;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class GenerateHMAC {
    private String[] args;
    private int choice;
    private String key;

    public GenerateHMAC(String[] args) {
        this.args = args;
        makeMove();
        generateKey();
    }

    private void makeMove() {
        choice = new Random().nextInt(args.length - 1);
    }

    public byte[] generateHMAC() throws NoSuchAlgorithmException {
        String input = args[choice].concat(key);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private void generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[16];
        secureRandom.nextBytes(token);
        key = new BigInteger(1, token).toString(16);
    }

    public String getKey() {
        return key;
    }

    public String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
}
