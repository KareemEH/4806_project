package main.repository;

import main.model.BookModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookModel, Integer> {
    Iterable<BookModel> findAll();
    BookModel findById(Long aLong);
}
