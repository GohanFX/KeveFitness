package hu.gohanfx.kevefitness.kevefitness.payload.response;

import hu.gohanfx.kevefitness.kevefitness.model.PassType;

import java.time.LocalDate;
import java.util.UUID;

public record LPassDecreaseResponse(UUID id, PassType passType,int usesLeft, LocalDate startDate, LocalDate endDate) {
}
