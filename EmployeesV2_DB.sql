CREATE TYPE users_role AS ENUM('admin', 'employee', 'recruiter');

CREATE TABLE IF NOT EXISTS users (
	id serial,
	name varchar(30),
	email varchar(30),
	password varchar(250),
	phone varchar(10),
	adresse varchar(50),
	users_role users_role,
	CONSTRAINT pk_users PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS notification (
	id serial,
	subject varchar(50),
	message text,
	users_id int,
	CONSTRAINT pk_notification PRIMARY KEY(id),
	constraint fk_notification_users foreign key(users_id)
		references users(id)
);

CREATE TABLE IF NOT EXISTS admin(
	CONSTRAINT pk_admin_users PRIMARY KEY(id)
)INHERITS(users);

CREATE TABLE IF NOT EXISTS recruiter(
	CONSTRAINT pk_recruiter_users PRIMARY KEY(id)
)INHERITS(users);

CREATE TABLE IF NOT EXISTS job_offer (
	id serial,
	title varchar(30),
	description text,
	posting_date timestamp,
	validity_duration interval,
	valid boolean,
	recruiter_id int,
	CONSTRAINT pk_job_offer PRIMARY KEY(id),
	constraint fk_job_offer_recruiter foreign key(recruiter_id)
		references recruiter(id) ON DELETE CASCADE
);

create type candidate_status as enum('recieved', 'ongoing', 'rejected', 'accepted');

create table if not exists candidate (
	id serial,
	name varchar(30),
	motivation text,
	file varchar(225),
	job_offer_id int,
	CONSTRAINT pk_candidate PRIMARY KEY(id),
	constraint fk_candidate_job_offer foreign key(job_offer_id)
		references job_offer(id) ON DELETE CASCADE
);
//////////////////////////////////////
create table if not exists department(
	id serial,
	name varchar(30),
	constraint pk_department primary key(id)
);

create table if not exists job(
	id serial,
	name varchar(30),
	constraint pk_job primary key(id)
);

CREATE TABLE IF NOT EXISTS employee(
	birth_date date,
	ussn integer ,
	hiring_date date,
	salary numeric,
	holiday_credit integer,
	kids integer,
	department_id int,
	job_id int,
	CONSTRAINT pk_employee_users PRIMARY KEY(id),
	constraint fk_employee_department foreign key(department_id)
		references department(id) ON DELETE CASCADE,
	constraint fk_job foreign key(job_id)
		references job(id) ON DELETE CASCADE
)INHERITS(users);

    CREATE TABLE IF NOT EXISTS absence (
	id serial,
	absence_date date,
	days integer,
	justified boolean DEFAULT false,
	file varchar(225),
	employee_id int,
	CONSTRAINT pk_absence PRIMARY KEY(id),
	constraint fk_employee foreign key(employee_id)
		references employee(id)
);

CREATE TABLE IF NOT EXISTS allowance (
	id serial,
	total_amount numeric,
	transaction date,
	employee_id int,
	CONSTRAINT pk_allowance PRIMARY KEY(id),
	constraint fk_employee foreign key(employee_id)
		references employee(id)
);

CREATE TABLE IF NOT EXISTS holiday (
	id serial,
	subject varchar(50),
	message text,
	start_date date,
	end_date date,
	accepted boolean,
	employee_id int,
	CONSTRAINT pk_holiday PRIMARY KEY(id),
	constraint fk_employee foreign key(employee_id)
		references employee(id)
);

CREATE TABLE IF NOT EXISTS employee_change (
    id serial,
    field varchar(30),
    old_value varchar(30),
    new_value varchar(30),
    employee_id int,
    CONSTRAINT pk_employee_change primary key(id),
    CONSTRAINT fk_employee FOREIGN KEY(employee_id)
        REFERENCES employee(id)
);

ALTER TABLE users
ADD CONSTRAINT uc_users UNIQUE (email);

ALTER TABLE admin
ADD CONSTRAINT uc_admin_users UNIQUE (email);

ALTER TABLE employee
ADD CONSTRAINT uc_employee_users UNIQUE (email);

ALTER TABLE recruiter
ADD CONSTRAINT uc_recruiter_users UNIQUE (email);

ALTER TABLE users
RENAME COLUMN users_role to user_role;

ALTER TABLE notification
DROP CONSTRAINT fk_notification_users;

ALTER TABLE notification
ADD CONSTRAINT fk_notification_users FOREIGN KEY (users_id)
    REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE job_offer
DROP CONSTRAINT fk_job_offer_recruiter;

ALTER TABLE job_offer
ADD CONSTRAINT fk_job_offer_recruiter FOREIGN KEY (recruiter_id)
    REFERENCES recruiter(id) ON DELETE CASCADE;

ALTER TABLE candidate
DROP CONSTRAINT fk_candidate_job_offer;

ALTER TABLE candidate
ADD constraint fk_candidate_job_offer foreign key(job_offer_id)
    		references job_offer(id) ON DELETE CASCADE;

ALTER TABLE allowance
DROP CONSTRAINT fk_employee;

ALTER TABLE allowance
ADD constraint fk_employee foreign key(employee_id)
    		references employee(id) ON DELETE CASCADE;

ALTER TABLE holiday
DROP CONSTRAINT fk_employee;

ALTER TABLE holiday
ADD constraint fk_employee foreign key(employee_id)
    		references employee(id) ON DELETE CASCADE;

ALTER TABLE job_offer
ALTER COLUMN valid SET DEFAULT true;

ALTER TABLE employee
ADD CONSTRAINT uc_employee_ussn UNIQUE (ussn);

ALTER TABLE employee_change
ADD CONSTRAINT fk_employee FOREIGN KEY (employee_id)
    REFERENCES employee(id) ON DELETE CASCADE;
