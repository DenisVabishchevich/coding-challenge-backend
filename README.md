# AM Coding Challenge - Backend (Java)

## How to run:
1. Build docker image:
```bash
./mvnw clean package spring-boot:build-image -Dspring-boot.build-image.imageName=am_challenge:latest
```
2. Run docker compose:
```bash
docker-compose up -d
```

## The case study: Application review

Reviewing applications is one of the key activities, when starting a new project.
To tackle that challenge, BP ACCELERATOR Inc. wants to provide an API, via which applicant can hand in their application
with past project experience.

The software stores the data in a database and generates a document, so the application can be reviewed by the HR
department. When an application is submitted it should also be sent via Kafka to the Compliance department for
validation.

## Product Requirements

As an applicant at BP ACCELERATOR Inc.,

- [ ] I want to provide my application via a REST-API, with the following data:
    - [ ] my work e-mail address
    - [ ] a name
    - [ ] a github user
    - [ ] past projects (min 1)
- [ ] for each past project I want to provide
    - [ ] name of the project
    - [ ] employment mode (options: freelance / employed)
    - [ ] capacity (options: part-time / full-time)
    - [ ] duration in months (allow to provide weeks)
    - [ ] start year
    - [ ] role
    - [ ] team size (number)
    - [ ] link to the repository (optional)
    - [ ] link to the live url (optional)

As an application reviewer at BP ACCELERATOR Inc.,

- [ ] I want to be able to access the database, to see all applications
- [ ] I want that when an applicant re-submits his application, the data of a previous application is automatically
  deleted
- [ ] I want to easily generate and download a PDF document, that lists the data provided from the applicant
    - [ ] the PDF contains the GitHub profile image of the
      applicant ([API](https://docs.github.com/en/rest/guides/getting-started-with-the-rest-api))

## Your Mission

Create a Java backend application that satisfies all must-have requirements above, plus any nice-to-have requirements
you wish to include.

For that, you'll need to provide a REST-API, set up a database and generate a PDF document, that contains the applicants
data.

You can run Kafka in a local container and send messages to the topic of your choice, no need to create a listener
application.

You can use any boilerplate/approach you prefer, but try to keep it simple. We encourage you to use your favorite tools
and packages to build a solid web application.

You don't have to host your service publicly, but feel free to do that.
Please include a description in the README.md how to run the project locally and provide us a well done description for
the API.

The final delivery must be running with Docker.

## Tech Requirements

- Use the LTS version of Java 11 or 17
- Run the generation of the pdf in another Thread

## Instructions

- Fork this repo
- The challenge is on!
- Build a performant, clean and well-structured solution
- Commit early and often. We want to be able to check your progress
- Please complete your working solution within 2 days of receiving this challenge, and be sure to notify us with a link
  to your repo, when it is ready for review.
- additionally to the solution, please hand-in the PDF document, filled with your actual project experience

**Happy coding!**

## License

We have licensed this project under the MIT license so that you may use this for a portfolio piece (or anything else!).
