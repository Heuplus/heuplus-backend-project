package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Setting;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/*
   SettingRepository is data access layer for Setting entity
*/
public interface SettingRepository extends JpaRepository<Setting, UUID> {}
