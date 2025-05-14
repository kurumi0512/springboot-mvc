package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.BookException;
import com.example.demo.model.Book;

public interface BookService {
	List<Book> findAllBooks();

	// 不用用optional了(因為沒找到會直接bookexception
	Book getBookById(Integer id) throws BookException;

	// 這些方法的目標是「做完就好」，不需要回傳資料，所以用 void。
	void addBook(Book book) throws BookException;

	void updateBook(Integer id, Book book) throws BookException;

	void updateBookName(Integer id, String name) throws BookException;

	void updateBookPrice(Integer id, Double price) throws BookException;

	void updateBookNameAndPrice(Integer id, String name, Double price) throws BookException;

	void deleteBook(Integer id) throws BookException;
}