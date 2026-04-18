import javax.swing.*;
import java.awt.*;
import java.util.*;

// Main Class
public class LibrarySystemUI extends JFrame {

    // ===== Book Class =====
    class Book {
        int id;
        String title, author;
        boolean isIssued;

        Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.isIssued = false;
        }
    }

    // ===== Library Class =====
    class Library {
        ArrayList<Book> books = new ArrayList<>();

        void addBook(Book b) {
            books.add(b);
        }

        Book searchBook(int id) {
            for (Book b : books) {
                if (b.id == id)
                    return b;
            }
            return null;
        }

        boolean issueBook(int id) {
            Book b = searchBook(id);
            if (b != null && !b.isIssued) {
                b.isIssued = true;
                return true;
            }
            return false;
        }

        boolean returnBook(int id) {
            Book b = searchBook(id);
            if (b != null && b.isIssued) {
                b.isIssued = false;
                return true;
            }
            return false;
        }
    }

    // ===== Object =====
    Library library = new Library();
    JTextArea displayArea;

    // ===== Constructor (UI) =====
    public LibrarySystemUI() {
        setTitle("Library Management System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Side Panel
        JPanel sidePanel = new JPanel(new GridLayout(5, 1, 10, 10));
        sidePanel.setBackground(new Color(30, 30, 60));

        JButton addBtn = createButton("Add Book");
        JButton searchBtn = createButton("Search Book");
        JButton issueBtn = createButton("Issue Book");
        JButton returnBtn = createButton("Return Book");
        JButton viewBtn = createButton("View Books");

        sidePanel.add(addBtn);
        sidePanel.add(searchBtn);
        sidePanel.add(issueBtn);
        sidePanel.add(returnBtn);
        sidePanel.add(viewBtn);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        displayArea.setEditable(false);

        add(sidePanel, BorderLayout.WEST);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Button Actions
        addBtn.addActionListener(e -> addBook());
        searchBtn.addActionListener(e -> searchBook());
        issueBtn.addActionListener(e -> issueBook());
        returnBtn.addActionListener(e -> returnBook());
        viewBtn.addActionListener(e -> viewBooks());
    }

    // ===== UI Helper =====
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    // ===== Functionalities =====
    private void addBook() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID:"));
            String title = JOptionPane.showInputDialog("Enter Title:");
            String author = JOptionPane.showInputDialog("Enter Author:");

            library.addBook(new Book(id, title, author));
            displayArea.setText("✅ Book Added Successfully!");
        } catch (Exception e) {
            displayArea.setText("❌ Invalid Input!");
        }
    }

    private void searchBook() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID:"));
            Book b = library.searchBook(id);

            if (b != null) {
                displayArea.setText(
                        "📖 Book Found\n\nID: " + b.id +
                        "\nTitle: " + b.title +
                        "\nAuthor: " + b.author +
                        "\nIssued: " + b.isIssued
                );
            } else {
                displayArea.setText("❌ Book Not Found!");
            }
        } catch (Exception e) {
            displayArea.setText("❌ Invalid Input!");
        }
    }

    private void issueBook() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID to Issue:"));

            if (library.issueBook(id))
                displayArea.setText("✅ Book Issued Successfully!");
            else
                displayArea.setText("❌ Book not available!");
        } catch (Exception e) {
            displayArea.setText("❌ Invalid Input!");
        }
    }

    private void returnBook() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID to Return:"));

            if (library.returnBook(id))
                displayArea.setText("✅ Book Returned Successfully!");
            else
                displayArea.setText("❌ Invalid Return!");
        } catch (Exception e) {
            displayArea.setText("❌ Invalid Input!");
        }
    }

    private void viewBooks() {
        StringBuilder sb = new StringBuilder();

        if (library.books.isEmpty()) {
            displayArea.setText("📭 No Books Available!");
            return;
        }

        for (Book b : library.books) {
            sb.append("ID: ").append(b.id)
              .append(" | ").append(b.title)
              .append(" | ").append(b.author)
              .append(" | Issued: ").append(b.isIssued)
              .append("\n");
        }

        displayArea.setText(sb.toString());
    }

    // ===== Main Method =====
    public static void main(String[] args) {
        new LibrarySystemUI().setVisible(true);
    }
}