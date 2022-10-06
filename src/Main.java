import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        System.out.println("Hello world!");

        // Assigning values *******************************************************************************
        int vehiclePropInt[] = {40, 3, 1};
        String vehiclePropStr[] = {"KA2231", "fourWheeler", "12:22", "", "15:00", "17:20"};
        boolean vehiclePropBool[] = {true, true, false, true, true, false};

        // Assigning initialized values to vehicle class properties ***************************************
        vehicleProp vehicleProp = new vehicleProp(vehiclePropInt, vehiclePropStr, vehiclePropBool);

//       Get the Vehicle Number *************************************************************************
//        String vehicleNumber = vehicleProp.getVehicleNumber();
//        System.out.println("Vehicle Number is : " + vehicleNumber);


        // Checks if the floor parking is available or not *************************************************
        boolean isFloorFull = vehicleProp.getFloorParkingDetails();
        if(!isFloorFull){
            System.out.println("The floor parking is available.");
        }
        else{
            System.out.println("The floor parking is not available.");
        }

        // Verifies/gets the vehicle details *****************************************************************
        String[] vehicleDetails = vehicleProp.getVehicleTypeDetails();
        if(vehicleDetails != null){
            System.out.println(
                    "Vehicle Number : " + vehicleDetails[0] + "\n" +
                    "Vehicle Type : " + vehicleDetails[1] + "\n" +
                    "Vehicle Entry Time : " + vehicleDetails[2]);

            if(vehicleDetails[4] != "") {
                System.out.println(
                        "Vehicle Charging Start Time : " + vehicleDetails[4]);
            }
        }
        else{
            System.out.println("Vehicle details not available.");
        }

        // Verifies/gets the vehicle payment details ************************************************************
        assert vehicleDetails != null;
        String[] vehiclePaymentDetails = vehicleProp.getVehiclePaymentDetails(vehicleDetails[0]);
        if(vehiclePaymentDetails != null){
            System.out.println(
                        "Vehicle Payment Mode : " + vehiclePaymentDetails[0] + "\n" +
                        "Vehicle decrement from floor : " + vehiclePaymentDetails[1] + "\n" +
                        "Vehicle Exit Time Payment : " + vehiclePaymentDetails[2]  + "\n" +
                                "Vehicle Exit Time : " + vehiclePaymentDetails[3]);

        }
        else{
            System.out.println("Vehicle details not available.");
        }
    }
}