package com.supercell.gaming_store.service.impl;

import com.supercell.gaming_store.entity.Games;
import com.supercell.gaming_store.repository.GamesRepository;
import com.supercell.gaming_store.service.interfaces.GamesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GamesServiceImpl implements GamesService {


    private final GamesRepository gamesRepository;

    public GamesServiceImpl(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @Override
    public Games addGame(Games game) {
        // Here you could add logic to check if a game with the same name already exists
        log.info("Adding game with name: {}", game.getName());
        return gamesRepository.save(game);
    }

    @Override
    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }
}
