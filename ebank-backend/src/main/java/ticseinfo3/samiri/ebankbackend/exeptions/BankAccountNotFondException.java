package ticseinfo3.samiri.ebankbackend.exeptions;

public class BankAccountNotFondException extends Throwable {
    public BankAccountNotFondException(String message) {
        super(message);
    }
}
