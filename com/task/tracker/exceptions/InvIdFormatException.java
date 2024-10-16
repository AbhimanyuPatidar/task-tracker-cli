/**
 * Custom exception for invalid ID format (if id is not a number).
 * 
 * @author Abhimanyu Patidar
 * 
 */

package com.task.tracker.exceptions;

public class InvIdFormatException extends Exception {
    public InvIdFormatException(String message) {
        super(message);
    }
}
