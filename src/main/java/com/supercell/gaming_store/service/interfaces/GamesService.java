package com.supercell.gaming_store.service.interfaces;

import com.supercell.gaming_store.entity.Games;

import java.util.List;

public interface GamesService {

    Games addGame(Games game);
    List<Games> getAllGames();

}
