CREATE TABLE patient (
  patientid int NOT NULL AUTO_INCREMENT,
  firstname varchar(30) NOT NULL,
  lastname varchar(30) NOT NULL,
  gender varchar(6) NOT NULL,
  dob date NOT NULL,
  emailid varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  contact varchar(255) NOT NULL,
  addressline1 varchar(255) DEFAULT NULL,
  addressline2 varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  state varchar(255) DEFAULT NULL,
  zip varchar(255) DEFAULT NULL,
  PRIMARY KEY (patientid)
);

CREATE TABLE provider (
  providerid int NOT NULL AUTO_INCREMENT,
  firstname varchar(30) NOT NULL,
  lastname varchar(30) NOT NULL,
  gender varchar(6) NOT NULL,
  dob date NOT NULL,
  emailid varchar(255) NOT NULL,
  PASSWORD varchar(255) NOT NULL,
  contact varchar(255) NOT NULL,
  addressline1 varchar(255) NOT NULL,
  addressline2 varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  state varchar(255) NOT NULL,
  zip varchar(255) NOT NULL,
  speciality varchar(255) NOT NULL,
  license varchar(255) NOT NULL,
  PRIMARY KEY (providerid)
);

CREATE TABLE admin (
  emailid varchar(255),
  password varchar(255),
  PRIMARY KEY (emailid)
);

CREATE TABLE insurancecompany (
  insurerid int NOT NULL AUTO_INCREMENT,
  name varchar(30) NOT NULL,
  emailid varchar(255) NOT NULL,
  PASSWORD varchar(255) NOT NULL,
  contact varchar(255) NOT NULL,
  addressline1 varchar(255) NOT NULL,
  addressline2 varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  state varchar(255) NOT NULL,
  zip varchar(255) NOT NULL,
  PRIMARY KEY (insurerid)
);

CREATE TABLE laboratory (
  laboratoryid int NOT NULL AUTO_INCREMENT,
  name varchar(30) NOT NULL,
  emailid varchar(255) NOT NULL,
  PASSWORD varchar(255) NOT NULL,
  contact varchar(255) NOT NULL,
  addressline1 varchar(255) NOT NULL,
  addressline2 varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  state varchar(255) NOT NULL,
  zip varchar(255) NOT NULL,
  PRIMARY KEY (laboratoryid)
);

CREATE TABLE visits (
  visitid int NOT NULL AUTO_INCREMENT,
  patientid int NOT NULL,
  providerid int NOT NULL,
  vdate date NOT NULL,
  vtime time NOT NULL,
  patientnotes varchar(1000) NOT NULL,
  providernotes varchar(1000) NOT NULL,
  symptoms varchar(255) NOT NULL,
  diagnosis varchar(255) NOT NULL,
  status varchar(255) NOT NULL,
  PRIMARY KEY (visitid),
  KEY patientid (patientid),
  KEY providerid (providerid),
  CONSTRAINT visits_ibfk_1 FOREIGN KEY (patientid) REFERENCES patient (patientid),
  CONSTRAINT visits_ibfk_2 FOREIGN KEY (providerid) REFERENCES provider (providerid)
);

CREATE TABLE pharmacy (
  pharmacyid int NOT NULL AUTO_INCREMENT,
  name varchar(30) NOT NULL,
  emailid varchar(255) NOT NULL,
  PASSWORD varchar(255) NOT NULL,
  contact varchar(255) NOT NULL,
  addressline1 varchar(255) NOT NULL,
  addressline2 varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  state varchar(255) NOT NULL,
  zip varchar(255) NOT NULL,
  PRIMARY KEY (pharmacyid)
);

CREATE TABLE labtests (
  testid int NOT NULL AUTO_INCREMENT,
  labid int NOT NULL,
  visitid int NOT NULL,
  testname varchar(255) NOT NULL,
  testreport varchar(255) NOT NULL,
  tdate date NOT NULL,
  status varchar(255) DEFAULT NULL,
  PRIMARY KEY (testid),
  KEY labid (labid),
  KEY visitid (visitid),
  CONSTRAINT labtests_ibfk_1 FOREIGN KEY (labid) REFERENCES laboratory (laboratoryid),
  CONSTRAINT labtests_ibfk_2 FOREIGN KEY (visitid) REFERENCES visits (visitid)
);

CREATE TABLE mdedications (
  medicationid int NOT NULL AUTO_INCREMENT,
  pharmacyid int NOT NULL,
  visitid int NOT NULL,
  medications varchar(255) NOT NULL,
  status varchar(255) DEFAULT NULL,
  PRIMARY KEY (medicationid),
  KEY pharmacyid (pharmacyid),
  KEY visitid (visitid),
  CONSTRAINT mdedications_ibfk_1 FOREIGN KEY (pharmacyid) REFERENCES pharmacy (pharmacyid),
  CONSTRAINT mdedications_ibfk_2 FOREIGN KEY (visitid) REFERENCES visits (visitid)
);

CREATE TABLE patientinsurance (
  insuranceid int NOT NULL AUTO_INCREMENT,
  patientid int NOT NULL,
  insurerid int NOT NULL,
  expiry date NOT NULL,
  status varchar(255) NOT NULL,
  PRIMARY KEY (insuranceid),
  KEY patientid (patientid),
  KEY insurerid (insurerid),
  CONSTRAINT patientinsurance_ibfk_1 FOREIGN KEY (patientid) REFERENCES patient (patientid),
  CONSTRAINT patientinsurance_ibfk_2 FOREIGN KEY (insurerid) REFERENCES insurancecompany (insurerid)
);

CREATE TABLE accountcharges (
  chargeid int NOT NULL AUTO_INCREMENT,
  visitid int NOT NULL,
  charges int NOT NULL,
  patientpayment int NOT NULL,
  insurancecoverage int NOT NULL,
  status varchar(255) NOT NULL,
  balance int NOT NULL,
  PRIMARY KEY (chargeid),
  KEY visitid (visitid),
  CONSTRAINT accountcharges_ibfk_1 FOREIGN KEY (visitid) REFERENCES visits (visitid)
);

insert  into admin(emailid,password) values 
('admin@admin.com','admin1234');

insert  into insurancecompany(insurerid,name,emailid,PASSWORD,contact,addressline1,addressline2,city,state,zip) values 
(2,'Purdy, Ruecker and Stoltenberg','jpiotrowski0@arstechnica.com','tcNoNdAb','281-43-7646','90 Autumn Leaf Street','Room 1498','New York City','New York','10045'),
(3,'Lehner, Borer and Bode','djentin1@edublogs.org','ALGy2m','479-85-1204','39 Canary Crossing','Room 1172','Santa Cruz','California','95064'),
(4,'Kunze-Powlowski','gdilkes2@craigslist.org','oT1dpe','492-54-7236','9 1st Way','Apt 1557','Anaheim','California','92805'),
(5,'Dooley-Corkery','dspringell3@nps.gov','bzRuUlsX28','381-16-5501','4228 Coolidge Road','PO Box 66128','Cincinnati','Ohio','45254'),
(6,'Langosh Inc','abough4@columbia.edu','yykrKE2ng','539-36-5429','2649 Chive Terrace','Apt 702','Mansfield','Ohio','44905'),
(7,'Toy, Cartwright and Frami','tstrand5@bizjournals.com','IDI2CmFy3X','611-99-2697','29 Butternut Park','10th Floor','Clearwater','Florida','34620'),
(8,'Walsh-Dickinson','wwann6@live.com','BoKDFrbqSFB','693-78-9707','179 7th Plaza','Suite 95','Olympia','Washington','98516'),
(9,'Jaskolski Inc','rprobyn7@infoseek.co.jp','2Dwt2PAbOHER','451-16-1564','35728 Corscot Place','PO Box 85273','Shawnee Mission','Kansas','66225'),
(10,'Kutch LLC','salonso8@whitehouse.gov','eGBBe4CfF8rk','533-62-3421','9168 Ruskin Drive','5th Floor','Fort Worth','Texas','76105');

insert  into laboratory(laboratoryid,name,emailid,PASSWORD,contact,addressline1,addressline2,city,state,zip) values 
(2,'Stamm-Armstrong','bsprott0@etsy.com','u7u8gvH2','352-45-9294','732 Mitchell Circle','5th Floor','Los Angeles','California','90189'),
(3,'Leannon, Kassulke and Green','leggerton1@youtu.be','mbHVzvSU2V','120-86-6257','78 Morning Crossing','Apt 600','Topeka','Kansas','66611'),
(4,'Heathcote Group','tkertess2@ed.gov','5WFnrv','822-28-6946','15 Pankratz Junction','Apt 282','Tampa','Florida','33605'),
(5,'Moore and Sons','kwaterstone3@histats.com','tvuCFp9','406-05-8831','2 Nobel Road','16th Floor','Clearwater','Florida','33763'),
(6,'Kihn, Kunde and Wehner','arodson4@is.gd','OgBvdJzczE','670-53-2857','35370 Hooker Road','PO Box 60664','Palatine','Illinois','60078'),
(7,'Luettgen-Boyle','ekobiera5@gnu.org','13BaEp8fWuw','664-27-1761','17741 Hagan Parkway','3rd Floor','Ocala','Florida','34479'),
(8,'Kreiger Inc','uchoffin6@nih.gov','pfJZQX7','372-89-7295','86829 Donald Circle','Suite 69','Jackson','Mississippi','39210'),
(9,'Heathcote-Williamson','ifellibrand7@wisc.edu','0i8KxFXO7N','553-63-1145','0 Summit Way','Room 24','Orlando','Florida','32819'),
(10,'Hahn Inc','btweedie8@state.gov','gz3JqS7qaEz','832-52-4154','8 Hoffman Terrace','Apt 997','Farmington','Michigan','48335');



insert  into patient(patientid,firstname,lastname,gender,dob,emailid,password,contact,addressline1,addressline2,city,state,zip) values 
(92,'Pennie','Cheyenne','Male','1990-02-24','pcheyenne0@imgur.com','JaaAFB7OH7','878-11-8060','40 Elgar Center','Apt 902','New York City','New York','10170'),
(93,'Burt','Begwell','Male','1987-10-15','bbegwell1@paginegialle.it','14H9wfttSQ','466-11-3805','475 Mitchell Road','Apt 593','New Orleans','Louisiana','70149'),
(94,'Corenda','Wanklyn','Female','1998-02-21','cwanklyn2@instagram.com','Tt5fV3zRS8b','308-84-0948','27 Eastwood Park','Suite 49','Hampton','Virginia','23668'),
(95,'Shawnee','Beatens','Female','1993-04-28','sbeatens3@prlog.org','ZjdcuKqzS','803-28-0190','16 Valley Edge Alley','Room 1820','Charleston','West Virginia','25356'),
(96,'Neel','MacRinn','Male','1971-05-31','nmacrinn5@g.co','xFXWGcQuD2','303-11-5221','34511 Milwaukee Lane','14th Floor','Peoria','Illinois','61656'),
(97,'Radcliffe','D\'Antuoni','Male','1975-08-14','rdantuoni6@time.com','kqwpNl','393-53-8795','645 Monterey Junction','Apt 1870','Fort Wayne','Indiana','46852'),
(98,'Remington','Gutteridge','Male','2006-04-03','rgutteridge7@boston.com','2QVm45','823-12-8120','54478 Forest Avenue','Apt 660','Boston','Massachusetts','02104'),
(99,'Timofei','Vondrak','Male','1983-04-10','tvondrak8@hc360.com','M6nh23qMtiiu','203-60-2817','298 Larry Trail','Suite 49','Daytona Beach','Florida','32128'),
(100,'Chuck','Pauncefort','Male','1986-09-14','cpauncefort9@java.com','o0iealWZSG','522-04-3513','6093 Eastwood Point','Suite 94','Sacramento','California','95813'),
(101,'Nevile','Gulleford','Male','1972-04-14','ngulleforda@chronoengine.com','tCAjIJVbph','202-69-2423','69 Carey Parkway','Apt 31','Atlanta','Georgia','31132'),
(102,'Keen','Ogelsby','Male','1983-03-15','kogelsbyb@squarespace.com','YrqHVpDE9e4','173-68-7078','956 Toban Pass','Apt 10','Wilmington','Delaware','19810'),
(103,'Rab','Mathes','Male','1999-12-23','rmathesd@a8.net','ZAsRD6YfVoJ0','409-28-9972','0405 Fuller Court','7th Floor','Longview','Texas','75605'),
(104,'Cob','Dallender','Male','1981-03-15','cdallenderf@devhub.com','jd4qlfQzP2q','882-95-6225','3 Quincy Crossing','Suite 37','Odessa','Texas','79769'),
(105,'Zara','Wolters','Female','1985-08-24','zwoltersg@toplist.cz','XSOaNpAG2a','815-64-4345','664 Michigan Court','Apt 541','Des Moines','Iowa','50347'),
(106,'Blithe','Ragbourn','Female','1995-06-08','bragbournh@boston.com','EtuSux','598-74-1707','1 Union Center','18th Floor','Portland','Oregon','97296'),
(107,'Price','Verbruggen','Male','1992-06-30','pverbruggenj@list-manage.com','RIbNYS','697-29-7019','8688 Rusk Avenue','Room 1546','Daytona Beach','Florida','32123'),
(108,'Diahann','Wogan','Female','2014-04-22','dwogank@nyu.edu','znBXoFG','657-80-4925','7 Clarendon Pass','Apt 1869','Kansas City','Missouri','64144'),
(109,'Borg','Pheasey','Male','1976-06-29','bpheaseyl@unicef.org','OpI4BKhb','797-87-7999','40 Stone Corner Circle','Suite 2','Tulsa','Oklahoma','74126'),
(110,'Isabelle','Pilmer','Female','1999-09-28','ipilmerm@utexas.edu','2MhOJ8CjViF','835-06-0307','010 Moulton Plaza','Suite 39','Corpus Christi','Texas','78470'),
(111,'Alejoa','Edwicker','Male','1971-05-31','aedwickero@quantcast.com','HsAQvJ3z','473-07-9506','33510 Crescent Oaks Crossing','12th Floor','Nashville','Tennessee','37205'),
(112,'Nicky','Beckwith','Male','1983-03-02','nbeckwithq@umn.edu','eiiEJDsrZB','834-89-2578','724 Lakewood Court','17th Floor','Sparks','Nevada','89436'),
(113,'Sheelagh','Baniard','Female','1977-12-14','sbaniardr@japanpost.jp','Z68MFWGBTT2E','605-40-4913','5155 Pennsylvania Parkway','2nd Floor','Montgomery','Alabama','36177'),
(114,'Moshe','Tribble','Male','1998-10-07','mtribbles@qq.com','lUQGSi1Igd','746-11-8271','09919 Nobel Park','Suite 14','Houston','Texas','77206'),
(115,'Kari','Capoun','Female','1996-07-17','kcapount@fema.gov','LbvA5SdJdPa','119-37-5624','7762 Del Mar Place','11th Floor','Atlanta','Georgia','30301'),
(116,'Kristine','MacGragh','Female','1972-04-19','kmacgraghu@nih.gov','0kBrGthLVhpL','325-10-5245','72 Becker Trail','Room 1463','San Francisco','California','94121'),
(117,'Nicolina','Grave','Female','2007-05-27','ngravev@furl.net','zBtyNe','680-15-3328','6549 Blaine Parkway','Suite 96','San Diego','California','92105'),
(118,'Abdel','Tremathick','Male','1996-02-18','atremathickw@smugmug.com','IHOLQaWfbCc','638-26-9374','353 Bunker Hill Park','Room 358','Spring','Texas','77388'),
(119,'Stuart','MacGebenay','Male','2010-06-21','smacgebenayx@mysql.com','bhgQiIUiIA','156-21-4832','13462 Marcy Way','Room 1813','Beaverton','Oregon','97075'),
(120,'Fleming','Hechlin','Male','1991-03-17','fhechliny@examiner.com','rJGLBTYZMm','593-23-4851','08 Ludington Point','Apt 393','Delray Beach','Florida','33448'),
(121,'Ly','Punton','Male','1980-08-16','lpuntonz@w3.org','IX2EPBI','503-36-1583','37655 Merry Trail','PO Box 21331','San Antonio','Texas','78220'),
(122,'Moina','Beagin','Female','2003-03-02','mbeagin10@simplemachines.org','iJCQ2BC','240-08-3549','84 Goodland Park','Room 432','Irvine','California','92710'),
(123,'David','Thaxter','Male','1974-11-16','dthaxter11@quantcast.com','FLkjbfeb9H','446-15-8421','2 Michigan Trail','Room 522','Los Angeles','California','90081'),
(124,'Anne-marie','Bulmer','Female','1995-12-11','abulmer12@wikispaces.com','WwrM542IBc','189-62-5417','6569 Schurz Road','Room 1432','Saint Louis','Missouri','63136'),
(125,'Hesther','Sandes','Female','2013-09-24','hsandes13@reverbnation.com','a1RdBo','765-47-5244','395 Cody Way','Apt 314','Young America','Minnesota','55557'),
(126,'Harris','Kirrens','Male','2005-11-12','hkirrens14@hexun.com','tLpvYe0','369-01-9590','4371 Old Gate Pass','Room 1266','New York City','New York','10099'),
(127,'Adriaens','O\'Coskerry','Female','2018-09-21','aocoskerry15@dagondesign.com','ySb1UfzIL','231-25-5307','11794 Boyd Center','PO Box 38113','Saint Petersburg','Florida','33705'),
(128,'Jewelle','Gerling','Female','1975-09-14','jgerling16@salon.com','BT1bsXdk','209-46-9810','75414 Doe Crossing Lane','Suite 25','Youngstown','Ohio','44511'),
(129,'Dermot','Lewendon','Male','2018-05-01','dlewendon17@yahoo.co.jp','BTVLG5','510-20-8650','6 Scott Point','Suite 54','Frederick','Maryland','21705'),
(130,'Kathe','Hamal','Female','1972-04-22','khamal18@amazon.co.uk','7YsZibrm6lre','890-71-2751','318 Monterey Lane','Suite 53','Killeen','Texas','76544'),
(131,'Nollie','Throughton','Male','2017-03-02','nthroughton19@hexun.com','Y2VOFtE','863-20-8451','2 Pepper Wood Parkway','Room 246','Austin','Texas','78715'),
(132,'Alberta','Whistlecraft','Female','1980-03-07','awhistlecraft1a@wp.com','uI514PNCuV','845-01-7813','570 Hanover Drive','PO Box 95077','Saint Louis','Missouri','63169'),
(133,'Edouard','Ksandra','Male','1976-03-19','eksandra1b@zimbio.com','y1QnSVadoW','698-48-2238','7 Brentwood Center','Apt 727','Louisville','Kentucky','40210'),
(134,'Winifred','Stretton','Female','1974-12-28','wstretton1c@gnu.org','qiMpduqb9','546-66-1305','87 Canary Point','15th Floor','Paterson','New Jersey','07544'),
(135,'Hayes','Master','Male','2013-10-25','hmaster1d@digg.com','3uSATQVfAN','163-13-7529','542 Claremont Parkway','PO Box 9439','Waltham','Massachusetts','02453'),
(136,'Holli','Dimmick','Female','1973-03-07','hdimmick1e@theguardian.com','XXowAAr','638-51-7530','4124 Hansons Center','Apt 370','Tampa','Florida','33673'),
(137,'Grazia','Privett','Female','1971-03-21','gprivett1f@cpanel.net','IjaeQmQw','131-28-2904','14 Starling Point','5th Floor','Sacramento','California','95865'),
(138,'Henderson','Meake','Male','2000-10-13','hmeake1g@baidu.com','JwEedF','330-26-5624','3852 Grasskamp Center','15th Floor','Los Angeles','California','90065'),
(139,'Ruprecht','Van der Son','Male','2015-10-20','rvanderson1h@storify.com','g6eE9pLasP','376-37-1894','1799 Moose Trail','19th Floor','Vancouver','Washington','98664'),
(140,'Sullivan','Schoolfield','Male','1988-10-16','sschoolfield1i@a8.net','cQdFFns5RA','711-41-4834','2659 Donald Pass','Suite 44','Cincinnati','Ohio','45228'),
(141,'Julius','Chantler','Male','2008-04-30','jchantler1j@rediff.com','2q1KAmaQndW0','461-96-9592','55562 Schlimgen Center','Room 1649','Lexington','Kentucky','40515'),
(142,'Harriet','Prandoni','Female','1995-05-13','hprandoni1k@chron.com','SqrUMt','156-27-7748','38 Talmadge Circle','Room 191','Charlotte','North Carolina','28242'),
(143,'Terrence','Nussgen','Male','2000-11-29','tnussgen1l@opensource.org','tIkqXBV4PTfe','582-41-0282','3201 Golf Alley','Room 344','Washington','District of Columbia','20420'),
(144,'Cookie','Tomaszek','Female','2008-03-16','ctomaszek1m@slashdot.org','hFCuDxAO','757-74-7689','824 Reindahl Crossing','Room 1628','Wilkes Barre','Pennsylvania','18706'),
(145,'Jean','Dakers','Female','1976-01-16','jdakers1n@nhs.uk','ZI06FCTa','846-62-9052','44533 Florence Crossing','Room 800','Newark','New Jersey','07188'),
(146,'Brandy','Preuvost','Female','1970-06-18','bpreuvost1o@nasa.gov','k8cM4gD','183-14-3772','80 Crowley Center','Suite 3','Erie','Pennsylvania','16522'),
(147,'Jimmy','Boyde','Male','1989-11-21','jboyde1p@163.com','Fc4ooks1M1','187-08-3155','67 Miller Terrace','Room 1014','Fresno','California','93721'),
(148,'Eydie','Petrushka','Female','1977-11-30','epetrushka1q@barnesandnoble.com','hM9Xh66HNcE','725-11-0302','302 Kinsman Park','Room 123','Rochester','New York','14619'),
(149,'Carroll','Ebertz','Female','1992-04-21','cebertz1r@independent.co.uk','28hNor0FVob','441-06-3342','61598 Utah Lane','Room 1442','Van Nuys','California','91411'),
(150,'Euell','MacAlees','Male','1972-01-22','emacalees1s@hexun.com','cmR5XZ7Ft','319-90-2609','86987 Manufacturers Plaza','PO Box 27405','Albany','New York','12205'),
(151,'Binni','Rembaud','Female','1987-08-10','brembaud1t@squarespace.com','QkDvLVN4i','317-77-3015','78937 Lunder Avenue','PO Box 2803','Greensboro','North Carolina','27409'),
(152,'Dyanne','Padberry','Female','2000-07-09','dpadberry1u@diigo.com','jYpfhmVi6pOE','633-82-2248','92 Utah Parkway','8th Floor','New Haven','Connecticut','06533'),
(153,'Tamra','Austin','Female','1978-06-22','taustin1v@freewebs.com','zkgrYPJ','569-43-5844','14 Thierer Avenue','1st Floor','Charleston','West Virginia','25362'),
(154,'Elyssa','Romeril','Female','2013-11-05','eromeril1w@discuz.net','ev8lsdwsR','889-39-3077','99945 Graceland Pass','Room 65','Houston','Texas','77075'),
(155,'Winston','Domini','Male','2007-10-08','wdomini1x@mtv.com','2ERBdY1Wsve','429-83-3686','72 Menomonie Parkway','PO Box 1909','Kansas City','Kansas','66105'),
(156,'Si','Ledgard','Male','2003-11-30','sledgard1y@ezinearticles.com','A9J6Sha','672-41-7666','63 Morningstar Parkway','Room 1163','Houston','Texas','77266'),
(157,'Rudie','Mazzilli','Male','1992-07-15','rmazzilli1z@ustream.tv','ThVyHm','782-33-5382','7199 Eagan Place','Room 1974','Waco','Texas','76796'),
(158,'Carroll','Hawsby','Male','2002-12-24','chawsby20@forbes.com','97OTDlzGi5Y','253-76-9218','52 Buhler Way','Suite 63','Atlanta','Georgia','31136'),
(159,'Dallon','Ilyushkin','Male','1991-02-04','dilyushkin21@weather.com','bnOkbq7qL5','120-60-6836','556 Delaware Terrace','Room 52','Canton','Ohio','44710'),
(160,'Roselin','Thams','Female','2009-08-30','rthams23@quantcast.com','WxN4Tt','183-80-6514','17681 Cottonwood Lane','Apt 394','Boulder','Colorado','80310'),
(161,'Aleta','Sleeman','Female','1975-07-21','asleeman24@sogou.com','OwFqFvatJPPt','512-14-6211','8 Autumn Leaf Way','Room 923','Lubbock','Texas','79405'),
(162,'Sophey','Eby','Female','2012-03-24','seby25@tamu.edu','3x6TxCQ','877-19-4925','6784 Wayridge Crossing','1st Floor','Fort Smith','Arkansas','72905'),
(163,'Laney','Justice','Male','2013-08-24','ljustice26@npr.org','N5sRKvSe','365-79-4547','0252 Dapin Drive','Apt 592','El Paso','Texas','79940'),
(164,'Daphna','Ilchuk','Female','2012-02-12','dilchuk27@ca.gov','NmCZSWHcl','744-95-2326','4 Surrey Plaza','PO Box 6442','Johnstown','Pennsylvania','15906'),
(165,'Jena','Renol','Female','2003-05-06','jrenol29@archive.org','KEsYA4SUHge','587-58-2957','7 Express Point','Room 991','Chattanooga','Tennessee','37450'),
(166,'Emmit','Batty','Male','1973-03-21','ebatty2b@mashable.com','waNPnHd','312-33-1327','409 Maple Hill','Suite 7','Tucson','Arizona','85743'),
(167,'Emyle','Darkins','Female','1992-10-19','edarkins2c@usda.gov','gWXoZQfSb10','822-92-6437','23796 8th Terrace','PO Box 86639','Roanoke','Virginia','24024'),
(168,'Tudor','Illidge','Male','2008-04-19','tillidge2d@virginia.edu','b42Dv1H','877-59-2709','5206 Maryland Place','PO Box 35601','Philadelphia','Pennsylvania','19136'),
(169,'Rollin','Humpage','Male','1984-09-26','rhumpage2f@51.la','Z3qpI2','668-03-4029','8185 Kropf Court','Apt 400','Fort Pierce','Florida','34981'),
(170,'Rycca','McManamon','Female','2012-06-17','rmcmanamon2g@webmd.com','7jDX4bSsGO','325-77-4015','34 Luster Drive','15th Floor','Sacramento','California','94263'),
(171,'Charmain','Niemetz','Female','1999-03-11','cniemetz2h@japanpost.jp','yi1eBVr4','748-82-7900','20614 Milwaukee Crossing','8th Floor','Fort Wayne','Indiana','46867'),
(172,'Reyna','Hadigate','Female','2012-03-18','rhadigate2i@exblog.jp','WmYBc4','692-50-1016','17290 Union Place','Room 198','Atlanta','Georgia','31190'),
(173,'Lyndsey','Kenset','Female','1970-04-10','lkenset2j@go.com','tSUfTDvM','192-26-8527','3 8th Circle','Suite 50','Portland','Oregon','97206'),
(174,'Boyce','Delacroix','Male','1972-07-27','bdelacroix2k@slate.com','zAcDe0','514-22-2450','4 Meadow Vale Crossing','PO Box 52870','Beaufort','South Carolina','29905'),
(175,'Sheilakathryn','Hovington','Female','1992-01-02','shovington2l@bloglovin.com','TtSm6GFuuLJ','146-91-0680','347 Center Drive','5th Floor','Midland','Texas','79705'),
(176,'Kellen','Byles','Male','1975-05-26','kbyles2m@wunderground.com','iGVn7ieR','124-57-5526','8 Talisman Crossing','Suite 48','Huntington','West Virginia','25716'),
(177,'Brandi','Westcott','Female','1977-03-09','bwestcott2n@china.com.cn','eZsDoJ0B','688-21-9066','3842 Starling Lane','9th Floor','Hot Springs National Park','Arkansas','71914'),
(178,'Lynsey','Innocent','Female','2003-11-13','linnocent2o@uol.com.br','voj8TQAE','642-99-4312','6338 Corry Road','PO Box 83018','Baton Rouge','Louisiana','70820'),
(179,'Delmore','Sprowson','Male','1978-07-01','dsprowson2p@shutterfly.com','lxmmf6LzPq','397-09-6118','90613 Vermont Avenue','Suite 78','Orange','California','92862'),
(180,'Marie','Ander','Female','1997-11-16','mander2q@alibaba.com','XVrV9hg60UT','414-89-4770','98 Northridge Hill','Apt 554','Cincinnati','Ohio','45203'),
(181,'Hoyt','Du Barry','Male','1997-08-13','hdubarry2r@who.int','cl2wXE','207-70-5678','4 Packers Trail','Suite 35','Sioux Falls','South Dakota','57198');




/*Data for the table pharmacy */

insert  into pharmacy(pharmacyid,name,emailid,PASSWORD,contact,addressline1,addressline2,city,state,zip) values 
(2,'Jubilant HollisterStier LLC','fgalea0@ning.com','T2kQ6xN','150-79-8834','149 Briar Crest Hill','PO Box 31614','Fargo','North Dakota','58106'),
(3,'REMEDYREPACK INC.','fcockerham1@ox.ac.uk','gYH6h1h','266-10-1925','4 Di Loreto Junction','3rd Floor','High Point','North Carolina','27264'),
(4,'Church & Dwight Co., Inc.','maveling2@ibm.com','5zMxLZ1YaD','702-53-9953','54512 Vernon Center','Room 271','Brooklyn','New York','11205'),
(5,'Hi-Tech Pharmacal Co., Inc.','hcoxon3@google.com.br','1HWR60','234-67-4619','837 Dawn Alley','PO Box 35453','Cincinnati','Ohio','45228'),
(6,'Wal-Mart Stores Inc','mdanbye4@vkontakte.ru','FHPWuc1e','241-91-4824','7311 Scott Terrace','Apt 1447','Panama City','Florida','32412'),
(7,'SUPERVALU INC.','sroskell5@ibm.com','YlHjQv71','804-14-3691','7 Sage Way','Apt 1056','Denver','Colorado','80243'),
(8,'American Welding & Gas','sluxen6@archive.org','VHSfAD','140-05-4236','0795 Karstens Plaza','Suite 43','Fort Lauderdale','Florida','33320'),
(9,'Deseret Biologicals, Inc.','hdowdle7@senate.gov','U6DaIm','677-15-4483','64 Manitowish Circle','Suite 31','Fullerton','California','92640'),
(10,'Meijer Distribution, Inc','aransfield8@microsoft.com','Dlo3DhPCPmS','880-04-5911','9242 Green Ridge Avenue','Suite 51','El Paso','Texas','88589');

insert  into provider(providerid,firstname,lastname,gender,dob,emailid,PASSWORD,contact,addressline1,addressline2,city,state,zip,speciality,license) values 
(2,'Saw','Marron','Male','1976-09-17','smarron0@goodreads.com','wX5f80SqWJ','282-68-6663','1 Cordelia Street','Apt 536','Garland','Texas','75049','Legal Assistant','476-29-7747'),
(3,'Fania','Aitkenhead','Female','2000-06-16','faitkenhead1@wp.com','wbkVoVY4','247-94-1885','01 Debs Terrace','Apt 136','Huntington','West Virginia','25726','Financial Advisor','232-45-7902'),
(4,'Franciska','Petrie','Female','1975-03-22','fpetrie2@globo.com','XuoXjH','390-39-1841','1592 Waubesa Place','13th Floor','Houston','Texas','77299','Assistant Media Planner','557-45-4113'),
(5,'Rufe','Benadette','Male','2018-12-09','rbenadette3@chicagotribune.com','pNamAkY83','779-39-2008','452 Village Green Alley','Room 910','Augusta','Georgia','30905','Media Manager II','497-38-1379'),
(6,'Arnoldo','Danielovitch','Male','1993-08-21','adanielovitch4@walmart.com','vsyalx','410-16-3845','79 Grasskamp Hill','2nd Floor','Garden Grove','California','92844','Teacher','207-19-4580'),
(7,'Torey','Pinck','Female','2017-03-07','tpinck5@ycombinator.com','QN2xzR1mQ','249-92-8249','03214 Old Gate Court','Apt 128','Philadelphia','Pennsylvania','19184','Senior Sales Associate','235-08-1834'),
(8,'Feliza','Gocke','Female','1972-06-13','fgocke6@hatena.ne.jp','unSyog','243-03-6263','9 Pearson Alley','PO Box 72115','Sioux Falls','South Dakota','57198','Paralegal','348-25-3026'),
(9,'Julio','Dowty','Male','2010-04-10','jdowty7@aol.com','PiTUuyH','802-16-4014','81851 Grayhawk Court','Apt 699','Los Angeles','California','90060','Senior Developer','147-78-2726'),
(10,'Marris','Blucher','Female','2005-10-25','mblucher9@wired.com','HAixxvLu2','343-67-8187','9466 Harper Terrace','PO Box 88398','Tulsa','Oklahoma','74126','Computer Systems Analyst II','349-09-8194');