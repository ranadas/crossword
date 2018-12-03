A crossword solver programming puzzle. 
A word list is attached (english.txt), for your reference, and the problem is as follows:

1.     User needs to be able to input the word length, and what known letters there are. You can use any format you want for this input, but it should be understandable.
2.     The program should return all words in the dictionary that are the correct length and that contain those letters at those positions.

There are a couple of things to consider:
1.     The algorithm should be fast. How fast is fast? Well, that’s a bit subjective, but we’ve come up with some extremely fast ones.
2.     Startup time (within reason) isn’t necessarily a big deal, nor is memory usage that big of a deal, but the algorithm should be quick. (optimize for speed vs initialization) Once the app is loaded it should run as fast as it can.


Additional work:
java 11

wip - https://www.baeldung.com/spring-boot-actuators

https://github.com/sivaprasadreddy/spring-boot-microservices-series/blob/master/docker-compose.yml

docker build -t rdasopenjdk11  .

docker run --name crss_instance  -p 5000:8080 -i -t rdasopenjdk11 

