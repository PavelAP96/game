package com.game.service;

import com.game.entity.Player;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PlayerService {
    public List<Player> getAllPlayers();

    public int getPlayersCount();

    public void savePlayer(Player player);

    public Player getPlayerById(Long id);

    public void deletePlayer(Long id);

    public List<Player> getPlayersWithSearchSpec(HttpServletRequest httpServletRequest);

    public int getPlayersCountWithSearchSpec(HttpServletRequest httpServletRequest);
}
