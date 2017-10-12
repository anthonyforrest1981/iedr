package pl.nask.crs.commons.utils;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.text.Normalizer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;

public final class Validator {

    private Validator() {}

    /**
     * Throws an exception, if the value parameter is null, or an empty String
     * after trimming.
     *
     * @param value     parameter to check
     * @param valueName name of the parameter to be used in the exception message
     * @throws IllegalArgumentException if value parameter is null, or an empty String after
     *                                  trimming.
     */
    /*>>>@EnsuresNonNull("#1")*/
    /*>>>@Pure*/
    public static void assertNotEmpty(/*>>>@Nullable*/ String value, String valueName) {
        if (isEmpty(value))
            throw new IllegalArgumentException(valueName + " cannot be empty");
    }

    /*>>>@EnsuresNonNullIf(expression = "#1", result = false)*/
    /*>>>@Pure*/
    public static boolean isEmpty(/*>>>@Nullable*/ String value) {
        return value == null || value.trim().length() == 0;
    }

    public static void assertNotEmpty(Collection<?> value, String valueName) {
        if (isEmpty(value))
            throw new IllegalArgumentException(valueName + " cannot be empty");
    }

    public static boolean isEmpty(/*>>>@Nullable*/ Collection<? /*>>>extends @Nullable Object*/> value) {
        return value == null || value.isEmpty();
    }

    /**
     * Throws an exception, if the value parameter is null.
     *
     * @param value     parameter to check
     * @param valueName name of the parameter to be used in the exception message
     * @throws IllegalArgumentException if the value parameter is null
     */
    /*>>>@EnsuresNonNull(value="#1")*/
    /*>>>@Pure*/
    public static void assertNotNull(/*>>>@Nullable*/ Object value, String valueName) {
        if (value == null)
            throw new IllegalArgumentException(valueName + " cannot be null");
    }

    /**
     * Throws an exception, if the value parameter is false.
     *
     * @param value     boolean parameter to check
     * @param valueName name of the parameter to be used in the exception message
     * @throws IllegalArgumentException if the value parameter is false
     */
    public static void assertTrue(boolean value, String valueName) {
        if (!value)
            throw new IllegalArgumentException(valueName + " cannot be false");
    }

    /**
     * If a collection contains duplicated elements, it will return one of the duplicates.
     *
     * @param list to be searched
     * @return duplicated element or null if there is no duplicates
     */
    public static <T> T getDuplicates(Collection<T> list) {
        Set<T> set = new HashSet<T>();
        for (T el : list) {
            if (!set.add(el))
                return el;
        }
        return null;
    }

    /**
     * Will return true, if the collection contains duplicated elements.
     *
     * @param collection collection to be checked.
     * @return true, if the collection contains duplicated elements.
     */
    public static boolean hasDuplicates(Collection<?> collection) {
        Object duplicated = getDuplicates(collection);
        return duplicated != null;
    }

    public static boolean isEqual(Object obj1, Object obj2) {
        if (obj1 == obj2)
            return true;

        return obj1 != null && obj1.equals(obj2);
    }

    public static String getNormalizedAndValidatedUtf8(String value) throws IncorrectUtf8FormatException {
        String normalized = getNormalizedUtf8(value);
        validateUtf8(normalized);
        return normalized;
    }

    public static String getNormalizedUtf8(String value) {
        if (value == null) {
            return null;
        } else {
            return Normalizer.normalize(value, Normalizer.Form.NFC);
        }
    }

    /**
     * Verifies whether UTF-8 string is correct - only 3- or less byte strings allowed
     */
    private static void validateUtf8(String value) throws IncorrectUtf8FormatException {
        if (value != null) {
            int i = 0;
            while (i < value.length()) {
                int codepoint = Character.codePointAt(value, i);
                i += Character.charCount(codepoint);
                if (!Character.isBmpCodePoint(codepoint) // correct set of chars that may be safely written to the database
                        || !isPrintableChar(codepoint)) {
                    throw new IncorrectUtf8FormatException();
                }
            }
        }
    }

    private static boolean isPrintableChar(int codepoint) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(codepoint);
        return (!Character.isIdentifierIgnorable(codepoint)) && block != null
                && block != Character.UnicodeBlock.SPECIALS;
    }

}
