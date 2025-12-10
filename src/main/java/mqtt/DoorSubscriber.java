package mqtt;

import org.eclipse.paho.client.mqttv3.*;
import java.io.InputStream;
import java.util.Properties;

public class DoorSubscriber {
    public static void run() throws Exception {
        Properties props = new Properties();
        try (InputStream input = DoorSubscriber.class.getClassLoader().getResourceAsStream("mqtt.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo mqtt.properties não encontrado no classpath!");
            }
            props.load(input);
        }

        String broker = props.getProperty("mqtt.broker");
        String clientId = props.getProperty("mqtt.clientId") + "-Door";
        String topic = props.getProperty("mqtt.topic.door");

        MqttClient client = new MqttClient(broker, clientId);
        client.connect();

        client.subscribe(topic, (t, msg) -> {
            String command = new String(msg.getPayload());
            System.out.println("Comando recebido: " + command);
            if ("LOCK".equalsIgnoreCase(command)) {
                command = "Porta trancada!";
            } else if ("UNLOCK".equalsIgnoreCase(command)) {
                command = "Porta destrancada!";
            }

            try {
                ResponsePublisher publisher = new ResponsePublisher();
                publisher.publishResponse(command);
                publisher.disconnect();
            } catch (Exception e) {
                System.err.println("Erro ao enviar resposta: " + e.getMessage());
            }
        });

        System.out.println("DoorSubscriber aguardando comandos...");
    }
}
