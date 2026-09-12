package library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Book class
class Book {

    String id;
    String title;
    String author;
    boolean available;

    Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }
}

// Student class
class Student {

    int id;
    String name;
    String phone;

    Student(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}

// Issue class
class Issue {

    Book book;
    Student student;
    LocalDate issueDate;
    LocalDate dueDate;

    Issue(Book book, Student student) {

        this.book = book;
        this.student = student;

        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(7);
    }
}

// Main class
public class Main {

    static Scanner sc = new Scanner(System.in);

    // Store all books
    static ArrayList<Book> books = new ArrayList<>();

    // Store issued books
    static Map<String, Book> issuedBooks = new HashMap<>();

    // Store students
    static Map<Integer, Student> students = new HashMap<>();

    // Store issue details
    static Map<String, Issue> issueDetails = new HashMap<>();

    // Librarian
    static String librarian = "Muni";

    // Default books
    static void addDefaultBooks() {

        books.add(new Book("01", "Ramayanam", "Valmiki"));
        books.add(new Book("02", "MahaBharatham", "Veda Vyasa"));
        books.add(new Book("03", "Java Programming", "Muni"));
        books.add(new Book("04", "Python Programming", "Ram"));
    }

    // 1. Add Book
    static void addBook() {

        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        Book book = new Book(id, title, author);

        books.add(book);

        System.out.println("Book added successfully.");
    }

    // 2. Issue Book
    static void issueBook() {

        try {

            System.out.print("Enter Book ID: ");
            String bookId = sc.nextLine();

            Book selectedBook = null;

            // Find book
            for (Book book : books) {

                if (book.id.equals(bookId)) {

                    selectedBook = book;
                    break;
                }
            }

            if (selectedBook == null) {

                throw new Exception("Book not found.");
            }

            if (!selectedBook.available) {

                throw new Exception("Book is already issued.");
            }

            // Student details
            System.out.print("Enter Student ID: ");
            int studentId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Student Name: ");
            String studentName = sc.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine();

            Student student =
                    new Student(studentId, studentName, phone);

            // Store student
            students.put(studentId, student);

            // Librarian approval
            System.out.println(
                    "Librarian " + librarian +
                    " approved the book issue."
            );

            // Create issue details
            Issue issue =
                    new Issue(selectedBook, student);

            // Make book unavailable
            selectedBook.available = false;

            // Store issued book
            issuedBooks.put(bookId, selectedBook);

            // Store issue details
            issueDetails.put(bookId, issue);

            System.out.println("\n===== BOOK ISSUE DETAILS =====");

            System.out.println("Student ID: " + student.id);
            System.out.println("Student Name: " + student.name);
            System.out.println("Phone: " + student.phone);

            System.out.println("Book ID: " + selectedBook.id);
            System.out.println("Book Title: " + selectedBook.title);
            System.out.println("Author: " + selectedBook.author);

            System.out.println("Issue Date: " + issue.issueDate);
            System.out.println("Due Date: " + issue.dueDate);

            System.out.println("Book issued successfully.");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 3. Return Book
    static void returnBook() {

        try {

            System.out.print("Enter Book ID: ");
            String bookId = sc.nextLine();

            Book book = issuedBooks.get(bookId);

            if (book == null) {

                throw new Exception("This book is not issued.");
            }

            Issue issue = issueDetails.get(bookId);

            System.out.println(
                    "Student Name: " + issue.student.name
            );

            System.out.println(
                    "Phone: " + issue.student.phone
            );

            // Fine
            System.out.print("Enter number of late days: ");

            int lateDays =
                    Integer.parseInt(sc.nextLine());

            int fine = calculateFine(lateDays);

            System.out.println("Fine Amount: Rs." + fine);

            // Collect fine
            if (fine > 0) {

                System.out.println(
                        "Fine collected successfully."
                );

            } else {

                System.out.println("No fine.");
            }

            // Make book available
            book.available = true;

            // Remove issued details
            issuedBooks.remove(bookId);
            issueDetails.remove(bookId);

            System.out.println(
                    "Book returned successfully."
            );

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    // 4. Search Book
    static void searchBook() {

        System.out.print(
                "Enter Book ID or Book Title: "
        );

        String search = sc.nextLine();

        for (Book book : books) {

            if (book.id.equals(search)
                    || book.title.equalsIgnoreCase(search)) {

                System.out.println(
                        "\nBook ID: " + book.id
                );

                System.out.println(
                        "Title: " + book.title
                );

                System.out.println(
                        "Author: " + book.author
                );

                if (book.available) {

                    System.out.println(
                            "Availability: Available"
                    );

                } else {

                    System.out.println(
                            "Availability: Issued"
                    );
                }

                return;
            }
        }

        System.out.println("Book not found.");
    }

    // 5. View Available Books
    static void availableBooks() {

        System.out.println(
                "\n===== AVAILABLE BOOKS ====="
        );

        boolean found = false;

        for (Book book : books) {

            if (book.available) {

                System.out.println(
                        book.id + " - "
                        + book.title + " - "
                        + book.author
                );

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No books are available."
            );
        }
    }

    // 6. Generate Report
    static void generateReport() {

        System.out.println(
                "\n===== LIBRARY REPORT ====="
        );

        System.out.println(
                "Librarian: " + librarian
        );

        System.out.println(
                "Total Books: " + books.size()
        );

        System.out.println(
                "Available Books: "
                + (books.size() - issuedBooks.size())
        );

        System.out.println(
                "Issued Books: " + issuedBooks.size()
        );

        System.out.println(
                "Registered Students: "
                + students.size()
        );

        System.out.println(
                "\n===== ISSUED BOOK DETAILS ====="
        );

        if (issueDetails.size() == 0) {

            System.out.println(
                    "No books are currently issued."
            );

            return;
        }

        for (Issue issue : issueDetails.values()) {

            System.out.println(
                    "Book: " + issue.book.title
            );

            System.out.println(
                    "Student: " + issue.student.name
            );

            System.out.println(
                    "Phone: " + issue.student.phone
            );

            System.out.println(
                    "Issue Date: " + issue.issueDate
            );

            System.out.println(
                    "Due Date: " + issue.dueDate
            );

            System.out.println("-------------------------");
        }
    }

    // 7. Due Date Reminder
    static void dueDateReminder() {

        System.out.println(
                "\n===== DUE DATE REMINDER ====="
        );

        if (issueDetails.size() == 0) {

            System.out.println(
                    "No books are currently issued."
            );

            return;
        }

        for (Issue issue : issueDetails.values()) {

            System.out.println(
                    "Student: " + issue.student.name
            );

            System.out.println(
                    "Book: " + issue.book.title
            );

            System.out.println(
                    "Due Date: " + issue.dueDate
            );

            System.out.println(
                    "Please return the book before "
                    + issue.dueDate
            );

            System.out.println("-------------------------");
        }
    }

    // 8. Student Details
    static void studentDetails() {

        System.out.print(
                "Enter Student ID: "
        );

        int studentId =
                Integer.parseInt(sc.nextLine());

        Student student =
                students.get(studentId);

        if (student == null) {

            System.out.println(
                    "Student not found."
            );

            return;
        }

        System.out.println(
                "\n===== STUDENT DETAILS ====="
        );

        System.out.println(
                "Student ID: " + student.id
        );

        System.out.println(
                "Student Name: " + student.name
        );

        System.out.println(
                "Phone: " + student.phone
        );

        // Check whether student has a book
        boolean found = false;

        for (Issue issue : issueDetails.values()) {

            if (issue.student.id == studentId) {

                System.out.println(
                        "Book Taken: " + issue.book.title
                );

                System.out.println(
                        "Issue Date: " + issue.issueDate
                );

                System.out.println(
                        "Due Date: " + issue.dueDate
                );

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No book is currently taken by this student."
            );
        }
    }

    // Fine Calculator
    static int calculateFine(int lateDays) {

        int fine = lateDays * 10;

        return fine;
    }

    // Main method
    public static void main(String[] args) {

        // Add default books
        addDefaultBooks();

        while (true) {

            System.out.println(
                    "\n===== LIBRARY MANAGEMENT SYSTEM ====="
            );

            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Search Book");
            System.out.println("5. View Available Books");
            System.out.println("6. Generate Report");
            System.out.println("7. Due Date Reminder");
            System.out.println("8. View Student Details");
            System.out.println("9. Exit");

            System.out.print(
                    "Enter your choice: "
            );

            try {

                int choice =
                        Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        addBook();
                        break;

                    case 2:
                        issueBook();
                        break;

                    case 3:
                        returnBook();
                        break;

                    case 4:
                        searchBook();
                        break;

                    case 5:
                        availableBooks();
                        break;

                    case 6:
                        generateReport();
                        break;

                    case 7:
                        dueDateReminder();
                        break;

                    case 8:
                        studentDetails();
                        break;

                    case 9:
                        System.out.println(
                                "Thank you!"
                        );
                        return;

                    default:
                        System.out.println(
                                "Invalid choice."
                        );
                }

            } catch (Exception e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}