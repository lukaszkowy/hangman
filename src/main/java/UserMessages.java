public enum UserMessages {
        ENTER("Enter the letter"),
        GOOD("Good you are the best"),
        WRONG("Wrong! Try again"),
        WIN("You win"),
        CHANCES_LEFT("You have %d chances"),
        WORD_TO_GUESS("The word was: %s");

    private String message;

    UserMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
