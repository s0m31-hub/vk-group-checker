# VK group checker
Currently in development state
## Installing
Download latest version from [release page] (https://github.com/s0m31-hub/vk-group-checker/releases) or compile it by yourself:  
* `git clone git@github.com:s0m31-hub/vk-group-checker.git`
* `cd vk-group-checker`
* `mvn package`  
Your package will be created as target/vk-group-checker-version-spring-boot.jar
## Usage
```
java -jar vk-group-checker.jar
-t --token vk.com user token
-s --start first id of a group to scan
-a --amount amount of groups to scan, <=100
