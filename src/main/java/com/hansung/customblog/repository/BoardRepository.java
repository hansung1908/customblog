package com.hansung.customblog.repository;

import com.hansung.customblog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Modifying
    @Query(value = "UPDATE board SET count = count + 1 WHERE id = ?1", nativeQuery = true)
    void countUpById(int id);
}
