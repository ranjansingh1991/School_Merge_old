package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 06-08-2017.
 */
@SuppressWarnings("ALL")
public class TeacherSubjectNames {
    private String name;
    private String stream;

    public TeacherSubjectNames(String name, String stream) {
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