package com.stm.marvel.Repositories.Specifications;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import jakarta.persistence.criteria.Join;
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
            root.join("comics", JoinType.RIGHT);
            return criteriaBuilder.equal(root.get("comics").get("id"),id);
    };

}
    public static Specification<Character> byComicsId(Integer comicsId) {
        return (root, query, criteriaBuilder) -> {
            Join<java.lang.Character,Comics> comicsJoin = root.join("comics");
            return criteriaBuilder.equal(comicsJoin.get("id"), comicsId);
        };

    }

}

