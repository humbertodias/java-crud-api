package com.tqdev.crudapi.spatial;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.impl.CustomCondition;
import org.jooq.impl.DSL;

public class IsSimple extends CustomCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final Field<?> field;

	IsSimple(Field<?> field) {
		super();
		this.field = field;
	}

	@Override
	public void accept(Context<?> context) {
		context.visit(delegate(context.configuration()));
	}

	private QueryPart delegate(Configuration configuration) {
		switch (configuration.dialect().family().toString()) {
		case "MYSQL":
		case "POSTGRES":
			return DSL.field("ST_IsSimple({0})", Boolean.class, field);
		case "SQLSERVER":
			return DSL.field("{0}.STIsSimple()", Boolean.class, field);
		default:
			throw new UnsupportedOperationException("Dialect not supported");
		}
	}
}
