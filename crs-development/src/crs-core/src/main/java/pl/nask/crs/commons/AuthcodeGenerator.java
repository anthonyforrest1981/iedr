package pl.nask.crs.commons;

import java.util.Iterator;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class AuthcodeGenerator {
    public static final String SUPPORTED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static ImmutableList<Character> charArray = Lists.charactersOf(SUPPORTED_CHARACTERS);

    private AuthcodeGenerator() {}

    public static String generateRandom(int desiredLength) {
        if (desiredLength < 0) {
            throw new IllegalArgumentException("Cannot generate validated random String with " + desiredLength + " length");
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < desiredLength; i++) {
            Character c = charArray.get(random.nextInt(charArray.size()));
            sb.append(c);
        }
        return sb.toString();
    }

    public static String generateValidated(int desiredLength) {
        if (desiredLength < 1) {
            throw new IllegalArgumentException("Cannot generate validated random String with " + desiredLength + " length");
        }
        String randomString = generateRandom(desiredLength - 1);
        StringBuilder result = new StringBuilder(desiredLength);
        result.append(randomString);
        result.append(calculateChecksumChar(randomString));
        return result.toString();
    }

    static public boolean isValid(String in) {
        return in != null && in.length() > 0 && getChecksumIndex(in) == 0;
    }

    // a 36x36 Matrix for Damm algorith, taken from http://www.md-software.de/math/DAMM_Quasigruppen.txt
    private static final int[][] MATRIX = new int[][] {
    { 0,  2,  3,  1,  4,  6,  7,  5,  8, 10, 11,  9, 12, 14, 15, 13, 16, 18, 19, 17, 20, 22, 23, 21, 24, 26, 27, 25, 28, 30, 31, 29, 32, 34, 35, 33},
    { 2,  0,  1,  3,  6,  4,  5,  7, 10,  8,  9, 11, 14, 12, 13, 15, 18, 16, 17, 19, 22, 20, 21, 23, 26, 24, 25, 27, 30, 28, 29, 31, 34, 32, 33, 35},
    { 3,  1,  0,  2,  7,  5,  4,  6, 11,  9,  8, 10, 15, 13, 12, 14, 19, 17, 16, 18, 23, 21, 20, 22, 27, 25, 24, 26, 31, 29, 28, 30, 35, 33, 32, 34},
    { 1,  3,  2,  0,  5,  7,  6,  4,  9, 11, 10,  8, 13, 15, 14, 12, 17, 19, 18, 16, 21, 23, 22, 20, 25, 27, 26, 24, 29, 31, 30, 28, 33, 35, 34, 32},
    {32, 34, 35, 33,  0,  2,  3,  1,  4,  6,  7,  5,  8, 10, 11,  9, 12, 14, 15, 13, 16, 18, 19, 17, 20, 22, 23, 21, 24, 26, 27, 25, 28, 30, 31, 29},
    {34, 32, 33, 35,  2,  0,  1,  3,  6,  4,  5,  7, 10,  8,  9, 11, 14, 12, 13, 15, 18, 16, 17, 19, 22, 20, 21, 23, 26, 24, 25, 27, 30, 28, 29, 31},
    {35, 33, 32, 34,  3,  1,  0,  2,  7,  5,  4,  6, 11,  9,  8, 10, 15, 13, 12, 14, 19, 17, 16, 18, 23, 21, 20, 22, 27, 25, 24, 26, 31, 29, 28, 30},
    {33, 35, 34, 32,  1,  3,  2,  0,  5,  7,  6,  4,  9, 11, 10,  8, 13, 15, 14, 12, 17, 19, 18, 16, 21, 23, 22, 20, 25, 27, 26, 24, 29, 31, 30, 28},
    {28, 30, 31, 29, 32, 34, 35, 33,  0,  2,  3,  1,  4,  6,  7,  5,  8, 10, 11,  9, 12, 14, 15, 13, 16, 18, 19, 17, 20, 22, 23, 21, 24, 26, 27, 25},
    {30, 28, 29, 31, 34, 32, 33, 35,  2,  0,  1,  3,  6,  4,  5,  7, 10,  8,  9, 11, 14, 12, 13, 15, 18, 16, 17, 19, 22, 20, 21, 23, 26, 24, 25, 27},
    {31, 29, 28, 30, 35, 33, 32, 34,  3,  1,  0,  2,  7,  5,  4,  6, 11,  9,  8, 10, 15, 13, 12, 14, 19, 17, 16, 18, 23, 21, 20, 22, 27, 25, 24, 26},
    {29, 31, 30, 28, 33, 35, 34, 32,  1,  3,  2,  0,  5,  7,  6,  4,  9, 11, 10,  8, 13, 15, 14, 12, 17, 19, 18, 16, 21, 23, 22, 20, 25, 27, 26, 24},
    {24, 26, 27, 25, 28, 30, 31, 29, 32, 34, 35, 33,  0,  2,  3,  1,  4,  6,  7,  5,  8, 10, 11,  9, 12, 14, 15, 13, 16, 18, 19, 17, 20, 22, 23, 21},
    {26, 24, 25, 27, 30, 28, 29, 31, 34, 32, 33, 35,  2,  0,  1,  3,  6,  4,  5,  7, 10,  8,  9, 11, 14, 12, 13, 15, 18, 16, 17, 19, 22, 20, 21, 23},
    {27, 25, 24, 26, 31, 29, 28, 30, 35, 33, 32, 34,  3,  1,  0,  2,  7,  5,  4,  6, 11,  9,  8, 10, 15, 13, 12, 14, 19, 17, 16, 18, 23, 21, 20, 22},
    {25, 27, 26, 24, 29, 31, 30, 28, 33, 35, 34, 32,  1,  3,  2,  0,  5,  7,  6,  4,  9, 11, 10,  8, 13, 15, 14, 12, 17, 19, 18, 16, 21, 23, 22, 20},
    {20, 22, 23, 21, 24, 26, 27, 25, 28, 30, 31, 29, 32, 34, 35, 33,  0,  2,  3,  1,  4,  6,  7,  5,  8, 10, 11,  9, 12, 14, 15, 13, 16, 18, 19, 17},
    {22, 20, 21, 23, 26, 24, 25, 27, 30, 28, 29, 31, 34, 32, 33, 35,  2,  0,  1,  3,  6,  4,  5,  7, 10,  8,  9, 11, 14, 12, 13, 15, 18, 16, 17, 19},
    {23, 21, 20, 22, 27, 25, 24, 26, 31, 29, 28, 30, 35, 33, 32, 34,  3,  1,  0,  2,  7,  5,  4,  6, 11,  9,  8, 10, 15, 13, 12, 14, 19, 17, 16, 18},
    {21, 23, 22, 20, 25, 27, 26, 24, 29, 31, 30, 28, 33, 35, 34, 32,  1,  3,  2,  0,  5,  7,  6,  4,  9, 11, 10,  8, 13, 15, 14, 12, 17, 19, 18, 16},
    {16, 18, 19, 17, 20, 22, 23, 21, 24, 26, 27, 25, 28, 30, 31, 29, 32, 34, 35, 33,  0,  2,  3,  1,  4,  6,  7,  5,  8, 10, 11,  9, 12, 14, 15, 13},
    {18, 16, 17, 19, 22, 20, 21, 23, 26, 24, 25, 27, 30, 28, 29, 31, 34, 32, 33, 35,  2,  0,  1,  3,  6,  4,  5,  7, 10,  8,  9, 11, 14, 12, 13, 15},
    {19, 17, 16, 18, 23, 21, 20, 22, 27, 25, 24, 26, 31, 29, 28, 30, 35, 33, 32, 34,  3,  1,  0,  2,  7,  5,  4,  6, 11,  9,  8, 10, 15, 13, 12, 14},
    {17, 19, 18, 16, 21, 23, 22, 20, 25, 27, 26, 24, 29, 31, 30, 28, 33, 35, 34, 32,  1,  3,  2,  0,  5,  7,  6,  4,  9, 11, 10,  8, 13, 15, 14, 12},
    {12, 14, 15, 13, 16, 18, 19, 17, 20, 22, 23, 21, 24, 26, 27, 25, 28, 30, 31, 29, 32, 34, 35, 33,  0,  2,  3,  1,  4,  6,  7,  5,  8, 10, 11,  9},
    {14, 12, 13, 15, 18, 16, 17, 19, 22, 20, 21, 23, 26, 24, 25, 27, 30, 28, 29, 31, 34, 32, 33, 35,  2,  0,  1,  3,  6,  4,  5,  7, 10,  8,  9, 11},
    {15, 13, 12, 14, 19, 17, 16, 18, 23, 21, 20, 22, 27, 25, 24, 26, 31, 29, 28, 30, 35, 33, 32, 34,  3,  1,  0,  2,  7,  5,  4,  6, 11,  9,  8, 10},
    {13, 15, 14, 12, 17, 19, 18, 16, 21, 23, 22, 20, 25, 27, 26, 24, 29, 31, 30, 28, 33, 35, 34, 32,  1,  3,  2,  0,  5,  7,  6,  4,  9, 11, 10,  8},
    { 8, 10, 11,  9, 12, 14, 15, 13, 16, 18, 19, 17, 20, 22, 23, 21, 24, 26, 27, 25, 28, 30, 31, 29, 32, 34, 35, 33,  0,  2,  3,  1,  4,  6,  7,  5},
    {10,  8,  9, 11, 14, 12, 13, 15, 18, 16, 17, 19, 22, 20, 21, 23, 26, 24, 25, 27, 30, 28, 29, 31, 34, 32, 33, 35,  2,  0,  1,  3,  6,  4,  5,  7},
    {11,  9,  8, 10, 15, 13, 12, 14, 19, 17, 16, 18, 23, 21, 20, 22, 27, 25, 24, 26, 31, 29, 28, 30, 35, 33, 32, 34,  3,  1,  0,  2,  7,  5,  4,  6},
    { 9, 11, 10,  8, 13, 15, 14, 12, 17, 19, 18, 16, 21, 23, 22, 20, 25, 27, 26, 24, 29, 31, 30, 28, 33, 35, 34, 32,  1,  3,  2,  0,  5,  7,  6,  4},
    { 4,  6,  7,  5,  8, 10, 11,  9, 12, 14, 15, 13, 16, 18, 19, 17, 20, 22, 23, 21, 24, 26, 27, 25, 28, 30, 31, 29, 32, 34, 35, 33,  0,  2,  3,  1},
    { 6,  4,  5,  7, 10,  8,  9, 11, 14, 12, 13, 15, 18, 16, 17, 19, 22, 20, 21, 23, 26, 24, 25, 27, 30, 28, 29, 31, 34, 32, 33, 35,  2,  0,  1,  3},
    { 7,  5,  4,  6, 11,  9,  8, 10, 15, 13, 12, 14, 19, 17, 16, 18, 23, 21, 20, 22, 27, 25, 24, 26, 31, 29, 28, 30, 35, 33, 32, 34,  3,  1,  0,  2},
    { 5,  7,  6,  4,  9, 11, 10,  8, 13, 15, 14, 12, 17, 19, 18, 16, 21, 23, 22, 20, 25, 27, 26, 24, 29, 31, 30, 28, 33, 35, 34, 32,  1,  3,  2,  0}};

    static private Character calculateChecksumChar(String in) {
        return charArray.get(getChecksumIndex(in));
    }

    static private int getChecksumIndex(String in) {
        int interim = 0;
        Iterator<Character> i = Lists.charactersOf(in).iterator();
        while (i.hasNext()) {
            interim = checksumStep(interim, i.next());
        }
        return interim;
    }

    static private int checksumStep(int interim, Character ch) {
        int charIndex = charArray.indexOf(ch);
        if (charIndex < 0) {
            throw new IllegalArgumentException("Unsupported character " + ch);
        }
        return MATRIX[interim][charIndex];
    }
}
