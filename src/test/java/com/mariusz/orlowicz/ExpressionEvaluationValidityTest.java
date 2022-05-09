package com.mariusz.orlowicz;

import com.mariusz.orlowicz.domain.operations.BaseOperation;
import com.mariusz.orlowicz.model.User;
import com.mariusz.orlowicz.rest.ExpressionEvaluation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.mariusz.orlowicz.model.User.UserConstraints.*;

class ExpressionEvaluationValidityTest {

	private final String EXPRESSION_1 = "OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))";
	private final String EXPRESSION_2 = "OR('VIP_ONLY', 'WITH_RELATIONS'))";

	@Test
	void should_VIPUser_BeAuthorizedToAccess() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of(VIP));


		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_1);

		Assertions.assertTrue(expression.validate(user));
	}

	@Test
	void should_UserWithRelations_BeAuthorizedToAccess() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of(HAS_RELATIONS));

		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_1);

		Assertions.assertTrue(expression.validate(user));
	}

	@Test
	void shouldNot_RichUser_BeAuthorizedToAccess() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of(RICH));

		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_1);

		Assertions.assertFalse(expression.validate(user));
	}

	@Test
	void shouldNot_RichAndWithRelationsUser_BeAuthorizedToAccess() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of(RICH, HAS_RELATIONS));

		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_1);

		Assertions.assertFalse(expression.validate(user));
	}

	@Test
	void should_RichAndWithRelationsAndVIPUser_BeAuthorizedToAccess() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of(VIP, RICH, HAS_RELATIONS));

		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_1);

		Assertions.assertTrue(expression.validate(user));
	}

	@Test
	void shouldNot_UserWithNoConstraints_BeAuthorizedToAccess() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of());

		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_1);

		Assertions.assertFalse(expression.validate(user));
	}

	@Test
	void should_VIPUser_BeAuthorizedToAccess_Expression2() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of(VIP));

		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_2);

		Assertions.assertTrue(expression.validate(user));
	}

	@Test
	void should_UserWithRelations_BeAuthorizedToAccess_Expression2() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of(HAS_RELATIONS));

		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_2);

		Assertions.assertTrue(expression.validate(user));
	}

	@Test
	void shouldNot_RichUser_BeAuthorizedToAccess_Expression2() {
		ExpressionEvaluation expressionEvaluation = new ExpressionEvaluation();
		User user = new User();
		user.setUserConstraintsSet(Set.of(RICH));

		BaseOperation expression = expressionEvaluation.evaluate(EXPRESSION_2);

		Assertions.assertFalse(expression.validate(user));
	}
}
