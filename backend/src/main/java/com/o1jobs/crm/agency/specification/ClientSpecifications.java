package com.o1jobs.crm.agency.specification;

import com.o1jobs.crm.agency.domain.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecifications {

    public static Specification<Client> notDeleted() {
        return  (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }

}
