package org.shirdrn.activemq.conext;

import org.shirdrn.activemq.common.ActiveMQContext;

public class DefaultActiveMQContext implements ActiveMQContext {

	private final ContextReadable context;
	
	public DefaultActiveMQContext(ContextReadable context) {
		this.context = context;
	}
	
	@Override
	public String get(String key) {
		return context.get(key);
	}

	@Override
	public String get(String key, String defaultValue) {
		return context.get(key, defaultValue);
	}

	@Override
	public byte getByte(String key, byte defaultValue) {
		return context.getByte(key, defaultValue);
	}

	@Override
	public short getShort(String key, short defaultValue) {
		return context.getShort(key, defaultValue);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return context.getInt(key, defaultValue);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return context.getLong(key, defaultValue);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		return context.getFloat(key, defaultValue);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return context.getDouble(key, defaultValue);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return context.getBoolean(key, defaultValue);
	}

	@Override
	public Object getObject(String key, Object defaultValue) {
		return context.getObject(key, defaultValue);
	}

}
