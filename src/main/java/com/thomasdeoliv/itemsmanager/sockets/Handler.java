package com.thomasdeoliv.itemsmanager.sockets;

/**
 * Contract for handling messages.
 */
public interface Handler {

	/**
	 * Handles the specified message.
	 *
	 * @param message the message to be handled.
	 */
	void handleMessage(String message);
}