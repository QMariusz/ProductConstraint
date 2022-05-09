package com.mariusz.orlowicz.model.domain.logical_operations;

import com.mariusz.orlowicz.model.domain.Constraint;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicalOperation implements Constraint {

    protected List<Constraint> constraintList = new ArrayList<>();
    private LogicalOperation parent;

    public void addElement(Constraint constraint) {
        constraintList.add(constraint);
    }

    public void setParent(LogicalOperation logicalOperation) {
        this.parent = logicalOperation;

    }

    public LogicalOperation getParent() {
        return parent;
    }
}
