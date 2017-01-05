// MusicGenerator.aidl
package com.example.ridhirustagi.cs478KeyCommon;

// Declare any non-default types here with import statements

interface MusicGenerator {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   // void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
           // double aDouble, String aString);
    void getPlaySong(String clipNum);
     void getPauseSong();
     void getStopSong();
     void getResumeSong();

}
