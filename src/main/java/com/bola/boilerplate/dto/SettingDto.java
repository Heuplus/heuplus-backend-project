package com.bola.boilerplate.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
