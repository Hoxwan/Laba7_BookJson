package org.example;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
class Visitor {
    private String name;
    private String surname;
    private String phone;
    private boolean subscribed;
    private List<Book> favoriteBooks;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class Book {
    private String name;
    private String author;
    private int publishingYear;
    private String isbn;
    private String publisher;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class SmsMessage {
    private String phone;
    private String message;
}
