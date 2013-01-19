/**
 *  Copyright (c) 2012, The Roggen Team
 *  Copyright (c) 2010-2012, The StaccatoCommons Team
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 */

package net.sf.roggen.lang.predicate;

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.Evaluable;
import net.sf.roggen.defs.Executable;
import net.sf.roggen.defs.predicate.Predicate;
import net.sf.roggen.lang.SoftException;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * <p>
 * A {@link AbstractPredicate} is an abstract {@link Evaluable}.
 * </p>
 * <p>
 * Predicates in addition understand the basic boolean logic messages
 * {@link #not()}, {@link #and(Evaluable)} and {@link #or(Evaluable)} that
 * perform those operations on evaluation result.
 * </p>
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 *          the type of argument to evaluate
 */
public abstract class AbstractPredicate<A> implements Predicate<A> {

  @Override
  public abstract boolean eval(@NonNull A argument);

  public Boolean apply(A arg) {
    return eval(arg);
  }

  /**
   * @return a {@link AbstractPredicate} that negates this
   *         {@link AbstractPredicate}'s result. Non Null.
   */
  public Predicate<A> not() {
    final class Not extends AbstractPredicate<A> {
      public boolean eval(A argument) {
        return !AbstractPredicate.this.eval(argument);
      }

      @Override
      public AbstractPredicate<A> not() {
        return AbstractPredicate.this;
      }
    }
    return new Not();
  }

  /**
   * Returns a predicate that, performs a short-circuit logical-or between this
   * {@link AbstractPredicate}'s {@link #eval(Object)} and other
   * 
   * @param other
   *          another {@link Evaluable}. Non null.
   * @return A new predicate that performs the short circuited or between this
   *         and other when evaluated. Non Null
   */
  public Predicate<A> or(@NonNull final Evaluable<? super A> other) {
    final class Or extends AbstractPredicate<A> {
      public boolean eval(A argument) {
        return AbstractPredicate.this.eval(argument) || other.eval(argument);
      }
    }
    return new Or();
  }

  /**
   * Returns a predicate that performs a short-circuit logical-and between this
   * {@link AbstractPredicate}'s {@link #eval(Object)} and other
   * 
   * @param other
   *          another {@link Evaluable}. Non null.
   * @return A new predicate that performs the short circuited logical-and
   *         between this and other when evaluated. Non Null
   */
  public Predicate<A> and(@NonNull final Evaluable<? super A> other) {
    final class And extends AbstractPredicate<A> {
      public boolean eval(A argument) {
        return AbstractPredicate.this.eval(argument) && other.eval(argument);
      }
    }
    return new And();
  }

  public final Predicate<A> andNotNull() {
    return Predicates.<A> notNull().and(this);
  }

  public final Predicate<A> orNull() {
    return Predicates.<A> null_().or(this);
  }

  public <B> Predicate<B> of(@NonNull final Applicable<? super B, ? extends A> other) {
    return new AbstractPredicate<B>() {
      public boolean eval(B argument) {
        return AbstractPredicate.this.eval(other.apply(argument));
      }
    };
  }

  public Predicate<A> withEffectOnFalse(final Executable<A> executable) {
    return new AbstractPredicate<A>() {
      public boolean eval(A argument) {
        boolean result = AbstractPredicate.this.eval(argument);
        if (result)
          executable.exec(argument);
        return result;
      }
    };
  }

  public Predicate<A> withEffectOnTrue(final Executable<A> executable) {
    return new AbstractPredicate<A>() {
      public boolean eval(A argument) {
        boolean result = AbstractPredicate.this.eval(argument);
        if (!result)
          executable.exec(argument);
        return result;
      }
    };
  }

  // public Predicate<A> withEffect(final Executable<A> executable) {
  // return new AbstractPredicate<A>() {
  // public boolean eval(A argument) {
  // executable.exec(argument);
  // return AbstractPredicate.this.eval(argument);
  // }
  // };
  // }

  public String toString() {
    return "Predicate";
  }

  /**
   * {@link AbstractPredicate} that handles exceptions by softening them using
   * {@link SoftException#soften(Throwable)}
   * 
   * @author flbulgarelli
   * 
   * @param <A>
   *          predicate argument type
   */
  public abstract static class Soft<A> extends AbstractPredicate<A> {

    public final boolean eval(A arg) {
      try {
        return softEval(arg);
      } catch (Throwable e) {
        throw SoftException.soften(e);
      }
    }

    /**
     * Evaluates this predicate, potentially throwing a checked exception
     * 
     * @see Predicate#eval(Object)
     */
    protected abstract boolean softEval(A arg) throws Throwable;
  }

}
