create database leavemanagement;
use leavemanagement;
create table employee(
id int not null auto_increment,
name varchar(100) not null,
email varchar(100) not null unique,
password varchar(250) not null,
date_of_joining timestamp default current_timestamp on update current_timestamp,
is_active boolean not null default '1',
date_of_relieving date null,
primary key(id)
);
-- drop table employee;


select * from employee;	

create table role(
id int not null auto_increment primary key,
name varchar(200) not null unique
);
-- drop table role;
select * from role;
describe role;
create table employee_role_details(
id int not null auto_increment primary key,
employee_id int not null,
role_id int not null,
reporting_manager_id int,
foreign key(employee_id)references employee(id),
foreign key(role_id)references role(id),
foreign key(reporting_manager_id)references role(id)
);
select * from employee_role_details;
-- drop table employee_role_details; 
-- insert into employee_role_details (employee_id,role_id,reporting_manager_id) values();

CREATE TABLE leave_types(
id int not null auto_increment primary key,
name varchar(2) not null,
description varchar(20) not null
);
select * from leave_types;
-- INSERT INTO employee(name,email,password,date_of_joining, is_active) VALUES ('pranaw','pranaw@gmail.com','password123','2022-07-11','1');
show tables;
