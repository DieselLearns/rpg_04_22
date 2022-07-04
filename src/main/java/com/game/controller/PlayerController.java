package com.game.controller;
///////////////////////////

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.filter.CountParams;
import com.game.filter.PlayerParamsFilter;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    PlayerService playerService;
    @Autowired
    public PlayerController(PlayerService ps){
        playerService = ps;
    }

    @GetMapping("rest/players")
    public List<Player> getPlayers(
                                   @RequestParam(value = "name",required = false) String name,
                                   @RequestParam(value = "title",required = false) String title,
                                   @RequestParam(value = "race",required = false) Race race,
                                   @RequestParam(value = "profession",required = false) Profession profession,
                                   @RequestParam(value = "after",required = false,defaultValue = "0") Long after,
                                   @RequestParam(value = "before",required = false, defaultValue = "0") Long before,
                                   @RequestParam(value = "banned",required = false) Boolean banned,
                                   @RequestParam(value = "minExperience",required = false ,defaultValue = "0") Integer minExperience,
                                   @RequestParam(value = "maxExperience",required = false, defaultValue = "0") Integer maxExperience,
                                   @RequestParam(value = "minLevel",required = false, defaultValue = "0") Integer minLevel,
                                   @RequestParam(value = "maxLevel",required = false, defaultValue = "0") Integer maxLevel,
                                   @RequestParam(value = "order",required = false, defaultValue = "ID") PlayerOrder order,
                                   @RequestParam(value = "pageNumber",required = false, defaultValue = "0") Integer pageNumber,
                                   @RequestParam(value = "pageSize",required = false, defaultValue = "3") Integer pageSize
                                   ){

        PlayerParamsFilter playerParamsFilter = new PlayerParamsFilter(name,title,race,profession,after,before,banned,minExperience,maxExperience,minLevel,maxLevel,order,pageNumber,pageSize);

        return playerService.findAllPlayers(playerParamsFilter);
    }

    @GetMapping("/rest/players/count")
    public long count(@RequestParam(value = "name",required = false) String name,
                      @RequestParam(value = "title",required = false) String title,
                      @RequestParam(value = "race",required = false) Race race,
                      @RequestParam(value = "profession",required = false) Profession profession,
                      @RequestParam(value = "after",required = false,defaultValue = "0") Long after,
                      @RequestParam(value = "before",required = false, defaultValue = "0") Long before,
                      @RequestParam(value = "banned",required = false) Boolean banned,
                      @RequestParam(value = "minExperience",required = false ,defaultValue = "0") Integer minExperience,
                      @RequestParam(value = "maxExperience",required = false, defaultValue = "0") Integer maxExperience,
                      @RequestParam(value = "minLevel",required = false, defaultValue = "0") Integer minLevel,
                      @RequestParam(value = "maxLevel",required = false, defaultValue = "0") Integer maxLevel){
        PlayerParamsFilter countParams = new PlayerParamsFilter(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        return playerService.count(countParams);//исправить
    }

    @DeleteMapping("/rest/players/{id}")
    public void deletePlayer(@PathVariable(name = "id") Long id){
        playerService.deletePlayer(id);
    }
    @GetMapping("/rest/players/{id}")
    public Player getPlayerById(@PathVariable(name = "id") Long id){
        return playerService.getPlayerById(id);
    }


    @PostMapping("/rest/players")
    public Player createPlayer(@RequestBody Player player){
       return playerService.createPlayer(player);
    }

    @PostMapping("/rest/players/{id}")
    public Player updatePlayer(@RequestBody Player player, @PathVariable(name = "id",required = false) Long id){
        return playerService.updatePlayer(player,id);
    }

}
