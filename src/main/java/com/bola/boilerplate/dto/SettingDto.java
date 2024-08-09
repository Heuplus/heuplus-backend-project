package com.bola.boilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
/*
   Dto for setting details
*/
public class SettingDto {
    private UUID settingId;
    private String key;
    private String value;
}
