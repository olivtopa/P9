create table if not exists Patient
(
   Id integer auto_increment primary key,
   Given_Name varchar(32) not null,
   Family_Name varchar(32) not null,
   Birth_Date datetime,
   Sex varchar(1) not null,
   Home_Address varchar(64) not null,
   Phone_Number varchar(16)
);
