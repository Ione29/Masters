import java.util.Scanner;
import java.util.Random;

class Lab_01{
public static void main(String args[]) {
    System.out.println("Exercise 1:");
    double result = (1.0/2 + (3.0/17 * 5.0/2) + Math.sqrt(1.0/2))/(2/Math.pow(7,3)) * (9.0/2 + 3.0/4)/1/Math.pow(3,3);
    System.out.println("Result = " + result);

    System.out.println("\nExercise 2:");
    Scanner scan = new Scanner(System.in);
    System.out.println("First Name: ");
    String fname = scan.nextLine();
    System.out.println("Last Name: ");
    String lname = scan.nextLine();
    System.out.println("CNP: ");
    @SuppressWarnings("unused")
    String cnp = scan.nextLine();

    String pass = fname.substring(0,2).toLowerCase() + lname.substring(lname.length()-2, lname.length()).toUpperCase();

    System.out.println("Your password is: "+ pass);

    System.out.println("\n" + "Exercise 3: ");
    System.out.println("Type in the sentence: ");
    String sentence = scan.nextLine();
    int vowels = 0, consonants = 0;
    for(int x = 0; x < sentence.length(); x++) {
        char letter = sentence.charAt(x);
        switch(letter){
            case 'a', 'e', 'i', 'o', 'u':
                vowels++;
                break;
            default:
                consonants++;
                break;
            } 
        }
    System.out.println("Number of vowels: " + vowels);
    System.out.println("Number of consonants: " + consonants);

    System.out.println("\nExercise 4: ");
    Random r = new Random();
    System.out.println("The matrix is: ");
    int[][] array = new int[5][6];
    for(int i = 0; i < 5; i++){
        for(int j = 0; j < 6; j++){
            array[i][j] = r.nextInt(101);
            System.out.print(array[i][j] + " ");
        }
        System.out.println();
    } 

    for(int i = 0; i < 5; i++) {
        int rowsum = 0;
        for(int j = 0; j <6; j++) {
            rowsum += array[i][j];
        }
        System.out.println("Sum of row " + (i + 1) + " is: " + rowsum);
    }

    for(int j = 0; j < 6; j++) {
        int colsum = 0;
        for(int i = 0; i < 5; i++) {
            colsum += array[i][j];
        }
        System.out.println("Sum of collumn " + (j + 1) + " is: " + colsum);
    }

    System.out.println("\nExercise 5:");
    int[] array2 = new int[100];
    for(int i = 0; i < 100; i++) {
        array2[i] = r.nextInt(101);
    }

    float maxdif = 0;
    int longest_suc_even = 0;
    int cur_suc_even = 0;
    for(int i = 0; i < 100; i++) {
        if(array2[i]%2 == 0 && array2[i+1]%2 == 0)
            cur_suc_even++;
        else cur_suc_even = 0;

        if(cur_suc_even > longest_suc_even)
            longest_suc_even = cur_suc_even;

        if(i <100)
            if(array2[i+1] - array2[i] >= maxdif)
                maxdif = array2[i+1]-array2[i];
        else break;
    }

    System.out.println("Longest successive even number: " + longest_suc_even);
    System.out.println("Greatest difference: " + maxdif);

    scan.close();
    }


}
