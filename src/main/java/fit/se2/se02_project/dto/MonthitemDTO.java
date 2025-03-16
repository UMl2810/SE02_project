package fit.se2.se02_project.dto;

public class MonthitemDTO {
    private int value;
    private String text;

    public MonthitemDTO() {
    }

    public MonthitemDTO(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
