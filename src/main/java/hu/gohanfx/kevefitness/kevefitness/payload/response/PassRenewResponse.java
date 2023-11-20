package hu.gohanfx.kevefitness.kevefitness.payload.response;

import hu.gohanfx.kevefitness.kevefitness.model.PassType;

import java.time.LocalDate;
import java.util.UUID;

public record PassRenewResponse(UUID id, PassType passType, LocalDate startDate, LocalDate endDate) {
}
