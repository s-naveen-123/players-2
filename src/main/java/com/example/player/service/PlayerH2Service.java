/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here

package com.example.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import com.example.player.model.Player;
import com.example.player.repository.PlayerRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.player.model.PlayerRowMapper;
import java.util.*;

@Service
public class PlayerH2Service implements PlayerRepository {

    @Autowired
    public JdbcTemplate db;

    @Override
    public ArrayList<Player> getPlayers() {
        List<Player> playerList = db.query("SELECT * FROM TEAM ", new PlayerRowMapper());
        ArrayList<Player> players = new ArrayList<>(playerList);
        return players;

    }

    @Override
    public Player getPlayerById(int playerId) {
        try {
            Player p1 = db.queryForObject("SELECT * FROM TEAM WHERE playerId = ? ", new PlayerRowMapper(), playerId);
            return p1;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no player found");
        }

    }

    @Override
    public Player putPlayer(int playerId, Player player) {
        try {
            db.queryForObject("SELECT * FROM TEAM WHERE playerId = ?", new PlayerRowMapper(),
                    playerId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no player found");
        }
        if (player.getPlayerName() != null) {
            db.update("UPDATE TEAM SET  playerName = ? WHERE playerId = ? ", player.getPlayerName(),
                    playerId);
        }
        if (player.getJerseyNumber() >= 0) {
            db.update("UPDATE TEAM SET  jerseyNumber = ? WHERE playerId = ?", player.getJerseyNumber(),
                    playerId);

        }
        if (player.getRole() != null) {
            db.update("UPDATE TEAM SET role = ? WHERE playerId = ?", player.getRole(), playerId);

        }

        return db.queryForObject("SELECT * FROM TEAM WHERE playerName = ? AND jerseyNumber = ? AND role = ?",
                new PlayerRowMapper(), player.getPlayerName(), player.getJerseyNumber(), player.getRole());
    }

    @Override
    public Player addPlayer(Player player) {
        db.update(
                "INSERT INTO TEAM(playerName,jerseyNumber,role) VALUES (?,?,?)",
                player.getPlayerName(), player.getJerseyNumber(), player.getRole());

        return db.queryForObject("SELECT * FROM TEAM WHERE playerName = ? AND jerseyNumber = ? AND role = ?",
                new PlayerRowMapper(), player.getPlayerName(), player.getJerseyNumber(), player.getRole());
    }

    @Override
    public void deletePlayer(int playerId) {

        int deletedPlayer = db.update("DELETE FROM TEAM WHERE playerId = ? ", playerId);
        if (deletedPlayer == 0) {
            throw new ResponseStatusException(HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.OK);

    }

}