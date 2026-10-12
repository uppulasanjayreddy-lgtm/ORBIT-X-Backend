package com.orbitx.repo;
import com.orbitx.model.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TelemetryRepository extends JpaRepository<Telemetry,Long> { List<Telemetry> findTop100ByOrderByTimestampDesc(); }
