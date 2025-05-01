package com.embarkx.reviewms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam(required = false) Long companyId){
        List<Review> reviews;
        if (companyId != null) {
            reviews = reviewService.getAllReviews(companyId);
        } else {
            reviews = reviewService.getAllReviews();
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        boolean isReviewSaved = reviewService.addReview(companyId, review);
        if(isReviewSaved) {
            return new ResponseEntity<>("Review Added Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Review Not Saved", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
    return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,//which review is to be updated
                                               @RequestBody Review review){//new updated review
        boolean isReviewupdated = reviewService.updateReview(reviewId, review);
        if(isReviewupdated){
            return new ResponseEntity<>("Review Updated Successfully", HttpStatus.OK );
        }else {
            return new ResponseEntity<>("Review Not Updated", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if(isReviewDeleted){
            return new ResponseEntity<>("Review Deleted Successfully", HttpStatus.OK );
        }else {
            return new ResponseEntity<>("Review Not Deleted", HttpStatus.NOT_FOUND);
        }
    }

}
