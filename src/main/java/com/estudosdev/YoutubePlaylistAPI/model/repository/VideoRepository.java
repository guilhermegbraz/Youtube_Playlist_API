package com.estudosdev.YoutubePlaylistAPI.model.repository;

import com.estudosdev.YoutubePlaylistAPI.model.entities.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {


    Page<VideoEntity> findAllByFlagExcluidoFalse(Pageable pageable);

    List<VideoEntity> findAllByFlagExcluidoFalseAndCategoriaId(Long categoriaId);

    List<VideoEntity> findByFlagExcluidoFalseAndTituloContainingIgnoreCase(String searchTerm);
    Optional<VideoEntity> findByIdAndFlagExcluidoFalse(Long aLong);
}
