package com.github.unafraid.editor.ui.util.logging;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * Also @see http://blog.pikodat.com/2015/10/11/frontend-logging-with-javafx/
 * 
 * @author lord_rex
 */
@Plugin(name = "GUILogger", category = "Core", elementType = "appender", printObject = true)
public final class GUILogger extends AbstractAppender {
	private static final long serialVersionUID = 6223410932674438595L;

	private static Consumer<String> ON_MESSAGE;
	private final Queue<String> _messages = new ConcurrentLinkedQueue<>();

	protected GUILogger(String name, Filter filter, Layout<? extends Serializable> layout,
			final boolean ignoreExceptions) {
		super(name, filter, layout, ignoreExceptions);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			if (_messages.isEmpty()) {
				return;
			}

			final Consumer<String> onMessage = ON_MESSAGE;
			if (onMessage == null) {
				return;
			}

			String message;
			while ((message = _messages.poll()) != null) {
				onMessage.accept(message);
			}
		}, 100, 100, TimeUnit.MILLISECONDS);
	}

	@Override
	public void append(LogEvent event) {
		_messages.offer(new String(getLayout().toByteArray(event)));
	}

	public static void onMessage(Consumer<String> onMessage) {
		ON_MESSAGE = onMessage;
	}

	@PluginFactory
	public static GUILogger createAppender(@PluginAttribute("name") String name,
			@PluginElement("Layout") Layout<? extends Serializable> layout,
			@PluginElement("Filter") final Filter filter) {
		if (name == null) {
			LOGGER.error("No name provided for GUILogger!");
			return null;
		}

		if (layout == null) {
			layout = PatternLayout.createDefaultLayout();
		}

		return new GUILogger(name, filter, layout, true);
	}
}