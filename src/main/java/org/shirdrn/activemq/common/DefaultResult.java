package org.shirdrn.activemq.common;

import java.util.ArrayList;
import java.util.List;

public class DefaultResult<M> implements Result<M> {

	protected Status status;
	protected List<Exception> exceptions = new ArrayList<Exception>(0);
	protected M message;
	
	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;		
	}

	@Override
	public void addException(Exception exception) {
		exceptions.add(exception);		
	}

	@Override
	public List<Exception> getExceptions() {
		return exceptions;
	}

	@Override
	public void setMessage(M message) {
		this.message = message;		
	}

	@Override
	public M getMessage() {
		return message;
	}

}
