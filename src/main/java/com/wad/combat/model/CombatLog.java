package com.wad.combat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "combat_logs")
public class CombatLog {

    @Id
    private String id;
    private String monster1Id;
    private String monster1Name;
    private String monster2Id;
    private String monster2Name;
    private String winnerId;
    private String winnerName;
    private List<TurnLog> logs;
    private LocalDateTime timestamp;

    public CombatLog() {}

    public CombatLog(String id, String monster1Id, String monster1Name, String monster2Id, String monster2Name, 
                     String winnerId, String winnerName, List<TurnLog> logs, LocalDateTime timestamp) {
        this.id = id;
        this.monster1Id = monster1Id;
        this.monster1Name = monster1Name;
        this.monster2Id = monster2Id;
        this.monster2Name = monster2Name;
        this.winnerId = winnerId;
        this.winnerName = winnerName;
        this.logs = logs;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMonster1Id() { return monster1Id; }
    public void setMonster1Id(String monster1Id) { this.monster1Id = monster1Id; }
    public String getMonster1Name() { return monster1Name; }
    public void setMonster1Name(String monster1Name) { this.monster1Name = monster1Name; }
    public String getMonster2Id() { return monster2Id; }
    public void setMonster2Id(String monster2Id) { this.monster2Id = monster2Id; }
    public String getMonster2Name() { return monster2Name; }
    public void setMonster2Name(String monster2Name) { this.monster2Name = monster2Name; }
    public String getWinnerId() { return winnerId; }
    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }
    public String getWinnerName() { return winnerName; }
    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }
    public List<TurnLog> getLogs() { return logs; }
    public void setLogs(List<TurnLog> logs) { this.logs = logs; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public static CombatLogBuilder builder() {
        return new CombatLogBuilder();
    }

    public static class CombatLogBuilder {
        private String id;
        private String monster1Id;
        private String monster1Name;
        private String monster2Id;
        private String monster2Name;
        private String winnerId;
        private String winnerName;
        private List<TurnLog> logs;
        private LocalDateTime timestamp;

        public CombatLogBuilder id(String id) { this.id = id; return this; }
        public CombatLogBuilder monster1Id(String monster1Id) { this.monster1Id = monster1Id; return this; }
        public CombatLogBuilder monster1Name(String monster1Name) { this.monster1Name = monster1Name; return this; }
        public CombatLogBuilder monster2Id(String monster2Id) { this.monster2Id = monster2Id; return this; }
        public CombatLogBuilder monster2Name(String monster2Name) { this.monster2Name = monster2Name; return this; }
        public CombatLogBuilder winnerId(String winnerId) { this.winnerId = winnerId; return this; }
        public CombatLogBuilder winnerName(String winnerName) { this.winnerName = winnerName; return this; }
        public CombatLogBuilder logs(List<TurnLog> logs) { this.logs = logs; return this; }
        public CombatLogBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public CombatLog build() {
            return new CombatLog(id, monster1Id, monster1Name, monster2Id, monster2Name, winnerId, winnerName, logs, timestamp);
        }
    }
}
