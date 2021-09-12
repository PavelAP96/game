package com.game.repository.impl;

import com.game.entity.Player;
import com.game.repository.PlayerDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PlayerDaoImpl implements PlayerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Player> getAllPlayers() {
        Query query = entityManager.createQuery("from Player order by id", Player.class);
        query.setMaxResults(3);
        return query.getResultList();
    }

    @Override
    public int getPlayersCount() {
        return entityManager.createQuery("select count(*) from Player", Long.class).getSingleResult().intValue();
    }

    @Override
    public void savePlayer(Player player) {
        entityManager.merge(player);
    }

    @Override
    public Player getPlayerById(Long id) {
        return entityManager.find(Player.class, id);
    }

    @Override
    public void deletePlayer(Long id) {
        Query query = entityManager.createQuery("delete from Player where id = :playerId");
        query.setParameter("playerId", id);
        query.executeUpdate();
    }

    @Override
    public List<Player> getPlayersWithSearchSpec(String queryString, String order, int pageNumber, int pageSize) {
        Query query = entityManager.createQuery("from Player" + queryString + " order by " + order);
        //query.setMaxResults(pageSize);
        List<Player> players = query.getResultList();
        return players.subList(pageNumber * pageSize, pageNumber * pageSize + pageSize);
    }

    @Override
    public int getPlayersCountWithSearchSpec(String queryString) {
        return entityManager.createQuery("select count(*) from Player" + queryString, Long.class).getSingleResult().intValue();
    }
}
