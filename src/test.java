import clientConnection.Client;
import publisher.SmartMirror_test_publisher;
import subscriber.SmartMirror_Subscriber;

public class test
{
    public static void main(String[] args)
    {
        Client client = new Client("tcp://codehigh.ddns.net:1883", "Tester");
        new SmartMirror_Subscriber(client, "test");



    }
}
