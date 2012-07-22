#About Staccatissimo-Restrictions
 
 Staccatissimo-Restrictions is the core annotations library of the [StaccatoCommons project](http://staccatocommons.sourceforge.net). It contains annotations that express restrictions, that can be used in the form of preconditions, postconditions or invariants over objects and messages, focused on documentation reuse. 

##Introduction

  Staccatissimo-Restrictions promotes reuse of common code properties expressed in the form of constraints, like NotNull, Positive, SideEffectFree or Unmodifiable. Just like executable code, such restrictions can be encapsulated  - by defining the restrictions semantics once and only once - and composed - by creating new restrictions aggregating existing ones, thus making consistent and meaningful documentation easier to write. 
  
  With such principles in mind, the Restrictions API is composed by source and class level annotations that express constraints over code elements, and the {{{http://staccatocommons.sourceforge.net/commons-restrictions/apidocs/net/sf/staccatocommons/restrictions/Restriction.html}Restriction}} meta-annotation, that express the semantics of such annotations, like for example, how both implementor and client code should behave on constraints violations, or how do they react to inheritance. 
 
  Although it is primary targeted for documenting such constraints, many of them may be processed in a non intrusive ways through [Staccatissimo-Restrictions-Instrument](http://staccatocommons.sourceforge.net/commons-restrictions-instrument/index.html).
 
  In addition to that, the project <encapsulates> consistent use of words of must, must not, etc. with [RFC2119](http://www.ietf.org/rfc/rfc2119.txt) across Javadoc. This means that whenever such words are used within Staccatissimo-Restrictions and the rest of the projects, they must be interpreted following such guidelines.  
 
##Examples

###Check restrictions

 Check restrictions are simple constraints that can annotate indistinctly return values, parameters  and attributes, to express postconditions, preconditions and invariants, respectively. Not all them are targeted for being used in combination with any type, for instance, NotNull should not be used with primitive types, and NotEmpty should not be used with objects without methods or public attributes like length, size or isEmpty:   

```java
  //Postcondition: the returned value is always > 0
  @Positive
  protected int activeThreadsCount() { ... }

  //Invariant: the size of the string builder is always <= 256 characters
  @MaxSize(256)
  private StringBuilder builder;

  //Precondition: recipients collection must be not  null and not empty
  public void sendTo(@NotEmpty Collection<User> recipients);

  //Precondition: Username must match be non null and match the given regexp
  public void setUserName(@Matches(USERNAME_REGEXP) username);
```

###Instantiation restriction

 Instantiation restriction are constraints about the number of different instances 
 a method returns. They express postconditions.  
 
```java
  //Postcondition: this method always returns the same instance. 
  //This does not mean it is a singleton - in fact Staccatissimo discourages them 
  @Constant
  public static <T> Predicate<T> notNull() { ... }

```   
     
###Value Restrictions

 Value Restrictions are constrains over types regarding their state changes and identity. 
 They always express invariants over instances of such types:   

```java
  //Invariant: state of Currency instances is always constant
  @Immutable
  public class Currency { ... }
  
  //Invariant: BoundingBox equals, hashcode and toString are implemented
  //based on the BoundingBox state instead of identity
  @Value
  public class BoundingBox { ... }
 
```
   
###Processing directives for tools that interpret restrictions. 

 Processing directives are annotation that exists just for passing hints to automated annotations processors regarding
 when they should be processed:    

```java
   //Directive for processors: restrictions in this constructor
   //should be ignored, as they are manually handled.  
   @IgnoreRestrictions
   public CopyOnWriteMemoryArea(@NotEmpty MemoryArea original) { ... } 
```
