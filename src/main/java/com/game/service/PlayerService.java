package com.game.service;

///////////////////////////
import com.game.entity.Player;
import com.game.exceptions.InvalidIdException;
import com.game.exceptions.NoSuchPlayerException;
import com.game.filter.*;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;



    public List<Player> findAllPlayers(PlayerParamsFilter playerParamsFilter){

        Sort sort = Sort.by(playerParamsFilter.getOrder().getFieldName()).ascending();
        Pageable pageable = PageRequest.of(playerParamsFilter.getPageNumber(),playerParamsFilter.getPageSize(),sort);
        Page<Player> playerPage;

        PlayerSpecification specification = new PlayerSpecification(playerParamsFilter);
        playerPage = playerRepository.findAll(specification,pageable);
        List<Player> players = playerPage.getContent();

        return players;
    }



    public long count(PlayerParamsFilter playerParamsFilter){

        PlayerSpecification specification = new PlayerSpecification(playerParamsFilter);

        return playerRepository.findAll(specification).size();
    }

    public void deletePlayer(Long id){
        if (id<=0) throw new InvalidIdException();
        if (!playerRepository.existsById(id)) throw new NoSuchPlayerException();
        playerRepository.deleteById(id);

    }
    // getPlayerById
    public Player getPlayerById(Long id){
        if (id<=0) throw new InvalidIdException();
        if (!playerRepository.findById(id).isPresent()) throw new NoSuchPlayerException();
        return playerRepository.findById(id).get();
    }

    // createPlayer
    public Player createPlayer(Player player){
        PlayerValidation playerValidation = new PlayerValidation(player);
        Player newPlayer = playerValidation.returnPlayer();
        playerRepository.save(newPlayer);
        return newPlayer;
    }

    //updatePlayer
    public Player updatePlayer(Player player, Long id){
        if (id<=0) throw new InvalidIdException();
        if (!playerRepository.findById(id).isPresent()) throw new NoSuchPlayerException();
        Player oldPlayer = getPlayerById(id);
        PlayerUpdater playerUpdater = new PlayerUpdater(oldPlayer,player);
        oldPlayer = playerUpdater.updatePlayer();

        return playerRepository.saveAndFlush(oldPlayer);
    }

}
