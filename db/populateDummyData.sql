insert into CLTGROUP(ID, NAME, DESCRIPTION)
values(1,"group1","group1Desc");

insert into CLTGROUP(ID, NAME, DESCRIPTION)
values(2,"group2","group2Desc");

insert into CLTGROUP(ID, NAME, DESCRIPTION)
values(3,"group3","group3Desc");

insert into CLUSTER(ID, GROUP_ID, NAME, CREATE_DATE, PASSWORD_HASH)
values(1,1,"cluster1",'2020-08-12',"asdasdasd");

insert into CLUSTER(ID, GROUP_ID, NAME, CREATE_DATE, PASSWORD_HASH)
values(2,2,"cluster2",'2020-08-12',"asdasdaDDDsd");

insert into CLTMODULE(ID, NAME, DESCRIPTION)
values(1,"module1","descmodule1");

insert into CLTMODULE(ID, NAME, DESCRIPTION)
values(2,"module2","descmodule2");

insert into CLTACCESS(MODULE_ID, GROUP_ID, PERMISSION)
values(1,1,1);

insert into CLTACCESS(MODULE_ID, GROUP_ID, PERMISSION)
values(1,2,1);


