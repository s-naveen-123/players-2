/*
 * 
 * You can use the following import statemets
 * 
 * import org.springframework.web.bind.annotation.*;
 * import java.util.*;
 * 
 */

package com.example.player.controller;

import com.example.player.service.PlayerH2Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.example.player.model.Player;
import com.example.player.service.PlayerH2Service;

@RestController

public class PlayerController {

  @Autowired
  private PlayerH2Service playerH2Service;

  @GetMapping("/players")
  public ArrayList<Player> getPlayers() {
    return playerH2Service.getPlayers();
  };

  @GetMapping("/players/{playerId}")
  public Player getPlayerById(@PathVariable("playerId") int playerId) {
    return playerH2Service.getPlayerById(playerId);
  }

  @PostMapping("/players")
  public Player addPlayer(@RequestBody Player player) {
    return playerH2Service.addPlayer(player);
  }

  @PutMapping("/players/{playerId}")
  public Player putPlayer(@PathVariable("playerId") int playerId, @RequestBody Player player) {
    return playerH2Service.putPlayer(playerId, player);
  }

  @DeleteMapping("/players/{playerId}")
  public void deletePlayer(@PathVariable("playerId") int playerId) {
    playerH2Service.deletePlayer(playerId);
  }
}
