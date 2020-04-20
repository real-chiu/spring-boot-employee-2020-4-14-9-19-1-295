CREATE TABLE IF NOT EXISTS Parking_boy(
    id int AUTO_INCREMENT PRIMARY KEY,
    nickname varchar(30)
);
CREATE TABLE IF NOT EXISTS Employee(
    id int AUTO_INCREMENT PRIMARY KEY,
    employee_name varchar(30),
    age int,
    gender varchar(30),
    salary int,
    company_id int,
    parking_boy_id int,
    FOREIGN KEY (parking_boy_id) REFERENCES Parking_boy(id)
);
