package com.bola.boilerplate.repository;

import com.bola.boilerplate.dto.SettingDto;
import com.bola.boilerplate.models.Setting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bola.boilerplate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
   SettingRepository is data access layer for Setting entity
*/
public interface SettingRepository extends JpaRepository<Setting, UUID> {
    /*
   Implementation blueprint for getting a setting's details without relations
  */
    @Query("SELECT new com.bola.boilerplate.dto.SettingDto(id, key, value) FROM Setting WHERE id = :settingId AND user.id = :userId")
    Optional<SettingDto> findSettingDtoByIdAndUser(@Param("settingId") UUID settingId, @Param("userId") UUID userId);

    /*
   Implementation blueprint for getting list of setting for a user without relations
  */
    @Query("SELECT new com.bola.boilerplate.dto.SettingDto(id, key, value) FROM Setting WHERE user.id = :userId")
    List<SettingDto> findSettingsDtoByUser(@Param("userId") UUID userId);
}
