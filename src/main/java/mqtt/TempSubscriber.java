package mqtt;

import org.eclipse.paho.client.mqttv3.*;
import java.io.InputStream;
import java.util.Properties;

public class TempSubscriber {
	public static void run() throws Exception {
        Properties props = new Properties();
        try (InputStream input = TempSubscriber.class.getClassLoader().getResourceAsStream("mqtt.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo mqtt.properties não encontrado no classpath!");
            }
            props.load(input);
        }

        String broker = props.getProperty("mqtt.broker");
        String clientId = props.getProperty("mqtt.clientId") + "-Temp";
        String topic = props.getProperty("mqtt.topic.temp");

        MqttClient client = new MqttClient(broker, clientId);
        client.connect();

        client.subscribe(topic, (t, msg) -> {
            String command = new String(msg.getPayload());
            System.out.println("Comando recebido: " + command);
			
			Double temperature = Math.random() * 100;
			String formattedTemp = String.format("%.2f", temperature);

			if (temperature > 30) {
				command = "Alerta: Temperatura alta detectada - " + formattedTemp + "°C";
			} else {
				command = "Temperatura normal: " + formattedTemp + "°C";
			}

			try {
				ResponsePublisher publisher = new ResponsePublisher();
				publisher.publishResponse(command);
				publisher.disconnect();
			} catch (Exception e) {
				System.err.println("Erro ao enviar resposta: " + e.getMessage());
			}
        });

        System.out.println("TempSubscriber aguardando comandos...");
    }
}
