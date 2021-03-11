package com.erik.bookstoremanager.books.repository;

import com.erik.bookstoremanager.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRespository extends JpaRepository<Book, Long> {
}
