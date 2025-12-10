package mqtt;

import org.eclipse.paho.client.mqttv3.*;
import java.io.InputStream;
import java.util.Properties;

public class ResponsePublisher {
    private final MqttClient client;
    private final String responseTopic;

    public ResponsePublisher() throws Exception {
        Properties props = new Properties();
        try (InputStream input = ResponsePublisher.class.getClassLoader().getResourceAsStream("mqtt.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo mqtt.properties não encontrado!");
            }
            props.load(input);
        }

        String broker = props.getProperty("mqtt.broker");
        String clientId = props.getProperty("mqtt.clientId") + "-Response";
        this.responseTopic = props.getProperty("mqtt.topic.response");

        this.client = new MqttClient(broker, clientId);
        this.client.connect();
    }

    public void publishResponse(String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(responseTopic, mqttMessage);
            System.out.println("Publicado: " + message);
        } catch (MqttException e) {
            System.err.println("Erro ao publicar resposta: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (client.isConnected()) {
                client.disconnect();
            }
        } catch (MqttException e) {
            System.err.println("Erro ao desconectar: " + e.getMessage());
        }
    }
}
