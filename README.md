# LRU-cache-implimentation
Creation of an LRU cache in Java. Created for WPI 2103.

In this Project a LRU cache was implimented and encapsulated in java along with custom written test cases. the assignment is listed here:

The LRUCache class you create must have a public constructor that takes two parameters. The first is the DataProvider that the cache will consult for every cache miss. How exactly is the data provider defined? We want to be flexible, so we will define a data provider as any Java class that implements a single method: U get (T key). Notice that, in contrast to ArrayList, which takes only one type parameter, our cache takes two type parameters: Here, T is the type of the key, and U is the type of the value associated with the key. For example, T might be String (the name of the image), and U might be Image. See the DataProvider interface in the project Zip file.

In addition to specifying the data provider, you also have to specify the capacity of the cache, which is equal to the maximum number of elements (i.e., (key,value) pairs) that the cache can store at one time. It is crucial that your cache allocate exactly enough memory to accomodate capacity memory slots -- no more and no less -- so that our automatic test code will evaluate your cache correctly.

The only methods that your LRU cache needs to implement are U get (T key) and int getNumMisses (). The get method has the exact same signature as in the DataProvider interface -- indeed the Cache interface is actually a subinterface of DataProvider. The getNumMisses reports the number of cache "misses" that have occurred since the Cache was instantiated.

This cache was implimented to use an java class as a directory while implimenting a cache for it. this forced the use of generic variable types and creating asystem that can be used in a wide variety of applications. 
