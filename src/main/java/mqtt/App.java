package mqtt;

public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Smart Home MQTT Application");
		DoorSubscriber.run();
		LightSubscriber.run();
		TempSubscriber.run();
	}
}
