package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Hello world!
 *
 */
@SpringBootApplication
public class BookStoreApp {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
		SpringApplication.run(BookStoreApp.class, args);
    }
}
