package com.embarkx.reviewms;

import com.embarkx.reviewms.review.Review;
import com.embarkx.reviewms.review.ReviewRepository;
import com.embarkx.reviewms.review.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    private ReviewRepository reviewRepository;
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        reviewService = new ReviewServiceImpl(reviewRepository);
        System.out.println("🔧 Setup complete for ReviewServiceImplTest");
    }

    @Test
    void testGetAllReviews() {
        System.out.println("🧪 Running: testGetAllReviews");

        Long companyId = 1L;
        List<Review> mockReviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByCompanyId(companyId)).thenReturn(mockReviews);

        List<Review> result = reviewService.getAllReviews(companyId);

        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByCompanyId(companyId);

        System.out.println("✅ Passed: testGetAllReviews");
    }

    @Test
    void testAddReview() {
        System.out.println("🧪 Running: testAddReview");

        Review review = new Review();
        Long companyId = 1L;

        boolean result = reviewService.addReview(companyId, review);

        assertTrue(result);
        assertEquals(companyId, review.getCompanyId());
        verify(reviewRepository, times(1)).save(review);
        System.out.println("✅ Passed: testAddReview");
    }

    @Test
    void testGetReview() {
        System.out.println("🧪 Running: testGetReview");
        Review review = new Review();
        review.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review found = reviewService.getReview(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        System.out.println("✅ Passed: testGetReview");

    }

    @Test
    void testDeleteReviewWhenExists() {
        System.out.println("🧪 Running: testDeleteReviewWhenExists");

        Review review = new Review();
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        boolean result = reviewService.deleteReview(1L);

        assertTrue(result);
        verify(reviewRepository, times(1)).delete(review);
        System.out.println("✅ Passed: testDeleteReviewWhenExists");
    }

    @Test
    void testDeleteReviewWhenNotExists() {
        System.out.println("🧪 Running: testDeleteReviewWhenNotExists");
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = reviewService.deleteReview(1L);

        assertFalse(result);
        verify(reviewRepository, never()).delete(any());
        System.out.println("✅ Passed: testDeleteReviewWhenNotExists");
    }
}
