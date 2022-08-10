# epcis-client
A Java library for GS1 EPCIS v2.0


# Maven dependencies
```xml
<dependency>
    <groupId>org.oliot.epcis-x</groupId>
    <artifactId>epcis-client</artifactId>
    <version>2.1.0</version>
</dependency>

<dependency>
    <groupId>org.oliot.epcis-x</groupId>
    <artifactId>epcis-common</artifactId>
    <version>2.1.0</version>
</dependency>
```

# Example: Capture (XML)
```java
EPCISCaptureClient client = new EPCISCaptureClient(url);		
client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE).build());
HttpResponse<String> result = client.send();
```

# Example: Query.Poll (XML)
```java
EPCISQueryClient client = new EPCISQueryClient(url);
		
List<Object> result=client.prepareEventQueryParameters()
		.equalReadPoint(List.of("urn:epc:id:sgln:0037000.00729.8202"), false).build()
		.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
	
System.out.println(result.size());
```
