package com.pranjal.PagePulse.service;

import com.pranjal.PagePulse.entity.Book;
import com.pranjal.PagePulse.entity.Borrowing;
import com.pranjal.PagePulse.entity.Member;
import com.pranjal.PagePulse.repository.BookRepository;
import com.pranjal.PagePulse.repository.BorrowingRepository;
import com.pranjal.PagePulse.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    // Borrow book logic
    @Transactional
    public ResponseEntity<?> borrowBook(Long bookId, Long memberId) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            Optional<Member> memberOptional = memberRepository.findById(memberId);

            if (bookOptional.isPresent() && memberOptional.isPresent()) {
                Book book = bookOptional.get();
                Member member = memberOptional.get();

                if (book.getCount() > 1) {
//                    book.setAvailable(false);

                    Borrowing borrowing = new Borrowing();
                    borrowing.setBook(book);
                    borrowing.setMember(member);
                    borrowing.setBorrow_date(LocalDate.now());
//                    if(book.getCount() > 1){
                        book.setCount(book.getCount() - 1);
//                    }
//                    if(book.getCount()== 1){
//                        book.setAvailable(false);
//                    }
                    Borrowing savedBorrowing = borrowingRepository.save(borrowing);
                    return ResponseEntity.ok("Book has been borrowed and your Borrowing ID is : "  + borrowing.getBorrowing_id() );
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Book is not available at this time.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Book or member not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


    // Return book logic
    @Transactional
    public ResponseEntity<?> returnBook(Long borrowingId) {
        try {
            Optional<Borrowing> borrowingOptional = borrowingRepository.findById(borrowingId);

            if (borrowingOptional.isPresent()) {
                Borrowing borrowing = borrowingOptional.get();
                Book book = borrowing.getBook();

                if(borrowing.getReturn_date() != null){
                    return ResponseEntity.badRequest().body("Book has already been returned");
                }
//                book.setAvailable(true); // Mark the book as available
                borrowing.setReturn_date(LocalDate.now()); // Set the return date
                book.setCount(book.getCount() + 1);
                bookRepository.save(book); // Save updated book status
                Borrowing savedBorrowing = borrowingRepository.save(borrowing); // Save updated borrowing record

                return ResponseEntity.ok("Book returned successfully"); // Return the updated borrowing record
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No borrowing record for this book is present.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
