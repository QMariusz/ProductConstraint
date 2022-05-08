package com.mariusz.orlowicz.domain.operations;

import com.mariusz.orlowicz.domain.Constraint;
import com.mariusz.orlowicz.model.User;

public class NotOperation extends LogicalOperation implements Constraint {

    @Override
    public boolean validate(User user) {
        return constraintList.stream()
                .allMatch(x -> !x.validate(user));
    }

//    public NotOperation(LogicalOperation parent) {
//        this.parent = parent;
//    }
}
