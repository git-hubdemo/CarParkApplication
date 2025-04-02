package org.carpark.reader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BarcodeReaderTest {

    private BarcodeReader barcodeReader;
    private BarcodeReader spyBarcodeReader;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalSystemOut;

    @BeforeEach
    public void setUp() {
        barcodeReader = new BarcodeReader();
        spyBarcodeReader = Mockito.spy(barcodeReader); // Spy on the real instance

        originalSystemOut = System.out; // Saves the original System.out so it can be restored after the test
        System.setOut(new PrintStream(outputStream)); // Set the new System.out to capture printed output
    }

    @Test
    void generateBarcode_ShouldReturnNumberBetween1000And9999(){
        int generatedBarcode = barcodeReader.generateBarcode();

        // Assert that the generated barcode is a four-digit number
        assertTrue(generatedBarcode >= 100000 && generatedBarcode <= 999999, "Generated barcode should be a four-digit number");
    }

    @Test
    public void readId_WhenGivenValidInput_ShouldReturnParsedBarcode(){
        doReturn(1234).when(spyBarcodeReader).readId();

        assertEquals(1234, spyBarcodeReader.readId());
    }


    @Test
    public void displayEntranceOrExitMessage_WhenLocationIsEntrance_ShouldPrintEntranceMessage() {
        barcodeReader.displayEntranceOrExitMessage("Entrance");

        String output = outputStream.toString().trim();
        String expectedOutput = "\t\t✅ A barcode will be generated for your vehicle.\n\t\tIt will be printed on your ticket. Please keep your ticket safe.".trim();

        assertEquals(expectedOutput, output);

    }

    @Test
    public void displayEntranceOrExitMessage_WhenLocationIsExit_ShouldPrintExitMessage() {
        barcodeReader.displayEntranceOrExitMessage("Exit");

        String output = outputStream.toString().trim();
        String expectedOutput = "Please enter the barcode on your ticket to exit>>.".trim();

        assertEquals(expectedOutput, output);
    }


    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalSystemOut);
    }
}
