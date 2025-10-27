public class Ex_02 {
    //LeetCode: https://leetcode.com/problems/can-place-flowers/description/

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        for(int i = 0; i < flowerbed.length; i++) {
            if(i == 0 && flowerbed[i] != 1){ 
                if(flowerbed[i+1] == 0) {
                    count++;
                    flowerbed[i] = 1;
                }
            }
            else if(flowerbed[i] != 1 && i != 0 && i!= flowerbed.length) {
                if(flowerbed[i-1] == 0 && flowerbed[i+1]==0) {
                    count++;
                    flowerbed[i] = 1;
                }
            }
            else if(i == flowerbed.length && flowerbed[i] != 1)
                if(flowerbed[i-1] == 0) {
                    count++;
                    flowerbed[i] = 1;
                }
        }
        System.out.println(count + " ");
        if(count < n)
            return false;
        else return true;
    }

    public static void main(String[] args) {
        int[] test1 = {1,0,0,0,1};
        int[] test2 = {0,1,0,1,0};
        int[] test3 = {0,0,0,1,0};

        System.out.println(canPlaceFlowers(test1, 1)); //true
        System.out.println(canPlaceFlowers(test1, 2)); //false
        System.out.println(canPlaceFlowers(test2, 1)); //false
        System.out.println(canPlaceFlowers(test3, 1)); //true
        System.out.println(canPlaceFlowers(test3, 2)); //false
    }
}
