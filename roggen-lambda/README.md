#About Staccatissimo-Lambda 
 
 Staccatissimo-Lambda is a minimalistic and lightweight library for instantiating simple Staccato-Commond-Defs' Functions and Evaluables in a DSL style, without using anonymous classes. This project is aimed to simplify higher-order programming within Staccatissimo until Java adds lambdas to its language (planned for Java 8) 

#Getting Started

 The entry point to Staccatissimo-Lambda is the <<<Lambda>>> class, which allows to create functions in a concise and redable way. There are two ways of using it, this introduction will just show one of them - using a shared lambda factory. The alternative, more performant but a bit more verbose way - using local lambda factories - is discussed in the API docs.  

##Instantiating 1 argument functions    
 
 The simplest function to create are one argument functions. Without Staccatissimo-Lambda, implementing such functions require to extend
 AbstractFunction. For example, a function that return book's ISBN looks like the following:
 
```java
  new AbstractFunction<Book, String>() {
    public String apply(@NonNull Book book){
        return book.getIsbn();
    }  
  };  
```

 Which is OK, but is far from being the panacea. In particular, it is not too readable. This can be improved statically importing Lambda:

```java
  lambda($(Book.class).getIsbn()); 
``` 
 
  Which, although is odd at first glance, is much more intention revealing.
   
  Functions that can be instantiated using this syntax are not restricted to getters, for example:

```java
  lambda($(List.class).size());  
  lambda($(List.class).contains("hello"));
  lambda($(List.class).get(2));
```  
  

##Instantiating 2 and 3 arguments functions

  Creating instances of Function2 and Function3 is also quite simple. For example, the following code:
  
```java
  new AbstractFunction2<Book, WeightUnit, BigDecimal>(){
   public BigDecimal apply(Book book, WeightUnit unit) {
     return book.getWeigth(unit);
   }
  };
```

  can be rewritten using lambdas:

```java
  lambda2($(Book.class).getWeight(_(WeightUnit.class));
```  

     
  
  
  
  