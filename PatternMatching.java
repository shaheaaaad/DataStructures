import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author SHAHD BARGOUTHI
 * @version 1.0
 * @userid sbargouthi3
 * @GTID 903876889
 *
 * Collaborators:
 *
 * Resources:
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is either null or of length zero! NULL to be compared!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or Comparator is null! Input valid params.");
        }
        int m = pattern.length();
        int n = text.length();
        List<Integer> occur = new LinkedList<Integer>();
        if (m > n) {
            return occur;
        }
        int[] table = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;


        while (i <= n - m) {
            while (j < m && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == m) {
                    occur.add(i);
                }
                i += j - table[j - 1];
                j = table[j - 1];
            }
        }
        return occur;




    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input pattern.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex. pattern = ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (comparator == null || pattern == null) {
            throw new IllegalArgumentException("Comparator or pattern inputted is null. "
                    + "Please retry with valid params!");
        }
        int m  = pattern.length();
        int[] failure = new int[m];
        int i = 0;
        int j = 1;
        failure[0] = 0;

        while (j < m) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failure[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    failure[j] = 0;
                    j++;
                } else {
                    i = failure[i - 1];
                }
            }
        }
        return failure;

    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is either null or of length zero! NULL to be compared!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or Comparator is null! Input valid params.");
        }
        int m = pattern.length();
        int n = text.length();
        List<Integer> occur = new LinkedList<Integer>();
        if (m > n) {
            return occur;
        }
        Map<Character, Integer> table = buildLastTable(pattern);
        int k = 0;
        while (k <= n - m) {
            int y = m - 1;
            while ((y) >= 0) {

                if (comparator.compare(pattern.charAt(y), text.charAt(k + y)) == 0) {
                    y--;
                } else {
                    break;
                }
            }
            if (y == -1) {
                //match found
                occur.add(k);
                k++;
            } else {
                int shift = table.getOrDefault(text.charAt(k + y), -1);
                if (shift < y) {
                    k += y - shift;
                } else {
                    k++;
                }
            }
        }
        return occur;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null!");
        }
        Map<Character, Integer> table = new HashMap<>(pattern.length() + 1);
        for (int i = 0; i < pattern.length(); i++) {
            table.put(pattern.charAt(i), i);
        }
        return table;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is either null or of length zero! NULL to be compared!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or Comparator is null! Input valid params.");
        }
        int m = pattern.length();
        int n = text.length();
        List<Integer> occur = new LinkedList<Integer>();
        if (m > n) {
            return occur;
        }
        // finding patterns hash
        int hashPattern = 0;
        int pow = 1;
        for (int k = m - 1; k >= 0; k--) {
            hashPattern += pattern.charAt(k) * pow;
            pow *= 113;
        }

        long hashText = 0;
        pow = 1;
        for (int k = m - 1; k >= 0; k--) {
            hashText += text.charAt(k) * pow;
            pow *= 113;
        }

        int i = 0;
        pow /= 113;
        while (i <= n - m) {
            if (hashPattern == hashText) {

                int j = 0;
                while (j < m && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == m) {

                    occur.add(i);

                }
            }
            if (i + m < n) {
                hashText = (hashText - text.charAt(i) * pow) * 113 + text.charAt(i + m);

            }
            i++;
        }

        return occur;
    }


    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is either null or of length zero! NULL to be compared!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or Comparator is null! Input valid params.");
        }
        int m = pattern.length();
        int n = text.length();
        List<Integer> occur = new LinkedList<>();
        if (m > n) {
            return occur;
        }

        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int[] failureTable = buildFailureTable(pattern, comparator);
        int p = m - failureTable[failureTable.length - 1];

        int i = 0;
        int lowBound = 0;
        while (i <= n - m) {
            int j = m - 1;
            while (j >= lowBound && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j < lowBound) {  /// all chars match
                occur.add(i);
                i += p;
                lowBound = m - p;
            } else {   /// not all chars match
                int shift = lastTable.getOrDefault(text.charAt(i + j), -1);
                if (shift < j) {
                    i += j - shift;
                } else {
                    i++;
                }
                lowBound = 0;
            }
        }

        return occur;
    }

}
