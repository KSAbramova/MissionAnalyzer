package mephi.MissionAnalyzer.controller;

import model.Mission;
import mephi.MissionAnalyzer.service.MissionPersistenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import mephi.MissionAnalyzer.service.MissionProcessingService;

@Controller
@RequestMapping("/missions")
public class MissionWebController {

    private final MissionPersistenceService persistenceService;
    private final MissionProcessingService processingService;

    public MissionWebController(MissionPersistenceService persistenceService, 
                                MissionProcessingService processingService) {
        this.persistenceService = persistenceService;
        this.processingService = processingService;
    }

    @GetMapping
    public String listMissions(
            @RequestParam(required = false) String outcome,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Model model) {

        List<Mission> missions;

        if (outcome != null && !outcome.isBlank()) {
            missions = persistenceService.findByOutcome(outcome);
        } else if (location != null && !location.isBlank()) {
            missions = persistenceService.searchByLocation(location);
        } else if ((startDate != null && !startDate.isBlank()) || (endDate != null && !endDate.isBlank())) {
            LocalDate sDate = startDate != null && !startDate.isBlank() ? LocalDate.parse(startDate) : null;
            LocalDate eDate = endDate != null && !endDate.isBlank() ? LocalDate.parse(endDate) : null;
            missions = persistenceService.findByDateBetween(sDate, eDate);
        } else {
            missions = persistenceService.findAll();
        }

        model.addAttribute("missions", missions);
        model.addAttribute("pageTitle", "Архив Миссий");
        model.addAttribute("selectedOutcome", outcome);
        model.addAttribute("searchLocation", location);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "missions/list";
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("pageTitle", "Загрузка новой миссии");
        return "missions/upload";
    }

    @PostMapping("/upload")
    public String uploadMission(@RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Выберите файл");
            return "redirect:/missions/upload";
        }

        try {
            Path tempFile = Files.createTempFile("mission_", file.getOriginalFilename());
            file.transferTo(tempFile.toFile());

            Mission savedMission = persistenceService.saveFromFile(tempFile.toFile());
            Files.deleteIfExists(tempFile);

            redirectAttributes.addFlashAttribute("success", "Миссия успешно загружена и сохранена!");
            redirectAttributes.addFlashAttribute("mission", savedMission);

            return "redirect:/missions/success";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка загрузки: " + e.getMessage());
            return "redirect:/missions/upload";
        }
    }

    @GetMapping("/success")
    public String showSuccess(Model model) {
        model.addAttribute("pageTitle", "Миссия загружена");
        return "missions/success";
    }

    @GetMapping("/{id}")
    public String viewMission(@PathVariable String id, Model model) {
        return persistenceService.findById(id)
                .map(mission -> {
                    model.addAttribute("mission", mission);
                    model.addAttribute("pageTitle", "Просмотр миссии");
                    return "missions/view";
                })
                .orElse("redirect:/missions");
    }
    
    @GetMapping("/{id}/report")
    public String showReport(@PathVariable String id, 
                             @RequestParam(defaultValue = "DEFAULT") String reportType,
                             Model model) {

        return persistenceService.findById(id)
                .map(mission -> {
                    String reportContent = processingService.generateReport(mission, reportType);

                    model.addAttribute("mission", mission);
                    model.addAttribute("reportType", reportType.toUpperCase());
                    model.addAttribute("reportContent", reportContent);
                    model.addAttribute("pageTitle", "Отчёт по миссии " + id);

                    return "missions/report";
                })
                .orElse("redirect:/missions");
    }
    
    @DeleteMapping("/{id}")
    public String deleteMission(@PathVariable String id, RedirectAttributes redirectAttributes) {
        boolean deleted = persistenceService.deleteById(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Миссия " + id + " успешно удалена");
        } else {
            redirectAttributes.addFlashAttribute("error", "Миссия не найдена");
        }
        return "redirect:/missions";
    }
}