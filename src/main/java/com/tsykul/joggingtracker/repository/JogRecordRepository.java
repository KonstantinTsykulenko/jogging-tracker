package com.tsykul.joggingtracker.repository;

import com.tsykul.joggingtracker.entity.JogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
public interface JogRecordRepository extends JpaRepository<JogRecord, Long>{
    @Query(value = "select r from JogRecord r where user_id = :userId order by date desc")
    public List<JogRecord> findByUserId(@Param("userId") String userId);

    @Query(nativeQuery = true,
            value = "select avg(distance) as avgDistance, avg(duration) as avgDuration, CAST(EXTRACT(YEAR FROM date) AS VARCHAR)|| '-' || CAST(EXTRACT(WEEK FROM date) AS VARCHAR) AS week from jog_record where user_id = :userId group by week")
    public List<Object[]> getWeeklyUserReport(@Param("userId") String userId);

    @Modifying
    @Query(value = "delete JogRecord where user_id = :userId and id = :recordId")
    public void deleteRecord(@Param("userId") String userId, @Param("recordId") Long recordId);
}
