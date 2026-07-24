package com.rebuildyourself.service;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.entity.*;
import com.rebuildyourself.repository.ReminderRepository;
import com.rebuildyourself.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final CurrentUserProvider currentUserProvider;

    public ReminderResponse createReminder(CreateReminderRequest request) {
        User user = currentUserProvider.getCurrentUser();

        Reminder reminder = Reminder.builder()
                .user(user)
                .title(request.getTitle())
                .message(request.getMessage())
                .reminderTime(request.getReminderTime())
                .active(true)
                .build();

        reminderRepository.save(reminder);

        return toResponse(reminder);
    }

    public List<ReminderResponse> getMyReminders() {
        User user = currentUserProvider.getCurrentUser();
        return reminderRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ReminderResponse updateReminder(Long id, UpdateReminderRequest request) {
        User user = currentUserProvider.getCurrentUser();

        Reminder reminder = reminderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        reminder.setTitle(request.getTitle());
        reminder.setMessage(request.getMessage());
        reminder.setReminderTime(request.getReminderTime());
        reminder.setActive(request.isActive());

        reminderRepository.save(reminder);

        return toResponse(reminder);
    }

    public void deleteReminder(Long id) {
        User user = currentUserProvider.getCurrentUser();
        Reminder reminder = reminderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));
        reminderRepository.delete(reminder);
    }

    private ReminderResponse toResponse(Reminder reminder) {
        return ReminderResponse.builder()
                .id(reminder.getId())
                .title(reminder.getTitle())
                .message(reminder.getMessage())
                .reminderTime(reminder.getReminderTime())
                .active(reminder.isActive())
                .build();
    }
}