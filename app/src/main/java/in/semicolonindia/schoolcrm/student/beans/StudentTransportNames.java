package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by Rupesh on 14/08/2017.
 */
@SuppressWarnings("ALL")
public class StudentTransportNames {
    private String transport_id;
    private String sRouteName;
    private String nNumberOfVehicle;
    private String sRoutFare;
    private String sDesp;

    public StudentTransportNames(String transport_id, String sRouteName, String nNumberOfVehicle, String sRoutFare, String sDesp) {
        this. transport_id = transport_id;
        this.sRouteName = sRouteName;
        this.nNumberOfVehicle = nNumberOfVehicle;
        this.sRoutFare = sRoutFare;
        this.sDesp = sDesp;
    }

    public String getTransport_id() {
        return transport_id;
    }

    public String getRouteName() {
        return sRouteName;
    }

    public void setRouteName(String routeName) {
        sRouteName = routeName;
    }

    public String getNumberOfVehicle() {
        return nNumberOfVehicle;
    }

    public void setNumberOfVehicle(String numberOfVehicle) {
        nNumberOfVehicle = numberOfVehicle;
    }

    public String getRoutFare() {
        return sRoutFare;
    }

    public void setRoutFare(String routFare) {
        sRoutFare = routFare;
    }

    public String getDesp() {
        return sDesp;
    }

    public void setDesp(String desp) {
        sDesp = desp;
    }
}