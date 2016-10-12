import clientConnection.Client;
import subscriber.SmartMirror_Subscriber;

public class test
{
    public static void main(String[] args)
    {
        Client client = new Client("tcp://codehigh.ddns.net:1883", "Tester");
        SmartMirror_Subscriber sms = new SmartMirror_Subscriber(client, "test");

    }
}
