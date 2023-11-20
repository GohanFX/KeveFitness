package hu.gohanfx.kevefitness.kevefitness.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Data
@Document("passes")
@NoArgsConstructor
public class Pass {

    @Id
    private UUID id;

    private String name;
    private LocalDate startDate;
    private String emailAddress;
    private LocalDate endDate;
    private PassType passType;
    private boolean currentlyIn;
    private int physicalKey;
    private int usesLeft;


    public Pass(String name, String emailAddress, PassType type, LocalDate startDate) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.startDate = startDate;
        this.emailAddress = emailAddress;
        this.endDate = calculateEndDate(startDate, type);
        this.usesLeft = type.equals(PassType.TENTIME) ? 10 : 0;
        this.passType = type;
        this.currentlyIn = false;
        this.physicalKey = 0;
    }

    private LocalDate calculateEndDate(LocalDate startDate, PassType type) {
        return switch (type) {

            case TENTIME -> startDate.plusWeeks(5);
            default -> startDate.plusDays(30);
        };
    }

    public void decreaseUses() {
        this.usesLeft -= this.usesLeft == 0 ? 0 : 1;
    }



}
