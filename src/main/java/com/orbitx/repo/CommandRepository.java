package com.orbitx.repo;
import com.orbitx.model.CommandRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CommandRepository extends JpaRepository<CommandRecord,Long> { List<CommandRecord> findTop50ByOrderByCreatedAtDesc(); }
