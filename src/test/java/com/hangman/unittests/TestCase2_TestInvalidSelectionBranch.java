package com.hangman.unittests;

import com.essenseglobal.hangman.Hangman;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestCase2_TestInvalidSelectionBranch {

    private static Hangman hangman;
    private Scanner scanner;
    private FileInputStream fis;

    /**
     * This class takes input to Scanner via a FileInputStream.
     * The InputStream is configured such that user enters a char which does not match either the word
     * or the character branch and system throws an invalid selection message.
     * System is forced to exit.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @BeforeClass
    public  void initialize() throws IOException, ClassNotFoundException {
        hangman = new Hangman();
        fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase2_TestInvalidSelectionBranch.txt");
    }

    /**
     * This test tests the following
     * 1) Number of attempts stays the same
     * 2) Number of available attempts stays the same
     * 3) Guessed word does not match the actual word
     * 4) WordFound flag is false
     * 5) System message is validated
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @org.testng.annotations.Test
    public void selectInvalidInputToNoneOfTheFunctionalBranches() throws IOException, ClassNotFoundException {
        scanner = new Scanner(fis);
        hangman.playGame(scanner);
        Assert.assertEquals(hangman.getNumberOfAttempts(), 0);
        Assert.assertEquals(hangman.getNumberOfAttemptsLeft(),9);
        Assert.assertFalse(hangman.getCurrentInputFromUser().equalsIgnoreCase(hangman.getRandomWord()));
        Assert.assertEquals(hangman.isWordFound(),false );
        Assert.assertEquals(hangman.getSystemMessage(), "Invalid Selection");
    }

    @AfterClass
    public void tearDown(){
        scanner.close();
    }
}
