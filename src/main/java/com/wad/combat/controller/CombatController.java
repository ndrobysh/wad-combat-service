package com.wad.combat.controller;

import com.wad.combat.dto.CombatRequest;
import com.wad.combat.dto.CombatResponse;
import com.wad.combat.model.CombatLog;
import com.wad.combat.service.AuthService;
import com.wad.combat.service.CombatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combat")
@Tag(name = "Combat", description = "API de simulation de combat")
public class CombatController {

    private final CombatService combatService;
    private final AuthService authService;

    public CombatController(CombatService combatService, AuthService authService) {
        this.combatService = combatService;
        this.authService = authService;
    }

    @PostMapping("/simulate")
    @Operation(summary = "Simuler un combat entre deux monstres")
    public ResponseEntity<CombatResponse> fight(
            @RequestHeader("Authorization") String token,
            @RequestBody CombatRequest request) {

        authService.validateToken(token);

        CombatLog log = combatService.fight(request.getMonster1Id(), request.getMonster2Id(), token);
        
        return ResponseEntity.ok(CombatResponse.builder()
                .combatId(log.getId())
                .winnerId(log.getWinnerId())
                .winnerName(log.getWinnerName())
                .message("Combat terminé. Vainqueur: " + log.getWinnerName())
                .build());
    }

    @GetMapping("/history")
    @Operation(summary = "Récupérer l'historique de tous les combats")
    public ResponseEntity<List<CombatLog>> history(@RequestHeader("Authorization") String token) {
        authService.validateToken(token);
        return ResponseEntity.ok(combatService.history());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer les logs d'un combat spécifique (rediffusion)")
    public ResponseEntity<CombatLog> getLog(
            @RequestHeader("Authorization") String token,
            @PathVariable String id) {
        authService.validateToken(token);
        CombatLog log = combatService.getLog(id);
        if (log == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(log);
    }
}
