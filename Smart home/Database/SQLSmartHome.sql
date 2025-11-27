CREATE DATABASE SmartHome;
GO

USE SmartHome;
GO



CREATE TABLE Users (
    userId INT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),

    preferredMode VARCHAR(20),
    language VARCHAR(20),
    notificationsEnabled BIT,
    theme VARCHAR(20),
    twoFactorEnabled BIT,
    lastLogin DATETIME,

    totalUsageHours FLOAT,
    lastUsedDevice INT,
    role VARCHAR(20)
);

CREATE TABLE Room (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    roomType VARCHAR(50),
    floorNumber INT,
    area FLOAT,
    ownerUserId INT NOT NULL,       -- MUST relationship (owns)

    FOREIGN KEY (ownerUserId) REFERENCES Users(userId)
);

CREATE TABLE Device (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    powerConsumption FLOAT,
    location VARCHAR(100),

    roomId INT NOT NULL,         -- MUST belong to a room
    FOREIGN KEY(roomId) REFERENCES Room(id)
);

CREATE TABLE Door (
    id INT PRIMARY KEY,
    isLocked BIT,
    FOREIGN KEY(id) REFERENCES Device(id)
);

CREATE TABLE Light (
    id INT PRIMARY KEY,
    brightness INT,
    FOREIGN KEY(id) REFERENCES Device(id)
);

CREATE TABLE AC (
    id INT PRIMARY KEY,
    temperature INT,
    FOREIGN KEY(id) REFERENCES Device(id)
);

CREATE TABLE Refrigerator (
    id INT PRIMARY KEY,
    internalTemp INT,
    FOREIGN KEY(id) REFERENCES Device(id)
);

CREATE TABLE RenewableEnergySource (
    id INT PRIMARY KEY,
    generatedPower FLOAT
);

CREATE TABLE SolarPanel (
    id INT PRIMARY KEY,
    capacity FLOAT,
    FOREIGN KEY(id) REFERENCES RenewableEnergySource(id)
);

CREATE TABLE WindTurbine (
    id INT PRIMARY KEY,
    speed FLOAT,
    FOREIGN KEY(id) REFERENCES RenewableEnergySource(id)
);


CREATE TABLE Device_Uses_Source (
    deviceId INT,
    sourceId INT,

    PRIMARY KEY (deviceId, sourceId),

    FOREIGN KEY(deviceId) REFERENCES Device(id),
    FOREIGN KEY(sourceId) REFERENCES RenewableEnergySource(id)
);

CREATE TABLE User_AllowedRooms (
    userId INT,
    roomId INT,

    PRIMARY KEY (userId, roomId),

    FOREIGN KEY(userId) REFERENCES Users(userId),
    FOREIGN KEY(roomId) REFERENCES Room(id)
);

CREATE TABLE User_AllowedDevices (
    userId INT,
    deviceId INT,

    PRIMARY KEY (userId, deviceId),

    FOREIGN KEY(userId) REFERENCES Users(userId),
    FOREIGN KEY(deviceId) REFERENCES Device(id)
);






