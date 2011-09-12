package com.wfms.common.web;

import java.io.Serializable;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;

public class PushletServlet implements Serializable {
	private static final long serialVersionUID = 1L;

	static public class SessionTimeoutPush extends EventPullSource {
		@Override
		protected long getSleepTime() {
			return 1000;
		}

		@Override
		protected Event pullEvent() {
			Event event = Event.createDataEvent("/session/timeout");
			event.setField("hw", "hello,world");
			return event;
		}
	}
}