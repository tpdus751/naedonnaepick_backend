package com.naedonnaepick.backend.chat.service;

import com.naedonnaepick.backend.chat.dao.ChatDAO;
import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import com.naedonnaepick.backend.chat.db.entity.ReportEntity;
import com.naedonnaepick.backend.chat.dto.TopVotedRestaurantDTO;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import com.naedonnaepick.backend.chat.websocket.repository.PositiveRestaurantManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatDAO chatDAO;
    private PositiveRestaurantManageRepository positiveRepo;

    @Autowired
    public ChatServiceImpl(ChatDAO chatDao, PositiveRestaurantManageRepository positiveRepo) {
        this.chatDAO = chatDao;
        this.positiveRepo = positiveRepo;
    }

    @Override
    public List<ChatroomEntity> findAllChatrooms() {

        return chatDAO.selectAllChatRooms();
    }

    @Override
    public List<ChatMessage> findAllChats(int roomNo) {
        return chatDAO.selectAllChats(roomNo);
    }

    @Override
    public void submitReport(ReportEntity report) {
        chatDAO.insertReport(report);
    }

    @Override
    public List<TopVotedRestaurantDTO> getTopVotedRestaurants(int roomNo) {
        String previousMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")); // 지난달 기준
        Pageable top5 = PageRequest.of(0, 5);  // 첫 페이지에서 5개만
        List<Object[]> results = positiveRepo.findTopVotedRestaurants(roomNo, previousMonth, top5);

        return results.stream()
                .map(r -> new TopVotedRestaurantDTO((String) r[0], (String) r[1], ((Number) r[2]).intValue()))
                .collect(Collectors.toList());
    }
}
