package main.service;

import main.model.BookModel;
import main.repository.BookRepository;
import org.springframework.stereotype.Service;


@Service
public class BookService {

    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo){
        this.bookRepo = bookRepo;
        initializeBooks();
    }

    public void initializeBooks(){
        bookRepo.save(new BookModel(1L, "ISBN-GHI456", "1984", "A book about technological oppression", "George Orwell", "Secker & Warburg", 14.38F));
        bookRepo.save(new BookModel(2L, "ISBN-ABC789", "The Mist", "A mist rolls over a small town", "Stephen King", "Viking Press", 17.99F));
        bookRepo.save(new BookModel(3L, "ISBN-DEF123", "FARENHEIT 451", "A very warm temperature", "Ray Bradbury", "Ballantine Books", 21.00F));

    }

}
