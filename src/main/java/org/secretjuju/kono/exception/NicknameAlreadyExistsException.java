package org.secretjuju.kono.exception;

public class NicknameAlreadyExistsException extends RuntimeException {
	public NicknameAlreadyExistsException(String message) {
		super(message);
	}
}