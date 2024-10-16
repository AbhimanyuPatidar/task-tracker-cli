/**
 * Custom exception for invalid use of '-' option.
 * 
 * @author Abhimanyu Patidar
 * 
 */

package com.task.tracker.exceptions;

public class InvUseOfOptionException extends Exception {
    public InvUseOfOptionException(String message) {
        super(message);
    }
}
