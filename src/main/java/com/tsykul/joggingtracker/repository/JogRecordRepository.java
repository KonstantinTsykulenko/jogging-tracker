package com.tsykul.joggingtracker.repository;

import com.tsykul.joggingtracker.entity.JogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
public interface JogRecordRepository extends JpaRepository<JogRecord, Long>{
    @Query(value = "select r from JogRecord r where user_id = :userId")
    public List<JogRecord> findByUserId(@Param("userId") String userId);
}
