create table imageinfo(

ID int not null,
PPT_ID int not null,
IMAGE_PATH varchar(500) not null,
IMAGE_NAME varchar(200) not null,
CREATED_DT datetime,
CREATED_BY varchar(200),


constraint IMAGEINFO_PK primary key (ID),
constraint IMAGEINFO_FK foreign key(PPT_ID) references pptinfo(ID)

);

insert into sequence values('se_imageinfo_id',0,1);

commit;