package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by Rupesh on 14/08/2017.
 */
@SuppressWarnings("ALL")
public class SubjectNames {
    private String name;
    private String stream;

    public SubjectNames(String name, String stream) {
        this.name = name;
        this.stream = stream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String speciality) {
        this.stream = speciality;
    }
}