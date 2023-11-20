package hu.gohanfx.kevefitness.kevefitness.payload.request;

import hu.gohanfx.kevefitness.kevefitness.model.PassType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PassCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String emailAddress;

    @NotNull
    private PassType passType;
    @NotNull
    private LocalDate startDate;
}
