package com.stm.marvel.Repositories.Specifications;

import com.stm.marvel.Entities.Character;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class CharacterSpecification {
    public static Specification<Character> descriptionLike(String description) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), String.format("%%%s%%", description));
    }
    public static Specification<Character> nameLike(String namePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), String.format("%%%s%%", namePart));
    }

    public static Specification<Character> ContainsComicsWhithId(Integer id) {
        return (root, query, criteriaBuilder) -> {
            root.join("comics", JoinType.INNER);
            return criteriaBuilder.equal(root.get("comics").get("id"),id);
    };

}

}

