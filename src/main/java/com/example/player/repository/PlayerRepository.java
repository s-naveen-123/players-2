// Write your code here

package com.example.player.repository;

import java.util.*;
import com.example.player.model.Player;

public interface PlayerRepository {
    public ArrayList<Player> getPlayers();

    public Player getPlayerById(int playerId);

    public Player putPlayer(int playerId, Player player);

    public Player addPlayer(Player player);

    public void deletePlayer(int playerId);

}