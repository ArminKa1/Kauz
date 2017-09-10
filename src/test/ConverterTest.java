package test;

import java.text.ParseException;
import org.junit.*;
import main.*;

public class ConverterTest {
    private Converter converter;

    @Before
    public void setup() {
        converter = new Converter();
    }

    @Test
    public void testToNumber() throws ParseException {
      Assert.assertEquals(1, converter.toNumber("I"));
      Assert.assertEquals("II", 2, converter.toNumber("II"));
      Assert.assertEquals(3, converter.toNumber("III"));
      Assert.assertEquals(4, converter.toNumber("IV"));
      Assert.assertEquals(5, converter.toNumber("V"));
      Assert.assertEquals(8, converter.toNumber("VIII"));
      Assert.assertEquals(9, converter.toNumber("IX"));
      Assert.assertEquals(10, converter.toNumber("X"));
      Assert.assertEquals(15, converter.toNumber("XV"));
      Assert.assertEquals(19, converter.toNumber("XIX"));
      Assert.assertEquals(33, converter.toNumber("XXXIII"));
      Assert.assertEquals(49, converter.toNumber("XLIX"));
      Assert.assertEquals(51, converter.toNumber("LI"));
      Assert.assertEquals(91, converter.toNumber("XCI"));
      Assert.assertEquals(101, converter.toNumber("CI"));
      Assert.assertEquals(444, converter.toNumber("CDXLIV"));
      Assert.assertEquals(499, converter.toNumber("CDXCIX"));
      Assert.assertEquals(1900, converter.toNumber("MCM"));
      Assert.assertEquals(1903, converter.toNumber("MCMIII"));
      Assert.assertEquals(1944, converter.toNumber("MCMXLIV"));
      Assert.assertEquals(1999, converter.toNumber("MCMXCIX"));
      Assert.assertEquals(1990, converter.toNumber("MCMXC"));
      Assert.assertEquals(2006, converter.toNumber("MMVI"));
      Assert.assertEquals(2012, converter.toNumber("MMXII"));
      Assert.assertEquals(3333, converter.toNumber("MMMCCCXXXIII"));
    }

    @Test
    public void testToNumberNonStrict() throws ParseException {
        Assert.assertEquals(5, converter.toNumber("VX"));
        Assert.assertEquals(50, converter.toNumber("LC"));
        Assert.assertEquals(500, converter.toNumber("DM"));
    }

    @Test
    public void testToNumberWithInvalidCharacters() {
      try {
        Assert.assertEquals(1, converter.toNumber("B"));
        Assert.fail("Should throw an exception before reaching here");
      } catch (ParseException pax) {
        // Should get here
      }
    }

    @Test
    public void testToNumberWithIncreasingCharacters() {
      try {
        converter.toNumber("IVX");
        Assert.fail("Should throw an exception about increasing characters");
      } catch (ParseException pax) {
        // Should get here
      }

      try {
        converter.toNumber("VXL");
        Assert.fail("Should throw an exception about increasing characters");
      } catch (ParseException pax) {
        // Should get here
      }
    }

    @Test
    public void testToNumberWithIllegalSubtractionJumpInStrictMode() throws ParseException {
      converter = new Converter(true);

        try {
            converter.toNumber("IM");
            Assert.fail("Should throw an exception about increasing characters");
        } catch (ParseException pax) {
            // Should get here
        }

        try {
            converter.toNumber("IL");
            Assert.fail("Should throw an exception about increasing characters");
        } catch (ParseException pax) {
            // Should get here
        }

        Assert.assertEquals(9, converter.toNumber("IX"));
    }

    @Test
    public void testToNumberWithIllegalSubtractionNumeralInStrictMode() throws ParseException {
      converter = new Converter(true);

        try {
            converter.toNumber("VX");
            Assert.fail("Should throw an exception about illegal V subtraction");
        } catch (ParseException pax) {
            // Should get here
        }

        try {
            converter.toNumber("LC");
            Assert.fail("Should throw an exception about illegal L subtraction");
        } catch (ParseException pax) {
            // Should get here
        }

        try {
            converter.toNumber("DM");
            Assert.fail("Should throw an exception about illegal D subtraction");
        } catch (ParseException pax) {
            // Should get here
        }

        Assert.assertEquals(9, converter.toNumber("IX"));
    }

    @Test
    public void testToRomanNumerals() {
      Assert.assertEquals("I", converter.toRomanNumerals(1));
      Assert.assertEquals("II", converter.toRomanNumerals(2));
      Assert.assertEquals("III", converter.toRomanNumerals(3));
      Assert.assertEquals("IV", converter.toRomanNumerals(4));
      Assert.assertEquals("V", converter.toRomanNumerals(5));
      Assert.assertEquals("VI", converter.toRomanNumerals(6));
      Assert.assertEquals("IX", converter.toRomanNumerals(9));
      Assert.assertEquals("XII", converter.toRomanNumerals(12));
      Assert.assertEquals("XLIX", converter.toRomanNumerals(49));
      Assert.assertEquals("LV", converter.toRomanNumerals(55));
      Assert.assertEquals("CDXLIV", converter.toRomanNumerals(444));
      Assert.assertEquals("CDXCIX", converter.toRomanNumerals(499));
      Assert.assertEquals("MCC", converter.toRomanNumerals(1200));
      Assert.assertEquals("MCM", converter.toRomanNumerals(1900));
      Assert.assertEquals("MCMI", converter.toRomanNumerals(1901));
      Assert.assertEquals("MCMIII", converter.toRomanNumerals(1903));
      Assert.assertEquals("MCMXLIV", converter.toRomanNumerals(1944));
      Assert.assertEquals("MCMXCIII", converter.toRomanNumerals(1993));
      Assert.assertEquals("MCMXCIV", converter.toRomanNumerals(1994));
      Assert.assertEquals("MCMXCIX", converter.toRomanNumerals(1999));
      Assert.assertEquals("MMVI", converter.toRomanNumerals(2006));
      Assert.assertEquals("MMM", converter.toRomanNumerals(3000));
      Assert.assertEquals("MMMCCCXXXIII", converter.toRomanNumerals(3333));
    }

    @Test
    public void testRoundTrip() throws ParseException {
        for (int i = 0; i < 3999; i++) {
            Assert.assertEquals("Testing round trip of " + i, i, converter.toNumber(converter.toRomanNumerals(i)));
        }
    }

    public void testToRomanNumeralsPerformance() throws ParseException {
        for (int i = 0; i < 333999; i++) {
            Assert.assertEquals("Testing toRomanNumerals " + i, converter.toRomanNumerals(i), converter.toRomanNumerals(i));
        }
    }

    public void testToNumberPerformance() throws ParseException {
        for (int i = 0; i < 333999; i++) {
            Assert.assertEquals("Testing toNumber " + i, converter.toNumber("MMMCCXXIII"), converter.toNumber("MMMCCXXIII"));
        }
    }
}

