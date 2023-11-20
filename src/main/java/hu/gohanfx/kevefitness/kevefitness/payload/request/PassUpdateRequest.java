package hu.gohanfx.kevefitness.kevefitness.payload.request;

import hu.gohanfx.kevefitness.kevefitness.model.PassType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PassUpdateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String emailAddress;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private int usesLeft;

    @NotNull
    private PassType passType;

    @NotNull
    private boolean currentlyIn;

    @NotNull
    private int physicalKey;
}
