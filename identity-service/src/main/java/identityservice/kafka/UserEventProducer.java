package identityservice.kafka;

import identityservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "user.events";

    public void sendUserCreatedEvent(User user) {
        if (user == null) {
            return;
        }
        var message = String.format("User created: %s (%s)", user.getUsername(), user.getEmail());
        kafkaTemplate.send(TOPIC, user.getUsername(), message);
    }
}
