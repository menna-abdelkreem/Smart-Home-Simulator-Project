-- Smart Home Database 
CREATE DATABASE SmartHome;
GO
USE SmartHome;
GO

-- 1. USER TABLE 
-- Represents the system users who control devices or rooms
CREATE TABLE [USER](
firstname NVARCHAR(50),
lastname NVARCHAR(50),
lastdc NVARCHAR(50) NULL,
PRIMARY KEY (firstname, lastname)
);

--2. ROOM TABLE
-- Represents rooms in the smart home
CREATE TABLE ROOM(
ar_1 NVARCHAR(50) PRIMARY KEY,
sror_1 NVARCHAR(50) NULL
);

--3. RENEWABLE_ENERGY_SOURCE TABLE
CREATE TABLE RENEWABLE_ENERGY_SOURCE (
M NVARCHAR(50) PRIMARY KEY
);

-- 4. DEVICE table (Parent entity)
-- Represents all devices in the smart home system
-- Each device is associated with one room and may be controlled by a user
CREATE TABLE DEVICE (
    sourceid NVARCHAR(50) PRIMARY KEY,
    room_ar_1 NVARCHAR(50) NOT NULL,
    user_firstname NVARCHAR(50),
    user_lastname NVARCHAR(50),
    FOREIGN KEY (room_ar_1) REFERENCES ROOM(ar_1),
    FOREIGN KEY (user_firstname, user_lastname) REFERENCES [USER](firstname, lastname)
);

-- 5. ROOM_CONTAINS_DEVICE relationship (N:M)
-- Represents that a room can contain multiple devices and a device can belong to multiple rooms
CREATE TABLE ROOM_CONTAINS_DEVICE (
    room_ar_1 NVARCHAR(50),
    device_sourceid NVARCHAR(50),
    PRIMARY KEY (room_ar_1, device_sourceid),
    FOREIGN KEY (room_ar_1) REFERENCES ROOM(ar_1),
    FOREIGN KEY (device_sourceid) REFERENCES DEVICE(sourceid)
);

-- 6. DEVICE_UTILIZES_SOURCE relationship (N:M:M)
-- Represents the ternary relationship between Device, Renewable Energy Source, and Utilizes
CREATE TABLE DEVICE_UTILIZES_SOURCE (
    device_sourceid NVARCHAR(50),
    source_M NVARCHAR(50),
    utilizes_M NVARCHAR(50),
    PRIMARY KEY (device_sourceid, source_M, utilizes_M),
    FOREIGN KEY (device_sourceid) REFERENCES DEVICE(sourceid),
    FOREIGN KEY (source_M) REFERENCES RENEWABLE_ENERGY_SOURCE(M)
);

-- 7. Sub-entities (IS-A / Inheritance)
-- Each sub-entity uses the same primary key as the parent DEVICE table

-- LIGHT table
CREATE TABLE LIGHT (
    sourceid NVARCHAR(50) PRIMARY KEY,
    brightness INT,
    FOREIGN KEY (sourceid) REFERENCES DEVICE(sourceid)
);

-- AC table
CREATE TABLE AC (
    sourceid NVARCHAR(50) PRIMARY KEY,
    msilleds NVARCHAR(50),
    FOREIGN KEY (sourceid) REFERENCES DEVICE(sourceid)
);

-- REFRIGERATOR table
CREATE TABLE REFRIGERATOR (
    sourceid NVARCHAR(50) PRIMARY KEY,
    temperature INT,
    FOREIGN KEY (sourceid) REFERENCES DEVICE(sourceid)
);

-- DOOR table
CREATE TABLE DOOR (
    sourceid NVARCHAR(50) PRIMARY KEY,
    temperature INT,
    FOREIGN KEY (sourceid) REFERENCES DEVICE(sourceid)
);


