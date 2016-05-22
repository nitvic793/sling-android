package in.sling.models;

/**
 * Created by Rotvic on 5/21/2016.
 */
public class ChatDialogViewModel {
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String lastText;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastText() {
        return lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    private String date;

}
