package net.sf.staccatocommons.lang.number.internal;

import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.type.NumberType;
import net.sf.staccatocommons.lang.function.AbstractFunction;
import net.sf.staccatocommons.lang.function.AbstractFunction2;
import net.sf.staccatocommons.lang.number.ImplicitNumberType;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * NumberTypeFunctions, override {@link AbstractFunction2#apply(Object)} so that
 * the resulting function is flipped and it implements
 * {@link ImplicitNumberType} too
 * 
 * @author flbulgarelli
 * 
 */
public abstract class NumberTypeFunction2<A> extends AbstractFunction2<A, A, A> implements
	ImplicitNumberType<A> {

	private final NumberType<A> type;

	/**
	 * Creates a new {@link NumberTypeFunction2}
	 */
	public NumberTypeFunction2(@NonNull NumberType<A> type) {
		this.type = type;
	}

	public final NumberType<A> numberType() {
		return type;
	}

	public Function<A, A> apply(final A arg1) {
		abstract class NumberTypeFunction extends AbstractFunction<A, A> implements
			ImplicitNumberType<A> {
			public NumberType<A> numberType() {
				return NumberTypeFunction2.this.numberType();
			}
		}
		return new NumberTypeFunction() {
			public A apply(A arg) {
				return NumberTypeFunction2.this.apply(arg, arg1);
			}
		};
	}

}