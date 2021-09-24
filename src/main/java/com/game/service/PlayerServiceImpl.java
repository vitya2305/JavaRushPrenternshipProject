package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> getAllPlayers(String name, String title, Race race,
                                      Profession profession, Long after,
                                      Long before, Boolean banned,
                                      Integer minExperience, Integer maxExperience,
                                      Integer minLevel, Integer maxLevel) {
        List<Player> playerList = playerRepository.findAll();
        List<Player> result = new ArrayList<>();
        for (Player player : playerList) {
            if (name != null && !player.getName().contains(name)){
                continue;
            }
            if (title != null && !player.getTitle().contains(title)){
                continue;
            }
            if (race != null && !player.getRace().equals(race)){
                continue;
            }
            if (profession != null && !player.getProfession().equals(profession)){
                continue;
            }
            if (after != null && !player.getBirthday().after(new Date(after))){
                continue;
            }
            if (before != null && !player.getBirthday().before(new Date(before))){
                continue;
            }
            if (banned != null && player.getBanned() != banned){
                continue;
            }
            if (minExperience != null && player.getExperience() < minExperience){
                continue;
            }
            if (maxExperience != null && player.getExperience() > maxExperience){
                continue;
            }
            if (minLevel != null && player.getLevel() < minLevel){
                continue;
            }
            if (maxLevel != null && player.getLevel() > maxLevel){
                continue;
            }
            result.add(player);
        }
        return result;
    }

    @Override
    public Player getPlayerById(Long id) {
        Player player = null;
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()){
            player = optionalPlayer.get();
        }
        return player;
    }

    @Override
    public Player savePlayer(Player player) {
        Player savePlayer = playerRepository.save(player);
        return savePlayer;
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<Player> getPage(List<Player> players, Integer pageNumber, Integer pageSize) {
        Integer pageNum = pageNumber == null ? 0 : pageNumber;
        Integer size = pageSize == null ? 3 : pageSize;
        int startPosition = pageNum * size;
        int finishPosition = startPosition + size;
        if (finishPosition > players.size()){
            finishPosition = players.size();
        }
        return players.subList(startPosition,finishPosition);
    }
}
