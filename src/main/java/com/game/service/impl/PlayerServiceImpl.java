package com.game.service.impl;

import com.game.entity.Player;
import com.game.repository.PlayerDao;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDao playerDao;

    @Override
    @Transactional
    public List<Player> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    @Override
    @Transactional
    public int getPlayersCount() {
        return playerDao.getPlayersCount();
    }

    @Override
    @Transactional
    public void savePlayer(Player player) {
        Integer playerLevel = new Double((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100).intValue();
        Integer untilNextLevel = 50 * (playerLevel + 1) * (playerLevel + 2) - player.getExperience();
        player.setLevel(playerLevel);
        player.setUntilNextLevel(untilNextLevel);
        playerDao.savePlayer(player);
    }

    @Override
    @Transactional
    public Player getPlayerById(Long id) {
        return playerDao.getPlayerById(id);
    }

    @Override
    @Transactional
    public void deletePlayer(Long id) {
        playerDao.deletePlayer(id);
    }

    @Override
    @Transactional
    public List<Player> getPlayersWithSearchSpec(HttpServletRequest httpServletRequest) {
        return playerDao.getPlayersWithSearchSpec(getQueryString(httpServletRequest),
                httpServletRequest.getParameter("order"),
                Integer.parseInt(httpServletRequest.getParameter("pageNumber")),
                Integer.parseInt(httpServletRequest.getParameter("pageSize")));
    }

    @Override
    @Transactional
    public int getPlayersCountWithSearchSpec(HttpServletRequest httpServletRequest) {
        return playerDao.getPlayersCountWithSearchSpec(getQueryString(httpServletRequest));
    }

    private String getQueryString(HttpServletRequest httpServletRequest) {
        String queryString = "";
        String[] numParamsNames = {"after", "before", "minExperience", "maxExperience", "minLevel", "maxLevel"};
        String[] notQueryParams = {"order", "pageNumber", "pageSize"};
        Map<String , String[]> paramsMap = httpServletRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry:
                paramsMap.entrySet()) {
            if (Arrays.asList(numParamsNames).contains(entry.getKey())) {
                switch (entry.getKey()) {
                    case "after" :
                        queryString = queryString + " AND birthday >= " + entry.getValue()[0];
                        break;
                    case "before" :
                        queryString = queryString + " AND birthday <= " + entry.getValue()[0];
                        break;
                    case "minExperience" :
                        queryString = queryString + " AND experience >= " + entry.getValue()[0];
                        break;
                    case "maxExperience" :
                        queryString = queryString + " AND experience <= " + entry.getValue()[0];
                        break;
                    case "minLevel" :
                        queryString = queryString + " AND level >= " + entry.getValue()[0];
                        break;
                    case "maxLevel" :
                        queryString = queryString + " AND level <= " + entry.getValue()[0];
                        break;
                    default:
                        break;
                }
            } else if (entry.getKey().equals("name")) {
                queryString = queryString + " AND " + entry.getKey() + " LIKE '%" + entry.getValue()[0] + "%'";
            } else if (!Arrays.asList(notQueryParams).contains(entry.getKey())) {
                queryString = queryString + " AND " + entry.getKey() + " = '" + entry.getValue()[0] + "'";
            }
        }
        return queryString.equals("") ? "" : " where " + queryString.substring(5);
    }
}
