package com.tsykul.joggingtracker.repository;

import com.tsykul.joggingtracker.entity.JogRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
public interface JogRecordRepository extends JpaRepository<JogRecord, Long>{
}
