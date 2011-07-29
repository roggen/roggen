/**
 *  Copyright (c) 2011, The Staccato-Commons Team
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

package net.sf.staccatocommons.collections.stream;

import java.io.Serializable;

import net.sf.staccatocommons.collections.restrictions.Projection;
import net.sf.staccatocommons.collections.restrictions.Repeatable;
import net.sf.staccatocommons.defs.ContainsAware;
import net.sf.staccatocommons.defs.Executable;
import net.sf.staccatocommons.defs.SizeAware;
import net.sf.staccatocommons.iterators.thriter.Thriterator;

/**
 * A {@link Stream} is a lazy, rich-interfaced, {@link Iterable}, chained
 * functional-style object that can retrieve and process an arbitrary - and
 * potentially unbound - amount of other objects of a parameterized type. Such
 * objects are called elements, and are generated by another object, called
 * source. Some examples of elements and its source are:
 * <ul>
 * <li>Elements in a collection</li>
 * <li>Files in a directory</li>
 * <li>Characters in a string</li>
 * <li>Lines in a file</li>
 * <li>Rows in a result set</li>
 * <li>Virtually anything that has sense to iterate through</li>
 * </ul>
 * 
 * Streams have the following properties:
 * <ol>
 * <li>Operation oriented: A stream represents a single and eventually complex
 * transformation over a source of objects; <strong>it is not a
 * container</strong></li>
 * <li>Reference Semantics: A stream internal state is meaningless, and Streams
 * do not override Object's {@link #equals(Object)}, {@link #hashCode()} nor
 * {@link #toString()}</li>
 * <li>Non persistent: Streams are not {@link Serializable} Thus, they should
 * not be used as attributes of long-living objects.</li>
 * <li>One-Message: Given a stream instance, only one message can be sent to it,
 * sending more than one message to it has an undefined result, as a
 * consequence, it may be iterated only once. There are three exceptions to this
 * rule, though:
 * <ol>
 * <li>Methods inherited from {@link Object} like {@link #toString()} and
 * {@link #hashCode()}. None of those methods affect the transformation nor the
 * underlying source</li>
 * <li>{@link #isEmpty()}: it may be sent multiple times and grants consistent
 * results as long as no other message except of those at previous item is sent.
 * This message does not affect the transformation, but may modify the
 * underlying source</li>
 * <li>Streams returned by class or instance methods annotated with
 * {@link Repeatable}, like {@link #force()} or {@link Streams#cons(Object)}.
 * Such streams may be reused and receive any message any number of times, and
 * grant consistent results, including repeatable iteration order.</li>
 * </ol>
 * </li>
 * <li>Lazy projections: all of the - many - transformations exposed by streams
 * that are annotated as {@link Projection} are lazy. Such methods do also work
 * with very large o potentially infinte streams.</li>
 * </ol>
 * 
 * Concrete, simple streams, collection handling-oriented, may be instantiated
 * through {@link Streams} class. Other concrete streams are be provided by
 * other staccato-commons libraries.
 * 
 * Aside from these concrete streams, client code may also implement new ones.
 * In order to do that, it <strong>must not</strong> implement this interface
 * directly, but inherit from {@link AbstractStream}, which implements all
 * methods except of {@link #iterator()}
 * 
 * @author fbulgarelli
 * 
 * @param <A>
 *          the type of object the stream is source of
 */
public interface Stream<A> extends //
  Indexed<A>, //
  Appendable<A>, //
  Collectible<A>, //
  ContainsAware<A>, //
  Crossable<A>, //
  Deconstructable<A>, //
  Filterable<A>, //
  Foldable<A>, //
  Interscalable<A>, //
  Iterable<A>, //
  Mappable<A>, //
  MapReduceable<A>, //
  Printable<A>, //
  Reversable<A>, //
  Searchable<A>, //
  SizeAware, //
  Sortable<A>, //
  Testeable<A>, //
  Transformable<A>, //
  Zippeable<A> {

  Thriterator<A> iterator();

  void each(Executable<? super A> block);

}
