package com.game.specification;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class PlayerSpecification extends BaseSpecification<Player, HttpServletRequest> {

    @Override
    public Specification<Player> getFilter(HttpServletRequest request) {

        return where(nameLike(request.getParameter("name")))
                .and(experienceGreaterThan(request.getParameter("minExperience")))
                .and(experienceLessThan(request.getParameter("maxExperience")))
                .and(levelGreaterThan(request.getParameter("minLevel")))
                .and(levelLessThan(request.getParameter("maxLevel")))
                .and(birthdayGreaterThan(request.getParameter("after")))
                .and(birthdayLessThan(request.getParameter("before")))
                .and(raceEqual(request.getParameter("race")))
                .and(professionEqual(request.getParameter("profession")))
                .and(bannedEqual(request.getParameter("banned")))
                .and(tittleLike(request.getParameter("title")));

    }

    private Specification<Player> nameLike(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.like(root.get("name"), contains(value));
        };
    }

    private Specification<Player> experienceGreaterThan(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), value);
        };
    }

    private Specification<Player> experienceLessThan(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("experience"), value);
        };
    }

    private Specification<Player> levelGreaterThan(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("level"), value);
        };
    }

    private Specification<Player> levelLessThan(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("level"), value);
        };
    }

    private Specification<Player> tittleLike(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.like(root.get("title"), contains(value));
        };
    }

    private Specification<Player> raceEqual(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("race"), Race.valueOf(value));
        };
    }

    private Specification<Player> professionEqual(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("profession"), Profession.valueOf(value));
        };
    }

    private Specification<Player> bannedEqual(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("banned"), Boolean.parseBoolean(value));
        };
    }

    private Specification<Player> birthdayGreaterThan(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(Long.parseLong(value)));
        };
    }

    private Specification<Player> birthdayLessThan(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(Long.parseLong(value)));
        };
    }
}
