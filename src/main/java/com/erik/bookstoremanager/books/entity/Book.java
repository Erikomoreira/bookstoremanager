package com.erik.bookstoremanager.books.entity;

import com.erik.bookstoremanager.author.entity.Author;
import com.erik.bookstoremanager.entity.Auditable;
import com.erik.bookstoremanager.publishers.entity.Publisher;
import com.erik.bookstoremanager.users.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String isbn;

    @Column(name = "pages", columnDefinition = "integer default 0")
    private int pages;

    @Column(name = "chapters", columnDefinition = "integer default 0")
    private int chapters;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Author author;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Publisher publisher;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private User user;

}
