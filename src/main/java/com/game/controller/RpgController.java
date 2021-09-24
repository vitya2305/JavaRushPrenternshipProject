package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class RpgController {

    private final PlayerService playerService;

    public RpgController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public List<Player> getPlayersList(@RequestParam(value = "name", required = false)String name, @RequestParam(value = "title", required = false)String title,
                                       @RequestParam(value = "race", required = false) Race race, @RequestParam(value = "profession", required = false)Profession profession,
                                       @RequestParam(value = "after", required = false)Long after, @RequestParam(value = "before", required = false)Long before,
                                       @RequestParam(value = "banned", required = false)Boolean banned, @RequestParam(value = "minExperience", required = false)Integer minExperience,
                                       @RequestParam(value = "maxExperience", required = false)Integer maxExperience, @RequestParam(value = "minLevel", required = false)Integer minLevel,
                                       @RequestParam(value = "maxLevel", required = false)Integer maxLevel, @RequestParam(value = "order", required = false)PlayerOrder order,
                                       @RequestParam(value = "pageNumber",required = false)Integer pageNumber, @RequestParam(value = "pageSize", required = false)Integer pageSize){
        List<Player> allPlayers = playerService.getAllPlayers(name, title, race, profession,
                after, before, banned, minExperience, maxExperience, minLevel, maxLevel);

        return playerService.getPage(allPlayers, pageNumber, pageSize);

    }

    @GetMapping("players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id){
        Player player = playerService.getPlayerById(id);

        if(!isValidId(id)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (player == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    private boolean isValidId(Long id) {
        return (id > 0) && (id % 1 == 0);
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player){
        if (!isCorrectPlayer(player)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (player.getBanned() == null){
            player.setBanned(false);
        }
        player.calcCurrentLevel();
        player.calcUntilNextLevel();
        Player resultPlayer = playerService.savePlayer(player);
        return new ResponseEntity<>(resultPlayer, HttpStatus.OK);
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player){
        ResponseEntity<Player> entity = getPlayer(id);
        Player playerById = entity.getBody();
        if (playerById == null){
            return entity;
        }
        playerById.changePlayer(player);
        if (!isCorrectPlayer(playerById)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        playerById.calcCurrentLevel();
        playerById.calcUntilNextLevel();
        Player result = playerService.savePlayer(playerById);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Long id){
        ResponseEntity<Player> entity = getPlayer(id);
        Player playerById = entity.getBody();
        if (playerById == null){
            return entity;
        }
        playerService.deletePlayer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/players/count")
    public Integer getPlayersCount(@RequestParam(value = "name", required = false)String name, @RequestParam(value = "title", required = false)String title,
                                   @RequestParam(value = "race", required = false) Race race, @RequestParam(value = "profession", required = false)Profession profession,
                                   @RequestParam(value = "after", required = false)Long after, @RequestParam(value = "before", required = false)Long before,
                                   @RequestParam(value = "banned", required = false)Boolean banned, @RequestParam(value = "minExperience", required = false)Integer minExperience,
                                   @RequestParam(value = "maxExperience", required = false)Integer maxExperience, @RequestParam(value = "minLevel", required = false)Integer minLevel,
                                   @RequestParam(value = "maxLevel", required = false)Integer maxLevel){
        return  playerService.getAllPlayers(name, title, race, profession, after, before,
                banned, minExperience, maxExperience, minLevel, maxLevel).size();
    }

    private boolean isCorrectPlayer(Player player) {
        Calendar calendarStart = new GregorianCalendar();
        calendarStart.set(Calendar.YEAR, 2000);
        Calendar calendarFinish = new GregorianCalendar();
        calendarFinish.set(Calendar.YEAR, 3000);

        return (player.getName() != null && player.getName().length() <= 12 && player.getName().length() > 0) &&
                (player.getTitle() != null && player.getTitle().length() <= 30) &&
                (player.getExperience() != null && player.getExperience() >=0 && player.getExperience() <= 10_000_000) &&
                (player.getBirthday() != null && player.getBirthday().after(calendarStart.getTime()) && player.getBirthday().before(calendarFinish.getTime())) &&
                (player.getRace() != null) && (player.getProfession() != null);
    }
}