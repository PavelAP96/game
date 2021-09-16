package com.game.service.impl;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import com.game.service.PlayerValidationResult;
import com.game.specification.PlayerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerSpecification playerSpecification;

    @Override
    @Transactional
    public List<Player> getAllPlayers() {
        Pageable paging = PageRequest.of(0, 3, Sort.by(PlayerOrder.ID.getFieldName()));
        Page<Player> page = playerRepository.findAll(paging);
        return page.getContent();
    }

    @Override
    @Transactional
    public int getPlayersCount() {
        return (int) playerRepository.count();
    }

    //
    @Override
    @Transactional
    public void savePlayer(Player player) {

        Integer playerLevel = new Double((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100).intValue();
        Integer untilNextLevel = 50 * (playerLevel + 1) * (playerLevel + 2) - player.getExperience();
        player.setLevel(playerLevel);
        player.setUntilNextLevel(untilNextLevel);
        playerRepository.save(player);
    }

    @Override
    @Transactional
    public Player getPlayerById(Long id) {
        return playerRepository.getById(id);
    }

    @Override
    @Transactional
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Player> getPlayersWithSearchSpec(HttpServletRequest httpServletRequest) {

        String pageNumberFromRequest = httpServletRequest.getParameter("pageNumber");
        int pageNumber = pageNumberFromRequest == null ? 0 : Integer.parseInt(pageNumberFromRequest);

        String pageSizeFromRequest = httpServletRequest.getParameter("pageSize");
        int pageSize = pageSizeFromRequest == null ? 3 : Integer.parseInt(pageSizeFromRequest);

        String orderFromRequest = httpServletRequest.getParameter("order");
        String order = orderFromRequest == null ? PlayerOrder.ID.getFieldName() : PlayerOrder.valueOf(orderFromRequest).getFieldName();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order));
        Page<Player> page = playerRepository.findAll(playerSpecification.getFilter(httpServletRequest), pageable);

        return page.getContent();
    }

    @Override
    @Transactional
    public int getPlayersCountWithSearchSpec(HttpServletRequest httpServletRequest) {
        return (int) playerRepository.count(playerSpecification.getFilter(httpServletRequest));
    }

    @Override
    public boolean validatePlayer(Player player) {

        if (player.getExperience() == null ||
                player.getBirthday() == null ||
                player.getName() == null ||
                player.getTittle() == null ||
                player.getRace() == null ||
                player.getProfession() == null) {
            return false;
        }

        if (player.getName().length() > 12 || player.getName().equals("") || player.getTittle().length() > 30)
            return false;

        if (player.getExperience() > 10000000 || player.getExperience() < 0)
            return false;

        if (player.getBirthday().getTime() < 0 || player.getBirthday().getYear() < 100 || player.getBirthday().getYear() > 1100)
            return false;

        return true;
    }

    @Override
    public void comparePlayersParamsBeforeUpdate(Player currentPlayer, Player newPlayer) {
        String[] entityColumnsName = {"name", "title", "race", "profession", "birthday", "banned", "experience"};
        try {
            for (String paramName : entityColumnsName) {
                Field field = currentPlayer.getClass().getDeclaredField(paramName);
                Field field1 = newPlayer.getClass().getDeclaredField(paramName);
                field1.setAccessible(true);
                field.setAccessible(true);
                Object field1Value = field1.get(newPlayer);
                if (field1Value != null)
                    field.set(currentPlayer, field1Value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
