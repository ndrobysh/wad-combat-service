package com.wad.combat.client;

import com.wad.combat.dto.MonsterDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MonsterClientImpl implements MonsterClient {

    private final RestTemplate restTemplate;
    private final String monsterServiceUrl;

    public MonsterClientImpl(@Value("${monster.service.url}") String monsterServiceUrl) {
        this.restTemplate = new RestTemplate();
        this.monsterServiceUrl = monsterServiceUrl;
    }

    @Override
    public MonsterDTO getMonster(String id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<MonsterDTO> response = restTemplate.exchange(
                    monsterServiceUrl + "/api/monster/" + id,
                    HttpMethod.GET,
                    entity,
                    MonsterDTO.class
            );
            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
