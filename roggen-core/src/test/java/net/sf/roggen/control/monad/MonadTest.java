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


package net.sf.roggen.control.monad;

import static net.sf.roggen.control.monad.Monads.*;
import static net.sf.roggen.numbers.NumberTypes.*;
import static net.sf.roggen.reductions.Reductions.*;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

import net.sf.roggen.collections.Lists;
import net.sf.roggen.collections.Maps;
import net.sf.roggen.control.monad.MonadicFunction;
import net.sf.roggen.control.monad.Monads;
import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.Executable;
import net.sf.roggen.defs.reduction.Accumulator;
import net.sf.roggen.defs.tuple.Tuple2;
import net.sf.roggen.io.IO;
import net.sf.roggen.lang.Compare;
import net.sf.roggen.lang.Option;
import net.sf.roggen.lang.block.Block;
import net.sf.roggen.lang.function.Functions;
import net.sf.roggen.reductions.Reductions;
import net.sf.roggen.util.Strings;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class MonadTest {

  /**
   */
  @Test
  public void flatMapSplitsProcessingPipeline() throws Exception {
    //  concatMap id $ return [4, 5]
    //  flip (>>=) id $ return [4, 5]
    //  return [4, 5] >>= id
    //  return >=> id $ [4, 5] 
    
    //  Cons >=> \x -> x $ [4, 5]
    //  Cons >=> flatMap id $ [4, 5]
    
    assertEquals(Lists.from(4, 5),
        result(Lists.from(4, 5), Monads.flatMap(Functions.<List<Integer>> identity())));
  }
  
  /**
   */
  @Test
  public void appendAddsAnElementToProcessingPipeline() throws Exception {
    assertEquals(Lists.from(4, 5),
        result(Lists.from(4, 5), Monads.flatMap(Functions.<List<Integer>> identity())));
  }
  
  
  /***/
  @Test
  public void klesiliOfMonadicFunctionsAppliedIsEquivalentToChainedBinding() throws Exception {
    Accumulator<Integer, List<Integer>> acc1 = Reductions.<Integer>append().newAccumulator();
    cons(3).map(add(1)).filter(Compare.greaterThan(2)).each(accumulate(acc1)).run();

    assertEquals(
        result(3, Monads.<Integer>cons().then(map(add(1))).then(filter(Compare.greaterThan(2)))), 
        acc1.value());
  }
    
  /***/
  @Test
  public void testname() throws Exception {
    final Collection<Integer> col = new LinkedList<Integer>();
    Monads//
      .cons(4)
      .map(add(5))
      .map(add(1))
      .filter(Compare.greaterThan(2))
      .each(new Block<Integer>() {
        protected void softExec(Integer argument) throws Exception {
          col.add(argument);
        }
      })
      .each(IO.printlnSysout())
      .run();
    Assert.assertEquals(1, col.size());
  }

  /***/
  @Test
  public void testKleisli() throws Exception {
    Monads //
      .from(4, 5, 6)
      .bind(map(add(5)) //
        .then(map(add(1)))
        .then(map(add(90)))
        .then(filter(Compare.greaterThan(2)))
        .then(each(IO.printlnSysout())))
      .run();
  }

  /***/
  @Test
  public void testAsync() throws Exception {
    Monads //
      .cons(4)
      .map(logThread())
      .map(add(1))
      .fork(Executors.newSingleThreadExecutor())
      .map(logThread())
      .run();
    Thread.sleep(1000);
  }

  /***/
  @Test
  public void testIterable() throws Exception {
    Monads //
      .from(4, 5, 6, 9)
      .map(add(1))
      .filter(Compare.greaterThan(6))
      .each(IO.printlnSysout())
      .run();

  }

  protected Applicable<Integer, Integer> logThread() {
    return new Applicable<Integer, Integer>() {
      public Integer apply(Integer arg) {
        System.out.println(Thread.currentThread());
        return arg;
      }
    };
  }

  protected static <A> Executable<A> printStackTrace() {
    return new Executable<A>() {
      public void exec(A argument) {
        try {
          throw new Exception();
        } catch (Exception e) {
          e.printStackTrace(System.out);
        }
      }
    };
  }

  /***/
  @Test
  public void testSkipAfterEffect() throws Exception {
    Accumulator<Integer, Integer> accum = Reductions.sum().newAccumulator();
    Monads.from(10, 20, 3).each(accumulate(accum)).skip(20).run();
    assertEquals(33, (int) accum.value());
  }

  /***/
  @Test
  public void testSkipBeforeEffect() throws Exception {
    Accumulator<Integer, Integer> accum = Reductions.sum().newAccumulator();
    Monads.from(10, 20, 3).skip(20).each(accumulate(accum)).run();
    assertEquals(13, (int) accum.value());
  }

  /***/
  @Test
  public void testAppendBeforeEffect() throws Exception {
    Accumulator<Integer, Integer> accum = Reductions.sum().newAccumulator();
    Monads.from(10, 3).append(Monads.from(10)).each(accumulate(accum)).run();
    assertEquals(23, (int) accum.value());
  }

  /***/
  @Test
  public void fromOption() throws Exception {
    Accumulator<Tuple2<String, Integer>, List<Tuple2<String, Integer>>> accum = Reductions
      .<Tuple2<String, Integer>> append()
      .newAccumulator();

    Monads.from(Option.some("hello")).clone(Strings.length()).each(accumulate(accum)).run();
    assertEquals(5, (int) Maps.from(accum.value()).get("hello"));

  }
  
  /***/
  protected <A, B>  List<B> result(A input, MonadicFunction<A, B> f) {
    Accumulator<B, List<B>> acc1 = Reductions.<B>append().newAccumulator();
    f.then(each(accumulate(acc1))).apply(input).run();
    return acc1.value();
  }

}
