package com.social.it;

public interface WordDistanceCalculator {
    /**
     *  Measures how close the two strings are.
     *  Basically calculates the amount of spelling errors.
     *
     * @param input1 input #1
     * @param input2 input #2
     * @return the distance
     */
    int calculateDistance(CharSequence input1, CharSequence input2);
}
