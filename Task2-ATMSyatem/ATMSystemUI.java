import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main Class
public class ATMSystemUI extends JFrame {

    // ===== ATM CLASS =====
    class ATM {
        private double balance;
        private final int PIN = 1234;
        private int[] transactions = new int[20];
        private int index = 0;

        public ATM(double initialBalance) {
            balance = initialBalance;
        }

        public boolean validatePIN(int inputPin) {
            return inputPin == PIN;
        }

        public String checkBalance() {
            return "Available Balance: Rs." + balance;
        }

        public String deposit(double amount) {
            if (amount <= 0)
                return "Invalid amount!";
            if (amount > 50000)
                return "Deposit limit exceeded!";

            balance += amount;
            if (index < transactions.length)
                transactions[index++] = (int) amount;

            return "Rs." + amount + " deposited successfully.";
        }

        public String withdraw(double amount) {
            if (amount <= 0)
                return "Invalid amount!";
            if (amount % 100 != 0)
                return "Must be multiple of 100!";
            if (amount > 20000)
                return "Limit is Rs.20,000!";
            if (amount > balance)
                return "Insufficient balance!";

            balance -= amount;
            if (index < transactions.length)
                transactions[index++] = -(int) amount;

            return "Rs." + amount + " withdrawn successfully.";
        }

        public String showTransactions() {
            if (index == 0)
                return "No transactions yet.";

            StringBuilder sb = new StringBuilder("---- Transaction History ----\n");
            for (int i = 0; i < index; i++) {
                if (transactions[i] > 0)
                    sb.append("Deposited: Rs.").append(transactions[i]).append("\n");
                else
                    sb.append("Withdrawn: Rs.").append(-transactions[i]).append("\n");
            }
            return sb.toString();
        }
    }

    // ===== OBJECT =====
    ATM user = new ATM(10000);
    JTextArea display;

    // ===== CONSTRUCTOR =====
    public ATMSystemUI() {
        setTitle("ATM Machine");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== PIN AUTHENTICATION =====
        if (!authenticate()) {
            JOptionPane.showMessageDialog(this, "Card Blocked!");
            System.exit(0);
        }

        // ===== LEFT PANEL =====
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(20, 30, 60));

        JButton balBtn = createButton("Balance");
        JButton depBtn = createButton("Deposit");
        JButton witBtn = createButton("Withdraw");
        JButton miniBtn = createButton("Mini Statement");
        JButton exitBtn = createButton("Exit");

        panel.add(balBtn);
        panel.add(depBtn);
        panel.add(witBtn);
        panel.add(miniBtn);
        panel.add(exitBtn);

        // ===== DISPLAY AREA =====
        display = new JTextArea();
        display.setFont(new Font("Arial", Font.PLAIN, 14));
        display.setEditable(false);

        add(panel, BorderLayout.WEST);
        add(new JScrollPane(display), BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====
        balBtn.addActionListener(e -> display.setText(user.checkBalance()));

        depBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter deposit amount:");
            try {
                double amt = Double.parseDouble(input);
                display.setText(user.deposit(amt));
            } catch (Exception ex) {
                display.setText("Invalid input!");
            }
        });

        witBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter withdrawal amount:");
            try {
                double amt = Double.parseDouble(input);

                int confirm = JOptionPane.showConfirmDialog(
                        this, "Confirm withdrawal?", "Confirm",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION)
                    display.setText(user.withdraw(amt));
                else
                    display.setText("Transaction Cancelled.");

            } catch (Exception ex) {
                display.setText("Invalid input!");
            }
        });

        miniBtn.addActionListener(e -> display.setText(user.showTransactions()));

        exitBtn.addActionListener(e -> System.exit(0));
    }

    // ===== BUTTON STYLE =====
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    // ===== PIN AUTH METHOD =====
    private boolean authenticate() {
        int attempts = 0;

        while (attempts < 3) {
            String input = JOptionPane.showInputDialog("Enter 4-digit PIN:");

            if (input == null) return false;

            try {
                int pin = Integer.parseInt(input);
                if (user.validatePIN(pin)) {
                    return true;
                } else {
                    attempts++;
                    JOptionPane.showMessageDialog(this,
                            "Wrong PIN! Attempts left: " + (3 - attempts));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid PIN!");
            }
        }
        return false;
    }

    // ===== MAIN METHOD =====
    public static void main(String[] args) {
        new ATMSystemUI().setVisible(true);
    }
}