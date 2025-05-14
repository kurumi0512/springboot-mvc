package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {
	// InMemory 版
	// CopyOnWriteArrayList 是為了讓它在多執行緒時比較安全（寫入時會複製一份新的 List，不會同時改到原本的）
	private List<Book> books = new CopyOnWriteArrayList<>();

	// 初始資料有 4 本書,初始化區塊
	{
		books.add(new Book(1, "機器貓小叮噹", 12.5, 20, false));
		books.add(new Book(2, "老夫子", 10.5, 30, false));
		books.add(new Book(3, "好小子", 8.5, 40, true));
		books.add(new Book(4, "尼羅河的女兒", 14.5, 50, true));
	}

	// 回傳整個書籍清單
	public List<Book> findAllBooks() {
		return books;
	}

	// 利用 Java Stream 過濾 ID 相同的書籍。
	// 用 Optional 包起來，代表「有可能找到」也「有可能沒找到」。
	public Optional<Book> getBookById(Integer id) {
		return books.stream().filter(book -> book.getId().equals(id)).findFirst();
	}

	// 「宣告一個變數 book，它的資料型別是 Book。」
	public boolean addBook(Book book) {
		// 建立 newId
		// 把每一本書的 id 抓出來（mapToInt(Book::getId)）
		// 找出目前最大 id（.max()）
		OptionalInt optMaxId = books.stream().mapToInt(Book::getId).max();
		int newId = optMaxId.isEmpty() ? 1 : optMaxId.getAsInt() + 1;
		// 將 newId 設定給 book
		book.setId(newId);

		return books.add(book);
	}

	// Book updateBook：你要用來取代原本內容的新書資料（例如新的名稱、價格、庫存等）
	public boolean updateBook(Integer id, Book updateBook) {
		// 找到要修改的 book
		// Optional 設計的意義就是用來表示 method 的回傳值可能會是空的
		Optional<Book> optBook = getBookById(id);
		if (optBook.isEmpty()) {
			return false;
		}
		// 找到該 book 在 books 的 index(怕有多執行續的問題)
		int index = books.indexOf(optBook.get());
		if (index == -1) {
			return false;
		}
		// 替換
		return books.set(index, updateBook) != null;
	}

	public boolean deleteBook(Integer id) {
		Optional<Book> optBook = getBookById(id);
		if (optBook.isPresent()) {
			return books.remove(optBook.get());
		}
		return false;
	}

}