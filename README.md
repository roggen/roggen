#What is Staccato-Commons?
 
 Staccato-Commons is a FLOSS project that extends and integrates existing Java frameworks in order to fill their gaps and unificate abstractions, focusing on productivity and maintainability, in a full object oriented way and using functional programming techniques.

#Staccato-Commons Principles

 Staccato-Commons follows some design guidelines, called Staccato-Commons Principles:
 
 * _Java-centric_. Staccato-Commons is written in pure Java, and it does not define nor use configuration files.   
 * _Object Oriented_. Staccato-Commons offers object oriented solutions - or solutions that explode objected orientation as much as Java can offer - and is implemented using the same techniques and design concepts.  
 * _Functional_. Staccato-Commons solutions make use of techniques and concepts taken from functional programming. It encourages immutable objects, pure code - without side effect - messages, laziness, and higher order programming using function-like objects. 
 * _Generic_. Staccato-Commons intesively uses Java Generics 
 * _Rich, fluent interfaces_. Staccato-Commons classes and interfaces  maximize the amount of useful, fluent methods exposed, in order to make client code cleaner and simpler. Complexity is not handled by client. 
 * _DRY_. Staccato-Commons libraries have ease of usage and code reuse as a priority, both for its implementation and the client code. It does not reinvent the wheel defining new interfaces and classes, when they are already there.      
 * _Documented and Contract-First_. Staccato-Commons codebase is fully documented using Javadoc and source annotations, focusing on well defining contracts between the apis and the client code.  
 * _Pragmatic_. Staccato-Commons is not a research project. It offers solutions that work.
   
#Projects 

 These are the projects currently in active development:
 
 * [staccato-commons-restrictions](http://staccatocommons.sourceforge.net/commons-restrictions): a library of annotations that express restrictions and properties of code, in order to augment documentation reuse, maintainability and consistency. Many of those annotations may be processed by to staccato-commons-restriction-instrument tool.  
 * [staccato-commons-defs](http://staccatocommons.sourceforge.net/commons-defs): the core Staccatisimo definitions, a minimal library of interfaces that are deliberately very abstract, with well  defined but generic semantics, in order to augment code reuse. They are the glue of Staccato-Commons    
 * [staccato-commons-lang](http://staccatocommons.sourceforge.net/commons-lang): simple but essential abstractions for the rest of the projects, like Options and Tuples. It contains also abstract and/or concrete implementations of most of the interfaces in staccato-commons-defs, like function objets,  and facilities for implementing equals, hashcode, toString and compareTo in a consistent way, without performance impacts or boilerplate code.    
 * [staccato-commons-util](http://staccatocommons.sourceforge.net/commons-util)
 * [staccato-commons-numbers](http://staccatocommons.sourceforge.net/commons-numbers): implementation of several number types as defined by staccato-commons-def
 * [staccato-commons-check](http://staccatocommons.sourceforge.net/commons-check): a general purpose, abstract validation ecosystem, with concrete classes for checking preconditions and postconditions  
 * [staccato-commons-iterators](http://staccatocommons.sourceforge.net/commons-iterators): a library of generic, reusable   common iterators, and thriters, advanced powerful iterator-like objects that permit lazy iterations impossible before them.       
 * [staccato-commons-collections](http://staccatocommons.sourceforge.net/commons-collections): dozens of class methods for iterables handling, in both a functional and a more imperative style,  and Streams, the "super"-iterable - rich, lazy, and generic enough to handle much more than just collections. 
 * [staccato-commons-control](http://staccatocommons.sourceforge.net/commons-control)  
 * [staccato-commons-io](http://staccatocommons.sourceforge.net/commons-io):  integration between the Staccato libraries and java.io.   
 * [staccato-commons-instrument](http://staccatocommons.sourceforge.net/commons-instrument): an extensible, compile-time, annotation processing tool with class-instrumentation capabilities.  
 * [staccato-commons-restrictions-instrument](http://staccatocommons.sourceforge.net/commons-restrictions-instrument) and [staccato-commons-restrictions-instrument-maven-plugin](http://staccatocommons.sourceforge.net/commons-restrictions-instrument-maven-plugin)  
 * [staccato-commons-lambda](http://staccatocommons.sourceforge.net/commons-lambda) 
 * [staccato-commons-dynamic](http://staccatocommons.sourceforge.net/commons-dynamic) 
 * staccato-commons-testing
 
