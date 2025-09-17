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

    // Endpoint to add a new game to the store
    // POST http://localhost:8080/api/games
    @PostMapping
    public ResponseEntity<Games> addGame(@RequestBody Games game) {
        Games newGame = gamesService.addGame(game);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    // Endpoint to get all available games
    // GET http://localhost:8080/api/games
    @GetMapping
    public ResponseEntity<List<Games>> getAllGames() {
        List<Games> games = gamesService.getAllGames();
        return ResponseEntity.ok(games);
    }
}
