package com.pranjal.PagePulse.controller;

import com.pranjal.PagePulse.entity.Borrowing;
import com.pranjal.PagePulse.service.BorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/borrowings")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    public static class BorrowRequest {

        public Long bookId;
        public Long memberId;
    }

    public static class ReturnRequest {
        public Long borrowingId;
    }

    // Borrow book
    @Operation(summary = "Borrow a book", description = "Allow a member to borrow a book from the library by providing the book ID and member ID")

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowRequest request) {
        try {
            return (ResponseEntity<String>) borrowingService.borrowBook(request.bookId, request.memberId);
//             ResponseEntity.ok("Book borrowed successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Return book
    @Operation(summary = "Return a book", description = "Allow a member to return a borrowed book by providing the borrowing ID")

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody ReturnRequest retReq) {
        try {
            return (ResponseEntity<String>) borrowingService.returnBook(retReq.borrowingId);
//             ResponseEntity.ok("Book returned successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
