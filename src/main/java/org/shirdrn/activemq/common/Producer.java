package org.shirdrn.activemq.common;

import javax.jms.Message;

public interface Producer<IN, OUT extends Message> extends Processor<IN, OUT> {

	Result<OUT> produce(IN message);
	
}
