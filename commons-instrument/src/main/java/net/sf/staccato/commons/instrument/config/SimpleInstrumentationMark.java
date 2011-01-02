package net.sf.staccato.commons.instrument.config;

import java.nio.charset.Charset;

import net.sf.staccato.commons.check.annotation.NonNull;

/**
 * @author flbulgarelli
 */
public final class SimpleInstrumentationMark implements InstrumentationMark {

	private String markName;
	private String markValue;

	/**
	 * Creates a new {@link SimpleInstrumentationMark}
	 */
	public SimpleInstrumentationMark(@NonNull String markName, @NonNull String markValue) {
		this.markName = markName;
		this.markValue = markValue;
	}

	/**
	 * @return The version number, as a class file version attribute value
	 */
	public final byte[] getMarkAttributeValue() {
		return markValue.getBytes(Charset.forName("UTF-8"));
	}

	public String getMarkAttributeName() {
		return markName;
	}
}