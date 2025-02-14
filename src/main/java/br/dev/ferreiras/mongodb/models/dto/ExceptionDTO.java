package br.dev.ferreiras.mongodb.models.dto;

import java.time.Instant;

public record  ExceptionDTO(Instant timestamp, Integer status, String message, String path) {


}
