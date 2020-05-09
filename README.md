### Enterprise Knowledge Graph System

---

## SECTION 1 : PROJECT TITLE
## Enterprise Knowledge Graph System

---

## SECTION 2 : EXECUTIVE SUMMARY / PAPER ABSTRACT
It is well-known that web contains massive amount of knowledge, in the form of structured and unstructured data. Extracting, re-organizing and integrating these fragmented knowledges into a large-scale knowledge graph will be the development trend of the Internet.  Currently, the construction and application of knowledge graphs have achieved great success, however, they have not been full used in many industry fields, especially for enterprise domain. On the contrast, there is a huge demand from big companies and different SME. Furthermore, all our team members are working in IT industries. Hence, we built a web-based Enterprise Knowledge Graph System, which is targeted to internal stakeholders (e.g. Enterprise Architect, Solutions Designer and Business Analyst etc.) 

The system provides comprehensive graph view and necessary text summary of business operation process regarding to “360-degree Scan”, “Relationship Scan”, “End-to-End Process Scan” portions. Its “Recommend Solution” function gives users with the specific recommendation about optimized operation process and solution design strategy. While interact with the intelligent system, uses are able to input free text or select listed options to understand whole business structure. Through adjusting the values of resource count and load distribution for nodes “People”, “Process” and “Technology” on “Resource Simulation” page, to get latest Cost and Time properties for requests “Request-to-Answer process with lowest cost or shortest response time”.

The biggest highlights of our system are the mature Framework (named FRAMWORX), Cognitive User Interface design (supported by Google Dialogflow) and solid basics with 3500+ nodes 3300+ relations in Graph database. In addition, with adoption of the CRISP-DM (Cross Industry Standard Process for Data Mining) methodology and other algorithms (e.g. decision tree, knowledge graph, BFS, DFS and Dijkstra etc.), the overall system is definitely stable and efficient to respond correct results in various forms including graphs, texts and spreadsheets.

---

## SECTION 3 : CREDITS / PROJECT CONTRIBUTION

| Official Full Name  | Student ID (MTech Applicable)  | Work Items (Who Did What) | Email (Optional) |
| :------------ |:---------------:| :-----| :-----|
| YU Yu | A0213495A |Overall system implementation and validation, project management and documentation | e0508596@u.nus.edu |
| YANG Lu Yi | |Overall system design, modelling, use case & algorithm design, system implementation | e0508578@u.nus.edu |
| YIN TianShi | A0213511Y |Overall system design, modelling, technical architecture design, system implementation | e0508612@u.nus.edu |

---

## SECTION 4 : VIDEO OF SYSTEM MODELLING & USE CASE DEMO
<div align="center">
  <a href="https://youtu.be/gvstrhI8Fkg">
    <img src="https://img.youtube.com/vi/gvstrhI8Fkg/0.jpg" alt="EKGS Project Video">
  </a>
</div>

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
EKGS application is now running and available on http://localhost. (or http://{DOCKER_HOST_IP} if Docker is hosted on a remote server,
e.g. Docker Toolbox installs Docker on VirtualBox VM which uses the VM's IP)

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

