/**
 * Custom exception for invalid command passed to the application.
 * 
 * @author Abhimanyu Patidar
 * 
 */

package com.task.tracker.exceptions;

public class InvCmdPassedException extends Exception {
    public InvCmdPassedException(String message) {
        super(message);
    }
}
