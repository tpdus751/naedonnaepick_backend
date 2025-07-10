package com.naedonnaepick.backend.chat.websocket.service;

import com.naedonnaepick.backend.chat.websocket.dao.ChatMessageDAO;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import com.naedonnaepick.backend.chat.websocket.dto.AnalysisResultDTO;
import com.naedonnaepick.backend.chat.websocket.entity.PositiveRestaurantManageEntity;
import com.naedonnaepick.backend.chat.websocket.repository.PositiveRestaurantManageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import com.naedonnaepick.backend.chat.websocket.dao.ChatMessageDAOImpl;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageDAO chatMessageDAO;
    private final PositiveRestaurantManageRepository positiveRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public ChatMessageServiceImpl(ChatMessageDAO chatMessageDAO, PositiveRestaurantManageRepository positiveRepository) {
        this.chatMessageDAO = chatMessageDAO;
        this.positiveRepository = positiveRepository;
    }

    @Override
    public void saveMessage(ChatMessage message) {
        chatMessageDAO.saveMessage(message);
    }

    @Override
    public boolean existsTodayPositive(String email, int roomNo, int restaurantNo) {
        LocalDate today = LocalDate.now(); // "2025-07-10"
        return positiveRepository.existsBySenderEmailAndRoomNoAndRestaurantNoAndVoteMonth(email, roomNo, restaurantNo, today);
    }

    @Override
    public AnalysisResultDTO anlysisSentiment(String content) {
        String url = "http://localhost:5000/api/sentiment";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("text", content);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<AnalysisResultDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    AnalysisResultDTO.class
            );
            return response.getBody();
        } catch (Exception e) {
            log.error("감성 분석 서버 통신 실패", e);
            return new AnalysisResultDTO("unknown", 0.0);
        }
    }

    @Override
    public void saveOrUpdatePositiveCount(String email, int roomNo, int restaurantNo) {
        LocalDate yearMonth = LocalDate.now(); // "2025-07"
        Optional<PositiveRestaurantManageEntity> recordOpt =
                positiveRepository.findBySenderEmailAndRoomNoAndRestaurantNoAndVoteMonth(email, roomNo, restaurantNo, yearMonth);

        if (recordOpt.isPresent()) {
            PositiveRestaurantManageEntity record = recordOpt.get();
            record.setPositive_count(record.getPositive_count() + 1);
            record.setVoteMonth(LocalDate.now());
            positiveRepository.save(record);
        } else {
            PositiveRestaurantManageEntity newRecord = new PositiveRestaurantManageEntity();
            newRecord.setSenderEmail(email);
            newRecord.setRoomNo(roomNo);
            newRecord.setRestaurantNo(restaurantNo);
            newRecord.setVoteMonth(yearMonth);
            newRecord.setPositive_count(1);
            positiveRepository.save(newRecord);
        }
    }
}

