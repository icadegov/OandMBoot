package in.OAndM.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import in.OAndM.utils.DataTypeUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class BaseSpecification<E> {

    public Specification<E> buildSpecification(Map<String, String> filters) {
        return (Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> filter : filters.entrySet()) {
                String field = filter.getKey();
                String value = filter.getValue();
                Class<?> valueType = DataTypeUtil.inferType(value);

                if (valueType == String.class) {
                    predicates.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
                } else if (valueType == Integer.class) {
                    predicates.add(criteriaBuilder.equal(root.get(field), Integer.parseInt(value)));
                } else if (valueType == Long.class) {
                    predicates.add(criteriaBuilder.equal(root.get(field), Long.parseLong(value)));
                } else if (valueType == Double.class) {
                    predicates.add(criteriaBuilder.equal(root.get(field), Double.parseDouble(value)));
                }
                // Add more types as needed
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
