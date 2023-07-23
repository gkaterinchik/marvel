package com.stm.marvel.Repositories.Specifications;


import com.stm.marvel.Entities.Comics;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ComicsSpecification {
    public static Specification<Comics> descriptionLike(String description) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), String.format("%%%s%%", description));
    }
    public static Specification<Comics> titleLike(String namePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", namePart));
    }
    public static Specification<Comics> creatorLike(String creator) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("creator"), String.format("%%%s%%", creator));
    }

    public static Specification<Comics> ContainsCharacterWhithId(Integer id) {
        return (root, query, criteriaBuilder) -> {
            root.join("characters", JoinType.RIGHT);
            return criteriaBuilder.equal(root.get("characters").get("id"), id);
        };
    }

}
