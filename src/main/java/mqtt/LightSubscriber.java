package mqtt;

import org.eclipse.paho.client.mqttv3.*;
import java.io.InputStream;
import java.util.Properties;

public class LightSubscriber {
    public static void run() throws Exception {
        Properties props = new Properties();
        try (InputStream input = LightSubscriber.class.getClassLoader().getResourceAsStream("mqtt.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo mqtt.properties não encontrado no classpath!");
            }
            props.load(input);
        }

        String broker = props.getProperty("mqtt.broker");
        String clientId = props.getProperty("mqtt.clientId") + "-Light";
        String topic = props.getProperty("mqtt.topic.light");

        MqttClient client = new MqttClient(broker, clientId);
        client.connect();

        client.subscribe(topic, (t, msg) -> {
            String command = new String(msg.getPayload());
            System.out.println("Comando recebido: " + command);
            if ("ON".equalsIgnoreCase(command)) {
                command = "Lâmpada ligada!";
            } else if ("OFF".equalsIgnoreCase(command)) {
                command = "Lâmpada desligada!";
            }

            try {
                ResponsePublisher publisher = new ResponsePublisher();
                publisher.publishResponse(command);
                publisher.disconnect();
            } catch (Exception e) {
                System.err.println("Erro ao enviar resposta: " + e.getMessage());
            }
        });

        System.out.println("LightSubscriber aguardando comandos...");
    }
}
