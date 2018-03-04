package com.hangman.unittests;

import com.essenseglobal.hangman.Hangman;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestCase4_TestCharacterAlreadyUsedBranch {

    private static Hangman hangman;
    private Scanner scanner;
    private FileInputStream fis;

    /**
     * This class takes input to Scanner via a FileInputStream.
     * The InputStream is configured such that it exercises the branch where user chooses to guess a character
     * and guesses the same character twice and as a result you are not penalised branch is executed.
     * System is forced to exit.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @BeforeClass
    public  void initialize() throws IOException, ClassNotFoundException {
        hangman = new Hangman();
        fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//testdata//objectdata//TestCase4_TestCharacterAlreadyUsedBranch.txt");
    }

    /**
     *This test tests the following
     * 1) Number of attempts increased by only 1 and not 2 as character was repeated
     * 2) Available attempts decreased by only 1 and not 2 as character was repeated
     * 3) Guessed word does not match the actual word
     * 4) WordFound flag is false
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @org.testng.annotations.Test
    public void selectToChooseACharacterAndExitAfterOneWrongAttemptAndRepeatSameCharacter() throws IOException, ClassNotFoundException {
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
