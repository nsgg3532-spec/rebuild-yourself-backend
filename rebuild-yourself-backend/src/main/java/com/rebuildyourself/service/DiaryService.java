package com.rebuildyourself.service;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.entity.*;
import com.rebuildyourself.repository.DiaryEntryRepository;
import com.rebuildyourself.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryEntryRepository diaryEntryRepository;
    private final CurrentUserProvider currentUserProvider;

    public DiaryEntryResponse createOrUpdateEntry(DiaryEntryRequest request) {
        User user = currentUserProvider.getCurrentUser();
        LocalDate date = request.getEntryDate() != null ? request.getEntryDate() : LocalDate.now();

        DiaryEntry entry = diaryEntryRepository.findByUserAndEntryDate(user, date)
                .orElse(DiaryEntry.builder()
                        .user(user)
                        .entryDate(date)
                        .build());

        entry.setMood(request.getMood().toUpperCase());
        entry.setContent(request.getContent());
        entry.setWordCount(countWords(request.getContent()));

        diaryEntryRepository.save(entry);

        return toResponse(entry);
    }

    public List<DiaryEntryResponse> getAllEntries() {
        User user = currentUserProvider.getCurrentUser();
        return diaryEntryRepository.findByUserOrderByEntryDateDesc(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DiaryEntryResponse getEntryByDate(LocalDate date) {
        User user = currentUserProvider.getCurrentUser();
        DiaryEntry entry = diaryEntryRepository.findByUserAndEntryDate(user, date)
                .orElseThrow(() -> new RuntimeException("No entry found for this date"));
        return toResponse(entry);
    }

    public List<DiaryEntryResponse> getEntriesBetween(LocalDate start, LocalDate end) {
        User user = currentUserProvider.getCurrentUser();
        return diaryEntryRepository.findByUserAndEntryDateBetweenOrderByEntryDateDesc(user, start, end)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<DiaryEntryResponse> searchEntries(String keyword) {
        User user = currentUserProvider.getCurrentUser();
        return diaryEntryRepository.findByUserAndContentContainingIgnoreCaseOrderByEntryDateDesc(user, keyword)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deleteEntry(Long id) {
        diaryEntryRepository.deleteById(id);
    }

    private int countWords(String content) {
        if (content == null || content.isBlank()) return 0;
        return content.trim().split("\\s+").length;
    }

    private DiaryEntryResponse toResponse(DiaryEntry entry) {
        return DiaryEntryResponse.builder()
                .id(entry.getId())
                .entryDate(entry.getEntryDate())
                .mood(entry.getMood())
                .content(entry.getContent())
                .wordCount(entry.getWordCount())
                .build();
    }
}