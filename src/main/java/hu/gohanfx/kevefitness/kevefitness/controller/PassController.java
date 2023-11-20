package hu.gohanfx.kevefitness.kevefitness.controller;

import hu.gohanfx.kevefitness.kevefitness.payload.request.PassCreateRequest;
import hu.gohanfx.kevefitness.kevefitness.payload.request.PassRenewRequest;
import hu.gohanfx.kevefitness.kevefitness.payload.request.PassUpdateRequest;
import hu.gohanfx.kevefitness.kevefitness.payload.response.LPassDecreaseResponse;
import hu.gohanfx.kevefitness.kevefitness.payload.response.PassDeleteResponse;
import hu.gohanfx.kevefitness.kevefitness.service.PassService;
import hu.gohanfx.kevefitness.kevefitness.model.PassType;
import hu.gohanfx.kevefitness.kevefitness.payload.response.PassRenewResponse;
import hu.gohanfx.kevefitness.kevefitness.model.Pass;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/pass")
public class PassController {

    private final PassService passService;

    public PassController(PassService passService) {
        this.passService = passService;
    }

    @GetMapping
    public List<Pass> getPasses() {
        return  passService.getPasses();
    }

    @PutMapping
    public Pass createPass(@Valid @RequestBody PassCreateRequest passRequest) {
        return passService.generateUser(passRequest.getName(), passRequest);
    }

    @PostMapping("/{id}")
    public Pass updatePass(@PathVariable UUID id, @Valid @RequestBody PassUpdateRequest request) {
        return passService.updatePass(id, request);
    }

    @PostMapping("/{id}/renew")
    public PassRenewResponse renewPass(@PathVariable UUID id, @RequestBody PassRenewRequest renewRequest) {
        return passService.renewPass(id, renewRequest);
    }

    @PostMapping("/{id}/decrease")
    public LPassDecreaseResponse decreaseResponse(@PathVariable UUID id) {
        return passService.limitedPassUsesModification(id);
    }

    @PostMapping("/{id}/delete")
    public PassDeleteResponse deletePass(@PathVariable UUID id) {
        return passService.deletePass(id);
    }

    @GetMapping("/{id}")
    public Pass getPassById(@PathVariable UUID id) {
        return passService.findPassById(id);
    }

    @GetMapping("/types")
    public List<PassType> passTypes() {
        return Arrays.stream(PassType.values()).toList();
    }

    @GetMapping("/{name}/by-name")
    public Pass getPassByName(@RequestBody String name) {
        return passService.findPassByName(name);
    }


}
