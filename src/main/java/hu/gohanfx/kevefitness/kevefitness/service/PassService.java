package hu.gohanfx.kevefitness.kevefitness.service;

import hu.gohanfx.kevefitness.kevefitness.exception.PassNotFoundException;
import hu.gohanfx.kevefitness.kevefitness.model.PassType;
import hu.gohanfx.kevefitness.kevefitness.payload.request.PassCreateRequest;
import hu.gohanfx.kevefitness.kevefitness.payload.request.PassRenewRequest;
import hu.gohanfx.kevefitness.kevefitness.payload.request.PassUpdateRequest;
import hu.gohanfx.kevefitness.kevefitness.payload.response.LPassDecreaseResponse;
import hu.gohanfx.kevefitness.kevefitness.payload.response.PassDeleteResponse;
import hu.gohanfx.kevefitness.kevefitness.payload.response.PassRenewResponse;

import hu.gohanfx.kevefitness.kevefitness.model.Pass;
import hu.gohanfx.kevefitness.kevefitness.repository.PassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PassService {

    private final PassRepository repository;
    @Autowired
    private final JavaMailSender sender;
    public PassService(PassRepository repository, JavaMailSender sender) {
        this.repository = repository;
        this.sender = sender;
    }
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void checkPasses() {
        System.out.println(LocalDate.now());
        for (Pass pass: repository.findAll()) {
            if(pass.getEndDate().minusDays(3).equals(LocalDate.now())) {
                System.out.println("lol");
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("noreply@kevefitness.hu");
                message.setTo(pass.getEmailAddress());
                message.setText("A bérleted 3 napon belül lejár!");


                sender.send(message);
            }
        }
    }

    public List<Pass> getPasses() {

        return this.repository.findAll();
    }


    public Pass generateUser(String name, PassCreateRequest passCreateRequest) {
        return this.repository.save(new Pass(name, passCreateRequest.getEmailAddress(), passCreateRequest.getPassType(),passCreateRequest.getStartDate()));
    }

    public LPassDecreaseResponse limitedPassUsesModification(UUID uuid) {
        final Pass pass = this.repository.findById(uuid).orElseThrow(PassNotFoundException::new);
        pass.decreaseUses();
        this.repository.save(pass);

        return new LPassDecreaseResponse(pass.getId(), pass.getPassType(), pass.getUsesLeft(), pass.getStartDate(), pass.getEndDate());
    }

    public Pass findPassByName(String name) {
        return this.repository.findByName(name).orElseThrow(PassNotFoundException::new);
    }

    public Pass findPassById(UUID id) {
        return this.repository.findById(id).orElseThrow(PassNotFoundException::new);
    }

    public Pass updatePass(UUID id, PassUpdateRequest request) {
        final Pass current = findPassById(id);
        current.setName(request.getName());
        current.setPassType(request.getPassType());
        current.setStartDate(request.getStartDate());
        current.setEndDate(request.getEndDate());
        current.setPhysicalKey(request.getPhysicalKey());
        return this.repository.save(current);
    }

    public PassRenewResponse renewPass(UUID id, PassRenewRequest request) {
        final Pass pass = findPassById(id);
        pass.setPassType(request.getPassType());
        pass.setStartDate(request.getStartDate());
        pass.setEndDate(calculateEndDate(pass.getStartDate(), request.getPassType()));
        if(pass.getPassType().equals(PassType.TENTIME)) {
            pass.setUsesLeft(10);
        }
        this.repository.save(pass);
        return new PassRenewResponse(id, request.getPassType(), pass.getStartDate(), pass.getEndDate());
    }
    public PassDeleteResponse deletePass(UUID id) {
        final Pass pass = findPassById(id);
        String deletedName = pass.getName();
        this.repository.delete(pass);
        return new PassDeleteResponse(id, deletedName);
    }




    private LocalDate calculateEndDate(LocalDate date, PassType type) {
        return switch (type) {
            case TENTIME -> date.plusWeeks(5);
            default -> date.plusDays(30);
        };
    }
}
