package com.hangman.unittests;

import com.essenseglobal.hangman.Hangman;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Scanner;

public class TestCase8_TestGuessCorrectWordByWordGuess {


    private static Hangman hangman;
    private Scanner scanner;
    private BufferedWriter writer;
    private FileInputStream fis;

    /**This class takes input to Scanner via a FileInputStream.
     * This InputStream is configured such that the user guesses the word in 2nd attempt, thus exercising
     * the branch where word is guess by virtue of a word. System exits on its own.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @BeforeClass
    public  void initialize() throws IOException, ClassNotFoundException {
        hangman = new Hangman();
        writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase8_TestGuessCorrectWordByWordGuess.txt")));
        fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase8_TestGuessCorrectWordByWordGuess.txt");
    }

    /**
     * This test tests the following
     * 1) Number of attempts increased by 2
     * 2) Available attempts decreased by max - number of guesses =7
     * 3) Guessed word does match the actual word
     * 4) WordFound flag is true
     *
     * @throws IOException
     */
    @Test
    public void testGuessCorrectWordByWordGuess() throws IOException {
        String randomWord = hangman.getRandomWord();
        writer.write("wordOrCharChoice:w");
        writer.write("\n");
        writer.write("charSequence:" + "v");
        writer.write("\n");
        writer.write("wordOrCharChoice:w");
        writer.write("\n");
        writer.write("charSequence:" + randomWord);
        writer.close();
        scanner = new Scanner(fis);
        hangman.playGame(scanner);
        Assert.assertEquals(hangman.getNumberOfAttempts(), 2);
        Assert.assertEquals(hangman.getNumberOfAttemptsLeft(),7);
        Assert.assertEquals(hangman.getGuessedWord(),hangman.getRandomWord());
        Assert.assertEquals(hangman.isWordFound(),true );

    }
}
