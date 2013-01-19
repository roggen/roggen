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

package net.sf.roggen.lambda;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.roggen.check.Validate;
import net.sf.roggen.defs.function.Function;
import net.sf.roggen.defs.function.Function2;
import net.sf.roggen.defs.function.Function3;
import net.sf.roggen.defs.predicate.Predicate;
import net.sf.roggen.defs.predicate.Predicate2;
import net.sf.roggen.lambda.internal.Unchecker;
import net.sf.roggen.lang.SoftException;
import net.sf.roggen.lang.function.AbstractFunction;
import net.sf.roggen.lang.function.AbstractFunction2;
import net.sf.roggen.lang.function.AbstractFunction3;
import net.sf.roggen.lang.predicate.AbstractPredicate;
import net.sf.roggen.lang.predicate.AbstractPredicate2;
import net.sf.roggen.restrictions.check.NonNull;
import net.sf.roggen.restrictions.processing.EnforceRestrictions;

import org.apache.commons.proxy.ProxyFactory;
import org.apache.commons.proxy.invoker.NullInvoker;

/**
 * 
 * {@link LambdaFactory}s are objects capable of creating simple
 * {@link Function}s and {@link Predicate}s of different arities in a DSL style,
 * instead of implementing anonymous classes.
 * 
 * @author flbulgarelli
 * 
 */
public final class LambdaFactory {

  private static final Validate<IllegalStateException> STATE = Validate.throwing(IllegalStateException.class);
  private final ProxyFactory proxyFactory;
  private final Handler handler = new Handler();
  private boolean firstStep = false;

  /**
   * Creates a new {@link LambdaFactory}
   */
  public LambdaFactory(@NonNull ProxyFactory proxyFactory) {
    this.proxyFactory = proxyFactory;
  }

  private final class Handler extends NullInvoker {
    private Method method;
    private Object[] args;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      this.method = method;
      this.args = args;
      return super.invoke(proxy, method, args);
    }

    public Method getMethod() {
      STATE.that(firstStep, "Wrong invocation order");
      firstStep = false;
      return method;
    }

    public Object[] getArgs() {
      return args;
    }

    public Object[] getArgsCopy() {
      return Arrays.copyOf(args, args.length);
    }
  };

  /**
   * Stubs a type for creating a lambda. The resulting stub
   * <strong>must</strong> receive one and only one message.
   * 
   * Not all {@link LambdaFactory}s can stub all types - ie, stubbing
   * interfaces, concrete classes or final classes. Their stubbing capabilities
   * depend on the {@link ProxyFactory} passed as constructor argument to this
   * {@link LambdaFactory}
   * 
   * @param <A>
   * @param clazz
   *          the type to stub
   * @return a new stub
   */
  @EnforceRestrictions
  @NonNull
  public <A> A $(@NonNull Class<A> clazz) {
    STATE.that(!firstStep, "Wrong invocation order");
    A stub = (A) proxyFactory.createInvokerProxy(handler, new Class[] { clazz });
    firstStep = true;
    return stub;
  }

  // public <A> A $(@NonNull A object) {
  // STATE.that(!firstStep, "Wrong invocation order");
  // A stub = (A) proxyFactory.createInvokerProxy(handler,
  // object.getClass().getInterfaces());
  // firstStep = true;
  // return stub;
  // }

  /**
   * Answers a {@link Predicate} that when evaluated sends to its argument the
   * message previously sent to the last stubbed type. Refer to the use cases
   * described in {@link Lambda}
   * 
   * @param returnType
   *          meaningless, this argument is simply ignored
   * @return a new {@link AbstractPredicate}
   * @see Lambda
   */
  @NonNull
  public <A> Predicate<A> lambda(boolean returnType) {
    final Method method = handler.getMethod();
    final Object[] args = handler.getArgs();
    return new AbstractPredicate<A>() {
      public boolean eval(Object argument) {
        return invoke(method, argument, args);
      }
    };
  }

  /**
   * Answers a {@link Function} that when applied sends to its argument the
   * message previously sent to the last stubbed type. Refer to the use cases
   * described in {@link Lambda}
   * 
   * @param returnType
   *          meaningless, this argument is simply ignored
   * @return a new {@link Function}
   * @see Lambda
   */
  @NonNull
  public <A, B> Function<A, B> lambda(B returnType) {
    final Method method = handler.getMethod();
    final Object[] args = handler.getArgs();
    return new AbstractFunction<A, B>() {
      public B apply(Object receptor) {
        return invoke(method, receptor, args);
      }
    };
  }

  /**
   * @ee {@link Lambda#lambda2(Object)}
   */
  @NonNull
  public <A, B, C> Function2<A, B, C> lambda2(C returnType) {
    final Method method = handler.getMethod();
    final Object[] args = handler.getArgsCopy();
    args[0] = null;
    return new AbstractFunction2<A, B, C>() {
      public C apply(Object arg0, Object arg1) {
        args[0] = arg1;
        return invoke(method, arg0, args);
      }
    };
  }

  /**
   * @see Lambda#lambda2(boolean)
   */
  @NonNull
  public <A, B> Predicate2<A, B> lambda2(boolean returnType) {
    final Method method = handler.getMethod();
    final Object[] args = handler.getArgsCopy();
    args[0] = null;
    return new AbstractPredicate2<A, B>() {
      public boolean eval(Object arg0, Object arg1) {
        args[0] = arg1;
        return invoke(method, arg0, args);
      }
    };
  }

  /**
   * Answers a {@link Function3} that when applied sends to its first argument
   * the message previously sent to the last stubbed type, passing its second
   * argument as the first message argument, and its third argument to the
   * second message argument.
   * 
   * Refer to the use cases described in {@link Lambda}
   * 
   * @param returnType
   *          meaningless, this argument is simply ignored
   * @return a new {@link Function3}
   * 
   * @see Lambda
   */
  public <A, B, C, D> Function3<A, B, C, D> lambda3(D returnType) {
    final Method method = handler.getMethod();
    final Object[] args = handler.getArgsCopy();
    args[0] = null;
    args[1] = null;
    return new AbstractFunction3<A, B, C, D>() {
      public D apply(Object arg0, Object arg1, Object arg2) {
        args[0] = arg1;
        args[1] = arg2;
        return invoke(method, arg0, args);
      }
    };
  }

  private <T> T invoke(final Method method, Object receptor, Object[] args) {
    try {
      return (T) method.invoke(receptor, args);
    } catch (IllegalAccessException e) {
      throw SoftException.soften(e);
    } catch (InvocationTargetException e) {
      Unchecker.throwUnchecked(e.getCause());
      return null;
    }
  }

}
