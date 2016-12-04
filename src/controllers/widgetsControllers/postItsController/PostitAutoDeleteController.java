//package controllers.widgetsControllers.postItsController;
//
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * Created by Axel on 11/27/2016.
// */
//
///*TODO:
//    - Get all post-its X (?)
//    - Get the expiration dates X
//    - Compare date respectively to the current date X
//    - Call delete for each of which have expired X (?)
//*/
//
//public class PostitAutoDeleteController extends PostItsController {
//
//    public void dailyDelete() {  //Call this to initiate the daily check. (should be threaded)
//
//        Date date = new Date();
//        Timer timer = new Timer();
//
//        timer.schedule(new TimerTask() {
//            public void run() {
//                System.out.println("Let's kill some post-its.. " + new Date());
//
//                checkPostIts();
//
//            }
//        }, date, 24 * 60 * 60 * 1000);
//    }
//
//    private void checkPostIts() {
//
//
//        for (int i = 0; i < booleanArray.length; i++) {
//            int postItTimestamp = postItNotes.getMap().get(postItIdList[i]).getTimestamp();
//
//            if (checkAgainstCurrDate(postItTimestamp)) {
//                int postItIndex = postItNotes.getMap().get(postItIdList[i]).getPostItIndex();
//                this.postItManager.deleteGraphicalNote(postItIndex);
//                booleanArray[postItIndex] = false;
//            }
//        }
//    }
//
//    //Example of a expiry date should be "2016-12-01"
//    private boolean checkAgainstCurrDate(int timestamp) { //This should be stored as a string in the first place if we go with dates
//
//        String expiryDate = ((Integer) timestamp).toString(); // Will be substringed and assigned to year, month and day if we go with dates
//
//        Calendar c = Calendar.getInstance();
//
//        // Specify midnight of today
//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        c.set(Calendar.MILLISECOND, 0);
//
//        Date today = c.getTime();
//
//        // This is where/how we assign the post-it expiration date
//        int year = 2016;
//        int month = 10;
//        int dayOfMonth = 29;
//
//        // Assign the values to the calendar
//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.MONTH, month);
//        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//        // Convert that to a Date
//        Date dateSpecified = c.getTime();
//
//        if (dateSpecified.before(today)) {
//            System.out.println("Date specified [" + dateSpecified + "] is before today [" + today + "]");
//            return true;
//        } else {
//            System.out.println("Date specified [" + dateSpecified + "] is NOT before today [" + today + "]");
//            return false;
//        }
//    }
//}
