package test;

import java.util.Scanner;

public class PostitTest {


    private static String randomColor() {
        String[] names = {"blue", "green", "yellow", "orange", "purple", "pink"};
        return names[(int) (Math.random() * names.length)];
    }

    private static String randomText() {
        String[] names = {"Your friends called! remember to call him back",
                "Dont forget to buy: milk, apple ,banana",
                "Do laundry at 8",
                "Please make me some Cookies",
                "Project due on monday, hurry!",
                "Check out the song Rockabye by Cleanbandit!",
                "Remember to pick up the movie ticket!"};
        return names[(int) (Math.random() * names.length)];
    }

    private static String randomImport() {
        String[] names = {"true", "false"};
        return names[(int) (Math.random() * names.length)];
    }

    private static String randomDate() {
        String[] names = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        return names[(int) (Math.random() * names.length)];
    }

    private static String randomID() {
        String[] names = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        return names[(int) (Math.random() * names.length)];
    }

    /*
    DO NOT DELETE IT IS FOR TESTING PURPOSE
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the id: ");
        String id = scan.nextLine();
        String topic = "dit029/SmartMirror/" + id + "/postit";
        Retrievedata R = new Retrievedata();

        System.out.println("How many post-its: ");
        String p = scan.nextLine();
        int pos = Integer.parseInt(p);

        for (int i = 0; i < pos; i++)
        {
            int finalI = i;
            Thread thread = new Thread() {
                public void run() {
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println(finalI + R.retrieve(topic, String.valueOf(finalI),
                            randomText(), randomColor(), randomImport(), randomDate()));

                }
            };
            thread.start();

        }
    }
}
