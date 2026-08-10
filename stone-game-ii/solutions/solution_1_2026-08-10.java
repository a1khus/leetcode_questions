class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;

        // suffix[i] = sum of piles from i to n-1
        int[] suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        // dp[i][m]
        int[][] dp = new int[n + 1][n + 1];

        // Start from the end
        for (int i = n - 1; i >= 0; i--) {

            for (int m = n; m >= 1; m--) {

                // Can take X piles where X <= 2*m
                for (int x = 1; x <= 2 * m && i + x <= n; x++) {

                    int newM = Math.max(m, x);

                    // Stones taken by current player
                    int taken = suffix[i] - suffix[i + x];

                    // Stones current player finally gets
                    int current = taken + 
                                  (suffix[i + x] - dp[i + x][newM]);

                    dp[i][m] = Math.max(dp[i][m], current);
                }
            }
        }

        return dp[0][1];
    }
}