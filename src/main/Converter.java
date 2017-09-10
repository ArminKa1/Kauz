package main;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
//Comment
public class Converter {

    private static final List<Integer> listValues = Arrays.asList(1000, 500, 100, 50, 10, 5, 1);
    private static final List<Character> listNumerals = Arrays.asList('M', 'D', 'C', 'L', 'X', 'V', 'I');
    private static final List<String> listNumeralStrings = Arrays.asList("M", "D", "C", "L", "X", "V", "I");
    private final boolean bStrictMode;

  public Converter() {
    bStrictMode = false;
  }

  public Converter(boolean bStrictMode) {
    this.bStrictMode = bStrictMode;
  }

    /**
     * Convert into Roman numerals.
     *
     * @param number the integer to convert to Roman numerals
     * @return The resulting Roman numerals
     */
    public String toRomanNumerals(int number) {
        StringBuilder stbRomanNumerals = new StringBuilder();

        int leftover = number;
        for (int i = 0; i < listValues.size(); i++) {
            int count = leftover / listValues.get(i);
            int remainder = leftover % listValues.get(i);

            boolean bMajorNine = (i < listValues.size() - 2) && ((remainder / listValues.get(i + 2)) == 9) && ((i % 2) == 0);

            if ((i == 0) || (count != 4)) {
                for (int j = 0; j < count; j++) {
                    stbRomanNumerals.append(listNumeralStrings.get(i));
                }
                leftover -= count * listValues.get(i);
            } else {
                stbRomanNumerals.append(listNumeralStrings.get(i));
                stbRomanNumerals.append(listNumeralStrings.get(i - 1));
                leftover -= count * listValues.get(i);
            }

            if (bMajorNine) {
              stbRomanNumerals.append(listNumeralStrings.get(i + 2));
              stbRomanNumerals.append(listNumeralStrings.get(i));
              leftover -= 9 * listValues.get(i + 2);
            }
        }

        return stbRomanNumerals.toString();
    }

    public int toNumber(String strRomanNumerals) throws ParseException {
        int total = 0;

        for (int i = 0; i < strRomanNumerals.length(); i++) {
            int currentNumeralIndex = listNumerals.indexOf(strRomanNumerals.charAt(i));

            if (currentNumeralIndex == -1) {
                throw new ParseException("Illegal character: " + strRomanNumerals.charAt(i), i);
            }

            boolean bAddNumeral = true;

            if (i < strRomanNumerals.length() - 1) {
                int nextNumeralIndex = listNumerals.indexOf(strRomanNumerals.charAt(i + 1));
                boolean bNextNumeralLarger = nextNumeralIndex < currentNumeralIndex;
                bAddNumeral = !bNextNumeralLarger;

                if (bStrictMode && bNextNumeralLarger) {
                    if ((currentNumeralIndex - nextNumeralIndex) > 2) {
                        throw new ParseException("Cannot increase by more than one numeral at a time in strict mode", i);
                    }

                    if ((currentNumeralIndex % 2) != 0) {
                        throw new ParseException("Cannot subtract V, L, D, or other 5-based numerals in strict mode", i);
                    }
                }

                if (bNextNumeralLarger && (i < strRomanNumerals.length() - 2)) {
                    int nextNextNumeralIndex = listNumerals.indexOf(strRomanNumerals.charAt(i + 2));
                    boolean bNextNextNumeralLarger = nextNextNumeralIndex < nextNumeralIndex;
                    // Throw exception if in strict mode
                    if (bNextNextNumeralLarger) {
                        throw new ParseException("Cannot have two increasing numerals interface a row", i);
                    }
                }
            }

            if (bAddNumeral) {
                total += listValues.get(listNumerals.indexOf(strRomanNumerals.charAt(i)));
            } else {
                total -= listValues.get(listNumerals.indexOf(strRomanNumerals.charAt(i)));
            }
        }

        return total;
    }
}
