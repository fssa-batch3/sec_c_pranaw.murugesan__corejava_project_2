# LeaveManagement

## Description

The LeaveManagement project is a leave management system that allows employees to manage their leave requests and helps employers track and approve leave requests.


## DB TABLES
## Employee Table

| Field             | Type         | Null | Key | Default             | Extra          |
|-------------------|--------------|------|-----|---------------------|----------------|
| id                | int          | NO   | PRI | auto_increment      |                |
| name              | varchar(100) | NO   |     |                     |                |
| email             | varchar(100) | NO   | UNI |                     |                |
| password          | varchar(250) | NO   |     |                     |                |
| date_of_joining   | timestamp    | YES  |     | current_timestamp   | on update CURRENT_TIMESTAMP |
| is_active         | boolean      | NO   |     | 1                   |                |
| date_of_relieving | date         | YES  |     | NULL                |                |

## Role Table

| Field | Type         | Null | Key | Default         | Extra          |
|-------|--------------|------|-----|-----------------|----------------|
| id    | int          | NO   | PRI | auto_increment  |                |
| name  | varchar(200) | NO   | UNI |                 |                |

## Employee_Role_Details Table

| Field               | Type  | Null | Key | Default         | Extra          |
|---------------------|-------|------|-----|-----------------|----------------|
| id                  | int   | NO   | PRI | auto_increment  |                |
| employee_id         | int   | NO   | MUL |                 |                |
| role_id             | int   | NO   | MUL |                 |                |
| reporting_manager_id| int   | YES  | MUL |                 |                |
