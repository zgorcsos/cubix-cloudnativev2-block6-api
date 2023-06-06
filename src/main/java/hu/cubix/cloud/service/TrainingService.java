package hu.cubix.cloud.service;

import hu.cubix.cloud.call.api.TrainingApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainingService {

    private static final Logger log = LoggerFactory.getLogger(TrainingService.class);

    private final TrainingApi api;

    public TrainingService(TrainingApi api) {
        this.api = api;
    }

    public int calculate(int leftNumber, int rightNumber) {
        log.info("Calculating: {} + {}", leftNumber, rightNumber);
        return leftNumber + rightNumber;
    }

    public void storeNewMessage(String message) {
        log.info("Creating new message on API: {}", message);
        api.newMessageArrived(message);
    }

    public List<String> retrieveRecentMessages() {
        log.info("Retrieving recent recentMessages from API");
        return api.getRecentMessages();
    }
}
