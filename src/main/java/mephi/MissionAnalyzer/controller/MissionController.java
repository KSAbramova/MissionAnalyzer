package mephi.MissionAnalyzer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Mission;
import mephi.MissionAnalyzer.service.MissionPersistenceService;
import mephi.MissionAnalyzer.service.MissionProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Миссия загружена"),
            @ApiResponse(responseCode = "400", description = "Файл не выбран"),
            @ApiResponse(responseCode = "500", description = "Ошибка парсинга или сохранения")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMission(
            @Parameter(description = "Файл с данными миссии") 
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл не выбран");
        }

        Path tempFile = null;
        try {
            tempFile = createTempFile(file);
            file.transferTo(tempFile.toFile());

            Mission mission = persistenceService.saveFromFile(tempFile.toFile());

            return ResponseEntity.ok(mission);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка загрузки: " + e.getMessage());
        } finally {
            deleteTempFile(tempFile);
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
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Миссия удалена"),
            @ApiResponse(responseCode = "404", description = "Миссия не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable String id) {
        boolean deleted = persistenceService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получить доступные типы отчетов")
    @GetMapping("/report-types")
    public Collection<String> getReportTypes() {
        return processingService.getAvailableReportTypes();
    }

    @Operation(summary = "Сгенерировать отчет по миссии")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Отчет сгенерирован"),
            @ApiResponse(responseCode = "404", description = "Миссия не найдена")
    })
    @GetMapping(value = "/{id}/report", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateReport(
            @PathVariable String id,
            @Parameter(description = "Тип отчета", schema = @Schema(allowableValues = {"DEFAULT", "DETAILED"}))
            @RequestParam(defaultValue = "DEFAULT") String reportType) {

        return persistenceService.findById(id)
                .map(mission -> {
                    String report = processingService.generateReport(mission, reportType);
                    return ResponseEntity.ok()
                            .contentType(MediaType.TEXT_PLAIN)
                            .body(report);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Тестовый эндпоинт")
    @GetMapping("/test")
    public String test() {
        return "Mission Archive API работает успешно!";
    }

    private Path createTempFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String suffix = ".tmp";
        if (originalFilename != null) {
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex >= 0) {
                suffix = originalFilename.substring(dotIndex);
            }
        }
        return Files.createTempFile("mission_", suffix);
    }

    private void deleteTempFile(Path tempFile) {
        if (tempFile == null) {
            return;
        }
        try {
            Files.deleteIfExists(tempFile);
        } catch (IOException ignored) {
        }
    }
}
