# artemis-starter
Starter package with Apache ActiveMQ Artemis supporting asynchronous messaging system for development Projects.
This custom spring boot artemis-starter can be used to push and consume messages to and from camel context. 

## Custom Spring boot Starter
In the development of spring boot, all kinds of starters (scene starters) are essential. 

They are just like pluggable plug-ins. As long as you refer to the scene starters provided by spring boot in the POM file and make a small amount of configuration, 
you can use the corresponding functions. 

But spring boot does not cover all our use case scenarios, At this time, we need to customize the starter to achieve the customized function.

How spring boot starter works
1. When starting, springboot will scan the jar package that the project depends on to find the jar package that contains the spring. Factors file
2. Read the spring.factors file to get the configured autoconfiguration class autoconfiguration
3. Put the @ bean satisfying the condition (@ conditionalonxxx) in the spring container (spring context)

## apache artemis 
Multi-Protocol Messaging. [Apache ActiveMQ](https://activemq.apache.org/components/artemis/) is the most popular open source, multi-protocol, Java-based message broker. 
It supports industry standard protocols so users get the benefits of client choices across a broad range of languages and platforms.

## Working
Push data into Artemis Queue
    1.Construct the data needed to be pushed
    2.Set it on to the camel body 
    3.Set the queue name to be published as a header value
    4.Invoke **pushMessageToQueue**

Push data into Artemis Queue with Attributes
    1.Construct the data needed to be pushed
    2.Set it on to the camel body
    3.Set the queue name to be published as a header value
    4.Set the attributes to be passed as header map
    5.Invoke **pushMessageToQueue**

Consume data from Artemis Queue
    1.Construct the **env.artemis.listener.queue.target.map** containing queue to be consumed 
    2.Consume the message from Queue and send it to camel direct
