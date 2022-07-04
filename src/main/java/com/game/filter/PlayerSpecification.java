package com.game.filter;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerSpecification implements Specification<Player> {


    PlayerParamsFilter playerParamsFilter;

    public PlayerSpecification(PlayerParamsFilter playerParamsFilter) {
        this.playerParamsFilter = playerParamsFilter;
    }

    @Override
    public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        //name
        if (playerParamsFilter.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + playerParamsFilter.getName().toLowerCase() + "%"));
        }
        //title
        if (playerParamsFilter.getTitle() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + playerParamsFilter.getTitle().toLowerCase() + "%"));
        }
        //banned
        if (playerParamsFilter.getBanned() != null) {
            predicates.add(criteriaBuilder.equal(root.get("banned"), playerParamsFilter.getBanned()));
        }
        //race
        if (playerParamsFilter.getRace() != null) {
            predicates.add(criteriaBuilder.equal(root.get("race"), playerParamsFilter.getRace()));
        }
        //profession
        if (playerParamsFilter.getProfession() != null) {
            predicates.add(criteriaBuilder.equal(root.get("profession"), playerParamsFilter.getProfession()));
        }
        //minLevel - maxLevel
        if (playerParamsFilter.getMinLevel() != 0 && playerParamsFilter.getMaxLevel() == 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), playerParamsFilter.getMinLevel()));
        } else if (playerParamsFilter.getMaxLevel() != 0 && playerParamsFilter.getMinLevel() == 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("level"), playerParamsFilter.getMaxLevel()));
        }
        else if (playerParamsFilter.getMinLevel() < playerParamsFilter.getMaxLevel()){
            predicates.add(criteriaBuilder.between(root.get("level"),playerParamsFilter.getMinLevel(), playerParamsFilter.getMaxLevel()));
        }
        else if ((playerParamsFilter.getMinLevel() == playerParamsFilter.getMaxLevel()) && (playerParamsFilter.getMinLevel() != 0 && playerParamsFilter.getMaxLevel() != 0 )){
            predicates.add(criteriaBuilder.equal(root.get("level"),playerParamsFilter.getMinLevel()));
        }

        //minExp - maxExp
        if (playerParamsFilter.getMinExperience() != 0 && playerParamsFilter.getMaxExperience() == 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), playerParamsFilter.getMinExperience()));
        } else if (playerParamsFilter.getMaxExperience() != 0 && playerParamsFilter.getMinExperience() == 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("experience"), playerParamsFilter.getMaxExperience()));
        }
        else if (playerParamsFilter.getMinExperience() < playerParamsFilter.getMaxExperience()){
            predicates.add(criteriaBuilder.between(root.get("experience"),playerParamsFilter.getMinExperience(), playerParamsFilter.getMaxExperience()));
        }
        else if ((playerParamsFilter.getMinExperience() == playerParamsFilter.getMaxExperience()) && (playerParamsFilter.getMinExperience() != 0 && playerParamsFilter.getMaxExperience() != 0 )){
            predicates.add(criteriaBuilder.equal(root.get("experience"),playerParamsFilter.getMinExperience()));
        }

        //after - before

        Date after = new Date(playerParamsFilter.getAfter());
        Date before = new Date(playerParamsFilter.getBefore());
        if (playerParamsFilter.getAfter() != 0 && playerParamsFilter.getBefore() == 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), after));
        } else if (playerParamsFilter.getBefore() != 0 && playerParamsFilter.getAfter() == 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), before));
        }
        else if (playerParamsFilter.getAfter() < playerParamsFilter.getBefore()){
            predicates.add(criteriaBuilder.between(root.get("birthday"),after, before));
        }
        else if ((playerParamsFilter.getAfter() == playerParamsFilter.getBefore()) && (playerParamsFilter.getAfter() != 0 && playerParamsFilter.getBefore() != 0 )){
            predicates.add(criteriaBuilder.equal(root.get("birthday"),after));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


}
