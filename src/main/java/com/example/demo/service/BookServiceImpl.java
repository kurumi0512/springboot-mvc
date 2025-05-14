package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BookException;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	// 自動綁定,service要串DAO,不用new會自動綁定
	// Spring 依賴注入（DI）設計原則：依賴「抽象」，不是「實作」
	// 你不需要寫 new BookRepositoryImpl()，Spring 幫你注入了！
	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAllBooks();
	}

	@Override
	public Book getBookById(Integer id) throws BookException {
		Optional<Book> optBook = bookRepository.getBookById(id);
		if (optBook.isEmpty()) {
			throw new BookException("id: " + id + ", 查無此書");
		}
		return optBook.get();
	}

	@Override
	public void addBook(Book book) throws BookException {
		if (!bookRepository.addBook(book)) {
			throw new BookException("新增失敗, " + book);
		}

	}

	@Override
	public void updateBook(Integer id, Book book) throws BookException {
		if (!bookRepository.updateBook(id, book)) {
			throw new BookException("修改失敗, id: " + id + ", " + book);
		}
	}

	@Override
	public void updateBookName(Integer id, String name) throws BookException {
		Book book = getBookById(id);
		book.setName(name);
		updateBook(book.getId(), book);
	}

	@Override
	public void updateBookPrice(Integer id, Double price) throws BookException {
		Book book = getBookById(id);// 1. 根據 id 拿到對應的 Book 物件
		book.setPrice(price); // 2. 設定這本書的新價格
		updateBook(book.getId(), book); // 3. 呼叫更新邏輯，把修改後的 book 更新回 Repository
	}

	@Override
	public void updateBookNameAndPrice(Integer id, String name, Double price) throws BookException {
		Book book = getBookById(id);
		book.setName(name);
		book.setPrice(price);
		updateBook(book.getId(), book); // 模擬資料庫
	}

	@Override
	public void deleteBook(Integer id) throws BookException {
		if (!bookRepository.deleteBook(id)) {
			throw new BookException("刪除失敗, id: " + id);
		}

	}

}