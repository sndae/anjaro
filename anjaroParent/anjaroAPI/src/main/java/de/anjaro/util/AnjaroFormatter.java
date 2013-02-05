package de.anjaro.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Simple formatter, which is logging the following information: <br/>
 * date time logLevel [threadGroup].[threadName] sourceClass/loggerName method
 * => message.
 * <p/>
 * Handling otherwise equal to {@link SimpleFormatter}
 * 
 * @author Joachim Pasquali
 * 
 */
public class AnjaroFormatter extends Formatter {

	private final static String format = "{0,date} {0,time}";
	private MessageFormat formatter;
	private final Date date = new Date();
	private final String lineSeparator = System.getProperty("line.separator");
	private final Object args[] = new Object[1];

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(final LogRecord record) {
		final StringBuffer result = new StringBuffer();
		final String threadGroup = Thread.currentThread().getThreadGroup().getName();
		final String threadName = Thread.currentThread().getName();
		if (this.formatter == null) {
			this.formatter = new MessageFormat(format);
		}
		this.date.setTime(record.getMillis());
		this.args[0] = this.date;
		this.formatter.format(this.args, result, null);
		result.append(" ");
		result.append(record.getLevel().getLocalizedName());
		result.append(": [");
		result.append(threadGroup);
		result.append("].[");
		result.append(threadName);
		result.append("] ");
		if (record.getSourceClassName() != null) {
			result.append(record.getSourceClassName());
		} else {
			result.append(record.getLoggerName());
		}
		if (record.getSourceMethodName() != null) {
			result.append(" ");
			result.append(record.getSourceMethodName());
		}
		result.append(" => ");
		final String message = this.formatMessage(record);
		result.append(message);
		result.append(this.lineSeparator);
		if (record.getThrown() != null) {
			try {
				final StringWriter sw = new StringWriter();
				final PrintWriter pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				result.append(sw.toString());
			} catch (final Exception ex) {
			}
		}
		return result.toString();
	}
}
