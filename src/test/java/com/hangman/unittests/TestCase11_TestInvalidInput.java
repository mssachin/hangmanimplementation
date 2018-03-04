package com.hangman.unittests;

import com.essenseglobal.hangman.Hangman;
import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TestCase11_TestInvalidInput {

    private static Hangman hangman;
    private Scanner scanner;
    private FileInputStream fis;

    /**
     *This class takes input to Scanner via a FileInputStream.
     * The InputStream is configured such that it produces invalid inputs to the application and system
     * validates the exceptions. The test is force exited.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @BeforeClass
    public void initialize() throws IOException, ClassNotFoundException {
        hangman = new Hangman();
        fis = new FileInputStream(System.getProperty("user.dir") + "//src//test//testdata//objectdata//TestCase11_TestInvalidInput.txt");
    }

    /**
     * This test tests the following
     * 1) Throws IllegalArgumentException for invalid input
     * 2) Throws NoSuchElementException for no EOF delimiter.
     * 3) Number of attempts stays the same
     * 4) Available attempts stays the same
     *
     * @throws IllegalArgumentException
     * @throws TestException
     */
    @Test(expectedExceptions ={IllegalArgumentException.class, NoSuchElementException.class}, expectedExceptionsMessageRegExp = "\"Only Uppercase and Lowercase alphabets are allowed\"")
    public void testInvalidInput() throws IllegalArgumentException, TestException {
            scanner = new Scanner(fis);
            hangman.playGame(scanner);
            Assert.assertEquals(hangman.getNumberOfAttempts(), 0);
            Assert.assertEquals(hangman.getNumberOfAttemptsLeft(), 9);
    }

}
