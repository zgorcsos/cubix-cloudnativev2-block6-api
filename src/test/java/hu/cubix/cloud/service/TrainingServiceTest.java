package hu.cubix.cloud.service;

import hu.cubix.cloud.call.api.TrainingApi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

class TrainingServiceTest {

    private static final TrainingApi api = Mockito.mock(TrainingApi.class);
    private static final TrainingService service = new TrainingService(api);

    @Test
    void calculate() {
        int result = service.calculate(1, 1);

        MatcherAssert.assertThat(result, Matchers.is(2));
    }

    @Test
    void createNewMessage() {
        String message = "message";

        service.storeNewMessage(message);

        Mockito.verify(api, Mockito.atLeastOnce()).newMessageArrived(message);
    }

    @Test
    void retrieveRecentMessages() {
        String message = "message";
        Mockito.when(api.getRecentMessages()).thenReturn(Collections.singletonList(message));

        List<String> result = service.retrieveRecentMessages();

        MatcherAssert.assertThat(result, Matchers.contains(message));
    }
}