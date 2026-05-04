package com.csc325.librarymanagementsystem.data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.model.Loan;
import com.csc325.librarymanagementsystem.model.User;

/**
 * Sample books, users, and loans for testing without Firebase.
 *-
 * These methods create fresh sample objects each time they are called, but
 * the returned lists are read-only. If a test needs to add, remove, or update
 * data, first copy the list into an ArrayList and change the copy.
 *-
 * Example:
 *     List<Book> books = new ArrayList<>(FakeData.getBooks());
 *     List<Loan> loans = new ArrayList<>(FakeData.getLoans());
 *     // Change books and loans here for the specific test case.
 */
public final class FakeData {

    private FakeData() {
    }

    public static List<Book> getBooks() {
        return List.of(
                new Book("book-001", "9780141439518", "Pride and Prejudice",
                        List.of("Jane Austen"), List.of("Classic", "Romance"), "https://fakeurl.com/image.jpg", 4),
                new Book("book-002", "9780061120084", "To Kill a Mockingbird",
                        List.of("Harper Lee"), List.of("Classic", "Fiction"),"https://fakeurl.com/image.jpg", 3),
                new Book("book-003", "9780451524935", "1984",
                        List.of("George Orwell"), List.of("Dystopian", "Science Fiction"), "https://fakeurl.com/image.jpg", 0),
                new Book("book-004", "9780743273565", "The Great Gatsby",
                        List.of("F. Scott Fitzgerald"), List.of("Classic", "Fiction"), "https://fakeurl.com/image.jpg", 5),
                new Book("book-005", "9781503280786", "Moby Dick",
                        List.of("Herman Melville"), List.of("Classic", "Adventure"), "https://fakeurl.com/image.jpg", 1),
                new Book("book-006", "9781594631931", "The Kite Runner",
                        List.of("Khaled Hosseini"), List.of("Fiction", "Drama"), "https://fakeurl.com/image.jpg", 3),
                new Book("book-007", "9780375842207", "The Book Thief",
                        List.of("Markus Zusak"), List.of("Historical Fiction", "Drama"), "https://fakeurl.com/image.jpg", 2),
                new Book("book-008", "9781400033416", "Beloved",
                        List.of("Toni Morrison"), List.of("Historical Fiction", "Drama"), "https://fakeurl.com/image.jpg",  2),
                new Book("book-009", "9780156027328", "Life of Pi",
                        List.of("Yann Martel"), List.of("Adventure", "Fiction"), "https://fakeurl.com/image.jpg",  4),
                new Book("book-010", "9780307387899", "The Road",
                        List.of("Cormac McCarthy"), List.of("Dystopian", "Fiction"), "https://fakeurl.com/image.jpg", 2),
                new Book("book-011", "9780547928227", "The Hobbit",
                        List.of("J.R.R. Tolkien"), List.of("Fantasy", "Adventure"), "https://fakeurl.com/image.jpg",  1),
                new Book("book-012", "9780590353427", "Harry Potter and the Sorcerer's Stone",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Young Adult"), "https://fakeurl.com/image.jpg",  8),
                new Book("book-013", "9780553593716", "A Game of Thrones",
                        List.of("George R.R. Martin"), List.of("Fantasy", "Epic"), "https://fakeurl.com/image.jpg",  3),
                new Book("book-014", "9780756404741", "The Name of the Wind",
                        List.of("Patrick Rothfuss"), List.of("Fantasy"), "https://fakeurl.com/image.jpg", 2),
                new Book("book-015", "9780441172719", "Dune",
                        List.of("Frank Herbert"), List.of("Science Fiction", "Epic"), "https://fakeurl.com/image.jpg", 0),
                new Book("book-016", "9780553293357", "Foundation",
                        List.of("Isaac Asimov"), List.of("Science Fiction"), "https://fakeurl.com/image.jpg",  4),
                new Book("book-017", "9780441569595", "Neuromancer",
                        List.of("William Gibson"), List.of("Science Fiction", "Cyberpunk"), "https://fakeurl.com/image.jpg",  2),
                new Book("book-018", "9780553418026", "The Martian",
                        List.of("Andy Weir"), List.of("Science Fiction"), "https://fakeurl.com/image.jpg", 1),
                new Book("book-019", "9780765342294", "Ender's Game",
                        List.of("Orson Scott Card"), List.of("Science Fiction", "Young Adult"), "https://fakeurl.com/image.jpg",  5),
                new Book("book-020", "9780307474278", "The Da Vinci Code",
                        List.of("Dan Brown"), List.of("Mystery", "Thriller"), "https://fakeurl.com/image.jpg",  0),
                new Book("book-021", "9780307588371", "Gone Girl",
                        List.of("Gillian Flynn"), List.of("Mystery", "Thriller"), "https://fakeurl.com/image.jpg",  3),
                new Book("book-022", "9780307454546", "The Girl with the Dragon Tattoo",
                        List.of("Stieg Larsson"), List.of("Mystery", "Thriller"), "https://fakeurl.com/image.jpg",  2),
                new Book("book-023", "9780062073488", "And Then There Were None",
                        List.of("Agatha Christie"), List.of("Mystery", "Classic"), "https://fakeurl.com/image.jpg", 1),
                new Book("book-024", "9780440423218", "Outlander",
                        List.of("Diana Gabaldon"), List.of("Romance", "Historical Fiction"), "https://fakeurl.com/image.jpg",  4),
                new Book("book-025", "9780143124542", "Me Before You",
                        List.of("Jojo Moyes"), List.of("Romance", "Drama"), "https://fakeurl.com/image.jpg", 3),
                new Book("book-026", "9780062316097", "Sapiens: A Brief History of Humankind",
                        List.of("Yuval Noah Harari"), List.of("Non-Fiction", "History"), "https://fakeurl.com/image.jpg",  6),
                new Book("book-027", "9780399590504", "Educated",
                        List.of("Tara Westover"), List.of("Non-Fiction", "Memoir"), "https://fakeurl.com/image.jpg", 3),
                new Book("book-028", "9780735211292", "Atomic Habits",
                        List.of("James Clear"), List.of("Non-Fiction", "Self-Help"), "https://fakeurl.com/image.jpg",  0),
                new Book("book-029", "9780374533557", "Thinking, Fast and Slow",
                        List.of("Daniel Kahneman"), List.of("Non-Fiction", "Psychology"), "https://fakeurl.com/image.jpg",  4),
                new Book("book-030", "9781400052189", "The Immortal Life of Henrietta Lacks",
                        List.of("Rebecca Skloot"), List.of("Non-Fiction", "Science"), "https://fakeurl.com/image.jpg",  1),
                new Book("book-031", "9781451648539", "Steve Jobs",
                        List.of("Walter Isaacson"), List.of("Biography", "Non-Fiction"), "https://fakeurl.com/image.jpg",  5),
                new Book("book-032", "9781524763138", "Becoming",
                        List.of("Michelle Obama"), List.of("Biography", "Memoir"), "https://fakeurl.com/image.jpg",  7),
                new Book("book-033", "9780132350884", "Clean Code",
                        List.of("Robert C. Martin"), List.of("Computer Science", "Programming"), "https://fakeurl.com/image.jpg", 0),
                new Book("book-034", "9780201616224", "The Pragmatic Programmer",
                        List.of("Andrew Hunt", "David Thomas"), List.of("Computer Science", "Programming"), "https://fakeurl.com/image.jpg", 3),
                new Book("book-035", "9780134685991", "Effective Java",
                        List.of("Joshua Bloch"), List.of("Computer Science", "Programming"), "https://fakeurl.com/image.jpg",  2),
                new Book("book-036", "9780201633610", "Design Patterns: Elements of Reusable Object-Oriented Software",
                        List.of("Erich Gamma", "Richard Helm", "Ralph Johnson", "John Vlissides"),
                        List.of("Computer Science", "Programming"), "https://fakeurl.com/image.jpg",  4),
                new Book("book-037", "9780262033848", "Introduction to Algorithms",
                        List.of("Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest", "Clifford Stein"),
                        List.of("Computer Science", "Programming"), "https://fakeurl.com/image.jpg", 1),
                new Book("book-038", "9780064400558", "Charlotte's Web",
                        List.of("E.B. White"), List.of("Children", "Classic"), "https://fakeurl.com/image.jpg",  6),
                new Book("book-039", "9780064431781", "Where the Wild Things Are",
                        List.of("Maurice Sendak"), List.of("Children", "Picture Book"), "https://fakeurl.com/image.jpg",  5),
                new Book("book-040", "9780517053614", "The Complete Works of William Shakespeare",
                        List.of("William Shakespeare"), List.of("Classic", "Drama", "Poetry"), "https://fakeurl.com/image.jpg",  0)
        );
    }

    /**
     * Each user covers a different login and checkout scenario:
     * a power user at the 3-book limit, a moderate user, and a light user.
     * {@code libraryId} and {@code libraryPin} are stored as {@code String}
     *
     * @return list of 3 fake users
     */
    public static List<User> getUsers() {
        return List.of(
                // Reason: alice has 3 active loans — exercises LIMIT_REACHED.
                new User("user-001", "00012345", "alice@example.com", "1234"),
                new User("user-002", "00067890", "bob@example.com",   "4321"),
                new User("user-003", "00045678", "carol@example.com", "9999"),
                new User("user-004", "00098765", "joe@example.com",   "1234") //user sharing pin with another, shouldnt be an issue since pin is linked to ID but for testing incase.
        );
    }

    /**
     *   Overdue (dueDate already passed, not returned)
     *   Due soon (dueDate within the next few days, not returned)
     *   Active normal (dueDate well in the future, not returned)
     *   Returned (already closed out)
     *
     * @return immutable list of 11 fake loans
     */
    public static List<Loan> getLoans() {
        return List.of(
                // alice (user-001): 3 active loans → at the limit
                // overdue → exercises NotificationService.sendOverdueAlerts.
                new Loan("loan-00001", "book-001", "user-001",
                        date(2026, 3, 15), date(2026, 3, 29), false),
                // due soon → exercises NotificationService.sendDueSoonAlerts.
                new Loan("loan-00002", "book-005", "user-001",
                        date(2026, 3, 15), date(2026, 3, 29), false),
                new Loan("loan-00003", "book-011", "user-001",
                        date(2026, 4, 20), date(2026, 5, 4), false),
                new Loan("loan-00004", "book-026", "user-001",
                        date(2026, 2, 10), date(2026, 2, 24), true),

                // bob (user-002): 2 active loans, 1 returned
                new Loan("loan-00005", "book-015", "user-002",
                        date(2026, 4, 28), date(2026, 5, 12), false),
                new Loan("loan-00006", "book-035", "user-002",
                        date(2026, 4, 28), date(2026, 5, 12), false),
                new Loan("loan-00007", "book-020", "user-002",
                        date(2026, 4, 28), date(2026, 5, 12), true),

                // carol (user-003): 1 active loan, 2 returned
                new Loan("loan-00008", "book-038", "user-003",
                        date(2026, 3, 1), date(2026, 3, 15), true),
                new Loan("loan-00009", "book-031", "user-003",
                        date(2026, 2, 15), date(2026, 3, 1), true), // 2026 is not a leap year
                new Loan("loan-00010", "book-024", "user-003",
                        date(2026, 4, 15), date(2026, 4, 29), false),

                // Joe (user-004): 1 active loan, same book as loan-00010
                new Loan("loan-00011", "book-024", "user-004",
                         date(2026, 4, 13), date(2026, 4, 27), false)
        );
    }

    /**
     * Turns a plain calendar date (year, month, day) into a {java.util.Date}.
     * We use midnight in the computer's local time zone so the sample data is easy to read,
     * but still matches what {Loan} and {CheckoutConfirmation} store.
     */
    private static Date date(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * Sample checkout confirmations tied to existing fake loans.
     *
     * @return immutable list of fake checkout confirmations
     */
    public static List<CheckoutConfirmation> getCheckoutConfirmations() {
        return List.of(
                // Confirmation for alice (user-001) checking out book-001 and book-005
                // Reason: matches the checkoutDate of loan-00001 and loan-00002 (2026-03-15).
                new CheckoutConfirmation(
                        "conf-00001",
                        "user-001",
                        List.of("book-001", "book-005"),
                        date(2026, 3, 15)),
                // bob (user-002): book-015, book-035, book-020 — matches loan-00005 through loan-00007 (2026-04-28).
                new CheckoutConfirmation(
                        "conf-00002",
                        "user-002",
                        List.of("book-015", "book-035", "book-020"),
                        date(2026, 4, 28)),

                // Remaining confirmations each match one existing fake loan by user, book, and checkout date.
                new CheckoutConfirmation(
                        "conf-00003",
                        "user-001",
                        List.of("book-011"),
                        date(2026, 4, 20)),
                new CheckoutConfirmation(
                        "conf-00004",
                        "user-001",
                        List.of("book-026"),
                        date(2026, 2, 10)),
                new CheckoutConfirmation(
                        "conf-00005",
                        "user-003",
                        List.of("book-038"),
                        date(2026, 3, 1)),
                new CheckoutConfirmation(
                        "conf-00006",
                        "user-003",
                        List.of("book-031"),
                        date(2026, 2, 15)),
                new CheckoutConfirmation(
                        "conf-00007",
                        "user-003",
                        List.of("book-024"),
                        date(2026, 4, 15)),
                new CheckoutConfirmation(
                        "conf-00008",
                        "user-004",
                        List.of("book-024"),
                        date(2026, 4, 13))
        );
    }
}
