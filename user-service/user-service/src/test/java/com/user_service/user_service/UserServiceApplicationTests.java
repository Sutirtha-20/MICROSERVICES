package com.user_service.user_service;

import com.user_service.user_service.entity.Rating;
import com.user_service.user_service.external.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private RatingService ratingService;

	@Test
	void createRating(){
		Rating rating = Rating.builder()
				.rating(10)
				.userId("")
				.hotelId("")
				.remark("test case")
				.build();
		Rating savedRating = ratingService.createRating(rating);
		System.out.println("new rating"+savedRating);
	}


}
