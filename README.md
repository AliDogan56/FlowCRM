# FlowCRM Software Project
# Instruction Of Running On Local Env

## DB Deployment and Initial SQL
## Please Before Starting, Install Docker and Docker-Compose

1. cd deployment/db
2. docker-compose up -d
3. run RestApplication for Migration with Java 17 on IDE
3. connect db with 
jdbc:mysql://localhost:3306/flow_crm?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
admin:password
4. run insert.sql file on db


## FE Project
## Please Before Starting, Install Node and Npm version with 20.1.15

1. cd ui
2. npm i
3. npm run start 

## Access to App
1. Go to browser and http://localhost:3000
2. Login with usernames which have different user role : 
username : "admin", "systemAdmin", "customer_ali"
password : "1234567a"
