package com.o1jobs.crm.agency.specification;

import com.o1jobs.crm.agency.domain.Assignment;
import org.springframework.data.jpa.domain.Specification;

public class AssignmentSpecifications {

    public static Specification<Assignment> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }

}
