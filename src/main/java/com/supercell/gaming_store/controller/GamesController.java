package com.supercell.gaming_store.controller;

import com.supercell.gaming_store.entity.Games;
import com.supercell.gaming_store.service.interfaces.GamesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GamesController {

    private final GamesService gamesService;

    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @PostMapping
    public ResponseEntity<Games> addGame(@RequestBody Games game) {
        Games newGame = gamesService.addGame(game);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Games>> getAllGames() {
        List<Games> games = gamesService.getAllGames();
        return ResponseEntity.ok(games);
    }
}
