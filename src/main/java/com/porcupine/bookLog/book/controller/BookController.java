package com.porcupine.bookLog.book.controller;

import com.porcupine.bookLog.book.dto.AladinBookResponse;
import com.porcupine.bookLog.book.service.AladinApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BookController {

    private final AladinApiService aladinApiService;

    @Autowired
    public BookController(AladinApiService aladinApiService) {
        this.aladinApiService = aladinApiService;
    }

    @GetMapping("/books/search")
    public String searchBooks(@RequestParam String keyword, Model model) {
        List<AladinBookResponse> books = aladinApiService.searchBooks(keyword);
        model.addAttribute("books", books);
        return "book/searchResult";
    }

    @GetMapping("/books/api")
    @ResponseBody
    public List<AladinBookResponse> testApi(@RequestParam String keyword) {
        return aladinApiService.searchBooks(keyword);
    }
}
