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

public class TestCase6_TestCharacterDoesNotExistInRandomWord {
    private static Hangman hangman;
    private Scanner scanner;
    private BufferedWriter writer;
    private FileInputStream fis;

    /**
     * This class takes input to Scanner via a FileInputStream.
     * The InputStream is configured such that it exercises the branch where user chooses a character and the character
     * does not exist in the random word generated thus exercising the branch where character is guessed incorrectly.
     * System is forced to exit.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @BeforeClass
    public  void initialize() throws IOException, ClassNotFoundException {
        hangman = new Hangman();
        writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase6_TestCharacterDoesNotExistInRandomWord.txt")));
        fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase6_TestCharacterDoesNotExistInRandomWord.txt");
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
    public void testCharacterDoesNotExistInRandomWord() throws IOException {
        String randomWord = hangman.getRandomWord();
        Random r = new Random();
        boolean isLoopRunning =true;
        while(isLoopRunning){
            String c = String.valueOf((char)(r.nextInt(26) + 'a'));
            if(!randomWord.contains(c)) {
                writer.write("wordOrCharChoice:c");
                writer.write("\n");
                writer.write("charSequence:" + c);
                writer.write("\n");
                writer.write("Exit:true");
                writer.flush();
                isLoopRunning = false;
                writer.close();
            }

        }

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
