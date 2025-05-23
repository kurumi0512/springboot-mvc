package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.BookException;
import com.example.demo.model.Book;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.BookService;

@CrossOrigin(origins = "http://localhost:5173") // 允許跨域請求,安全性的問題
@RestController
@RequestMapping("/book")
public class BookController {

	// Spring 依賴注入（DI）設計原則：依賴「抽象」，不是「實作」
	@Autowired
	private BookService bookService;

	// 每個方法都對應到 HTTP 方法與路由，用來實作對書籍資料的操作
	@GetMapping // 後面不加代表只要/book就好
	public ResponseEntity<ApiResponse<List<Book>>> findAllBooks() {
		List<Book> books = bookService.findAllBooks();
		if (books.size() == 0) {
			return ResponseEntity.badRequest().body(ApiResponse.error("查無此書"));
		}
		return ResponseEntity.ok(ApiResponse.success("查詢成功:", books));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Integer id) {
		try {
			Book book = bookService.getBookById(id);
			return ResponseEntity.ok(ApiResponse.success("查詢成功:", book));
		} catch (BookException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PostMapping // 新增
	// @RequestBody 的用途，就是「接住放在 request body 中的參數」
	public ResponseEntity<ApiResponse<Book>> addBook(@RequestBody Book book) {
		try {
			bookService.addBook(book);
			return ResponseEntity.ok(ApiResponse.success("新增成功", book));
		} catch (BookException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@DeleteMapping("/{id}") // 根據id來刪除
	public ResponseEntity<ApiResponse<String>> deletedBook(@PathVariable("id") Integer id) {
		try {
			bookService.deleteBook(id);
			return ResponseEntity.ok(ApiResponse.success("刪除成功", ""));
		} catch (BookException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PutMapping("/{id}") // 修改 路徑會知道是要修改{id}這一筆,寫在路徑看起來會比較直接
	public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable("id") Integer id, @RequestBody Book book) {
		try {
			bookService.updateBook(id, book);
			return ResponseEntity.ok(ApiResponse.success("修改成功", book));
		} catch (BookException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	// PUT 是完整修改(整筆更新)，PATCH 是部分更新
	// @PathVariable Integer id(路徑資料), @RequestBody Book book(要傳進去所有的json檔案)
	// 部分修改 name 與 price
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Book>> updateBookNameAndPrice(@PathVariable Integer id, @RequestBody Book book) {
		try {
			bookService.updateBookNameAndPrice(id, book.getName(), book.getPrice());
			return ResponseEntity.ok(ApiResponse.success("修改書名與價格成功", book));
		} catch (BookException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	// 部分修改 price,還是存成JSON,只是是這樣處理
	@PatchMapping("/price/{id}")
	public ResponseEntity<ApiResponse<Book>> updateBookPrice(@PathVariable Integer id, @RequestBody Book book) {
		try {
			bookService.updateBookPrice(id, book.getPrice());
			return ResponseEntity.ok(ApiResponse.success("修改價格成功", book));
		} catch (BookException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	// 部分修改 name
	@PatchMapping("/name/{id}")
	public ResponseEntity<ApiResponse<Book>> updateBookName(@PathVariable Integer id, @RequestBody Book book) {
		try {
			bookService.updateBookName(id, book.getName());
			return ResponseEntity.ok(ApiResponse.success("修改書名成功", book));
		} catch (BookException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

}