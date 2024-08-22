package com.hansung.customblog.repository;

import com.hansung.customblog.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
