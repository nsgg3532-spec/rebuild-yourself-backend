package com.rebuildyourself.controller;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryEntryResponse> createOrUpdateEntry(@Valid @RequestBody DiaryEntryRequest request) {
        return ResponseEntity.ok(diaryService.createOrUpdateEntry(request));
    }

    @GetMapping
    public ResponseEntity<List<DiaryEntryResponse>> getAllEntries() {
        return ResponseEntity.ok(diaryService.getAllEntries());
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<DiaryEntryResponse> getEntryByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(diaryService.getEntryByDate(date));
    }

    @GetMapping("/range")
    public ResponseEntity<List<DiaryEntryResponse>> getEntriesBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(diaryService.getEntriesBetween(start, end));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiaryEntryResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(diaryService.searchEntries(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        diaryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
}