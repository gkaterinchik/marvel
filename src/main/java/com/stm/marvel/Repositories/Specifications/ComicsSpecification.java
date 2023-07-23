package com.stm.marvel.Repositories.Specifications;


import com.stm.marvel.Entities.Comics;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ComicsSpecification {
    public static Specification<Comics> descriptionLike(String description) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), String.format("%%%s%%", description));
    }
    public static Specification<Comics> titleLike(String namePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", namePart));
    }

    public static Specification<Comics> ContainsCharacterWhithId(Integer id) {
        return (root, query, criteriaBuilder) -> {
            root.join("character", JoinType.RIGHT);
            return criteriaBuilder.equal(root.get("character").get("id"), id);
        };
    }
        public static Specification<Comics> byCharacterId(Integer characterId) {
            return (root, query, criteriaBuilder) -> {
                Join<Comics, Character> characterJoin = root.join("characters");
                return criteriaBuilder.equal(characterJoin.get("id"), characterId);
            };

    }
}
