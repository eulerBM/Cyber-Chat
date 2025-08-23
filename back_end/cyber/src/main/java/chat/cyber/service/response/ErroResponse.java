package chat.cyber.service.response;

public class ErroResponse {

    private String content;
    private int status;

    public ErroResponse(String content, int status) {
        this.content = content;
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public int getStatus() {
        return status;
    }
}
