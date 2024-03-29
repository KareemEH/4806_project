package main.service;

import main.model.BookModel;
import main.model.UserModel;
import main.repository.BookRepository;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    public BookService(BookRepository bookRepo, UserRepository userRepo){
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
        initializeBooks();
        initializeAdmin();
    }

    public void initializeBooks(){
        bookRepo.save(new BookModel(1L, "ISBN-GHI456", "1984", "'1984' is a dystopian novella by George Orwell published in 1949, which follows the life of Winston Smith, a low ranking member of ‘the Party’, who is frustrated by the omnipresent eyes of the party, and its ominous ruler Big Brother.", "George Orwell", "Secker & Warburg", "Science Fiction",14.38F, "1984.jpg",10));
        bookRepo.save(new BookModel(2L, "ISBN-ABC789", "The Mist", "'The Mist' is a horror novella by Stephen King, first published in 1980 as part of the collection Dark Forces. The story takes place in a small town in Maine, where a severe thunderstorm knocks out power and phone lines, followed by a thick mist that traps a group of people in a local supermarket. As the characters try to survive in the increasingly dangerous and isolated environment, they encounter terrifying creatures unleashed by the mist.", "Stephen King", "Viking Press", "Horror",17.99F, "the_mist.jpg",10));
        bookRepo.save(new BookModel(3L, "ISBN-DEF123", "To Kill a Mockingbird", "'To Kill a Mockingbird' is a Pulitzer Prize-winning novel by Harper Lee, first published in 1960. Set in the Depression-era South, the novel explores themes of racism, injustice, and the loss of innocence through the eyes of young Scout Finch. The novel has become a classic of American literature and is widely taught in schools today.", "Harper Lee", "J. B. Lippincott & Co.", "Classic", 12.99F, "to_kill_a_mockingbird.jpg",10));
        bookRepo.save(new BookModel(4L, "ISBN-JKL789", "The Great Gatsby", "'The Great Gatsby' is a novel by F. Scott Fitzgerald, first published in 1925. Set in the Jazz Age, the novel tells the story of Jay Gatsby, a wealthy and mysterious man who throws extravagant parties in hopes of winning back his lost love, Daisy Buchanan. The novel explores themes of love, wealth, and the decline of the American Dream.", "F. Scott Fitzgerald", "Charles Scribner's Sons", "Classic", 9.99F, "the_great_gatsby.jpg",10));
        bookRepo.save(new BookModel(5L, "ISBN-MNO123", "The Hitchhiker's Guide to the Galaxy", "'The Hitchhiker's Guide to the Galaxy' is a comic science fiction series created by Douglas Adams, first published in 1978. The series follows the misadventures of an unwitting human and his alien friend as they travel through space and encounter various bizarre creatures and situations. The series is known for its humor and satire, and has gained a cult following over the years.", "Douglas Adams", "Pan Books", "Science Fiction", 8.99F, "the_hitchhiker's_guide_to_the_galaxy.jpg",10));
        bookRepo.save(new BookModel(6L, "ISBN-DEF123", "FARENHEIT 451", "'Fahrenheit 451' is a dystopian novel by Ray Bradbury, first published in 1953. The story is set in a future society where books are outlawed and firemen are employed to burn any that are found. The novel follows the story of a fireman named Guy Montag, who begins to question his role in society and becomes drawn into a group of rebels who seek to preserve knowledge and free thought. The novel explores themes of censorship, knowledge, and the importance of literature in a free society.", "Ray Bradbury", "Ballantine Books","Science Fiction",19.49F, "farenheit_451.jpg",10));
        bookRepo.save(new BookModel(7L, "ISBN-PQR456", "The Catcher in the Rye", "'The Catcher in the Rye' is a novel by J.D. Salinger, first published in 1951. The story is told from the perspective of Holden Caulfield, a teenage boy who has been expelled from his prep school and is wandering around New York City, struggling with feelings of alienation and disillusionment. The novel has become a classic of American literature and is widely taught in schools today.", "J.D. Salinger", "Little, Brown and Company", "Classic", 11.99F, "the_catcher_in_the_rye.jpg",10));
        bookRepo.save(new BookModel(8L, "ISBN-STU789", "The Lord of the Rings", "'The Lord of the Rings' is an epic high fantasy novel by J.R.R. Tolkien, first published in 1954-55. The novel follows the journey of hobbit Frodo Baggins and his companions as they seek to destroy the One Ring and defeat the evil Lord Sauron. The novel explores themes of power, sacrifice, and the struggle between good and evil.", "J.R.R. Tolkien", "George Allen & Unwin", "Fantasy", 24.99F, "the_lord_of_the_rings.jpg",10));
        bookRepo.save(new BookModel(9L, "ISBN-VWX123", "The Hunger Games", "'The Hunger Games' is a dystopian novel by Suzanne Collins, first published in 2008. The story is set in a post-apocalyptic world where the wealthy Capitol forces children from the twelve districts to compete in a televised fight to the death called the Hunger Games. The novel explores themes of power, rebellion, and the ethics of violence.", "Suzanne Collins", "Scholastic Press", "Young Adult", 13.99F, "the_hunger_games.jpg",10));
        bookRepo.save(new BookModel(10L, "ISBN-YZA456", "Pride and Prejudice", "'Pride and Prejudice' is a novel by Jane Austen, first published in 1813. The novel follows the story of Elizabeth Bennet, one of five sisters, as she navigates the social conventions of the Georgian era in her quest for love and marriage. The novel explores themes of marriage, class, and gender roles.", "Jane Austen", "T. Egerton, Whitehall", "Classic", 10.99F, "pride_and_prejudice.jpg",10));
        bookRepo.save(new BookModel(11L, "ISBN-BCD789", "The Girl with the Dragon Tattoo", "'The Girl with the Dragon Tattoo' is a crime novel by Stieg Larsson, first published in 2005. The novel follows journalist Mikael Blomkvist and computer hacker Lisbeth Salander as they investigate a decades-old case of a missing woman. The novel explores themes of violence, corruption, and abuse.", "Stieg Larsson", "Norstedts Förlag", "Mystery", 15.99F, "the_girl_with_the_dragon_tattoo.jpg",10));
        bookRepo.save(new BookModel(12L, "ISBN-EFG123", "The Road", "'The Road' is a post-apocalyptic novel by Cormac McCarthy, first published in 2006. The novel follows a father and his young son as they journey through a bleak and desolate landscape in search of safety and a better life. The novel explores themes of survival, morality, and the enduring bonds of love.", "Cormac McCarthy", "Alfred A. Knopf", "Science Fiction", 12.99F, "the_road.jpg",10));
        bookRepo.save(new BookModel(13L, "ISBN-HIJ456", "The Alchemist", "'The Alchemist' is a novel by Paulo Coelho, first published in 1988. The story follows Santiago, a young Andalusian shepherd who dreams of discovering a treasure buried in the Egyptian pyramids. The novel explores themes of destiny, spirituality, and the pursuit of one's dreams.", "Paulo Coelho", "HarperCollins", "Fiction", 9.99F, "the_alchemist.jpg",10));
        bookRepo.save(new BookModel(14L, "ISBN-KLM789", "The Count of Monte Cristo", "'The Count of Monte Cristo' is an adventure novel by Alexandre Dumas, first published in 1844. The novel follows the story of Edmond Dantès, a young sailor who is wrongfully imprisoned and seeks revenge against those who betrayed him. The novel explores themes of justice, revenge, and the power of perseverance.", "Alexandre Dumas", "Pétion", "Classic", 16.99F, "the_count_of_monte_cristo.jpg",10));
    }

    public void initializeAdmin(){
        if(userRepo.findByUsername("Admin") == null){
            userRepo.save(new UserModel("Admin", "Admin"));
        }
    }

    public boolean addBook(String isbn, String title, String description, String author, String publisher, String genre, Float price, String coverFilename, Integer stock){

        bookRepo.save(new BookModel(null, isbn, title, description, author, publisher, genre, price, coverFilename, stock));

        return true;
    }

    public boolean restockBook(Long bookID, int restockQuantity){
        BookModel book = null;
        for(BookModel b : bookRepo.findAll()){
            if(b.getId() == bookID){
                book = b;
            }
        }

        if(book == null){
            return false;
        }

        book.setStock(book.getStock() + restockQuantity);
        bookRepo.save(book);

        return true;
    }

    public boolean updateBook(Long bookID, String title, String isbn, String description, String author, String publisher, Float price, Integer stock){
        BookModel book = null;
        for(BookModel b : bookRepo.findAll()){
            if(b.getId() == bookID){
                book = b;
            }
        }

        if(book == null){
            return false;
        }

        book.setTitle(title);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(price);
        book.setStock(stock);
        
        bookRepo.save(book);

        return true;
    }

}
