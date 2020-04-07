### Enterprise Knowledge Graph System

---

## SECTION 1 : PROJECT TITLE
## Enterprise Knowledge Graph System

---

## SECTION 2 : EXECUTIVE SUMMARY / PAPER ABSTRACT

---

## SECTION 3 : CREDITS / PROJECT CONTRIBUTION

| Official Full Name  | Student ID (MTech Applicable)  | Work Items (Who Did What) | Email (Optional) |
| :------------ |:---------------:| :-----| :-----|
| Yu Yu | A0213495A | | e0508596@u.nus.edu |
| Yang Lu Yi | | | e0508578@u.nus.edu |
| Yin TianShi | A0213511Y | | e0508612@u.nus.edu |

---

## SECTION 4 : VIDEO OF SYSTEM MODELLING & USE CASE DEMO

---

## SECTION 5 : USER GUIDE

`Refer to appendix <Installation & User Guide> in project report at Github Folder: ProjectReport`

### Simple Version - Quick start with pre-built Docker image
- Make sure [docker & docker-compose](https://docs.docker.com/install/) has been installed on local system.
- Download [ekgs-compose.yml](https://github.com/IRS-3Y/Enterprise-Knowledge-Graph-System/blob/master/SystemCode/ekgs-compose.yml) to a local directory.
- Open a command shell, goto the local directory where ekgs-compose.yml has been downloaded, and execute below command:
```
docker-compose -p ekgs -f ekgs-compose.yml up -d
```
EKGS application is now running and available on http://localhost.

To shutdown the application, execute below command:
```
docker-compose -p ekgs -f ekgs-compose.yml down -v
```

### Developer Version - Build everything locally
Make sure all developer tools have been installed:
- Docker
- Node.js
- JDK 11
- Apache Maven

Clone this Git repository to local, goto SystemCode directory, and use below commands to build EKGS application:
```
# to build
npm install && npm run build

# to start application
npm start

# to shutdown application
npm stop
```

---

## SECTION 6 : PROJECT REPORT / PAPER

`Refer to project report at Github Folder: ProjectReport`

---

## SECTION 7 : MISCELLANEOUS

`Refer to Github Folder: Miscellaneous`

---

