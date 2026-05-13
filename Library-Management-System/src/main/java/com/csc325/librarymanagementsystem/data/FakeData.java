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
                        List.of("Jane Austen"), List.of("Classic", "Romance"), "https://covers.openlibrary.org/b/id/14348537-L.jpg?default=false", 4),
                new Book("book-002", "9780061120084", "To Kill a Mockingbird",
                        List.of("Harper Lee"), List.of("Classic", "Fiction"),"https://covers.openlibrary.org/b/id/14351077-L.jpg?default=false", 3),
                new Book("book-003", "9780451524935", "1984",
                        List.of("George Orwell"), List.of("Dystopian", "Science Fiction"), "https://covers.openlibrary.org/b/id/8745958-L.jpg?default=false", 0),
                new Book("book-004", "9780743273565", "The Great Gatsby",
                        List.of("F. Scott Fitzgerald"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/10590366-L.jpg?default=false", 5),
                new Book("book-005", "9781503280786", "Moby Dick",
                        List.of("Herman Melville"), List.of("Classic", "Adventure"), "https://covers.openlibrary.org/b/id/10544254-L.jpg?default=false", 1),
                new Book("book-006", "9781594631931", "The Kite Runner",
                        List.of("Khaled Hosseini"), List.of("Fiction", "Drama"), "https://covers.openlibrary.org/b/id/14846827-L.jpg?default=false", 3),
                new Book("book-007", "9780375842207", "The Book Thief",
                        List.of("Markus Zusak"), List.of("Historical Fiction", "Drama"), "https://covers.openlibrary.org/b/id/8153054-L.jpg?default=false", 2),
                new Book("book-008", "9781400033416", "Beloved",
                        List.of("Toni Morrison"), List.of("Historical Fiction", "Drama"), "https://covers.openlibrary.org/b/id/8261367-L.jpg?default=false",  2),
                new Book("book-009", "9780156027328", "Life of Pi",
                        List.of("Yann Martel"), List.of("Adventure", "Fiction"), "https://covers.openlibrary.org/b/id/12840573-L.jpg?default=false",  4),
                new Book("book-010", "9780307387899", "The Road",
                        List.of("Cormac McCarthy"), List.of("Dystopian", "Fiction"), "https://covers.openlibrary.org/b/id/198120-L.jpg?default=false", 2),
                new Book("book-011", "9780547928227", "The Hobbit",
                        List.of("J.R.R. Tolkien"), List.of("Fantasy", "Adventure"), "https://covers.openlibrary.org/b/id/14627509-L.jpg?default=false",  1),
                new Book("book-012", "9780590353427", "Harry Potter and the Sorcerer's Stone",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Young Adult"), "https://covers.openlibrary.org/b/id/6509920-L.jpg?default=false",  8),
                new Book("book-013", "9780553593716", "A Game of Thrones",
                        List.of("George R.R. Martin"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/9269962-L.jpg?default=false",  3),
                new Book("book-014", "9780756404741", "The Name of the Wind",
                        List.of("Patrick Rothfuss"), List.of("Fantasy"), "https://covers.openlibrary.org/b/id/11480483-L.jpg?default=false", 2),
                new Book("book-015", "9780441172719", "Dune",
                        List.of("Frank Herbert"), List.of("Science Fiction", "Epic"), "https://covers.openlibrary.org/b/id/11481354-L.jpg?default=false", 0),
                new Book("book-016", "9780553293357", "Foundation",
                        List.of("Isaac Asimov"), List.of("Science Fiction"), "https://covers.openlibrary.org/b/id/14612610-L.jpg?default=false",  4),
                new Book("book-017", "9780441569595", "Neuromancer",
                        List.of("William Gibson"), List.of("Science Fiction", "Cyberpunk"), "https://covers.openlibrary.org/b/id/283860-L.jpg?default=false",  2),
                new Book("book-018", "9780553418026", "The Martian",
                        List.of("Andy Weir"), List.of("Science Fiction"), "https://covers.openlibrary.org/b/id/11447888-L.jpg?default=false", 1),
                new Book("book-019", "9780765342294", "Ender's Game",
                        List.of("Orson Scott Card"), List.of("Science Fiction", "Young Adult"), "https://covers.openlibrary.org/b/id/12996033-L.jpg?default=false",  5),
                new Book("book-020", "9780307474278", "The Da Vinci Code",
                        List.of("Dan Brown"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/9255229-L.jpg?default=false",  0),
                new Book("book-021", "9780307588371", "Gone Girl",
                        List.of("Gillian Flynn"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/8368314-L.jpg?default=false",  3),
                new Book("book-022", "9780307454546", "The Girl with the Dragon Tattoo",
                        List.of("Stieg Larsson"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/10567897-L.jpg?default=false",  2),
                new Book("book-023", "9780062073488", "And Then There Were None",
                        List.of("Agatha Christie"), List.of("Mystery", "Classic"), "https://covers.openlibrary.org/b/id/11172296-L.jpg?default=false", 1),
                new Book("book-024", "9780440423218", "Outlander",
                        List.of("Diana Gabaldon"), List.of("Romance", "Historical Fiction"), "https://covers.openlibrary.org/b/id/14428230-L.jpg?default=false",  4),
                new Book("book-025", "9780143124542", "Me Before You",
                        List.of("Jojo Moyes"), List.of("Romance", "Drama"), "https://covers.openlibrary.org/b/id/14419339-L.jpg?default=false", 3),
                new Book("book-026", "9780062316097", "Sapiens: A Brief History of Humankind",
                        List.of("Yuval Noah Harari"), List.of("Non-Fiction", "History"), "https://covers.openlibrary.org/b/id/8519064-L.jpg?default=false",  6),
                new Book("book-027", "9780399590504", "Educated",
                        List.of("Tara Westover"), List.of("Non-Fiction", "Memoir"), "https://covers.openlibrary.org/b/id/8314077-L.jpg?default=false", 3),
                new Book("book-028", "9780735211292", "Atomic Habits",
                        List.of("James Clear"), List.of("Non-Fiction", "Self-Help"), "https://covers.openlibrary.org/b/id/12539702-L.jpg?default=false",  0),
                new Book("book-029", "9780374533557", "Thinking, Fast and Slow",
                        List.of("Daniel Kahneman"), List.of("Non-Fiction", "Psychology"), "https://covers.openlibrary.org/b/id/13290711-L.jpg?default=false",  4),
                new Book("book-030", "9781400052189", "The Immortal Life of Henrietta Lacks",
                        List.of("Rebecca Skloot"), List.of("Non-Fiction", "Science"), "https://covers.openlibrary.org/b/id/8364866-L.jpg?default=false",  1),
                new Book("book-031", "9781451648539", "Steve Jobs",
                        List.of("Walter Isaacson"), List.of("Biography", "Non-Fiction"), "https://covers.openlibrary.org/b/id/12374726-L.jpg?default=false",  5),
                new Book("book-032", "9781524763138", "Becoming",
                        List.of("Michelle Obama"), List.of("Biography", "Memoir"), "https://covers.openlibrary.org/b/id/11463139-L.jpg?default=false",  7),
                new Book("book-033", "9780132350884", "Clean Code",
                        List.of("Robert C. Martin"), List.of("Computer Science", "Programming"), "https://covers.openlibrary.org/b/id/8065615-L.jpg?default=false", 0),
                new Book("book-034", "9780201616224", "The Pragmatic Programmer",
                        List.of("Andrew Hunt", "David Thomas"), List.of("Computer Science", "Programming"), "https://covers.openlibrary.org/b/id/10143650-L.jpg?default=false", 3),
                new Book("book-035", "9780134685991", "Effective Java",
                        List.of("Joshua Bloch"), List.of("Computer Science", "Programming"), "https://covers.openlibrary.org/b/id/1176573-L.jpg?default=false",  2),
                new Book("book-036", "9780201633610", "Design Patterns: Elements of Reusable Object-Oriented Software",
                        List.of("Erich Gamma", "Richard Helm", "Ralph Johnson", "John Vlissides"),
                        List.of("Computer Science", "Programming"), "https://covers.openlibrary.org/b/id/13750918-L.jpg?default=false",  4),
                new Book("book-037", "9780262033848", "Introduction to Algorithms",
                        List.of("Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest", "Clifford Stein"),
                        List.of("Computer Science", "Programming"), "https://covers.openlibrary.org/b/id/2341462-L.jpg?default=false", 1),
                new Book("book-038", "9780064400558", "Charlotte's Web",
                        List.of("E.B. White"), List.of("Children", "Classic"), "https://covers.openlibrary.org/b/id/8461797-L.jpg?default=false",  6),
                new Book("book-039", "9780064431781", "Where the Wild Things Are",
                        List.of("Maurice Sendak"), List.of("Children", "Picture Book"), "https://covers.openlibrary.org/b/id/50842-L.jpg?default=false",  5),
                new Book("book-040", "9780517053614", "The Complete Works of William Shakespeare",
                        List.of("William Shakespeare"), List.of("Classic", "Drama", "Poetry"), "https://covers.openlibrary.org/b/id/12946560-L.jpg?default=false",  0),

                new Book(null, "9780141439662", "Sense and Sensibility",
                        List.of("Jane Austen"), List.of("Classic", "Romance"), "https://covers.openlibrary.org/b/id/9278292-L.jpg?default=false", 3),
                new Book(null, "9780141439587", "Emma",
                        List.of("Jane Austen"), List.of("Classic", "Romance"), "https://covers.openlibrary.org/b/id/9278312-L.jpg?default=false", 5),
                new Book(null, "9780141439686", "Persuasion",
                        List.of("Jane Austen"), List.of("Classic", "Romance"), "https://covers.openlibrary.org/b/id/12824691-L.jpg?default=false", 2),
                new Book(null, "9780451526342", "Animal Farm",
                        List.of("George Orwell"), List.of("Classic", "Dystopian"), "https://covers.openlibrary.org/b/id/11261770-L.jpg?default=false", 4),
                new Book(null, "9780439064873", "Harry Potter and the Chamber of Secrets",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Young Adult"), "https://covers.openlibrary.org/b/id/15158664-L.jpg?default=false", 6),
                new Book(null, "9780439136365", "Harry Potter and the Prisoner of Azkaban",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Young Adult"), "https://covers.openlibrary.org/b/id/10580435-L.jpg?default=false", 7),
                new Book(null, "9780439139601", "Harry Potter and the Goblet of Fire",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Young Adult"), "https://covers.openlibrary.org/b/id/12059372-L.jpg?default=false", 5),
                new Book(null, "9780439358071", "Harry Potter and the Order of the Phoenix",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Young Adult"), "https://covers.openlibrary.org/b/id/15158666-L.jpg?default=false", 4),
                new Book(null, "9780439784542", "Harry Potter and the Half-Blood Prince",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Young Adult"), "https://covers.openlibrary.org/b/id/10716273-L.jpg?default=false", 3),
                new Book(null, "9780545010221", "Harry Potter and the Deathly Hallows",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Young Adult"), "https://covers.openlibrary.org/b/id/15158660-L.jpg?default=false", 6),
                new Book(null, "9780547928210", "The Fellowship of the Ring",
                        List.of("J.R.R. Tolkien"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/14627060-L.jpg?default=false", 3),
                new Book(null, "9780547928203", "The Two Towers",
                        List.of("J.R.R. Tolkien"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/14627564-L.jpg?default=false", 2),
                new Book(null, "9780547928197", "The Return of the King",
                        List.of("J.R.R. Tolkien"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/14627062-L.jpg?default=false", 4),
                new Book(null, "9780544338012", "The Silmarillion",
                        List.of("J.R.R. Tolkien"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/14627042-L.jpg?default=false", 1),
                new Book(null, "9780062693662", "Murder on the Orient Express",
                        List.of("Agatha Christie"), List.of("Mystery", "Classic"), "https://covers.openlibrary.org/b/id/11100465-L.jpg?default=false", 3),
                new Book(null, "9780062073563", "The Murder of Roger Ackroyd",
                        List.of("Agatha Christie"), List.of("Mystery", "Classic"), "https://covers.openlibrary.org/b/id/13151356-L.jpg?default=false", 2),
                new Book(null, "9780062073501", "Death on the Nile",
                        List.of("Agatha Christie"), List.of("Mystery", "Classic"), "https://covers.openlibrary.org/b/id/14066646-L.jpg?default=false", 4),
                new Book(null, "9780553382563", "I, Robot",
                        List.of("Isaac Asimov"), List.of("Science Fiction"), "https://covers.openlibrary.org/b/id/6517784-L.jpg?default=false", 3),
                new Book(null, "9780553293371", "Foundation and Empire",
                        List.of("Isaac Asimov"), List.of("Science Fiction", "Epic"), "https://covers.openlibrary.org/b/id/9300695-L.jpg?default=false", 2),
                new Book(null, "9780553293364", "Second Foundation",
                        List.of("Isaac Asimov"), List.of("Science Fiction", "Epic"), "https://covers.openlibrary.org/b/id/9261324-L.jpg?default=false", 1),
                new Book(null, "9780593098233", "Dune Messiah",
                        List.of("Frank Herbert"), List.of("Science Fiction", "Epic"), "https://covers.openlibrary.org/b/id/2421405-L.jpg?default=false", 2),
                new Book(null, "9780593098240", "Children of Dune",
                        List.of("Frank Herbert"), List.of("Science Fiction", "Epic"), "https://covers.openlibrary.org/b/id/6976407-L.jpg?default=false", 1),
                new Book(null, "9781400033423", "Song of Solomon",
                        List.of("Toni Morrison"), List.of("Fiction", "Drama"), "https://covers.openlibrary.org/b/id/9317262-L.jpg?default=false", 3),
                new Book(null, "9780307278463", "The Bluest Eye",
                        List.of("Toni Morrison"), List.of("Fiction", "Drama"), "https://covers.openlibrary.org/b/id/15152696-L.jpg?default=false", 2),
                new Book(null, "9780679728757", "Blood Meridian",
                        List.of("Cormac McCarthy"), List.of("Fiction", "Western"), "https://covers.openlibrary.org/b/id/419991-L.jpg?default=false", 1),
                new Book(null, "9780375706677", "No Country for Old Men",
                        List.of("Cormac McCarthy"), List.of("Fiction", "Thriller"), "https://covers.openlibrary.org/b/id/9296899-L.jpg?default=false", 3),
                new Book(null, "9780553579901", "A Clash of Kings",
                        List.of("George R.R. Martin"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/8231751-L.jpg?default=false", 2),
                new Book(null, "9780553573428", "A Storm of Swords",
                        List.of("George R.R. Martin"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/15124196-L.jpg?default=false", 4),
                new Book(null, "9780743493468", "Angels & Demons",
                        List.of("Dan Brown"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/11408459-L.jpg?default=false", 2),
                new Book(null, "9781400079155", "Inferno",
                        List.of("Dan Brown"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/6387077-L.jpg?default=false", 1),
                new Book(null, "9780684801544", "Tender Is the Night",
                        List.of("F. Scott Fitzgerald"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/6984433-L.jpg?default=false", 2),
                new Book(null, "9781594489501", "A Thousand Splendid Suns",
                        List.of("Khaled Hosseini"), List.of("Fiction", "Drama"), "https://covers.openlibrary.org/b/id/8579790-L.jpg?default=false", 4),
                new Book(null, "9781594631764", "And the Mountains Echoed",
                        List.of("Khaled Hosseini"), List.of("Fiction", "Drama"), "https://covers.openlibrary.org/b/id/7258558-L.jpg?default=false", 2),
                new Book(null, "9780593135204", "Project Hail Mary",
                        List.of("Andy Weir"), List.of("Science Fiction"), "https://covers.openlibrary.org/b/id/11200092-L.jpg?default=false", 5),
                new Book(null, "9780812550757", "Speaker for the Dead",
                        List.of("Orson Scott Card"), List.of("Science Fiction"), "https://covers.openlibrary.org/b/id/9315123-L.jpg?default=false", 3),
                new Book(null, "9780307341556", "Sharp Objects",
                        List.of("Gillian Flynn"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/167973-L.jpg?default=false", 2),
                new Book(null, "9780307341570", "Dark Places",
                        List.of("Gillian Flynn"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/5728115-L.jpg?default=false", 1),
                new Book(null, "9780743264747", "Einstein: His Life and Universe",
                        List.of("Walter Isaacson"), List.of("Biography", "Non-Fiction"), "https://fakeurl.com/image.jpg", 3),
                new Book(null, "9780143105831", "Billy Budd, Sailor",
                        List.of("Herman Melville"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/9220063-L.jpg?default=false", 1),
                new Book(null, "9780307743657", "The Shining",
                        List.of("Stephen King"), List.of("Horror", "Thriller"), "https://covers.openlibrary.org/b/id/12376585-L.jpg?default=false", 4),
                new Book(null, "9781501142970", "It",
                        List.of("Stephen King"), List.of("Horror", "Fiction"), "https://covers.openlibrary.org/b/id/8569284-L.jpg?default=false", 3),
                new Book(null, "9780307743688", "The Stand",
                        List.of("Stephen King"), List.of("Horror", "Dystopian"), "https://covers.openlibrary.org/b/id/9255992-L.jpg?default=false", 2),
                new Book(null, "9780385490818", "The Handmaid's Tale",
                        List.of("Margaret Atwood"), List.of("Dystopian", "Fiction"), "https://covers.openlibrary.org/b/id/8231851-L.jpg?default=false", 5),
                new Book(null, "9780385721677", "Oryx and Crake",
                        List.of("Margaret Atwood"), List.of("Dystopian", "Science Fiction"), "https://covers.openlibrary.org/b/id/12507658-L.jpg?default=false", 2),
                new Book(null, "9780063081918", "American Gods",
                        List.of("Neil Gaiman"), List.of("Fantasy", "Fiction"), "https://covers.openlibrary.org/b/id/8494659-L.jpg?default=false", 3),
                new Book(null, "9780380807345", "Coraline",
                        List.of("Neil Gaiman"), List.of("Fantasy", "Children"), "https://covers.openlibrary.org/b/id/14171421-L.jpg?default=false", 4),
                new Book(null, "9780547722023", "A Wizard of Earthsea",
                        List.of("Ursula K. Le Guin"), List.of("Fantasy", "Classic"), "https://covers.openlibrary.org/b/id/13617691-L.jpg?default=false", 2),
                new Book(null, "9780441478125", "The Left Hand of Darkness",
                        List.of("Ursula K. Le Guin"), List.of("Science Fiction"), "https://covers.openlibrary.org/b/id/10618463-L.jpg?default=false", 3),
                new Book(null, "9781451673319", "Fahrenheit 451",
                        List.of("Ray Bradbury"), List.of("Dystopian", "Science Fiction"), "https://covers.openlibrary.org/b/id/12993656-L.jpg?default=false", 4),
                new Book(null, "9781451678185", "The Illustrated Man",
                        List.of("Ray Bradbury"), List.of("Science Fiction", "Classic"), "https://covers.openlibrary.org/b/id/9345484-L.jpg?default=false", 1),

                new Book(null, "9780141439747", "Northanger Abbey",
                        List.of("Jane Austen"), List.of("Classic", "Romance"), "https://covers.openlibrary.org/b/id/12567961-L.jpg?default=false", 2),
                new Book(null, "9780141439792", "Mansfield Park",
                        List.of("Jane Austen"), List.of("Classic", "Romance"), "https://covers.openlibrary.org/b/id/14618737-L.jpg?default=false", 1),
                new Book(null, "9780451529305", "Brave New World",
                        List.of("Aldous Huxley"), List.of("Dystopian", "Science Fiction"), "https://covers.openlibrary.org/b/id/8231823-L.jpg?default=false", 4),
                new Book(null, "9780060850524", "Brave New World Revisited",
                        List.of("Aldous Huxley"), List.of("Non-Fiction", "Science"), "https://covers.openlibrary.org/b/id/35568-L.jpg?default=false", 1),
                new Book(null, "9780553380163", "A Feast for Crows",
                        List.of("George R.R. Martin"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/6501256-L.jpg?default=false", 3),
                new Book(null, "9780553801477", "A Dance with Dragons",
                        List.of("George R.R. Martin"), List.of("Fantasy", "Epic"), "https://covers.openlibrary.org/b/id/11298743-L.jpg?default=false", 2),
                new Book(null, "9780439785969", "Harry Potter and the Cursed Child",
                        List.of("J.K. Rowling"), List.of("Fantasy", "Drama"), "https://covers.openlibrary.org/b/id/8763851-L.jpg?default=false", 5),
                new Book(null, "9780316769488", "The Catcher in the Rye",
                        List.of("J.D. Salinger"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/9273490-L.jpg?default=false", 3),
                new Book(null, "9780060935467", "To Kill a Mockingbird: 50th Anniversary Edition",
                        List.of("Harper Lee"), List.of("Classic", "Fiction"), "https://fakeurl.com/image.jpg", 2),
                new Book(null, "9780062409850", "Go Set a Watchman",
                        List.of("Harper Lee"), List.of("Fiction", "Drama"), "https://covers.openlibrary.org/b/id/7383195-L.jpg?default=false", 1),
                new Book(null, "9780060883287", "One Flew Over the Cuckoo's Nest",
                        List.of("Ken Kesey"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/9272688-L.jpg?default=false", 3),
                new Book(null, "9780142437209", "Don Quixote",
                        List.of("Miguel de Cervantes"), List.of("Classic", "Adventure"), "https://covers.openlibrary.org/b/id/568626-L.jpg?default=false", 1),
                new Book(null, "9780140449136", "Crime and Punishment",
                        List.of("Fyodor Dostoevsky"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/10040573-L.jpg?default=false", 2),
                new Book(null, "9780140449242", "The Brothers Karamazov",
                        List.of("Fyodor Dostoevsky"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/11263774-L.jpg?default=false", 1),
                new Book(null, "9780060512804", "Slaughterhouse-Five",
                        List.of("Kurt Vonnegut"), List.of("Science Fiction", "Classic"), "https://covers.openlibrary.org/b/id/12727001-L.jpg?default=false", 3),
                new Book(null, "9780385333481", "Cat's Cradle",
                        List.of("Kurt Vonnegut"), List.of("Science Fiction", "Satire"), "https://covers.openlibrary.org/b/id/12709654-L.jpg?default=false", 2),
                new Book(null, "9780061120091", "The Alchemist",
                        List.of("Paulo Coelho"), List.of("Fiction", "Adventure"), "https://covers.openlibrary.org/b/id/7463992-L.jpg?default=false", 5),
                new Book(null, "9780679720201", "The Stranger",
                        List.of("Albert Camus"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/9173884-L.jpg?default=false", 2),
                new Book(null, "9780679724728", "One Hundred Years of Solitude",
                        List.of("Gabriel Garcia Marquez"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/6807555-L.jpg?default=false", 3),
                new Book(null, "9780060929879", "Siddhartha",
                        List.of("Hermann Hesse"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/6562535-L.jpg?default=false", 2),
                new Book(null, "9780316066525", "The Goldfinch",
                        List.of("Donna Tartt"), List.of("Fiction", "Drama"), "https://covers.openlibrary.org/b/id/8771366-L.jpg?default=false", 4),
                new Book(null, "9780679745587", "The Secret History",
                        List.of("Donna Tartt"), List.of("Fiction", "Mystery"), "https://covers.openlibrary.org/b/id/744854-L.jpg?default=false", 3),
                new Book(null, "9780307949486", "The Girl on the Train",
                        List.of("Paula Hawkins"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/7350360-L.jpg?default=false", 4),
                new Book(null, "9780399501487", "Lord of the Flies",
                        List.of("William Golding"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/8684447-L.jpg?default=false", 3),
                new Book(null, "9780316769174", "Franny and Zooey",
                        List.of("J.D. Salinger"), List.of("Classic", "Fiction"), "https://covers.openlibrary.org/b/id/6501579-L.jpg?default=false", 1),
                new Book(null, "9780060934347", "Don't Let the Pigeon Drive the Bus!",
                        List.of("Mo Willems"), List.of("Children", "Picture Book"), "https://covers.openlibrary.org/b/id/544664-L.jpg?default=false", 6),
                new Book(null, "9780064401845", "Matilda",
                        List.of("Roald Dahl"), List.of("Children", "Fantasy"), "https://covers.openlibrary.org/b/id/12889769-L.jpg?default=false", 5),
                new Book(null, "9780142410318", "Charlie and the Chocolate Factory",
                        List.of("Roald Dahl"), List.of("Children", "Fantasy"), "https://covers.openlibrary.org/b/id/12459564-L.jpg?default=false", 4),
                new Book(null, "9780590302715", "The Giver",
                        List.of("Lois Lowry"), List.of("Dystopian", "Young Adult"), "https://covers.openlibrary.org/b/id/8352502-L.jpg?default=false", 3),
                new Book(null, "9780060256654", "Where the Sidewalk Ends",
                        List.of("Shel Silverstein"), List.of("Children", "Poetry"), "https://covers.openlibrary.org/b/id/31070-L.jpg?default=false", 5),
                new Book(null, "9780060256678", "The Giving Tree",
                        List.of("Shel Silverstein"), List.of("Children", "Picture Book"), "https://covers.openlibrary.org/b/id/8981758-L.jpg?default=false", 7),
                new Book(null, "9781501175466", "The Great Alone",
                        List.of("Kristin Hannah"), List.of("Fiction", "Historical Fiction"), "https://covers.openlibrary.org/b/id/8315368-L.jpg?default=false", 3),
                new Book(null, "9780312577223", "The Nightingale",
                        List.of("Kristin Hannah"), List.of("Historical Fiction", "Drama"), "https://covers.openlibrary.org/b/id/8314147-L.jpg?default=false", 4),
                new Book(null, "9780307588364", "Jurassic Park",
                        List.of("Michael Crichton"), List.of("Science Fiction", "Thriller"), "https://covers.openlibrary.org/b/id/12882940-L.jpg?default=false", 3),
                new Book(null, "9780345391803", "The Hitchhiker's Guide to the Galaxy",
                        List.of("Douglas Adams"), List.of("Science Fiction", "Comedy"), "https://covers.openlibrary.org/b/id/11972784-L.jpg?default=false", 5),
                new Book(null, "9780345539434", "Ready Player One",
                        List.of("Ernest Cline"), List.of("Science Fiction", "Adventure"), "https://covers.openlibrary.org/b/id/8737626-L.jpg?default=false", 4),
                new Book(null, "9780553588484", "Artemis",
                        List.of("Andy Weir"), List.of("Science Fiction"), "https://covers.openlibrary.org/b/id/8235551-L.jpg?default=false", 2),
                new Book(null, "9780316452465", "Recursion",
                        List.of("Blake Crouch"), List.of("Science Fiction", "Thriller"), "https://covers.openlibrary.org/b/id/8748478-L.jpg?default=false", 3),
                new Book(null, "9781101904220", "Dark Matter",
                        List.of("Blake Crouch"), List.of("Science Fiction", "Thriller"), "https://covers.openlibrary.org/b/id/7436634-L.jpg?default=false", 4),
                new Book(null, "9780062024039", "Divergent",
                        List.of("Veronica Roth"), List.of("Dystopian", "Young Adult"), "https://covers.openlibrary.org/b/id/13274634-L.jpg?default=false", 5),
                new Book(null, "9780439023528", "The Hunger Games",
                        List.of("Suzanne Collins"), List.of("Dystopian", "Young Adult"), "https://covers.openlibrary.org/b/id/12646537-L.jpg?default=false", 6),
                new Book(null, "9780439023498", "Catching Fire",
                        List.of("Suzanne Collins"), List.of("Dystopian", "Young Adult"), "https://covers.openlibrary.org/b/id/12646539-L.jpg?default=false", 4),
                new Book(null, "9780439023511", "Mockingjay",
                        List.of("Suzanne Collins"), List.of("Dystopian", "Young Adult"), "https://covers.openlibrary.org/b/id/12646459-L.jpg?default=false", 3),
                new Book(null, "9781501110368", "11/22/63",
                        List.of("Stephen King"), List.of("Science Fiction", "Thriller"), "https://covers.openlibrary.org/b/id/10713447-L.jpg?default=false", 2),
                new Book(null, "9781982110567", "The Institute",
                        List.of("Stephen King"), List.of("Horror", "Thriller"), "https://covers.openlibrary.org/b/id/10712767-L.jpg?default=false", 3),
                new Book(null, "9780307588357", "The Lost Symbol",
                        List.of("Dan Brown"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/8373389-L.jpg?default=false", 2),
                new Book(null, "9780385537858", "Origin",
                        List.of("Dan Brown"), List.of("Mystery", "Thriller"), "https://covers.openlibrary.org/b/id/11013590-L.jpg?default=false", 1),
                new Book(null, "9780593311257", "The Midnight Library",
                        List.of("Matt Haig"), List.of("Fiction", "Fantasy"), "https://covers.openlibrary.org/b/id/10313767-L.jpg?default=false", 5),
                new Book(null, "9780525559474", "The Invisible Life of Addie LaRue",
                        List.of("V.E. Schwab"), List.of("Fantasy", "Romance"), "https://covers.openlibrary.org/b/id/10092261-L.jpg?default=false", 4)
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
