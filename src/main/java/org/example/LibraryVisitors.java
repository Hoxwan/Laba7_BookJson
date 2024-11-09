package org.example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
public class LibraryVisitors {
    public static void main(String[] args) {
        List<Visitor> visitors = readVisitorsFromJson();
        // Задание 1: Вывести список посетителей и их количество.
        System.out.println("Список посетителей:");
        visitors.forEach(v -> System.out.println(v.getName() + " " + v.getSurname()));
        System.out.println("Количество посетителей: " + visitors.size());
        // Задание 2: Вывести список и количество книг, добавленных посетителями в избранное, без повторений.
        Set<Book> uniqueBooks = visitors.stream()
                .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                .collect(Collectors.toSet());
        System.out.println("Количество уникальных книг: " + uniqueBooks.size());
        uniqueBooks.forEach(book -> System.out.println(book.getName()));
        // Задание 3: Отсортировать по году издания и вывести список книг.
        List<Book> sortedBooks = new ArrayList<>(uniqueBooks);
        sortedBooks.sort(Comparator.comparingInt(Book::getPublishingYear));
        System.out.println("Отсортированный список книг по году издания:");
        sortedBooks.forEach(book -> System.out.println(book.getName() + " (" + book.getPublishingYear() + ")"));
        // Задание 4: Проверить, есть ли у кого-то в избранном книга автора “Jane Austen”.
        boolean hasJaneAusten = visitors.stream()
                .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                .anyMatch(book -> "Jane Austen".equals(book.getAuthor()));
        System.out.println("Есть ли книга автора 'Jane Austen' в избранном: " + hasJaneAusten);
        // Задание 5: Вывести максимальное число добавленных в избранное книг.
        int maxFavorites = visitors.stream()
                .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                .max()
                .orElse(0);
        System.out.println("Максимальное число добавленных в избранное книг: " + maxFavorites);
        // Задание 6: Создать SMS-сообщения для группировки посетителей.
        double averageFavorites = visitors.stream()
                .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                .average()
                .orElse(0);
        List<SmsMessage> smsMessages = visitors.stream()
                .filter(Visitor::isSubscribed)
                .map(visitor -> {
                    int favoritesCount = visitor.getFavoriteBooks().size();
                    String message;
                    if (favoritesCount > averageFavorites) {
                        message = "Да ты книжный червь!";
                    } else if (favoritesCount < averageFavorites) {
                        message = "Читайте больше";
                    } else {
                        message = "Нормас";
                    }
                    return new SmsMessage(visitor.getPhone(), message);
                })
                .collect(Collectors.toList());
        System.out.println("SMS-сообщения:");
        smsMessages.forEach(sms -> System.out.println(sms.getPhone() + ": " + sms.getMessage()));
    }
    private static List<Visitor> readVisitorsFromJson() {
        Gson gson = new Gson();
        Type visitorListType = new TypeToken<List<Visitor>>() {}.getType();
        try (FileReader reader = new FileReader("books.json")) {
            return gson.fromJson(reader, visitorListType);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
