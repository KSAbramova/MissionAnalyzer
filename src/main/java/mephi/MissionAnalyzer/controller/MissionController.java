package mephi.MissionAnalyzer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Mission;
import mephi.MissionAnalyzer.service.MissionPersistenceService;
import mephi.MissionAnalyzer.service.MissionProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/missions")
@Tag(name = "Mission API", description = "Основное API для работы с архивом миссий")
public class MissionController {

    private final MissionPersistenceService persistenceService;
    private final MissionProcessingService processingService;

    public MissionController(MissionPersistenceService persistenceService,
                             MissionProcessingService processingService) {
        this.persistenceService = persistenceService;
        this.processingService = processingService;
    }

    @Operation(summary = "Загрузить файл миссии и сохранить в БД", 
               description = "Принимает файл (.json, .yaml, .xml, .txt) и сохраняет миссию")
    @PostMapping("/upload")
    public ResponseEntity<Mission> uploadMission(
            @Parameter(description = "Файл с данными миссии") 
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Path tempFile = Files.createTempFile("mission_", "_" + file.getOriginalFilename());
            file.transferTo(tempFile.toFile());

            Mission mission = persistenceService.saveFromFile(tempFile.toFile());

            Files.deleteIfExists(tempFile);

            return ResponseEntity.ok(mission);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Получить все миссии")
    @GetMapping
    public List<Mission> getAllMissions() {
        return persistenceService.findAll();
    }

    @Operation(summary = "Получить миссию по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Mission> getMissionById(@PathVariable String id) {
        return persistenceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить миссию по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable String id) {
        boolean deleted = persistenceService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Сгенерировать отчет по миссии")
    @GetMapping("/{id}/report")
    public ResponseEntity<String> generateReport(
            @PathVariable String id,
            @RequestParam(defaultValue = "FULL") String reportType) {

        return persistenceService.findById(id)
                .map(mission -> {
                    String report = processingService.generateReport(mission, reportType);
                    return ResponseEntity.ok(report);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Тестовый эндпоинт")
    @GetMapping("/test")
    public String test() {
        return "Mission Archive API работает успешно!";
    }
}