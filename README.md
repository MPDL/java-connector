java-connector
==============
Ein .jar library which offers the quickest and the most convenient way to use diverse mpdl services.

----------

Usage:
--------------------------------
Using mpdl [screenshot service][1](eg.) by doing
```
Connector.getScreenshotService().captureFromURL("serviceTargetURL", "url", "outputFormat", "outputSize", "crop")
```

Download:
--------------------------------
The java-connector can be downloaded manually or via maven.

####Downloads
[0.1][2]

####Downloading with Maven:
Dependency configuration would be as follows
```
<dependency>
    <groupId>de.mpg.mpdl.service.connector</groupId>
    <artifactId>java-connector</artifactId>
    <version>0.1</version>
</dependency>
```
and add the following repository definition tou your pom.xml in repositories section
```
<repository>
    <id>nexus</id>
    <name>Max Planck Digital Library Maven Repository</name>
    <url>http://rd.mpdl.mpg.de/nexus/content/groups/public</url>
</repository>
```

[1]: https://github.com/MPDL/screenshot-service
[2]: http://rd.mpdl.mpg.de/nexus/content/groups/public/de/mpg/mpdl/service/connector/java-connector/0.1/java-connector-0.1.jar
