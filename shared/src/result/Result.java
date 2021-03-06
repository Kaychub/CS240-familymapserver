package result;

/**
 * The base result class.
 */
public abstract class Result {
    /**
     * The message explaining why the API failed or succeeded.
     */
    private String message;
    /**
     * Whether the API was successful.
     */
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
