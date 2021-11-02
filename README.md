# Description
This filter allows you to Invoke AWS Lambda Function


## API Management Version Compatibilty
This artifact was successfully tested for the following versions:
- 7.7 Auguest 2021 Release

## Build Custom Filter

```bash
./gradlew build
```


## Install

- Copy  aws-lambda-apim-sdk\lib\aws-lambda-apim-sdk-*.jar file in the API Gateway VORDEL_HOME/groups/group-x/instance-x/ext/lib 
- Download https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk-lambda/1.11.564/aws-java-sdk-lambda-1.11.564.jar and copy the file to VORDEL_HOME/groups/group-x/instance-x/ext/lib 
- Download https://repo1.maven.org/maven2/com/amazonaws/jmespath-java/1.11.564/jmespath-java-1.11.564.jar and copy the file to VORDEL_HOME/groups/group-x/instance-x/ext/lib
- Restart API Gateway instance
- Copy aws-lambda-apim-sdk\lib\aws-lambda-apim-sdk-*.jar file to Policy Studio dropins folder
- Restart Policystudo with policstudio -clean option
- Select Policystudio Import custom filter option from File menu and select file aws-lambda-apim-sdk/src/main/resources/AWSLambdaTypeSet.xml




## Contributing

Please read [Contributing.md](https://github.com/Axway-API-Management-Plus/Common/blob/master/Contributing.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Team

![alt text][Axwaylogo] Axway Team

[Axwaylogo]: https://github.com/Axway-API-Management/Common/blob/master/img/AxwayLogoSmall.png  "Axway logo"

## License
[Apache License 2.0](LICENSE)
