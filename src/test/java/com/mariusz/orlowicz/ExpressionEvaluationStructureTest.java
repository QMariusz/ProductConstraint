package com.mariusz.orlowicz;

import com.mariusz.orlowicz.model.domain.Constraint;
import com.mariusz.orlowicz.model.domain.constraint_leafs.HasRelationUser;
import com.mariusz.orlowicz.model.domain.constraint_leafs.RichUser;
import com.mariusz.orlowicz.model.domain.constraint_leafs.VIPUser;
import com.mariusz.orlowicz.model.domain.logical_operations.AndOperation;
import com.mariusz.orlowicz.model.domain.logical_operations.BaseOperation;
import com.mariusz.orlowicz.model.domain.logical_operations.NotOperation;
import com.mariusz.orlowicz.model.domain.logical_operations.OrOperation;
import com.mariusz.orlowicz.expression.ExpressionEvaluation;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.hamcrest.Matchers.instanceOf;

class ExpressionEvaluationStructureTest {

	private final String EXPRESSION_1 = "OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))";
	private final String EXPRESSION_2 = "OR('VIP_ONLY','WITH_RELATIONS'))";

	@Test
	void should_evaluateExpression1() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();

		BaseOperation evaluatedOperation = expressionEvaluation.evaluate(EXPRESSION_1);

		List<Constraint> baseOperationConstraints = (List<Constraint>) ReflectionTestUtils.getField(evaluatedOperation, "constraintList");
		List<Constraint> orOperationConstraints = (List<Constraint>) ReflectionTestUtils.getField(baseOperationConstraints.get(0), "constraintList");
		List<Constraint> andOperationConstraints = (List<Constraint>) ReflectionTestUtils.getField(orOperationConstraints.get(1), "constraintList");
		List<Constraint> notOperationConstraints = (List<Constraint>) ReflectionTestUtils.getField(andOperationConstraints.get(0), "constraintList");

		Assertions.assertNotNull(baseOperationConstraints);
		Assertions.assertEquals(1, baseOperationConstraints.size());
		MatcherAssert.assertThat(baseOperationConstraints.get(0), instanceOf(OrOperation.class));

		Assertions.assertNotNull(orOperationConstraints);
		Assertions.assertEquals(2, orOperationConstraints.size());
		MatcherAssert.assertThat(orOperationConstraints.get(0), instanceOf(VIPUser.class));
		MatcherAssert.assertThat(orOperationConstraints.get(1), instanceOf(AndOperation.class));

		Assertions.assertNotNull(andOperationConstraints);
		Assertions.assertEquals(2, andOperationConstraints.size());
		MatcherAssert.assertThat(andOperationConstraints.get(0), instanceOf(NotOperation.class));
		MatcherAssert.assertThat(andOperationConstraints.get(1), instanceOf(HasRelationUser.class));

		Assertions.assertNotNull(notOperationConstraints);
		Assertions.assertEquals(1, notOperationConstraints.size());
		MatcherAssert.assertThat(notOperationConstraints.get(0), instanceOf(RichUser.class));
	}

	@Test
	void should_evaluateExpression2() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		BaseOperation evaluatedOperation = expressionEvaluation.evaluate(EXPRESSION_2);

		List<Constraint> baseOperationConstraints = (List<Constraint>) ReflectionTestUtils.getField(evaluatedOperation, "constraintList");
		List<Constraint> orOperationConstraints = (List<Constraint>) ReflectionTestUtils.getField(baseOperationConstraints.get(0), "constraintList");

		Assertions.assertNotNull(baseOperationConstraints);
		Assertions.assertEquals(1, baseOperationConstraints.size());
		MatcherAssert.assertThat(baseOperationConstraints.get(0), instanceOf(OrOperation.class));

		Assertions.assertNotNull(orOperationConstraints);
		Assertions.assertEquals(2, orOperationConstraints.size());
		MatcherAssert.assertThat(orOperationConstraints.get(0), instanceOf(VIPUser.class));
		MatcherAssert.assertThat(orOperationConstraints.get(1), instanceOf(HasRelationUser.class));
	}

}
