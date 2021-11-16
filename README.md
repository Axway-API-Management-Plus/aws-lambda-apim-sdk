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
- Configure AWS region 
    ```bash
    $export AWS_REGION=us-west-2
    ```
- Configure AWS Credentials profile
- Restart API Gateway instance
- Add the new JAR and any third-party JAR files used by the AWS lambda filter classes  to the runtime dependencies in Policy Studio. Select Window > Preferences > runtime Dependencies, and click Add to browse to the new JAR and any third-party JARs, and add them to the list. Click Apply to save the changes.
- Restart Policystudo with policstudio -clean option
- Select Policystudio Import custom filter option from File menu and select file aws-lambda-apim-sdk/src/main/resources/AWSLambdaTypeSet.xml

## Policy 
![image](https://user-images.githubusercontent.com/3596610/139945308-2d623276-6601-4395-9dbb-4831a17963c0.png)

Policy accepts following parameters

- Lambda function name
- AWS credential profile name [Refer](https://docs.aws.amazon.com/sdk-for-php/v3/developer-guide/guide_credentials_profiles.html) for more information


Policy generates following parameters

- aws.lambda.response - Response body from Lambda function
- aws.lambda.http.status.code - Response code from Lambda functoin



## Contributing

Please read [Contributing.md](https://github.com/Axway-API-Management-Plus/Common/blob/master/Contributing.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Team

![alt text][Axwaylogo] Axway Team

[Axwaylogo]: https://github.com/Axway-API-Management/Common/blob/master/img/AxwayLogoSmall.png  "Axway logo"

## License
[Apache License 2.0](LICENSE)
