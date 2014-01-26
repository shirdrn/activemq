package org.shirdrn.activemq.producer;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.MapMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;

public class ActiveMQMapProducer extends AbstractSimpleActiveMQProducer<Map<?, ?>> {

	private static final Log LOG = LogFactory.getLog(ActiveMQMapProducer.class);
	
	public ActiveMQMapProducer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void produce(Map<?, ?> message) {
		try {
			MapMessage m = session.createMapMessage();
			Iterator<?> iter = message.entrySet().iterator();
			while(iter.hasNext()) {
				@SuppressWarnings("unchecked")
				Entry<String, ?> entry = (Entry<String, ?>) iter.next();
				String name = entry.getKey();
				Object value = entry.getValue();
				unwrap(m, name, value);
			}
			messageProducer.send(m);
		} catch (JMSException e) {
			LOG.error("Fail to produce message: " + e);
		}
	}

	private void unwrap(MapMessage m, String name, Object value) throws JMSException {
		if(value instanceof Byte) {
			m.setByte(name, (byte) value);
		} if(value instanceof Boolean) {
			m.setBoolean(name, (boolean) value);
		} if(value instanceof Character) {
			m.setChar(name, (char) value);			
		} else if(value instanceof Short) {
			m.setShort(name, (short) value);
		} else if(value instanceof Integer) {
			m.setInt(name, (int) value);
		} else if(value instanceof Long) {
			m.setLong(name, (long) value);
		} else if(value instanceof Float) {
			m.setFloat(name, (float) value);
		} else if(value instanceof Double) {
			m.setDouble(name, (double) value);
		} else if(value instanceof byte[]) {
			m.setBytes(name, (byte[]) value);
		} else if(value instanceof String) {
			m.setString(name, (String) value);
		} else {
			m.setObject(name, value);
		}
	}

}
