# What is this?
Just a practice project to try out test driven development and troubleshoot Spring annotations. 

## Troubleshooting Issues with @RequestMapping

In particular, I found out that 
`@RequestMapping("/health")` is marked on class level but then at method level, there is just a `@RequestMapping("/")`, then Spring framework does not resolve it to overall `"/health/"` with concatenation treating class level detail as base path. However, `@RequestMapping("")` works just fine.

## Test Driven Development with spring-boot-test annotations
This repository also demonstrates how simple test methods can help you check out integration tests with spring test annotations. However, there is a lot to be worked out for this to be helpful to readers than the author himself.

## Datasource
In default configurations, if you have included spring-data-jpa-starter on path, it would not allow spring module to boot up, unless we provide datasource url. 