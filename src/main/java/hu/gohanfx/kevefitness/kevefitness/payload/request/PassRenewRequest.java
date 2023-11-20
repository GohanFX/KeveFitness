package hu.gohanfx.kevefitness.kevefitness.payload.request;

import hu.gohanfx.kevefitness.kevefitness.model.PassType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PassRenewRequest {

    @NotBlank
    private String name;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private PassType passType;

}
