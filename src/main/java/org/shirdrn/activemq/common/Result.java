package org.shirdrn.activemq.common;

import java.util.List;

public interface Result<M> {

	Status getStatus();
	void setStatus(Status status);
	
	void setMessage(M message);
	M getMessage();
	
	void addException(Exception exception);
	List<Exception> getExceptions();
	
	enum Status {
		SUCCESS,
		FAILURE
	}
}
