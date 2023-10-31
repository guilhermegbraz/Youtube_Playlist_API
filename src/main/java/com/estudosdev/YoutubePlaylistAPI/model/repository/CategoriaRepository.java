package com.estudosdev.YoutubePlaylistAPI.model.repository;

import com.estudosdev.YoutubePlaylistAPI.model.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {


    List<CategoriaEntity> findAllByFlagExcluidoFalse();

    Optional<CategoriaEntity> findByIdAndFlagExcluidoFalse(Long aLong);
}
