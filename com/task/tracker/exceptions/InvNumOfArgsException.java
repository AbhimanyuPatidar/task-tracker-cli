/**
 * Custom exception for invalid number of arguments passed to the program.
 * 
 * @author Abhimanyu Patidar
 * 
 */

package com.task.tracker.exceptions;

public class InvNumOfArgsException extends Exception {
    public InvNumOfArgsException(String message) {
        super(message);
    }
}