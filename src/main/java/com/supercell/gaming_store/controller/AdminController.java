package com.supercell.gaming_store.controller;

import com.supercell.gaming_store.entity.Games;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.entity.Recharges;
import com.supercell.gaming_store.entity.Transactions;
import com.supercell.gaming_store.repository.GamesRepository;
import com.supercell.gaming_store.repository.MembersRepository;
import com.supercell.gaming_store.repository.RechargesRepository;
import com.supercell.gaming_store.repository.TransactionsRepository;
import com.supercell.gaming_store.service.interfaces.GamesService;
import com.supercell.gaming_store.service.interfaces.MembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final MembersService membersService;
    private final GamesService gamesService;
    private final MembersRepository membersRepository;
    private final GamesRepository gamesRepository;
    private final TransactionsRepository transactionsRepository;
    private final RechargesRepository rechargesRepository;

    public AdminController(MembersService membersService, GamesService gamesService,
                          MembersRepository membersRepository, GamesRepository gamesRepository,
                          TransactionsRepository transactionsRepository, RechargesRepository rechargesRepository) {
        this.membersService = membersService;
        this.gamesService = gamesService;
        this.membersRepository = membersRepository;
        this.gamesRepository = gamesRepository;
        this.transactionsRepository = transactionsRepository;
        this.rechargesRepository = rechargesRepository;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        
        long totalMembers = membersRepository.count();
        long totalGames = gamesRepository.count();
        long totalTransactions = transactionsRepository.count();
        long totalRecharges = rechargesRepository.count();
        
        // Calculate total revenue from transactions
        List<Transactions> allTransactions = transactionsRepository.findAll();
        double totalRevenue = allTransactions.stream()
                .mapToDouble(Transactions::getAmount)
                .sum();
                
        // Calculate total recharge amount
        List<Recharges> allRecharges = rechargesRepository.findAll();
        double totalRechargeAmount = allRecharges.stream()
                .mapToDouble(Recharges::getAmount)
                .sum();
        
        dashboard.put("totalMembers", totalMembers);
        dashboard.put("totalGames", totalGames);
        dashboard.put("totalTransactions", totalTransactions);
        dashboard.put("totalRecharges", totalRecharges);
        dashboard.put("totalRevenue", totalRevenue);
        dashboard.put("totalRechargeAmount", totalRechargeAmount);
        
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Members>> getAllMembers() {
        List<Members> members = membersService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable String id) {
        return membersService.getMemberById(id);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Map<String, String>> deleteMember(@PathVariable String id) {
        try {
            membersRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Member deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to delete member: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/games")
    public ResponseEntity<Games> addGame(@RequestBody Games game) {
        Games newGame = gamesService.addGame(game);
        return ResponseEntity.ok(newGame);
    }

    @GetMapping("/games")
    public ResponseEntity<List<Games>> getAllGames() {
        List<Games> games = gamesService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Map<String, String>> deleteGame(@PathVariable String id) {
        try {
            gamesRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Game deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to delete game: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transactions>> getAllTransactions() {
        List<Transactions> transactions = transactionsRepository.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/recharges")
    public ResponseEntity<List<Recharges>> getAllRecharges() {
        List<Recharges> recharges = rechargesRepository.findAll();
        return ResponseEntity.ok(recharges);
    }
}
