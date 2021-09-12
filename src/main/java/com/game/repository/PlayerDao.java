package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;

import java.util.List;

public interface PlayerDao {
    public List<Player> getAllPlayers();

    public int getPlayersCount();

    public void savePlayer(Player player);

    public Player getPlayerById(Long id);

    public void deletePlayer(Long id);

    public List<Player> getPlayersWithSearchSpec(String queryString, String order, int pageNumber, int pageSize);

    public int getPlayersCountWithSearchSpec(String queryString);
}
