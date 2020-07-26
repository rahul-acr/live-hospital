package com.social.it.impl;

import com.social.it.WordDistanceCalculator;
import org.springframework.stereotype.Component;

@Component
public class LevenshteinDistanceCalculator implements WordDistanceCalculator {

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    @Override
    public int calculateDistance(CharSequence input1, CharSequence input2) {
        int[][] dp = new int[input1.length() + 1][input2.length() + 1];

        for (int i = 0; i <= input1.length(); i++) {
            for (int j = 0; j <= input2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + (input1.charAt(i - 1) == input2.charAt(j - 1) ? 0 : 1),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[input1.length()][input2.length()];
    }
}
