class Solution {

    // factors[d] = {number of 2s, 3s, 5s, 7s in digit d}
    private static final int[][] FACTORS = {
        {0, 0, 0, 0}, // 0
        {0, 0, 0, 0}, // 1
        {1, 0, 0, 0}, // 2
        {0, 1, 0, 0}, // 3
        {2, 0, 0, 0}, // 4
        {0, 0, 1, 0}, // 5
        {1, 1, 0, 0}, // 6
        {0, 0, 0, 1}, // 7
        {3, 0, 0, 0}, // 8
        {0, 2, 0, 0}  // 9
    };

    public String smallestNumber(String num, long t) {

        // --------------------------------------------------
        // 1. Factorize t into 2, 3, 5, 7
        // --------------------------------------------------

        int[] need = new int[4];

        int[] primes = {2, 3, 5, 7};

        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                need[i]++;
                t /= primes[i];
            }
        }

        // If anything remains, it has a prime factor > 7.
        // No digit from 1 to 9 can provide that factor.
        if (t != 1) {
            return "-1";
        }

        // --------------------------------------------------
        // 2. Find minimum number of digits required
        // --------------------------------------------------

        int[] factorCount = getFactorCount(need);

        if (sum(factorCount) > num.length()) {
            return construct(factorCount);
        }

        // --------------------------------------------------
        // 3. Count factors already present in num
        // --------------------------------------------------

        int[] prefix = new int[4];

        for (int i = 0; i < num.length(); i++) {

            int digit = num.charAt(i) - '0';

            for (int j = 0; j < 4; j++) {
                prefix[j] += FACTORS[digit][j];
            }
        }

        // Find first zero.
        int firstZero = num.indexOf('0');

        if (firstZero == -1) {
            firstZero = num.length();

            // num itself is already valid
            if (contains(prefix, need)) {
                return num;
            }
        }

        // --------------------------------------------------
        // 4. Change a digit from RIGHT to LEFT
        // --------------------------------------------------

        for (int i = num.length() - 1; i >= 0; i--) {

            int digit = num.charAt(i) - '0';

            // Remove current digit from prefix
            for (int j = 0; j < 4; j++) {
                prefix[j] -= FACTORS[digit][j];

                if (prefix[j] < 0) {
                    prefix[j] = 0;
                }
            }

            int spaceAfter = num.length() - 1 - i;

            // Anything after the first zero cannot remain unchanged.
            if (i > firstZero) {
                continue;
            }

            // Try the smallest digit greater than current digit.
            for (int bigger = digit + 1; bigger <= 9; bigger++) {

                int[] remaining = new int[4];

                // Required factors after:
                // prefix + bigger digit
                for (int j = 0; j < 4; j++) {

                    remaining[j] =
                        Math.max(
                            0,
                            need[j]
                                - prefix[j]
                                - FACTORS[bigger][j]
                        );
                }

                // Convert remaining prime factors into digits.
                int[] suffix = getFactorCount(remaining);

                int requiredDigits = sum(suffix);

                // Can the remaining factors fit?
                if (requiredDigits <= spaceAfter) {

                    StringBuilder ans = new StringBuilder();

                    // Keep prefix unchanged.
                    ans.append(num.substring(0, i));

                    // Bigger digit.
                    ans.append((char) ('0' + bigger));

                    // Fill unused positions with 1.
                    for (int j = 0;
                         j < spaceAfter - requiredDigits;
                         j++) {

                        ans.append('1');
                    }

                    // Add required digits.
                    ans.append(construct(suffix));

                    return ans.toString();
                }
            }
        }

        // --------------------------------------------------
        // 5. No same-length answer.
        //    Construct answer with one extra digit.
        // --------------------------------------------------

        int[] suffix = getFactorCount(need);

        StringBuilder ans = new StringBuilder();

        int ones = num.length() + 1 - sum(suffix);

        for (int i = 0; i < ones; i++) {
            ans.append('1');
        }

        ans.append(construct(suffix));

        return ans.toString();
    }


    // ------------------------------------------------------
    // Convert prime factors into minimum number of digits.
    //
    // 2 * 2 * 2 = 8
    // 3 * 3     = 9
    // 2 * 2     = 4
    // 2 * 3     = 6
    // ------------------------------------------------------

    private int[] getFactorCount(int[] count) {

        int c2 = count[0];
        int c3 = count[1];
        int c5 = count[2];
        int c7 = count[3];

        int[] result = new int[10];

        // 2^3 = 8
        result[8] = c2 / 3;
        c2 %= 3;

        // 3^2 = 9
        result[9] = c3 / 2;
        c3 %= 2;

        // 2^2 = 4
        result[4] = c2 / 2;
        c2 %= 2;

        // 2 * 3 = 6
        if (c2 == 1 && c3 == 1) {

            result[6]++;

            c2 = 0;
            c3 = 0;
        }

        // 3 * 4 = 12
        // Better represented as 2 * 6
        if (c3 == 1 && result[4] == 1) {

            result[4]--;
            result[6]++;

            c2++;
            c3 = 0;
        }

        result[2] += c2;
        result[3] += c3;

        result[5] = c5;
        result[7] = c7;

        return result;
    }


    // ------------------------------------------------------
    // Construct digits in increasing order.
    // ------------------------------------------------------

    private String construct(int[] count) {

        StringBuilder sb = new StringBuilder();

        for (int digit = 2; digit <= 9; digit++) {

            for (int i = 0; i < count[digit]; i++) {
                sb.append((char) ('0' + digit));
            }
        }

        return sb.toString();
    }


    // ------------------------------------------------------
    // Sum number of required digits
    // ------------------------------------------------------

    private int sum(int[] count) {

        int total = 0;

        for (int digit = 2; digit <= 9; digit++) {
            total += count[digit];
        }

        return total;
    }


    // ------------------------------------------------------
    // Check whether prefix contains all required factors.
    // ------------------------------------------------------

    private boolean contains(int[] have, int[] need) {

        for (int i = 0; i < 4; i++) {

            if (have[i] < need[i]) {
                return false;
            }
        }

        return true;
    }
}