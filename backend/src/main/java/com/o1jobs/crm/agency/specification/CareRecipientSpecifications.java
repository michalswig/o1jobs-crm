package com.o1jobs.crm.agency.specification;

import com.o1jobs.crm.agency.domain.CareRecipient;
import org.springframework.data.jpa.domain.Specification;

public class CareRecipientSpecifications {

    public static Specification<CareRecipient> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }

}