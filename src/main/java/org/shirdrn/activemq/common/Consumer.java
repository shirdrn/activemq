package org.shirdrn.activemq.common;

import javax.jms.Message;

public interface Consumer<IN extends Message, OUT> extends Processor<IN, OUT> {

	Result<OUT> consume();
	
}
