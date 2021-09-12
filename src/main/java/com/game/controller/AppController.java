package com.game.controller;

import com.game.entity.Player;
import com.game.exception_handling.exceptions.NoSuchPlayerException;
import com.game.service.PlayerService;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class AppController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getPlayersRequest(HttpServletRequest httpServletRequest) {

        if (httpServletRequest.getQueryString() == null)
            return playerService.getAllPlayers();
        else
            return playerService.getPlayersWithSearchSpec(httpServletRequest);
    }

    @GetMapping("/players/count")
    public int getPlayersCountRequest(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getQueryString() == null)
            return playerService.getPlayersCount();
        else
            return playerService.getPlayersCountWithSearchSpec(httpServletRequest);
    }

    @PostMapping("/players")
    public Player savePlayerRequest(@RequestBody Player player) {
        playerService.savePlayer(player);
        return player;
    }

    @GetMapping("/players/{id}")
    public Player getPlayerRequest(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);
        if (player == null)
            throw new NoSuchPlayerException();
        return player;
    }

    @PostMapping("/players/{id}")
    public Player updatePlayerRequest(@PathVariable Long id, @RequestBody Player player) {
        if (playerService.getPlayerById(id)==null)
            throw new NoSuchPlayerException();
        player.setId(id);
        playerService.savePlayer(player);
        return player;
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayerRequest(@PathVariable Long id){
        if (playerService.getPlayerById(id)==null)
            throw new NoSuchPlayerException();
        playerService.deletePlayer(id);
    }
}
