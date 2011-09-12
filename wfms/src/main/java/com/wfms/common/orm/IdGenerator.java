package com.wfms.common.orm;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IdGenerator implements IdentifierGenerator {

	private static AtomicLong id = new AtomicLong(System.currentTimeMillis());
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		if (object instanceof Persistent) {
			Persistent p = (Persistent) object;
			if (p.getId() != null)
				return p.getId();
		}
		return String.valueOf(id.incrementAndGet());
	}
	
}
