package com.game.filter;

import com.game.entity.Player;
import com.game.exceptions.CantCreatePlayerException;

import java.util.Date;

public class PlayerValidation {

    Player player;

    public PlayerValidation(Player player) {
        this.player = player;
    }

    void checkName() {
        if (player.getName() == null) throw new CantCreatePlayerException();
        if (player.getName().isEmpty()) throw new CantCreatePlayerException();
        if (player.getName().length() > 12) throw new CantCreatePlayerException();
//        System.out.println("name checked");
    }

    void checkTitle() {
        if (player.getTitle() == null) throw new CantCreatePlayerException();
        if (player.getTitle().isEmpty()) throw new CantCreatePlayerException();
        if (player.getTitle().length() > 30) throw new CantCreatePlayerException();
//        System.out.println("title checked");
    }

    void checkRace() {
        if (player.getRace() == null) throw new CantCreatePlayerException();
//        System.out.println("race checked");
    }

    void checkProfession() {
        if (player.getProfession() == null) throw new CantCreatePlayerException();
//        System.out.println("profession checked");
    }

    void checkBanned() {
        if (player.getBanned() == null) player.setBanned(false);
//        System.out.println("ban checked");
    }

    void checkBirthday() {
        Date date = player.getBirthday();
        int year = date.getYear();
        if (date.getTime() < 0 && (year < 2000 || year > 3000)) throw new CantCreatePlayerException();
//        System.out.println("birthday checked");
    }

    void checkExperience() {
        int experience = player.getExperience();
        if (experience < 0 || experience > 10_000_000) throw new CantCreatePlayerException();
        int level = (int) (Math.sqrt(2500 + 200 * experience) - 50) / 100;
        int untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
//        System.out.println("exp checked");
    }

    public Player returnPlayer(){
        checkName();
        checkTitle();
        checkRace();
        checkProfession();
        checkBanned();
        checkBirthday();
        checkExperience();
        return player;
    }

}
