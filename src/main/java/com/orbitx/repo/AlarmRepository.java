package com.orbitx.repo;
import com.orbitx.model.AlarmRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AlarmRepository extends JpaRepository<AlarmRecord,Long> { List<AlarmRecord> findAllByOrderByCreatedAtDesc(); }
