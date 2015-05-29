package com.mdream.gamemanage.common.cache;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class CusSimpleKey implements Serializable {

	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = 1162317261461911092L;

	public static final CusSimpleKey EMPTY = new CusSimpleKey();

	private final Object[] params;
	private final int hashCode;


	/**
	 * Create a new {@link SimpleKey} instance.
	 * @param elements the elements of the key
	 */
	public CusSimpleKey(Object... elements) {
		Assert.notNull(elements, "Elements must not be null");
		this.params = new Object[elements.length];
		System.arraycopy(elements, 0, this.params, 0, elements.length);
		this.hashCode = Arrays.deepHashCode(this.params);
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj || (obj instanceof CusSimpleKey
				&& Arrays.deepEquals(this.params, ((CusSimpleKey) obj).params)));
	}

	@Override
	public final int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		
		return getClass().getSimpleName() + "[" + StringUtils.arrayToCommaDelimitedString(this.params) + "]";
	}
	
	public String getString() {
		
		return  "[" + StringUtils.arrayToCommaDelimitedString(this.params) + "]";
	}
}
