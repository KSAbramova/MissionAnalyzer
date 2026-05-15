package mephi.MissionAnalyzer.service;

import mephi.MissionAnalyzer.entity.MissionEntity;
import mephi.MissionAnalyzer.mapper.MissionMapper;
import model.Mission;
import mephi.MissionAnalyzer.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mephi.MissionAnalyzer.parser.MissionParseException;
import mephi.MissionAnalyzer.parser.MissionParserChain;

@Service
public class MissionPersistenceService {

    private final MissionRepository repository;
    private final MissionMapper mapper;
    private final MissionParserChain parserChain;

    public MissionPersistenceService(MissionRepository repository,
                                     MissionMapper mapper,
                                     MissionParserChain parserChain) {
        this.repository = repository;
        this.mapper = mapper;
        this.parserChain = parserChain;
    }

    //парсит файл и сохраняет миссию в БД
    @Transactional
    public Mission saveFromFile(File file) throws MissionParseException {
        Mission mission = parserChain.parse(file);
        MissionEntity entity = mapper.toEntity(mission);
        MissionEntity savedEntity = repository.save(entity);
        return mapper.toModel(savedEntity);
    }

    //Получить миссию по ID
    @Transactional(readOnly = true)
    public Optional<Mission> findById(String missionId) {
        return repository.findById(missionId)
                .map(mapper::toModel);
    }

    //Получить все миссии
    @Transactional(readOnly = true)
    public List<Mission> findAll() {
        return repository.findAll().stream()
                .map(mapper::toModel)
                .toList();
    }

    //Поиск по результату миссии
    @Transactional(readOnly = true)
    public List<Mission> findByOutcome(String outcomeStr) {
        try {
            MissionEntity.Outcome outcome = MissionEntity.Outcome.valueOf(outcomeStr.toUpperCase());
            return repository.findByOutcome(outcome).stream()
                    .map(mapper::toModel)
                    .toList();
        } catch (Exception e) {
            return findAll(); // fallback
        }
    }

    //Удалить миссию
    @Transactional
    public boolean deleteById(String missionId) {
        if (repository.existsById(missionId)) {
            repository.deleteById(missionId);
            return true;
        }
        return false;
    }

    //Проверить существование миссии
    public boolean existsById(String missionId) {
        return repository.existsById(missionId);
    }
    
    @Transactional(readOnly = true)
    public List<Mission> searchByLocation(String locationPart) {
        if (locationPart == null || locationPart.trim().isEmpty()) {
            return findAll();
        }
        return repository.findByLocationContainingIgnoreCase(locationPart.trim())
                .stream()
                .map(mapper::toModel)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<Mission> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return findAll();
        }
        return repository.findByDateBetween(startDate, endDate)
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}