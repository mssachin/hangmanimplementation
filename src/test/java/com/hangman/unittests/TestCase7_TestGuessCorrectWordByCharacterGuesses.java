package com.hangman.unittests;

import com.essenseglobal.hangman.Hangman;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class TestCase7_TestGuessCorrectWordByCharacterGuesses {

    private static Hangman hangman;
    private Scanner scanner;
    private BufferedWriter writer;
    private FileInputStream fis;

    /**
     * This class takes input to Scanner via a FileInputStream.
     * The InputStream is configured such that user makes all correct guesses by char guesses and finds the word.
     * This exercises the branch where the word is found. System exists on its own.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @BeforeClass
    public  void initialize() throws IOException, ClassNotFoundException {
        hangman = new Hangman();
        writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase7_TestGuessCorrectWordByCharacterGuesses.txt")));
        fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase7_TestGuessCorrectWordByCharacterGuesses.txt");
    }

    /**
     * This test tests the following
     * 1) Number of attempts increased by as many number of guesses
     * 2) Available attempts decreased by max - number of guesses
     * 3) Guessed word does matches the actual word
     * 4) WordFound flag is true
     * @throws IOException
     */
    @Test
    public void testGuessCorrectWordByCharacterGuesses() throws IOException {
        String randomWord = hangman.getRandomWord();
        Set<String> charSet = new HashSet<>();
        char[] randomWordChars = randomWord.toCharArray();
        for(char wordChar: randomWordChars) {
            String charAsString = String.valueOf(wordChar);
            charSet.add(charAsString);
        }
        for (String charAsString:charSet) {
            String tempString = charAsString;
            writer.write("wordOrCharChoice:c");
            writer.write("\n");
            writer.write("charSequence:" + tempString);
            writer.write("\n");
        }
        writer.close();
        scanner = new Scanner(fis);
        hangman.playGame(scanner);
        Assert.assertEquals(hangman.getNumberOfAttempts(), charSet.size());
        Assert.assertEquals(hangman.getNumberOfAttemptsLeft(),9-charSet.size());
        Assert.assertTrue(hangman.getCurrentInputFromUser().equalsIgnoreCase(hangman.getRandomWord()));
        Assert.assertEquals(hangman.isWordFound(),true );
    }

}
