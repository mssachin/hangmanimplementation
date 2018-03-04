package com.essenseglobal.hangman;

import java.io.*;
import java.util.*;

public class Hangman {

    public static void main(String[] args) {

        Hangman hangman = new Hangman();
        Scanner scanner = new Scanner(System.in);
        hangman.playGame(scanner);

    }

    private BufferedReader br;
    private List<String> allWords = new ArrayList<>();
    private String[] guessedChars;
    private static final int maxNumberOfAttempts = 9;
    private int numberOfAttemptsLeft;
    private boolean wordFound = false;
    private int numberOfAttempts;
    private String inputFromUser;
    private String randomWord;
    private String systemMessage;
    private String guessedWord;


    public Hangman() {
        try {
            // br = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "//src//test/testdata/Words.txt")));
            br = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "//src//test/testdata/Words.txt")));

            String word;
            while ((word = br.readLine()) != null) {
                allWords.add(word);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        randomWord = selectARandomWord();

    }

    public void playGame(Scanner scanner) {
        numberOfAttempts = 0;
        Set<String> currentCharacterGuesses = new HashSet<>();
        System.out.println(randomWord);
        displayCharsInTheWordToTheUser(randomWord);
        char[] randomWordCharArray = randomWord.toCharArray();
        guessedChars = new String[randomWordCharArray.length];
        Arrays.fill(guessedChars, " - ");
        while (numberOfAttempts <= maxNumberOfAttempts) {
            System.out.println();
            System.out.println(" Enter \"W\" or \"w\" at this time to guess the word or  \"C\" or \"c\" character to continue guessing characters");
            String inputFromUserKeyValuePair = null;
            try {
                inputFromUserKeyValuePair = getUserInput(scanner);
                inputFromUser = inputFromUserKeyValuePair.split(":")[1];
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundException) {
                arrayIndexOutOfBoundException.getSuppressed();
                inputFromUser = inputFromUserKeyValuePair;
            } catch (NoSuchElementException noSuchElementException) {
                throw new NoSuchElementException("\"Only Uppercase and Lowercase alphabets are allowed\"");
            }

            if (inputFromUserKeyValuePair.split(":")[0].equals("Exit") && inputFromUserKeyValuePair.split(":")[1].equals("true")) {
                System.out.println("Forced System Exit");
                break;
            }
            if (inputFromUser.equalsIgnoreCase("C")) {
                String characterFromUser;
                try {
                    characterFromUser = acceptAndReturnUserInputSingleChar(scanner);

                } catch (IllegalArgumentException iae) {
                    System.out.println(iae.getMessage());
                    numberOfAttemptsLeft = maxNumberOfAttempts - numberOfAttempts;
                    continue;
                }
                if (randomWord.contains(characterFromUser) && !currentCharacterGuesses.contains(characterFromUser)) {
                    printCorrectlyOrIncorrectlyGuessedCharactersToConsole(randomWordCharArray, characterFromUser);
                    numberOfAttempts++;
                    numberOfAttemptsLeft = maxNumberOfAttempts - numberOfAttempts;
                    currentCharacterGuesses.add(characterFromUser);
                    if (returnWordFromChars(guessedChars).equalsIgnoreCase(randomWord)) {
                        System.out.println(" You have guessed the word " + returnWordFromChars(guessedChars) + " correctly in " + numberOfAttempts + " attempts.");
                        System.out.println(" You have " + numberOfAttemptsLeft + " left ");
                        wordFound = true;
                        scanner.close();
                        break;
                    }
                    System.out.println(" This is your attempt " + numberOfAttempts);
                    System.out.println(" You have " + numberOfAttemptsLeft + " left ");

                } else {
                    if (currentCharacterGuesses.contains(characterFromUser)) {
                        System.out.println(" Character Already Used You Will Not be Penalised  " + characterFromUser);

                        printCorrectlyOrIncorrectlyGuessedCharactersToConsole(randomWordCharArray, characterFromUser);
                    } else {
                        System.out.println("Sorry wrong guess " + characterFromUser);
                        currentCharacterGuesses.add(characterFromUser);
                        printCorrectlyOrIncorrectlyGuessedCharactersToConsole(randomWordCharArray, characterFromUser);
                        numberOfAttempts++;
                        numberOfAttemptsLeft = maxNumberOfAttempts - numberOfAttempts;
                        System.out.println(" This is your attempt " + numberOfAttempts);
                        System.out.println(" You have " + numberOfAttemptsLeft + " left ");

                    }
                }
            } else if (inputFromUser.equalsIgnoreCase("W")) {
                guessedWord = acceptAndReturnUserInputWholeString(scanner);
                if (guessedWord.equalsIgnoreCase(randomWord)) {
                    numberOfAttempts++;
                    numberOfAttemptsLeft = maxNumberOfAttempts - numberOfAttempts;
                    System.out.println(" You have guessed the word " + guessedWord + " correctly in " + numberOfAttempts + " attempts.");
                    System.out.println(" You have " + numberOfAttemptsLeft + " left ");
                    wordFound = true;
                    scanner.close();
                    break;
                } else {
                    numberOfAttempts++;
                    numberOfAttemptsLeft = maxNumberOfAttempts - numberOfAttempts;
                    System.out.println(" You have guessed the word " + guessedWord + " Incorrectly in " + numberOfAttempts + " attempts.");
                    System.out.println(" This is your attempt " + numberOfAttempts);
                    System.out.println(" You have " + numberOfAttemptsLeft + " left ");


                }

            } else {
                System.out.println("Invalid Selection");
                systemMessage = "Invalid Selection";
                numberOfAttemptsLeft = maxNumberOfAttempts - numberOfAttempts;


            }

            if (numberOfAttempts == 9) {
                if (!returnWordFromChars(guessedChars).equals(randomWord)) {
                    wordFound = false;
                    System.out.println("Game over word not found ");
                } else {
                    wordFound = true;
                    System.out.println("Game over congratulations you win!!! ");
                }
                scanner.close();
                break;
            }

        }
    }

    public String getUserInput(Scanner scanner) {
        return scanner.next();
    }


    public String selectARandomWord() {
        Random random = new Random();
        return allWords.get(random.nextInt(allWords.size()));
    }

    public void displayCharsInTheWordToTheUser(String word) {
        char[] charsInWord = word.toCharArray();
        System.out.println("Your word has " + charsInWord.length + " characters");
        for (int i = 0; i < charsInWord.length; i++) {
            System.out.print(" - ");
        }
        System.out.println();
        System.out.println("---------------------------------------");

    }

    public String acceptAndReturnUserInputWholeString(Scanner scanner) {
        System.out.println("Please enter your guess word ");
        String keyValuePair = getUserInput(scanner);
        try {
            return keyValuePair.split(":")[1];

        } catch (ArrayIndexOutOfBoundsException exception) {
            return keyValuePair;
        }
    }


    public String acceptAndReturnUserInputSingleChar(Scanner scanner) {
        System.out.println("Please enter your guess character ");
        System.out.println("");
        String character;
        String keyValuePairCharacter = getUserInput(scanner).toLowerCase();
        try {
            character = keyValuePairCharacter.split(":")[1];
        } catch (ArrayIndexOutOfBoundsException aeoe) {
            character = keyValuePairCharacter;
        }
        IllegalArgumentException iae = new IllegalArgumentException("\"Only Uppercase and Lowercase alphabets are allowed\"");
        if (!character.matches("[A-Za-z]")) {
            throw iae;

        }
        return character;
    }

    public void printCorrectlyOrIncorrectlyGuessedCharactersToConsole(char[] randomWordCharArray, String guessedChar) {
        for (int i = 0; i < randomWordCharArray.length; i++) {
            char charInRandomWord = randomWordCharArray[i];
            if (String.valueOf(charInRandomWord).equals(guessedChar)) {
                guessedChars[i] = String.valueOf(charInRandomWord);
            }

        }
        System.out.println();
        for (String character : guessedChars) {
            System.out.print(character + " ");

        }
        System.out.println();


    }

    public String returnWordFromChars(String[] charsArr) {
        StringBuilder sb = new StringBuilder();
        for (String singeChar : charsArr) {
            sb.append(singeChar);
        }
        return sb.toString();
    }


    public int getNumberOfAttemptsLeft() {
        return numberOfAttemptsLeft;
    }


    public boolean isWordFound() {
        return wordFound;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public String getCurrentInputFromUser() {
        return returnWordFromChars(guessedChars);
    }

    public String getRandomWord() {
        return randomWord;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public String getGuessedWord() {
        return guessedWord;
    }
}
