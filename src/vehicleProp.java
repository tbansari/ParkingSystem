import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class vehicleProp {

//    private int feeAmt;  // Rs 40 (+30 + 20 ...)
//    private int vehicleParkedOnNo;   // 3
//    private int vehicleParkedOff;  // 1
//
//    private String vehicleNumber;  // KA2231
//    private String vehicleType; // Compact, Large, Handicap, twoWheeler, fourWheeler, truck
//    private String vehicleEntryTime;  // 14:50
//    private String vehicleExitTime;  // 17:30
//    private String chargingPluginTime;  // 15:00
//    private String chargingPlugOutTime; // 17:20
//
//    private boolean isCash;   // true if false then credit payment
//    private boolean isPaymentClear;  // true if paid at exit panel / attendant /customer desk
//    private boolean isFloorParkingFull;  // true if all slots full else false on the same floor
//    private boolean isElectricCar;  // true is electric card
//    private boolean isChargingRequired; // true if electric car needs to be charged
//    private boolean isFreeParkingSpot;  // true if in free parking zone


    private int[] vehiclePropIntVal;
    private String[] vehiclePropStrVal;
    private boolean[] vehiclePropBoolVal;

    // Constructor to assign basic values
    public vehicleProp(int[] vehiclePropInt, String[] vehiclePropStr, boolean[] vehiclePropBool) {
        this.vehiclePropIntVal = vehiclePropInt;
        this.vehiclePropStrVal = vehiclePropStr;
        this.vehiclePropBoolVal = vehiclePropBool;
    }

    // Method to get the vehicle number
    public String getVehicleNumber(){
        try{
            return this.vehiclePropStrVal[0];
        }
            catch(Exception ex){
            return "";
        }
    }

    // Method to check if the floor parking is full or not
    public boolean getFloorParkingDetails(){
        try{
            // Checks if floor parking is available or not and if the vehicle parked vehicle number has reached to limit or not
            // if yes
            if(this.vehiclePropIntVal[1] <= 15){
                this.vehiclePropBoolVal[2] = false;
            }
            else{
                this.vehiclePropBoolVal[2] = true;
            }
            return this.vehiclePropBoolVal[2];
        }
        catch(Exception ex){
            return false;
        }
    }

    public String[] commonVehicleDetails(String[] temp){
        try{
            temp = new String[]{
                    this.vehiclePropStrVal[0], //vehicleNumber
                    this.vehiclePropStrVal[1], //vehicleType
                    this.vehiclePropStrVal[2], //vehicleEntryTime
                    this.vehiclePropStrVal[3], //vehicleExitTime
                    this.vehiclePropStrVal[4] != "" ? this.vehiclePropStrVal[4] : "", //chargingPluginTime
                    this.vehiclePropStrVal[5] != "" ? this.vehiclePropStrVal[5] : ""//chargingPlugOutTime
            };

            return temp;
        }
        catch(Exception ex){
            return new String[]{""};
        }
    }

    // Method to get the type of vehicle details
    public String[] getVehicleTypeDetails(){
        try{
            String[] temp = new String[0];
            // If the vehicle type is fourWheeler
            if(this.vehiclePropStrVal[1] == "fourWheeler"){

                // If the vehicle modal is electric
                if (this.vehiclePropBoolVal[3]) {

                    // If the vehicle needs charging
                    temp = this.commonVehicleDetails(temp);
                }
                // If the vehicle is not electric
                else{
                    temp = this.commonVehicleDetails(temp);
                }
            }
            else {
                temp = this.commonVehicleDetails(temp);

            }
            // Increment the value of vehicle on getting new vehicle details at entry
            this.vehiclePropIntVal[1] = this.vehiclePropIntVal[1] + 1;
            return temp;

        }
        catch(Exception ex){
            return new String[]{""};
        }
    }

    // Reusable Code to get the payment details
    private String[] commonPaymentDetails(String[] temp) throws ParseException {
        try{
            temp = new String[]{
                    // If yes then marks the exit time, checks payment mode(cash/credit).
    //                            String.valueOf(this.vehiclePropBoolVal[0]),
                    this.vehiclePropBoolVal[5] == true ? "Free Spot Parking" : (this.vehiclePropBoolVal[0] == true ? "Cash Payment" : "Credit Payment"),

                    // Decrements the vehicle count by 1
                    String.valueOf(this.vehiclePropIntVal[1] = this.vehiclePropIntVal[1] - 1),

                    // Exit time payment
                    String.valueOf(this.getExitTimeCalculated(this.vehiclePropStrVal[3])),

                    // Marks the exit time
                    String.valueOf(this.getExitTime(this.vehiclePropStrVal[3]))
            };
            return temp;
        }
        catch(Exception ex){
            return new String[]{""};
        }
    }

    // Method to get the payment details of vehicle
    public String[] getVehiclePaymentDetails(String vehicleNumber) throws ParseException {
        try{
            String[] temp = new String[0];

            // If vehicle number exists
            if(vehicleNumber != ""){

                // Checks if exit time is available or not. if not then car is still parked
                if(this.vehiclePropStrVal[3] == ""){

                    // else checks the details for payment that isPaymentClear
                    temp = this.commonPaymentDetails(temp);
                }
            }
            return temp;
        }
        catch(Exception ex){
            return new String[]{""};
        }
    }

    // Method to get the exit time
    private String getExitTime(String exitTime) throws ParseException {
        try{
            // Checks of the exit time is available or not
            if(exitTime == "") {
                LocalTime time = LocalTime.now();
                exitTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
            }

            return exitTime;
        }
            catch(Exception ex){
            return "";
        }
    }

    // Method to calculate the exit time to initialize the payment
    private long getExitTimeCalculated(String exitTime) throws ParseException {
        try {
            String getEntryTime = this.vehiclePropStrVal[2];

            // Creating a SimpleDateFormat object
            // to parse time in the format HH:MM:SS
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("HH:mm");

            // Parsing the Time Period
            Date date1 = simpleDateFormat.parse(getEntryTime);
            Date date2 = simpleDateFormat.parse(this.getExitTime(exitTime));

            // Calculating the difference in milliseconds
            long diffSeconds = Math.abs(date2.getTime() - date1.getTime());

            // Calculating the difference in Hours
            long diffHours = (diffSeconds / (60 * 60 * 1000)) % 24;

            // Calculating the difference in Minutes
            long diffMinutes = (diffSeconds / (60 * 1000)) % 60;

            // Calls the method to get the calculated amt based on minutes/hours difference
            int calculatedPayment = this.getExitPaymentCalculated(diffMinutes, diffHours);
            return calculatedPayment;
        }
        catch(Exception ex){
            return 0;
        }
    }

    // Method to calculate the payment based on time
    private int getExitPaymentCalculated(long diffMinutes, long diffHours){
        try {
            int amt = 0;

            // Calculation for 1 hour payment
            if (diffHours < 1 && diffMinutes == 0) {
                amt = 40;
            }
            // Calculation for 2 hours payment
            else if (diffHours > 1 && diffMinutes < 60 || diffHours < 2 && diffMinutes > 60) {
                amt = 40 + 30;
            }
            // Calculation for every hour more than 2 hours
            else if (diffHours > 2) {
                amt = (int) (diffHours * 10);
            }

            return amt;
        }
        catch(Exception ex){
            return 0;
        }
    }

}
