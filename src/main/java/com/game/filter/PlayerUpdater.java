package com.game.filter;

import com.game.entity.Player;
import com.game.exceptions.CantCreatePlayerException;

import java.time.LocalDate;
import java.time.ZoneId;

public class PlayerUpdater {

    Player oldPlayer;
    Player newPlayerFields;

    public PlayerUpdater(Player player, Player newPlayerFields) {
        oldPlayer = player;
        this.newPlayerFields = newPlayerFields;
    }

    public Player updatePlayer() {
        updateName();
        updateTitle();
        updateRace();
        updateProfession();
        updateBanned();
        updateBirthday();
        updateExp();
        return oldPlayer;
    }

    void updateName() {
        if (newPlayerFields.getName() != null && newPlayerFields.getName().length() <= 12) {
            oldPlayer.setName(newPlayerFields.getName());
//            System.out.println("name updated");
        }

    }

    void updateTitle() {
        if (newPlayerFields.getTitle() != null && newPlayerFields.getTitle().length() <= 30) {
            oldPlayer.setTitle(newPlayerFields.getTitle());
//            System.out.println("title updated");
        }

    }

    void updateRace() {
        if (newPlayerFields.getRace() != null) {
            oldPlayer.setRace(newPlayerFields.getRace());
//            System.out.println("race updated");
        }

    }

    void updateProfession() {
        if (newPlayerFields.getProfession() != null) {
            oldPlayer.setProfession(newPlayerFields.getProfession());
//            System.out.println("prof updated");
        }

    }

    void updateBanned() {
        if (newPlayerFields.getBanned() != null) {
            oldPlayer.setBanned(newPlayerFields.getBanned());

        }

    }

    void updateBirthday() {
        if (newPlayerFields.getBirthday() != null) {
            LocalDate localDate = newPlayerFields.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); //!!!
            int year = localDate.getYear();
            if (year >= 2000 && year <= 3000) {
                oldPlayer.setBirthday(newPlayerFields.getBirthday());
//                System.out.println("birth updated");
            } else throw new CantCreatePlayerException();
        }

    }

    void updateExp() {
        if (newPlayerFields.getExperience() != null) {
            int experience = newPlayerFields.getExperience();
            if (experience < 0 || experience > 10_000_000) throw new CantCreatePlayerException();
            int level = (int) (Math.sqrt(2500 + 200 * experience) - 50) / 100;
            int untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
            oldPlayer.setExperience(experience);
            oldPlayer.setLevel(level);
            oldPlayer.setUntilNextLevel(untilNextLevel);
//            System.out.println("exp updated");
        }

    }

}
