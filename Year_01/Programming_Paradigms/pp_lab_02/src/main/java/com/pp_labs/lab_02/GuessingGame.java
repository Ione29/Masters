package com.pp_labs.lab_02;

import java.util.Random;

// LeetCode: https://leetcode.com/problems/guess-number-higher-or-lower/description/

public class GuessingGame {

    protected int guess(int num, int pick) {
        if (num > pick)
            return -1;
        else if (num < pick)
            return 1;
        else 
            return num;
          
    }

    public int guessNumber(int n) {
        return guessNumberIterative(n);
    }

    public int guessNumberIterative(int n) {
        int low = 1, high = n;
        Random random = new Random();
        int pick = random.nextInt(n);
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int res = guess(mid, pick);
            if (res == 0)
                return mid;
            else if (res < 0)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }

    public int guessNumberRecursive(int n) {
        Random random = new Random();
        int pick = random.nextInt(n);
        return guessHelper(1, n, pick);
    }

    private int guessHelper(int low, int high, int pick) {
        if (low > high)
            return -1;
        int mid = low + (high - low) / 2;
        int res = guess(mid, pick);
        if (res == 0)
            return mid;
        else if (res < 0)
            return guessHelper(low, mid - 1, pick);
        else
            return guessHelper(mid + 1, high, pick);
    }
}