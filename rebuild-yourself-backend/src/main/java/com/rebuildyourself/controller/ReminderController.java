package com.rebuildyourself.controller;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.service.ReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping
    public ResponseEntity<ReminderResponse> createReminder(@Valid @RequestBody CreateReminderRequest request) {
        return ResponseEntity.ok(reminderService.createReminder(request));
    }

    @GetMapping
    public ResponseEntity<List<ReminderResponse>> getMyReminders() {
        return ResponseEntity.ok(reminderService.getMyReminders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderResponse> updateReminder(@PathVariable Long id,
                                                           @Valid @RequestBody UpdateReminderRequest request) {
        return ResponseEntity.ok(reminderService.updateReminder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}