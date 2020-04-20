CREATE TABLE IF NOT EXISTS Parking_boy(
    id int AUTO_INCREMENT PRIMARY KEY,
    nickname varchar(30)
);
CREATE TABLE IF NOT EXISTS Company(
    id int AUTO_INCREMENT PRIMARY KEY,
    company_name varchar(30),
    employees_number int
);
CREATE TABLE IF NOT EXISTS Employee(
    id int AUTO_INCREMENT PRIMARY KEY,
    name varchar(30),
    age int,
    gender varchar(30),
    salary int,
    company_id int,
    parking_boy_id int,
    FOREIGN KEY (company_id) REFERENCES Company(id),
    FOREIGN KEY (parking_boy_id) REFERENCES Parking_boy(id)

);