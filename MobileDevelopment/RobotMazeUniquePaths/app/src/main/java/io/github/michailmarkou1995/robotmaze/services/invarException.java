package io.github.michailmarkou1995.robotmaze.services;

import android.widget.Toast;

import io.github.michailmarkou1995.robotmaze.services.VDMException;

public class invarException extends VDMException {
    @Override
    public String toString() {
        return ("Global Invariant is violated! Starts only at x=1, y=1");
    }
}
