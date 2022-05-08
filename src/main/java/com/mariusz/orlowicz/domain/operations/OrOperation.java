package com.mariusz.orlowicz.domain.operations;

import com.mariusz.orlowicz.domain.Constraint;
import com.mariusz.orlowicz.model.User;

public class OrOperation extends LogicalOperation implements Constraint {

    @Override
    public boolean validate(User user) {
        return constraintList.stream()
                .anyMatch(x -> x.validate(user));
    }
}
