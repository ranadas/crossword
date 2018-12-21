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

* Dockerizing Spring Boot (or any executable .jar file)
    1. Use a Dockerfile to define an image, then build it and run it with plain Docker commands.
            We build the image so it’ll be available in our local Docker registry. We give it a name with the -t flag, and specify the current directory as the one in which the Dockerfile lives. : 
                <code>docker build -t sbootopenj11  . </code>
            We just need to create a container using the new image:    
                <code>docker run --name crss_instance  -p 5000:8080 -i -t sbootopenj11  </code>    
            By using the -p flag we’re telling docker to expose the container’s port 8080 (on the right of the colon) on the host’s port 8000 (on the left, our machine). We can access from our machine to localhost:8000 (you can also use your browser) and see the greeting message again, this time coming from the Docker container:
            
    2. Use a docker-compose.yml and its command line interface to extend the functionalities when running multiple containers, and simplify it by having a predefined configuration.
            <code>docker-compose up --scale template=2</code>
            <code>docker-compose ps</code>
    3. Use docker to build our java code as well (note that this is more an experiment than a real-life case).




