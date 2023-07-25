import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class CourseWork_SD2 {
    // Define static cashier Array
    static String[] cashier1 = new String[2];
    static String[] cashier2 = new String[3];
    static String[] cashier3 = new String[5];
    // Define static init burgers counts
    static int burgersCount = 50;
    // Define the cashiers text
    static String cashiers = """
                ******************
                **   Cashiers   **
                ******************
                """;

    public static void main(String[] args) {
        // Define the menu
        String question = """
                \n-----------------------------------------  Menu  -----------------------------------------
                100 or VFQ: View all Queues.
                101 or VEQ: View all Empty Queues.
                102 or ACQ: Add customer to a Queue.
                103 or RCQ: Remove a customer from a Queue.
                104 or PCQ: Remove a served customer.
                105 or VCS: View Customers Sorted in alphabetical order.
                106 or SPD: Store Program Data into file.
                107 or LPD: Load Program Data from file.
                108 or STK: View Remaining burgers Stock.
                109 or AFS: Add burgers to Stock.
                999 or EXT: Exit the Program.
                ------------------------------------------------------------------------------------------
                """;

        for (int i = 0; i < 2; i++) {
            cashier1[i] = "X";
        }

        for (int i = 0; i < 3; i++) {
            cashier2[i] = "X";
        }

        for (int i = 0; i < 5; i++) {
            cashier3[i] = "X";
        }

        boolean exit = true;
        while (exit) {
            System.out.println(question);

            Scanner input = new Scanner(System.in);
            System.out.print("Enter the number corresponding to your choice: ");
            String userInput = input.nextLine();

            // Check burger count less than 10
            if (burgersCount <= 10) {
                System.out.println("Attention! burger store low on inventory. Please restock");
            }

            switch (userInput) {
                case "100", "VFQ" ->
                        viewAllQueues();
                case "101", "VEQ" ->
                        viewEmptyQueues();
                case "102", "ACQ" ->
                        addCustomer();
                case "103", "RCQ" -> {
                    if (!cashier1[0].equals("X") || !cashier2[0].equals("X") || !cashier3[0].equals("X")) {
                        removeCustomer();
                    } else {
                        System.out.println("Customers are not in customer queues");
                    }
                }
                case "104", "PCQ" -> {
                    if (!cashier1[0].equals("X") || !cashier2[0].equals("X") || !cashier3[0].equals("X")) {
                        removeServedCustomer();
                    } else {
                        System.out.println("Customers are not in customer queues");
                    }
                }
                case "105", "VCS" -> {
                    if (!cashier1[0].equals("X") || !cashier2[0].equals("X") || !cashier3[0].equals("X")) {
                        viewCustomersList();
                    } else {
                        System.out.println("Customers are not in customer queues");
                    }
                }
                case "106", "SPD" ->
                        storeDataIntoFile();
                case "107", "LPD" ->
                        loadDataFromFile();
                case "108", "STK" ->
                        viewRemainingBurgers();
                case "109", "AFS" ->
                        addBurgers();
                case "999", "EXT" ->
                        exit = false;
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Define method
    private static void viewAllQueues() {
        System.out.print(cashiers);
        for (int i = 0; i <= 4; i++) {
            try {
                if (!Objects.equals(cashier1[i], "X")) {
                    System.out.print("O" + "\t\t");
                } else {
                    System.out.print(cashier1[i] + "\t\t");
                }
            } catch (IndexOutOfBoundsException error) {
                System.out.print(" \t\t");
            }

            try {
                if (!Objects.equals(cashier2[i], "X")) {
                    System.out.print("O" + "\t\t");
                } else {
                    System.out.print(cashier2[i] + "\t\t");
                }
            } catch (IndexOutOfBoundsException error) {
                System.out.print(" \t\t");
            }

            try {
                if (!Objects.equals(cashier3[i], "X")) {
                    System.out.println("O" + "\t\t");
                } else {
                    System.out.println(cashier3[i] + "\t\t");
                }
            } catch (IndexOutOfBoundsException error) {
                System.out.println(" \t\t");
            }
        }
        System.out.println("\nO – Occupied   X – Not Occupied");
    }
    // Define method
    public static void viewEmptyQueues() {
        System.out.print(cashiers);
        for (int i = 0; i <= 4; i++) {
            try {
                if (!cashier1[i].equals("X")) {
                    System.out.print(" \t\t");
                } else {
                    System.out.print(cashier1[i] + "\t\t");
                }
            } catch (IndexOutOfBoundsException error) {
                System.out.print(" \t\t");
            }

            try {
                if (!cashier2[i].equals("X")) {
                    System.out.print(" \t\t");
                } else {
                    System.out.print(cashier2[i] + "\t\t");
                }
            } catch (IndexOutOfBoundsException error) {
                System.out.print(" \t\t");
            }

            try {
                if (!cashier3[i].equals("X")) {
                    System.out.println(" \t\t");
                } else {
                    System.out.println(cashier3[i] + "\t\t");
                }
            } catch (IndexOutOfBoundsException error) {
                System.out.println(" \t\t");
            }
        }

        System.out.println("\nX – Not Occupied\n");

        int numOfCustomerCashier1 = countCustomerAtQueues(cashier1);
        if (numOfCustomerCashier1 == 2) {
            System.out.println("Attention: Cashier1 Customer Queue is currently full.");
        }

        int numOfCustomerCashier2 = countCustomerAtQueues(cashier2);
        if (numOfCustomerCashier2 == 3) {
            System.out.println("Attention: Cashier2 Customer Queue is currently full.");
        }

        int numOfCustomerCashier3 = countCustomerAtQueues(cashier3);
        if (numOfCustomerCashier3 == 5) {
            System.out.println("Attention: Cashier3 Customer Queue is currently full.");
        }
    }
    // Define method
    public static void addCustomer() {
        if (burgersCount >= 5) {
            if (cashier1[1].equals("X") || cashier2[2].equals("X") || cashier3[4].equals("X")) {
                int queueNumber = cashierQueueValidation();
                String name = stringValidation();
                switch (queueNumber) {
                    case 1 -> {
                        for (int i = 0; i < 2; i++) {
                            try {
                                if (cashier1[i].equals("X")) {
                                    cashier1[i] = name;
                                    System.out.println("Added a customer to cashier 1 queues.");
                                    return;
                                } else if (!cashier1[1].equals("X")) {
                                    System.out.printf("Attention: Cashier %s Customer Queues are currently full.", queueNumber);
                                    return;
                                }
                            } catch (IndexOutOfBoundsException ignored) {
                            }
                        }
                    }
                    case 2 -> {
                        for (int i = 0; i < 3; i++) {
                            try {
                                if (cashier2[i].equals("X")) {
                                    cashier2[i] = name;
                                    System.out.println("Added a customer to cashier 2 queues.");
                                    return;
                                } else if (!cashier2[2].equals("X")) {
                                    System.out.printf("Attention: Cashier %s Customer Queues are currently full.", queueNumber);
                                    return;
                                }
                            } catch (IndexOutOfBoundsException ignored) {}
                        }
                    }
                    case 3 -> {
                        for (int i = 0; i < 5; i++) {
                            try {
                                if (cashier3[i].equals("X")) {
                                    cashier3[i] = name;
                                    System.out.println("Added a customer to cashier 3 queues.");
                                    return;
                                } else if (!cashier1[4].equals("X")) {
                                    System.out.printf("Attention: Cashier %s Customer Queues are currently full.", queueNumber);
                                    return;
                                }
                            } catch (IndexOutOfBoundsException ignored) {}
                        }
                    }
                    default -> System.out.printf("Attention: Cashier %s Customer Queues are currently full.", queueNumber);
                }
            } else {
                System.out.println("Attention: Cashier Customer Queues are currently full.");
            }
        } else {
            System.out.println("Attention: The burger store is currently at full capacity.");
        }
    }
    // Define method
    public static void removeCustomer() {
        int queueNum = cashierQueueValidation();
        int position = queueValidation(queueNum);


        try {
            switch (queueNum) {
                case 1 -> {
                    if (!cashier1[position - 1].equals("X")) {
                        for (int i = 0; i < 2 - position; i++) {
                            cashier1[position - 1 + i] = cashier1[position + i];
                        }
                        cashier1[1] = "X";
                        System.out.println("Removed " + position + " customer at cashier 1 queue");
                    } else {
                        System.out.println("No person found at position");
                    }
                }
                case 2 -> {
                    if (!cashier2[position - 1].equals("X")) {
                        for (int i = 0; i < 3 - position; i++) {
                            cashier2[position - 1 + i] = cashier2[position + i];
                        }
                        cashier2[2] = "X";
                        System.out.println("Removed " + position + " customer at cashier 2 queue");
                    } else {
                        System.out.println("No person found at position");
                    }
                }
                case 3 -> {
                    if (!cashier3[position - 1].equals("X")) {
                        for (int i = 0; i < 5 - position; i++) {
                            cashier3[position - 1 + i] = cashier3[position + i];
                        }
                        cashier3[4] = "X";
                        System.out.println("Removed " + position + " customer at cashier 3 queue");
                    } else {
                        System.out.println("No person found at position");
                    }
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        } catch (InputMismatchException error) {
            System.out.println("Invalid input. Please try again.");
        }
    }
    // Define method
    public static void removeServedCustomer() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter cashier number (1, 2, or 3): ");
        String queueNum = input.nextLine();

        switch (queueNum) {
            case "1" -> {
                if (!cashier1[0].equals("X")) {
                    for (int i = 0; i < 1; i++) {
                        cashier1[i] = cashier1[i + 1];
                    }
                    cashier1[1] = "X";
                    burgersCount -= 5;
                    System.out.println("Removed served customer from cashier 1 queue.");
                } else {
                    System.out.println("No person found at position");
                }
            }
            case "2" -> {
                if (!cashier2[0].equals("X")) {
                    for (int i = 0; i < 2; i++) {
                        cashier2[i] = cashier2[i + 1];
                    }
                    cashier2[2] = "X";
                    burgersCount -= 5;
                    System.out.println("Removed served customer from cashier 2 queue.");
                } else {
                    System.out.println("No person found at position");
                }
            }
            case "3" -> {
                if (!cashier3[0].equals("X")) {
                    for (int i = 0; i < 4; i++) {
                        cashier3[i] = cashier3[i + 1];
                    }
                    cashier3[4] = "X";
                    burgersCount -= 5;
                    System.out.println("Removed served customer from cashier 3 queue.");
                } else {
                    System.out.println("No person found at position");
                }
            }
            default -> System.out.println("Invalid input. Please try again.");
        }
    }
    // Define method
    public static void viewCustomersList() {
        int index = 0;
        String[] names = new String[10];

        for (String cashier1Name: cashier1) {
            if (!Objects.equals(cashier1Name, "X")) {
                names[index] = cashier1Name;
                index++;
            }
        }
        for (String cashier2Name: cashier2) {
            if (!Objects.equals(cashier2Name, "X")) {
                names[index] = cashier2Name;
                index++;
            }
        }
        for (String cashier3Name: cashier3) {
            if (!Objects.equals(cashier3Name, "X")) {
                names[index] = cashier3Name;
                index++;
            }
        }

        for (int i = 0; i < index - 1; i++) {
            for (int j = 0; j < index - i - 1; j++) {
                String name1 = names[j];
                String name2 = names[j + 1];


                if (name1.compareTo(name2) > 0) {
                    names[j] = name2;
                    names[j + 1] = name1;
                }
            }
        }

        for (String name: names) {
            if (name != null) {
                System.out.println(name);
            }
        }

    }
    // Define method
    public static void storeDataIntoFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("w1987581.txt"))) {
            writer.write("Cashier 1: " + Arrays.toString(cashier1) + "\n");
            writer.write("Cashier 2: " + Arrays.toString(cashier2) + "\n");
            writer.write("Cashier 3: " + Arrays.toString(cashier3) + "\n");
            writer.write("X – Not Occupied\n\n");
            writer.write("Burgers Stock: " + burgersCount);

            System.out.println("Data has been written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing data to the file: " + e.getMessage());
        }
    }
    // Define method
    public static void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("w1987581.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading data from the file: " + e.getMessage());
        }
    }
    // Define method
    public static void viewRemainingBurgers() {
        System.out.println("Burgers Stock: " + burgersCount);
    }
    // Define method
    public static void addBurgers() {
        Scanner input = new Scanner(System.in);
        System.out.print("New stock burgers: ");
        try {
            int newNumOfBurgers = input.nextInt();
            burgersCount += newNumOfBurgers;
            System.out.println("New Burgers Stock: " + burgersCount);
        } catch (InputMismatchException error) {
            System.out.println("Invalid input. Please try again.");
        }
    }
    // Define private method
    private static int countCustomerAtQueues(String[] cashier) {
        int numOfCustomer = 0;
        for (String occupied: cashier) {
            if (!occupied.equals("X")) {
                numOfCustomer++;
            }
        }
        return numOfCustomer;
    }
    // Define private method
    private static String stringValidation() {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter customer name: ");
            String name = input.nextLine();
            if (name.matches("^[a-zA-Z]+$")) {
                return name.trim();
            }
            System.out.println("Please enter correct name.");
        }

    }
    // Define private method
    private static int cashierQueueValidation() {
        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter cashier number (1, 2, or 3): ");
                int userInput = input.nextInt();
                if (1 <= userInput && userInput <= 3) {
                    return userInput;
                } else {
                    System.out.println("Enter number should be 1 or 2 or 3. Please try again");
                }
            } catch (InputMismatchException error) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
    // Define private method
    private static int queueValidation(int cashierNo) {
        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter customer position at the queue: ");
                int position = input.nextInt();
                if (cashierNo == 1) {
                    if (1 <= position && position <= 2) {
                        return position;
                    } else {
                        System.out.println("Entered position is out of range. Please try again.");
                    }
                } else if (cashierNo == 2) {
                    if (1 <= position && position <= 3) {
                        return position;
                    } else {
                        System.out.println("Entered position is out of range. Please try again.");
                    }
                } else if (cashierNo == 3) {
                    if (1 <= position && position <= 5) {
                        return position;
                    } else {
                        System.out.println("Entered position is out of range. Please try again.");
                    }
                }
            } catch (InputMismatchException error) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
