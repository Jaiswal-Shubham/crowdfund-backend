package com.jaisshu.crowdfund.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "CrowdFund",
                        email = "shkjais@gmail.com",
                        url = "https://test.com/"
                ),
                description = "OpenApi documentation for Backend APIs",
                title = "CrowdFund Craft Demo by Shubham Kumar Jaiswal",
                version = "1.0",
                license = @License(
                        name = "Developer License",
                        url = ""
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "http://ec2-3-13-157-238.us-east-2.compute.amazonaws.com:8080/"
                )
        }
)
public class OpenApiConfig {
}
