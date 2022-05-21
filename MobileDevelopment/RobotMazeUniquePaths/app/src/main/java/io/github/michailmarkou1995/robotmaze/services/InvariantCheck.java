package io.github.michailmarkou1995.robotmaze.services;

public interface InvariantCheck {

    public boolean invariant();

    public boolean invUp();

    public boolean invDown();

    public boolean invLeft();

    public boolean invRight();

    public boolean invStatus();
}
