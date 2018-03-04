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

public class TestCase9_TestGuessedWordAtLastAttempt {


    private static Hangman hangman;
    private Scanner scanner;
    private BufferedWriter writer;
    private FileInputStream fis;

     /**
     * This class takes input to Scanner via a FileInputStream.
     * The InputStream is configured such that the user guess the word by virtue of a word in the last attempt
     * there by exercising the exist branch. The system exists on its own.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @BeforeClass
    public  void initialize() throws IOException, ClassNotFoundException {
        hangman = new Hangman();
        writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase9_TestGuessedWordAtLastAttempt.txt"),true));
        fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase9_TestGuessedWordAtLastAttempt.txt");
    }

    /**
     * This test tests the following
     * 1) Number of attempts increased to 9
     * 2) Available attempts decreased to 0
     * 3) Guessed word does match the actual word
     * 4) WordFound flag is true
     * @throws IOException
     */

    @Test
    public void testGuessCorrectWordByWordGuessFinalAttempt() throws IOException {
        String randomWord = hangman.getRandomWord();
        Set<String> charSet = new HashSet<>();
        Random r = new Random();
        while(charSet.size()<8){
            String c = String.valueOf((char)(r.nextInt(26) + 'a'));
            charSet.add(c);
        }
        for (String charInSet:charSet) {
            String tempString = charInSet;
            writer.write("wordOrCharChoice:c");
            writer.write("\n");
            writer.write("charSequence:" + tempString);
            writer.write("\n");
        }
        writer.write("wordOrCharChoice:w");
        writer.write("\n");
        writer.write("charSequence:" + randomWord);
        writer.flush();
        writer.close();
        scanner = new Scanner(fis);
        hangman.playGame(scanner);
        Assert.assertEquals(hangman.getNumberOfAttempts(), 9);
        Assert.assertEquals(hangman.getNumberOfAttemptsLeft(),0);
        Assert.assertEquals(hangman.getGuessedWord(),hangman.getRandomWord());
        Assert.assertEquals(hangman.isWordFound(),true );
        writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase9_TestGuessedWordAtLastAttempt.txt")));
        writer.close();
    }
}
