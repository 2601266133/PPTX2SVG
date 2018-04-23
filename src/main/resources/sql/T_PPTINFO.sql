create table pptinfo(
ID int ,
FILE_PATH varchar(500) not null,
FILE_NEW_NAME varchar(200) not null,
FILE_ORIGN_NAME varchar(200) not null,
FILE_SIZE varchar(50) not null,
IMAGE_PATH varchar(500) not null,
IMAGE_UUID varchar(200) not null,
CREATED_DT datetime,
CREATED_BY varchar(200),

constraint PPTINFO_PK primary key (ID)
);


insert into sequence values('se_pptinfo_id',0,1);

commit;