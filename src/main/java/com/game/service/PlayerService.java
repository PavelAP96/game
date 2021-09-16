package com.game.service;

import com.game.entity.Player;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayers();

    int getPlayersCount();

    void savePlayer(Player player);

    Player getPlayerById(Long id);

    void deletePlayer(Long id);

    List<Player> getPlayersWithSearchSpec(HttpServletRequest httpServletRequest);

    int getPlayersCountWithSearchSpec(HttpServletRequest httpServletRequest);

    boolean validatePlayer(Player player);

    void comparePlayersParamsBeforeUpdate(Player currentPlayer, Player newPlayer);
}
