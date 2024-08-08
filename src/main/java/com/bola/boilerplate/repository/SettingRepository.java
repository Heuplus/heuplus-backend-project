package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
/*
   SettingRepository is data access layer for Setting entity
*/
public interface SettingRepository extends JpaRepository<Setting, UUID> {
}
