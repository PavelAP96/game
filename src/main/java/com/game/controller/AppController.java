package com.game.controller;

import com.game.entity.Player;
import com.game.exception_handling.exceptions.NoSuchPlayerException;
import com.game.exception_handling.exceptions.NotValidRequestException;
import com.game.service.PlayerService;
import com.game.service.PlayerValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
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
        if (!playerService.validatePlayer(player))
            throw new NotValidRequestException();
        playerService.savePlayer(player);
        return player;
    }

    @GetMapping("/players/{id}")
    public Player getPlayerRequest(@PathVariable Long id) {
        if (id <= 0)
            throw new NotValidRequestException();
        Player player = playerService.getPlayerById(id);
        if (player == null)
            throw new NoSuchPlayerException();
        return player;
    }

    @PostMapping("/players/{id}")
    public Player updatePlayerRequest(@PathVariable Long id, @RequestBody Player player) {
        if (id <= 0)
            throw new NotValidRequestException();

        Player currentPlayer = playerService.getPlayerById(id);

        if (currentPlayer == null)
            throw new NoSuchPlayerException();

        playerService.comparePlayersParamsBeforeUpdate(currentPlayer, player);

        if (!playerService.validatePlayer(currentPlayer))
            throw new NotValidRequestException();

        playerService.savePlayer(currentPlayer);

        return currentPlayer;
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayerRequest(@PathVariable Long id) {
        if (id <= 0)
            throw new NotValidRequestException();
        if (playerService.getPlayerById(id) == null)
            throw new NoSuchPlayerException();
        playerService.deletePlayer(id);
    }
}
