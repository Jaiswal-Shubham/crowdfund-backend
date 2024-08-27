Overview

This is a Spring Boot application designed to support a scalable crowdfunding platform where innovators can create projects and donors can contribute to them.

Features

	•	RESTful APIs: Built using Spring Boot to handle requests related to project creation, contributions, and user management.
	•	Authentication & Authorization: Secured using Spring Security with JWT tokens.
	•	Database Management: Integrated with MySQL via Spring Data JPA, leveraging AWS RDS for production environments.
	•	Caching: Utilizes Redis for caching frequently accessed data to improve performance.
	•	API Documentation: Integrated with OpenAPI for generating Swagger documentation.

Deployment

The application can be deployed using Docker or directly on AWS EC2 with a Systemd service configuration for managing the application lifecycle.

Build & Run

	•	Build: ./mvnw clean install
	•	Run: java -jar target/your-app.jar
	•	Deploy: Use the provided Systemd service file (/etc/systemd/system/crowdfund.service) for deploying on Linux servers.

CI/CD

Automated deployment is set up using GitHub Actions, which triggers on every push to the main branch, deploying the latest build to the AWS EC2 instance.
