package com.mariusz.orlowicz;

import com.mariusz.orlowicz.rest.ExpressionEvaluation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductConstraintApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testExpression() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		expressionEvaluation.test();
	}
}
