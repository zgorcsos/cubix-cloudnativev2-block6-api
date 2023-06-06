package hu.cubix.cloud.api.controller;

import hu.cubix.cloud.api.dto.TrainingRequest;
import hu.cubix.cloud.api.dto.TrainingResponse;
import hu.cubix.cloud.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/training")
@CrossOrigin // for development with OpenAPI
public class TrainingController {

    private static final Logger log = LoggerFactory.getLogger(TrainingController.class);

    private final TrainingService service;

    public TrainingController(TrainingService service) {
        this.service = service;
    }

    @GetMapping(value = "/test",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TrainingResponse test(@RequestParam int leftNumber, @RequestParam int rightNumber) {
        return localCalculation("test", new TrainingRequest("", leftNumber, rightNumber));
    }

    @PostMapping(value = "/local",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TrainingResponse local(@RequestBody TrainingRequest request) {
        return localCalculation("local", request);
    }

    @PostMapping(value = "/store",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TrainingResponse store(@RequestBody TrainingRequest request) {
        logRequest("store", request);
        int result = service.calculate(request.leftNumber(), request.rightNumber());
        service.storeNewMessage(request.message());
        List<String> recentMessages = service.retrieveRecentMessages();
        TrainingResponse response = new TrainingResponse(recentMessages, result);
        logResponse("store", response);
        return response;
    }

    private TrainingResponse localCalculation(String endpointName, TrainingRequest request) {
        logRequest(endpointName, request);
        int result = service.calculate(request.leftNumber(), request.rightNumber());
        List<String> recentMessages = Collections.singletonList(request.message());
        TrainingResponse response = new TrainingResponse(recentMessages, result);
        logResponse(endpointName, response);
        return response;
    }

    private static void logRequest(String endpointName, TrainingRequest request) {
        log.info("Request arrived on /training/{} endpoint with message: {}, leftNumber: {}, rightNumber: {}",
                endpointName, request.message(), request.leftNumber(), request.rightNumber());
    }

    private static void logResponse(String endpointName, TrainingResponse response) {
        log.info("Sending back response on /training/{} endpoint with data: number of recentMessages {}, result: {}",
                endpointName, response.recentMessages().size(), response.result());
    }
}
