package com.example.moviesystem.repository;

import com.example.moviesystem.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // 根据标题模糊查询
    List<Movie> findByTitleContaining(String title);
}