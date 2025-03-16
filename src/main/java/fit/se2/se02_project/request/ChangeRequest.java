package fit.se2.se02_project.request;

public class ChangeRequest {
    private String currentPass;
    private String newPass;
    private String confirmNewPass;

    public ChangeRequest() {
    }

    public ChangeRequest(String currentPass, String newPass, String confirmNewPass) {
        this.currentPass = currentPass;
        this.newPass = newPass;
        this.confirmNewPass = confirmNewPass;
    }

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmNewPass() {
        return confirmNewPass;
    }

    public void setConfirmNewPass(String confirmNewPass) {
        this.confirmNewPass = confirmNewPass;
    }
}
