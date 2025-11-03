                                                     
/** 
 * Sums-of-Product and Product-of-Sums Calculator Implementation
 * <p>
 * This Java program simulates a console-based version of the SOP and POS Calculator, where the user
 * fills the truth table with boolean values.
 * It is designed for a single user to enter the degreeValue and the function values one input at a tinputMismatchExc.
 * The program continues continues to allow the user to enter the function values until the table slots for function values is filled.
 * Incorrect inputs are tracked, and the player is informed when the input is invalid, at each input to keep the user on track. </p>
 * <p>
 * Key Design Considerations:
 * - User: inputs degreeValue (2-4) and the boolean function value(0 or 1) and the program checks these for correctness. 
 * Up on valid inputs, truth table is displayed, which can be edited if the user spots an error by typing (yes/y), 
 * until user wants to continue with the current truth table by typing (no/n).
 * The program is designed to display truth table before user edits functioon values and after edits too, ensuring that user stays update.
 * Next, the program computes and displays the sum-of-products and product-of-sums expansions.
 * Importantly, our program is designed to recognize when an SOP or POS expansion isn't available, 
 * such as when all function values are 0s or 1s, and it will clearly indicate this to the user.
 * This thoughtful feature ensures that our users are well-informed of the validity of their function's expansions.
 * </p>
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class SOP_POS {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int degreeValue = readDegreeValue();
        int numOfRows = (int) Math.pow(2, degreeValue);
        int[][] tableValues = generateTableValues(numOfRows, degreeValue);
        char[] variableNames = generateVariableNames(degreeValue);
        int[] functionValue = readFunctionValues(numOfRows, degreeValue);
        allowUserEdits(tableValues, functionValue, variableNames, numOfRows, degreeValue);
        String sop = computeTruthTableSOP(tableValues, functionValue, numOfRows, degreeValue);
        String pos = computeTruthTablePOS(tableValues, functionValue, numOfRows, degreeValue);
        System.out.println(pos);
    }

    /**
     * Prompts the user to enter a degree for the function, checking if it ranges
     * between 2 to 4, inclusive.
     * This method repeatedly asks the user for input until user enters a valid
     * degree (an integer between 2 and 4, inclusive) is provided.
     * It handles input errors through exceptions, prompting the user to re-enter if
     * non-integer
     * values are entered.
     * 
     * @return degreeValue represents the valid degree entered by the user,
     *         guaranteed to be either 2, 3, or 4.
     * @throws InputMismatchException if the input provided by the user is not an
     *                                integer.
     *                                This exception is caught and handled within
     *                                the method,
     *                                therefore the user is prompted again until a
     *                                valid integer is entered.
     */

    public static int readDegreeValue() {
        int degreeValue = 0;
        while (degreeValue < 2 || degreeValue > 4) {
            System.out.print("Enter value of degree for the function (2-4): ");
            try {
                degreeValue = scanner.nextInt();
                if (degreeValue < 2 || degreeValue > 4) {
                    System.out.println("Invalid degree! Please enter integer between 2-4, inclusive.");
                }
            } catch (InputMismatchException inpMismatchEx) {
                System.out.println("Invalid Input. Please enter an integer between 2-4, inclusive. ");
                scanner.next(); // read the user input until space is found
            }
        }
        return degreeValue;
    }

    /**
     * Generates array of variable names for the table based on the degreeValue.
     *
     * @param degreeValue represents The number of variables.
     * @return variableNames An array of characters,
     *         each representing a variable name.
     *         The array length is equal to the degreeValue entered by the user,
     *         and the names are letters starting from 'A'.
     *         i.e., if degreeValue is 4, then variable names would be 'A', 'B',
     *         'C', 'D'.
     */

    private static char[] generateVariableNames(int degreeValue) {
        char[] variableNames = new char[degreeValue];
        for (int i = 0; i < degreeValue; i++) {
            variableNames[i] = (char) ('A' + i);
        }
        return variableNames;
    }

    /**
     * Generates the truth table values, given the number of rows and
     * the degreeValue which is number of variables.
     *
     * @param numOfRows   The total number of rows in the table.
     * @param degreeValue The number of variables.
     * @return two dinputMismatchExcnsion array representing the truth table values.
     */

    private static int[][] generateTableValues(int numOfRows, int degreeValue) {
        int[][] tableValues = new int[numOfRows][degreeValue];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < degreeValue; j++) {
                tableValues[i][j] = (i / (int) Math.pow(2, degreeValue - j - 1)) % 2;
            }
        }
        return tableValues;
    }

    /**
     * Reads and generate the function values for each variable in truth table.
     * It prompts the user to enter the function value for each row of the truth
     * table.
     * 
     * @param numOfRows   represents The total number of rows in the table.
     * @param degreeValue represents The number of variables.
     */

    public static int[] readFunctionValues(int numOfRows, int degreeValue) {
        int[] functionValue = new int[numOfRows];
        int[][] genBooleanTableValues = generateTableValues(numOfRows, degreeValue); // Reuse the method for generating
                                                                                     // the
        // table

        for (int i = 0; i < numOfRows; i++) {
            // Generate and update message showing the state of each variable for each row
            StringBuilder message = new StringBuilder();
            for (int j = 0; j < degreeValue; j++) {
                char variableName = (char) ('A' + j);
                message.append(variableName).append("=").append(genBooleanTableValues[i][j]);
                if (j < degreeValue - 1) {
                    message.append(", ");
                } else {
                    message.append(": ");
                }
            }

            boolean validUserInput = false;
            while (!validUserInput) {
                System.out.print("Enter the value  of function 'Y'" + " where ");
                System.out.print(message.toString());
                try {
                    int userInput = scanner.nextInt();
                    // Check if the input is 0 or 1
                    if (userInput == 0 || userInput == 1) {
                        functionValue[i] = userInput;
                        validUserInput = true;
                    } else {
                        System.out.println("Invalid boolean value. Please enter 0 or 1");
                    }
                } catch (InputMismatchException inputMismatchExc) {
                    System.out.println("Invalid Input. Please enter 0 or 1.");
                    scanner.next(); // Read the user input until space is found.
                }
            }
        }
        return functionValue;
    }

    /**
     * Displays a truth table along with the function values.
     * 
     * @param functionValue represent the array containing function values.
     * @param variableNames represents array of characters representing the variable
     *                      names.
     * @param tableValues   represents array containing values the truth table.
     * 
     */
    public static void displayTruthTable(int[][] tableValues, int[] functionValue, char[] variableNames,
            int numOfRows, int degreeValue) {
        // Print the table header with underscores
        printTopBorder(degreeValue);
        // print the table header containing variable names and the function (Y).
        System.out.print("|");
        for (int i = 0; i < degreeValue; i++) {
            // Print each variable name within a 3-character width space, followed by a
            // vertical bar.
            System.out.printf(" %c |", variableNames[i]);
        }
        // Print the header for the function value column.
        System.out.println(" Y |");
        // Print the borderline below the table header.
        printMiddleBorder(degreeValue);

        // Print table rows
        for (int i = 0; i < numOfRows; i++) {
            System.out.print("|");
            for (int j = 0; j < degreeValue; j++) {
                System.out.printf(" %d |", tableValues[i][j]);
            }
            // Print the function value for the row, aligned with the columns.
            System.out.printf(" %d |", functionValue[i]);
            System.out.println();
            // Print the borderline to separate each row
            printMiddleBorder(degreeValue);
        }
    }

    /**
     * Prints the top border of the table.
     * The border contains a "+" at the start and end, with a "-" for each
     * variable and function column.
     *
     * @param degreeValue The number of variables
     */
    private static void printTopBorder(int degreeValue) {

        // Add the border style for nice appearance.
        for (int i = 0; i < degreeValue + 1; i++) {
            System.out.print("****");
        }
        // close the top border.
        System.out.println("*");
    }

    /**
     * method to print the middle border, separating header from the table content
     * in the table.
     * 
     * @param degreeValue The number of variables.
     */

    private static void printMiddleBorder(int degreeValue) {
        printTopBorder(degreeValue); // This function can be reused for the middle borders
    }

    /**
     * Allows the user to edit the function values in the truth table.
     * 
     * @param tableValues   represents an array containing values of the truth
     *                      table.
     * @param functionValue represents an array containing function values.
     * @param variableNames represents an array of variable names.
     * @param numOfRows     represents number of rows in the truth table.
     * @param degreeValue   represents number of variables.
     */
    public static void allowUserEdits(int[][] tableValues, int[] functionValue, char[] variableNames, int numOfRows,
            int degreeValue) {
        boolean userWantsToEdit = true;
        boolean validUserResponse = false;

        while (userWantsToEdit) {
            if (!validUserResponse) {
                displayTruthTable(tableValues, functionValue, variableNames, numOfRows, degreeValue);
            }

            System.out.print("Do you want to edit the function values? Enter (yes/no) or (y/n): ");
            String userInput = scanner.next().trim().toLowerCase();

            if ("yes".equals(userInput) || "y".equals(userInput)) {
                validUserResponse = false; // Reset for the next iteration
                int rowNum = -1;
                while (rowNum == -1) {
                    System.out.print("Enter the row number you want to change: ");
                    try {
                        rowNum = scanner.nextInt();
                        if (rowNum < 1 || rowNum > numOfRows) {
                            System.out.println("Invalid row number. Please enter a row number between 1 and "
                                    + numOfRows + ".");
                            rowNum = -1; // Reset to indicate invalid input
                        } else {
                            // Edit the function value: if 0 then 1, if 1 then 0
                            functionValue[rowNum - 1] = functionValue[rowNum - 1] == 0 ? 1 : 0;
                        }
                    } catch (InputMismatchException inputMismatchExc) {
                        System.out
                                .println("Invalid input. Please enter integer to represent a row number between 1 and "
                                        + numOfRows + ".");
                        scanner.next(); // Read the user input until space is found
                    }
                }
            } else if ("no".equals(userInput) || "n".equals(userInput)) {
                userWantsToEdit = false;
                validUserResponse = true; // The last response was valid
            } else {
                System.out.println("Invalid input. Please enter (yes/no) or (y/n).");
                validUserResponse = true; // Restricting the table to be displayed only if the input is valid.
            }
        }

        if (validUserResponse) {
            // After all edits or a valid refusal to edit, display final truth table.
            displayTruthTable(tableValues, functionValue, variableNames, numOfRows, degreeValue);
        }
    }

    /**
     * Computes the Sum of Products (SOP) expansion for the truth table based on the
     * values in the trth table.
     * Each product term represents a combination of variables that results in the
     * function value being 1 (true).
     * Values in the product term have a prinputMismatchExc symbol('), if their
     * value in truth
     * table is 0.
     * 
     * @param tableValues   represents two dinputMismatchExcnsion array array
     *                      containing values
     *                      filled in the truth table.
     * @param functionValue represents one dinputMismatchExcnsion array containing
     *                      funtion
     *                      values.
     * @param numOfRows     represents number of rows of the truth table.
     * @param degreeValue   representsthe nuumber of variables.
     * @return sop representing sop expansion computed from the truth table.
     * 
     */

    public static String computeTruthTableSOP(int[][] tableValues, int[] functionValue, int numOfRows,
            int degreeValue) {
        System.out.print("Sum of products (SOP) expansion: ");
        String sop = "";
        for (int i = 0; i < numOfRows; i++) {
            if (functionValue[i] == 1) {
                String ANDTerm = "";
                for (int j = 0; j < degreeValue; j++) {
                    if (tableValues[i][j] == 1) {
                        ANDTerm += (char) ('A' + j); // Append the variable if the value is 0
                    } else {
                        ANDTerm += (char) ('A' + j) + "'"; // Append the complement of the variable if the value is
                                                           // 1
                    }
                }

                if (!sop.isEmpty()) {
                    sop += " + ";
                }
                sop += "(" + ANDTerm + ")";
            }
        }
        System.out.println(sop.isEmpty() ? "No SOP expression (all function values are 0)." : sop);
        return sop;
    }

    /**
     *
     * Computes the Product of Sums (POS) expansion for the truth table
     * based on the values in the truth table.
     * The following method computes for Product Of Sums.
     * Each ORTerm represents a combination of variables that results in the
     * function value being 0 (false).
     * Values in the sum term have a prinputMismatchExc symbol('), if their value in
     * truth table
     * is 1.
     * 
     * @param tableValues   represents 2D array ccontaining values of the truth
     *                      table.
     * @param functionValue represents 1D array containing values of the function.
     * @param numOfRows     represents number of rows of the truth table.
     * @param degreeValue   represents number of variables.
     * @return pos expansion computed form the truth table.
     */
    public static String computeTruthTablePOS(int[][] tableValues, int[] functionValue, int numOfRows,
            int degreeValue) {
        System.out.print("Product Of Sums (POS) expansion: ");
        String pos = "";
        for (int i = 0; i < numOfRows; i++) {
            if (functionValue[i] == 0) { // where the functionValue is False(0).
                String ORTerm = "";
                for (int j = 0; j < degreeValue; j++) {
                    if (tableValues[i][j] == 0) {
                        ORTerm += (char) ('A' + j) + "+"; // Append the variable if the value is 0
                    } else {
                        ORTerm += (char) ('A' + j) + "'" + "+"; // Append the complement of the variable if the value
                                                                // is 1
                    }
                }

                // removing the additional "+" operator
                if (!ORTerm.isEmpty()) {
                    ORTerm = ORTerm.substring(0, ORTerm.length() - 1);
                }

                // Adding * operator between brackets
                if (!pos.isEmpty()) {
                    pos += " * "; // Add the OR operator between terms
                }
                pos += "(" + ORTerm + ")";
            }
        }

        /**
         * Displaying the output status.
         */
        return pos.isEmpty() ? "No POS expression (all function values are 1)." : pos;
    }
}



























