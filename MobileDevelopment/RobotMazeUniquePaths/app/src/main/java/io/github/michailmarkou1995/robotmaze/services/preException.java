package io.github.michailmarkou1995.robotmaze.services;

public class preException extends VDMException {
    @Override
    public String toString() {
        return ("Pre condition is violated! No edge wrapping!");
    }
}