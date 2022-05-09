package com.mariusz.orlowicz.expression;

import com.mariusz.orlowicz.model.domain.Constraint;
import com.mariusz.orlowicz.model.domain.constraint_leafs.HasRelationUser;
import com.mariusz.orlowicz.model.domain.constraint_leafs.RichUser;
import com.mariusz.orlowicz.model.domain.constraint_leafs.VIPUser;
import com.mariusz.orlowicz.model.infrastructure.Product;
import com.mariusz.orlowicz.model.domain.logical_operations.*;

import java.util.Arrays;

public class ExpressionEvaluation {

    public BaseOperation evaluate(String productConstraint) {
        String replacedClosingBracket = productConstraint.replace(")", "'END'");
        String[] productConstraintKeywords = Arrays.stream(replacedClosingBracket.split("[', (]"))
                .filter(x -> !x.isEmpty())
                .toArray(String[]::new);

        BaseOperation baseOperation = new BaseOperation();
        mapKeywordsToOperationStructure(productConstraintKeywords, baseOperation, 0);
        return baseOperation;
    }

    private void mapKeywordsToOperationStructure(String[] replaced, LogicalOperation baseOperation, int i) {
        if (i < replaced.length) {
            if (replaced[i].equals(Product.Logical.END.getValue())) {
                evaluateOnParentOperationLevel(replaced, baseOperation, i);
            } else {
                evaluateOnCurrentOperationLevel(replaced, baseOperation, i);
            }
        }
    }

    private void evaluateOnParentOperationLevel(String[] replaced, LogicalOperation baseOperation, int i) {
        mapKeywordsToOperationStructure(replaced, baseOperation.getParent(), i + 1);
    }

    private void evaluateOnCurrentOperationLevel(String[] replaced, LogicalOperation baseOperation, int i) {
        if (Arrays.stream(Product.Logical.values()).anyMatch(x -> x.getValue().equals(replaced[i]))) {
            LogicalOperation nestedOperation = logicalFactory(replaced[i], baseOperation);
            baseOperation.addElement(nestedOperation);
            mapKeywordsToOperationStructure(replaced, nestedOperation, i + 1);
        } else if (Arrays.stream(Product.Constraints.values()).anyMatch(x -> x.getValue().equals(replaced[i]))) {
            Constraint constraint = constraintFactory(replaced[i]);
            baseOperation.addElement(constraint);
            mapKeywordsToOperationStructure(replaced, baseOperation, i + 1);
        }
    }

    public static LogicalOperation logicalFactory(String logical, LogicalOperation constraint) {
        if (Product.Logical.OR.getValue().equals(logical)) {
            OrOperation operation = new OrOperation();
            operation.setParent(constraint);
            return operation;
        } else if (Product.Logical.AND.getValue().equals(logical)) {
            AndOperation operation = new AndOperation();
            operation.setParent(constraint);
            return operation;
        } else if (Product.Logical.NOT.getValue().equals(logical)) {
            NotOperation operation = new NotOperation();
            operation.setParent(constraint);
            return operation;
        }
        return null;
    }

    public static Constraint constraintFactory(String logical) {
        if (Product.Constraints.WITH_RELATIONS.getValue().equals(logical)) {
            return new HasRelationUser();
        } else if (Product.Constraints.VIP_ONLY.getValue().equals(logical)) {
            return new VIPUser();
        } else if (Product.Constraints.FOR_RICH_PEOPLE.getValue().equals(logical)) {
            return new RichUser();
        }
        return null;
    }
}
