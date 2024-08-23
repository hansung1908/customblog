package com.hansung.customblog.repository;

import com.hansung.customblog.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Integer> {

    Optional<File> findByBoardId(int id);
}
