import java.util.Scanner;

// Calculator class (Encapsulation)
class Calculate {

    // Methods for operations (Abstraction)
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            System.out.println("Error: Division by zero not allowed.");
            return 0;
        }
        return a / b;
    }
}

// Main class
public class Calculator{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Calculate calc = new Calculate(); // Object creation

        int choice;
        double num1, num2, result;

        do {
            // Menu
            System.out.println("\n===== CALCULATOR MENU =====");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            // Exit condition
            if (choice == 5) {
                System.out.println("Exiting calculator. Thank you!");
                break;
            }

            // Input numbers
            System.out.print("Enter first number: ");
            num1 = sc.nextDouble();

            System.out.print("Enter second number: ");
            num2 = sc.nextDouble();

            // Perform operation
            switch (choice) {
                case 1:
                    result = calc.add(num1, num2);
                    System.out.println("Result = " + result);
                    break;

                case 2:
                    result = calc.subtract(num1, num2);
                    System.out.println("Result = " + result);
                    break;

                case 3:
                    result = calc.multiply(num1, num2);
                    System.out.println("Result = " + result);
                    break;

                case 4:
                    result = calc.divide(num1, num2);
                    System.out.println("Result = " + result);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5); // do-while loop

        sc.close();
    }
}