package com.wad.combat.dto;

public class CombatResponse {
    private String combatId;
    private String winnerId;
    private String winnerName;
    private String message;

    public CombatResponse() {}

    public CombatResponse(String combatId, String winnerId, String winnerName, String message) {
        this.combatId = combatId;
        this.winnerId = winnerId;
        this.winnerName = winnerName;
        this.message = message;
    }

    public String getCombatId() { return combatId; }
    public void setCombatId(String combatId) { this.combatId = combatId; }
    public String getWinnerId() { return winnerId; }
    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }
    public String getWinnerName() { return winnerName; }
    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public static CombatResponseBuilder builder() {
        return new CombatResponseBuilder();
    }

    public static class CombatResponseBuilder {
        private String combatId;
        private String winnerId;
        private String winnerName;
        private String message;

        public CombatResponseBuilder combatId(String combatId) { this.combatId = combatId; return this; }
        public CombatResponseBuilder winnerId(String winnerId) { this.winnerId = winnerId; return this; }
        public CombatResponseBuilder winnerName(String winnerName) { this.winnerName = winnerName; return this; }
        public CombatResponseBuilder message(String message) { this.message = message; return this; }
        public CombatResponse build() {
            return new CombatResponse(combatId, winnerId, winnerName, message);
        }
    }
}
