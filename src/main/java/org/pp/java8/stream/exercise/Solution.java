package org.pp.java8.stream.exercise;

import java.util.Arrays;
import java.util.Scanner;


class Difference {
    private int[] elements;
    public int maximumDifference;

    // Add your code here
    public Difference(int[] elements) {
        this.elements = elements;
    }

    /**
     * Sorts the array
     * Saves max abs difference between first and last element to maximumDifference.
     **/
    public void computeDifference() {
        maximumDifference = Arrays.stream(elements).max().getAsInt() - Arrays.stream(elements).min().getAsInt();
    }

} // End of Difference class

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        sc.close();

        Difference difference = new Difference(a);

        difference.computeDifference();

        System.out.print(difference.maximumDifference);
    }
}