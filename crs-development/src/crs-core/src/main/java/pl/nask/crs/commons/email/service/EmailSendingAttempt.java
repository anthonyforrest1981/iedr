package pl.nask.crs.commons.email.service;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import pl.nask.crs.commons.email.Email;

public class EmailSendingAttempt implements Delayed {

    private long delay;
    private Email email;
    private int attemptsLeft;

    public EmailSendingAttempt(Email email, int attemptLimit) {
        this.delay = 0l;
        this.email = email;
        this.attemptsLeft = attemptLimit;
    }

    public EmailSendingAttempt(long delay, Email email, int attemptLimit) {
        this.delay = delay;
        this.email = email;
        this.attemptsLeft = attemptLimit;
    }

    public void fail() {
        attemptsLeft--;
    }

    public boolean isLimitOver() {
        return (attemptsLeft <= 0);
    }

    public Email getEmail() {
        return email;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long current = System.currentTimeMillis();
        long result = unit.convert(delay - current, TimeUnit.MILLISECONDS);
        return result;
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.valueOf(delay).compareTo(o.getDelay(TimeUnit.MILLISECONDS));
    }

    public void setDelay(long delay, TimeUnit unit) {
        long current = System.currentTimeMillis();
        this.delay = TimeUnit.MILLISECONDS.convert(delay, unit) + current;
    }
}
