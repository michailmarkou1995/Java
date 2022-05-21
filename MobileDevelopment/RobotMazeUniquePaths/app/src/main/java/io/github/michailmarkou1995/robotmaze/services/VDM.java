package io.github.michailmarkou1995.robotmaze.services;

import io.github.michailmarkou1995.robotmaze.invarException;

public class VDM {
    public static void invTest(InvariantCheck i) throws invarException {
        if (!i.invariant()) throw new invarException();
    }

    public static boolean preTest(boolean i) throws preException {
        if (!i) throw new preException();
        else return true;
    }
}
