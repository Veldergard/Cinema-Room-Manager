package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    final static Scanner scanner = new Scanner(System.in);
    final static int price = 8;
    final static int bigPrice = 10;
    final static int maxSize = 60;
    static int numberOfPurchased = 0;
    static int income = 0;
    static int totalSeats = 0;
    static int totalIncome = 0;

    public static void main(String[] args) {
        boolean isCorrect = false;
        int rows;
        int seats;
        do {
            System.out.println("Enter the number of rows:");
            rows = scanner.nextInt();
            System.out.println("Enter the number of seats in each row:");
            seats = scanner.nextInt();
            if (rows > 0 && seats > 0) {
                isCorrect = true;
            } else {
                error();
            }
        } while (!isCorrect);
        char[][] cinema = new char[rows][seats];
        initCinema(cinema, rows, seats);
        boolean result = true;
        while (result) {
            result = showMenu(cinema, rows, seats);
        }
    }

    public static void initCinema(char[][] cinema, int rows, int seats) {
        for (char[] row : cinema) {
            Arrays.fill(row, 'S');
        }
        if (rows * seats > maxSize) {
            totalIncome = rows / 2 * seats * bigPrice;
            totalIncome += (rows % 2 == 1 ? rows / 2 + 1 : rows / 2) * seats * price;
        } else {
            totalIncome = rows * seats * bigPrice;
        }
        totalSeats = rows * seats;
    }

    public static boolean showMenu(char[][] cinema, int rows, int seats) {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        int action = scanner.nextInt();
        switch (action) {
            case 1:
                showSeats(cinema, rows, seats);
                return true;
            case 2:
                buyTicket(cinema, rows, seats);
                return true;
            case 3:
                statistics();
                return true;
            case 0:
                return false;
            default:
                error();
                return true;
        }
    }

    public static void error() {
        System.out.println("Wrong input!");
    }

    public static void buyTicket(char[][] cinema, int rows, int seats) {
        boolean isPurchased = false;
        do {
            System.out.println("\nEnter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();
            if (row <= 0 || row > rows || seat <= 0 || seat > seats) {
                error();
            } else if (cinema[row - 1][seat - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                cinema[row - 1][seat - 1] = 'B';
                int ticketPrice = bigPrice;
                if (rows * seats > maxSize && row > rows / 2) {
                    ticketPrice = price;
                }
                income += ticketPrice;
                numberOfPurchased += 1;
                isPurchased = true;
                System.out.println("\nTicket price: $" + ticketPrice);
            }
        } while (!isPurchased);
    }

    public static void showSeats(char[][] cinema, int rows, int seats) {
        System.out.println("\nCinema:");
        for (int i = 0; i <= seats; i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.print(i + " ");
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= seats; j++) {
                if (j == 0) {
                    System.out.println();
                    System.out.print(i + 1 + " ");
                } else {
                    System.out.print(cinema[i][j - 1] + " ");
                }
            }
        }
        System.out.println();
    }

    public static void statistics() {
        System.out.println();
        System.out.printf("Number of purchased tickets: %d%n", numberOfPurchased);
        System.out.printf("Percentage: %.2f%%%n", 100.0 * numberOfPurchased / totalSeats);
        System.out.printf("Current income: $%d%n", income);
        System.out.printf("Total income: $%d", totalIncome);
        System.out.println();
    }
}