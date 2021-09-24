package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerService {

    List<Player> getAllPlayers(String name, String title, Race race,
                               Profession profession, Long after,
                               Long before, Boolean banned,
                               Integer minExperience, Integer maxExperience,
                               Integer minLevel, Integer maxLevel);

    Player getPlayerById(Long id);

    Player savePlayer(Player player);

    void deletePlayer(Long id);

    List<Player> getPage(List<Player>players, Integer pageNumber, Integer pageSize);

}
