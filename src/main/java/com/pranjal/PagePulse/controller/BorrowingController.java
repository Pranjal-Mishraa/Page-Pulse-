package com.pranjal.PagePulse.controller;


import com.pranjal.PagePulse.entity.Borrowing;
import com.pranjal.PagePulse.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowRequest request) {
        try {
            Borrowing borrowing = borrowingService.borrowBook(request.bookId, request.memberId);
            return ResponseEntity.ok("Book borrowed successfully! ");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // Return the exception message
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody ReturnRequest retReq) {
        try {
            Borrowing borrowing = borrowingService.returnBook(retReq.borrowingId);
            return ResponseEntity.ok("Book returned successfully! ");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // Return the exception message
        }
    }
}




