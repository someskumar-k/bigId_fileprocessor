package com.skk.interview.bigId.dto;

public class Position {
    private final int lineOffset;
    private final int charOffset;

    public Position(int lineOffset, int charOffset) {
        this.lineOffset = lineOffset;
        this.charOffset = charOffset;
    }

    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
    }
}