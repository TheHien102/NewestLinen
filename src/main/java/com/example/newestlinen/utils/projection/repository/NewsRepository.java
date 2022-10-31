package com.example.newestlinen.utils.projection.repository;

import com.example.newestlinen.storage.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository  extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
}
