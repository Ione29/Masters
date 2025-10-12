import java.util.Arrays;

public class Ex_01 {
    // LeetCode: https://leetcode.com/problems/kids-with-the-greatest-number-of-candies/description/

    public static boolean[] moreCandies(int[] candies, int extraCandies){
        int n = candies.length;
        boolean[] result = new boolean[n];

        int max = Integer.MIN_VALUE;
        for(int c : candies) {
            if(c > max) max = c;
        }

        for(int i = 0; i < n; i++){
            result[i] = (candies[i] + extraCandies) >= max;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] candies1 = {2,3,5,1,3};
        int[] candies2 = {4,2,1,1,2};
        int[] candies3 = {12,1,12};

        System.out.println("Test 1:");
        System.out.println(Arrays.toString(moreCandies(candies1, 3)));

        System.out.println("Test 2:");
        System.out.println(Arrays.toString(moreCandies(candies2, 1)));

        System.out.println("Test 3:");
        System.out.println(Arrays.toString(moreCandies(candies3, 10)));
    }
}

