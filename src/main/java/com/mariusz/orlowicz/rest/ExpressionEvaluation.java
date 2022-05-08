package com.mariusz.orlowicz.rest;

import com.mariusz.orlowicz.domain.Constraint;
import com.mariusz.orlowicz.domain.RelationLeaf;
import com.mariusz.orlowicz.domain.RichLeaf;
import com.mariusz.orlowicz.domain.VIPLeaf;
import com.mariusz.orlowicz.domain.operations.*;
import com.mariusz.orlowicz.model.Product;

import java.util.Arrays;

public class ExpressionEvaluation {

    public void test() {
        String s = "OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))";
        String replacedClosingBracket = s.replace(")", "'END'");

        String[] split = Arrays.stream(replacedClosingBracket.split("[', (]"))
                .filter(x -> !x.isEmpty())
                .toArray(String[]::new);

        BaseOperation constraint = new BaseOperation();
        createConsRecur(split, constraint, 0);
        System.out.println(constraint);
    }

    private void createConsRecur(String[] replaced, LogicalOperation constraint, int i) {
        if (i < replaced.length) {
            if (replaced[i].equals(Product.Logical.END.getValue())) {
                createConsRecur(replaced, constraint.getParent(), i + 1);
            } else {
                if (Arrays.stream(Product.Logical.values()).anyMatch(x -> x.getValue().equals(replaced[i]))) {
                    LogicalOperation logicalOperation = logicalFactory(replaced[i], constraint);
                    constraint.addElement(logicalOperation);
                    createConsRecur(replaced, logicalOperation, i + 1);
                } else if (Arrays.stream(Product.Constraints.values()).anyMatch(x -> x.getValue().equals(replaced[i]))) {
                    Constraint constraintFactory = constraintFactory(replaced[i]);
                    constraint.addElement(constraintFactory);
                    createConsRecur(replaced, constraint, i + 1);
                }
            }
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
            return new RelationLeaf();
        } else if (Product.Constraints.VIP_ONLY.getValue().equals(logical)) {
            return new VIPLeaf();
        } else if (Product.Constraints.FOR_RICH_PEOPLE.getValue().equals(logical)) {
            return new RichLeaf();
        }
        return null;
    }
}
