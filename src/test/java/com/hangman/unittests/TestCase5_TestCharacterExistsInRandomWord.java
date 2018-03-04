package com.hangman.unittests;

import com.essenseglobal.hangman.Hangman;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TestCase5_TestCharacterExistsInRandomWord {

    private static Hangman hangman;
    private Scanner scanner;
    private BufferedWriter writer;
    private FileInputStream fis;

    /**This class takes input to Scanner via a FileInputStream.
     * The InputStream is configured such that it exercises the branch where user chooses a character and it matches
     * to the random word generated thus exercising branch where character matches. System is forced to exit.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @BeforeClass
    public  void initialize() throws IOException, ClassNotFoundException {
        hangman = new Hangman();
        writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase5_TestCharacterExistsInRandomWord.txt")));
        fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase5_TestCharacterExistsInRandomWord.txt");
    }

    /**
     * This test tests the following
     * 1) Number of attempts increased by 1
     * 2) Available attempts decreased by 1
     * 3) Guessed word does not match the actual word
     * 4) WordFound flag is false
     * @throws IOException
     */
    @Test
    public void testCharacterExistsRandomWord() throws IOException {
        String randomWord = hangman.getRandomWord();
        Random random = new Random();
        int randomIndex = random.nextInt(randomWord.length());
        String randomChar = String.valueOf(randomWord.charAt(randomIndex));
        writer.write("wordOrCharChoice:c");
        writer.write("\n");
        writer.write("charSequence:"+randomChar);
        writer.write("\n");
        writer.write("Exit:true");
        writer.flush();
        writer.close();
        scanner = new Scanner(fis);
        hangman.playGame(scanner);
        Assert.assertEquals(hangman.getNumberOfAttempts(), 1);
        Assert.assertEquals(hangman.getNumberOfAttemptsLeft(),8);
        Assert.assertFalse(hangman.getCurrentInputFromUser().equalsIgnoreCase(hangman.getRandomWord()));
        Assert.assertEquals(hangman.isWordFound(),false );
    }
    @AfterClass
    public void tearDown(){
        scanner.close();
    }
}
