package net.kazed.android.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that Class can be managed by application context.
 * @author Koert Zeilstra
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
//	/**
//	 * @return Interface that this component implements and can be used to automatic wiring.
//	 */
//	Class<? extends Object> value();
	String value();
}
