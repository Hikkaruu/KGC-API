package pl.wolinski.unofficialkgcapi.util;

import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class EntityUtils {

    public  <T> Predicate greaterLessEqualFiltering(CriteriaBuilder criteriaBuilder, Root<T> root, Predicate predicate, String fieldPath, Object value) {
        if (value instanceof String stringValue ) {
            if (stringValue.startsWith(">=")) {
                int minValue = Integer.parseInt(stringValue.substring(2));
                return predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get(fieldPath).as(Integer.class), minValue));
            } else if (stringValue.startsWith(">")) {
                int minValue = Integer.parseInt(stringValue.substring(1));
                return predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get(fieldPath).as(Integer.class), minValue));
            } else if (stringValue.startsWith("<=")) {
                int maxValue = Integer.parseInt(stringValue.substring(2));
                return predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get(fieldPath).as(Integer.class), maxValue));
            } else if (stringValue.startsWith("<")) {
                int maxValue = Integer.parseInt(stringValue.substring(1));
                return predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get(fieldPath).as(Integer.class), maxValue));
            } else {
                int exactValue = Integer.parseInt(stringValue);
                return predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(fieldPath).as(Integer.class), exactValue));
            }
        } else if (value instanceof Integer intValue) {
            return predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(fieldPath).as(Integer.class), intValue));
        }
        return predicate;
    }

}
