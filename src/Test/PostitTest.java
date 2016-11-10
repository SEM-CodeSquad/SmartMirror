package Test;

/**
 * Created by Geoffrey on 2016/11/10.
 */

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Scanner;

public class PostitTest {


    public static String randomColor() {
        String[] names = {"blue", "green", "yellow", "orange", "purple", "pink"};
        String color = names[(int) (Math.random() * names.length)];
        return color;
    }

    public static String randomText() {
        String[] names = {"Your friends called! remember to call him back",
                "Dont forget to buy: milk, apple ,banana",
                "Do laundry at 8",
                "Please make me some Cookies",
                "Project due on monday, hurry!",
                "Check out the song Rockabye by Cleanbandit!",
                "Remember to pick up the movie ticket!"};
        String text = names[(int) (Math.random() * names.length)];
        return text;
    }

    public static String randomImport() {
        String[] names = {"true", "false"};
        String important = names[(int) (Math.random() * names.length)];
        return important;
    }

    public static String randomeDate() {
        String[] names = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String date = names[(int) (Math.random() * names.length)];
        return date;
    }

    public static String randomeID() {
        String[] names = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String id = names[(int) (Math.random() * names.length)];
        return id;
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the topic: ");
        String topic = scan.nextLine();
        System.out.println(topic);
        Retrievedata R = new Retrievedata();

        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread() {
                public void run() {
                    R.Retrievedata(topic, "postit", randomText(), randomColor(), randomImport(), randomeDate(), "2");
                }
            };
            thread.start();
            System.out.println("this is the " + i + " times to post");
        }
    }
}

//B7D9BDDB-5E26-47CC-A135-FFA4C191EEF4