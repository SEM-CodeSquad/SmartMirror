package smartMirror.dataModels.applicationModels;

/**
 * @author Pucci on 29/11/2016.
 */
public class Preferences {
    private String name;
    private String value;

    public Preferences(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
