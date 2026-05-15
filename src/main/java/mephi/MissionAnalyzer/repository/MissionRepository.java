package mephi.MissionAnalyzer.repository;

import mephi.MissionAnalyzer.entity.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<MissionEntity, String> {

    List<MissionEntity> findByOutcome(MissionEntity.Outcome outcome);
    
    List<MissionEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<MissionEntity> findByLocationContainingIgnoreCase(String locationPart);
    
    List<MissionEntity> findByCurse_NameContainingIgnoreCase(String curseName);
    
    boolean existsByMissionId(String missionId);
}