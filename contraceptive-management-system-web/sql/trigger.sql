-- ----------------------------
-- Procedure structure for `proc_ageStatistical`
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_ageStatistical`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_ageStatistical`(in getmedicinerecordId int,in age int)
begin

declare	community int;
declare	TownshipStreet int;
declare	County int;
#判断是否存在存在update不存在insert
declare isexitage int;
#判断性别
declare	manTotalage int;
declare	womanTotalage int;
#判断
declare	countyofcityage int;
declare	countyoutcityage int;
declare provinceoutcityage int;
declare	otherprovincesage int;


set @manTotalage= (select count(*) from getmedicinerecord where id =getmedicinerecordId and sex ='男');
set @womanTotalage= (select count(*) from getmedicinerecord where id =getmedicinerecordId and sex ='女');
set @countyofcityage = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本市本县');
set @countyoutcityage = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本市外县');
set @provinceoutcityage = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本省外市');
set @otherprovincesage = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='外省市');

set @community = (select area_id from MachineryEquipment where  id =(select machineryEquipment_id from getmedicinerecord where  id =getmedicinerecordId));
set @TownshipStreet= (select parentArea_id from area where id = @community);
set @County=(select parentArea_id from area where id = @TownshipStreet);

if age<25 then	
   set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @community and ageGroup='Below25');
   if @isexitage=0 then
		
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'Community',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@community,'Below25');
  elseif @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = `countyOfCity`+@countyofcityage,
				`countyOutCity` = `countyOutCity`+@countyoutcityage,`manTotal` = `manTotal`+@manTotalage,`otherProvinces` = `otherProvinces`+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = `womanTotal`+@womanTotalage
			WHERE `area_id` = @community and statisticalDate =curdate() and ageGroup='Below25';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @TownshipStreet and ageGroup='Below25');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'TownshipStreet',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@TownshipStreet,'Below25');
	ELSEIF @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
				`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
			WHERE `area_id` = @TownshipStreet and statisticalDate =curdate() and ageGroup='Below25';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @County and ageGroup='Below25');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'County',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@County,'Below25');

	ELSEIF @isexitage>0 then
	UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
		`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
	WHERE `area_id` = @County and statisticalDate =curdate() and ageGroup='Below25';

  end if;

elseif age>=25 and age<=40 then

	 set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @community and ageGroup='Between25And40');
   if @isexitage=0 then
		
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'Community',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@community,'Between25And40');
  elseif @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = `countyOfCity`+@countyofcityage,
				`countyOutCity` = `countyOutCity`+@countyoutcityage,`manTotal` = `manTotal`+@manTotalage,`otherProvinces` = `otherProvinces`+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = `womanTotal`+@womanTotalage
			WHERE `area_id` = @community and statisticalDate =curdate() and ageGroup='Between25And40';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @TownshipStreet and ageGroup='Between25And40');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'TownshipStreet',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@TownshipStreet,'Between25And40');
	ELSEIF @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
				`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
			WHERE `area_id` = @TownshipStreet and statisticalDate =curdate() and ageGroup='Between25And40';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @County and ageGroup='Between25And40');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'County',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@County,'Between25And40');

	ELSEIF @isexitage>0 then
	UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
		`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
	WHERE `area_id` = @County and statisticalDate =curdate() and ageGroup='Between25And40';

  end if;
   
elseif age>=41 and age<=50 then
	 set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @community and ageGroup='Between41And50');
   if @isexitage=0 then
		
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'Community',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@community,'Between41And50');
  elseif @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = `countyOfCity`+@countyofcityage,
				`countyOutCity` = `countyOutCity`+@countyoutcityage,`manTotal` = `manTotal`+@manTotalage,`otherProvinces` = `otherProvinces`+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = `womanTotal`+@womanTotalage
			WHERE `area_id` = @community and statisticalDate =curdate() and ageGroup='Between41And50';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @TownshipStreet and ageGroup='Between41And50');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'TownshipStreet',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@TownshipStreet,'Between41And50');
	ELSEIF @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
				`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
			WHERE `area_id` = @TownshipStreet and statisticalDate =curdate() and ageGroup='Between41And50';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @County and ageGroup='Between41And50');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'County',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@County,'Between41And50');

	ELSEIF @isexitage>0 then
	UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
		`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
	WHERE `area_id` = @County and statisticalDate =curdate() and ageGroup='Between41And50';

  end if;
elseif age>=51 and age<=60 then

   set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @community and ageGroup='Between51And60');
   if @isexitage=0 then
		
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'Community',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@community,'Between51And60');
  elseif @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = `countyOfCity`+@countyofcityage,
				`countyOutCity` = `countyOutCity`+@countyoutcityage,`manTotal` = `manTotal`+@manTotalage,`otherProvinces` = `otherProvinces`+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = `womanTotal`+@womanTotalage
			WHERE `area_id` = @community and statisticalDate =curdate() and ageGroup='Between51And60';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @TownshipStreet and ageGroup='Between51And60');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'TownshipStreet',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@TownshipStreet,'Between51And60');
	ELSEIF @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
				`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
			WHERE `area_id` = @TownshipStreet and statisticalDate =curdate() and ageGroup='Between51And60';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @County and ageGroup='Between51And60');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'County',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@County,'Between51And60');

	ELSEIF @isexitage>0 then
	UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
		`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
	WHERE `area_id` = @County and statisticalDate =curdate() and ageGroup='Between51And60';

   end if;
elseif age>60 then
   set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @community and ageGroup='MoreThan60');
   if @isexitage=0 then
		
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'Community',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@community,'MoreThan60');
  elseif @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = `countyOfCity`+@countyofcityage,
				`countyOutCity` = `countyOutCity`+@countyoutcityage,`manTotal` = `manTotal`+@manTotalage,`otherProvinces` = `otherProvinces`+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = `womanTotal`+@womanTotalage
			WHERE `area_id` = @community and statisticalDate =curdate() and ageGroup='MoreThan60';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @TownshipStreet and ageGroup='MoreThan60');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'TownshipStreet',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@TownshipStreet,'MoreThan60');
	ELSEIF @isexitage>0 then
			UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
				`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
				`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
			WHERE `area_id` = @TownshipStreet and statisticalDate =curdate() and ageGroup='MoreThan60';
	end if;

	set	@isexitage =(select count(*) from  agestatistical where statisticalDate=curdate() and area_id = @County and ageGroup='MoreThan60');
	if @isexitage=0 then
	INSERT INTO agestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`ageGroup`)
			VALUES
				(@countyofcityage,@countyoutcityage,'County',@manTotalage,@otherprovincesage,@provinceoutcityage,curdate(),1,@womanTotalage,@County,'MoreThan60');

	ELSEIF @isexitage>0 then
	UPDATE agestatistical SET `countyOfCity` = countyOfCity+@countyofcityage,
		`countyOutCity` = countyOutCity+@countyoutcityage,`manTotal` = manTotal+@manTotalage,`otherProvinces` = otherProvinces+@otherprovincesage,`provinceOutCity` = provinceOutCity+@provinceoutcityage,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotalage
	WHERE `area_id` = @County and statisticalDate =curdate() and ageGroup='MoreThan60';
  end if;
end if;
		


end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `proc_areaStatistical`
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_areaStatistical`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_areaStatistical`(in getmedicinerecordId int)
begin

declare	community int;
declare	TownshipStreet int;
declare	County int;
#判断是否存在存在update不存在insert
declare isexitcommunity int;
declare isexitTownshipStreet int;
declare isexitCounty int;
#判断性别
declare	manTotalarea int;
declare	womanTotalarea int;
#判断
declare	countyofcityarea int;
declare	countyoutcityarea int;
declare provinceoutcityarea int;
declare	otherprovincesarea int;


set @manTotalarea= (select count(*) from getmedicinerecord where id =getmedicinerecordId and sex ='男');
set @womanTotalarea= (select count(*) from getmedicinerecord where id =getmedicinerecordId and sex ='女');

set @countyofcityarea = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本市本县');
set @countyoutcityarea = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本市外县');
set @provinceoutcityarea = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本省外市');
set @otherprovincesarea = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='外省市');

set	@community = (select area_id from MachineryEquipment where  id =(select machineryEquipment_id from getmedicinerecord where  id =getmedicinerecordId));
set	@TownshipStreet= (select parentArea_id from area where id=@community);
set	@County=(select parentArea_id from area where id=@TownshipStreet);

set	@isexitcommunity =(select count(*) from  areastatistical where statisticalDate=curdate() and area_id= @community);
set	@isexitTownshipStreet =(select count(*) from  areastatistical where statisticalDate=curdate() and area_id= @TownshipStreet);
set	@isexitCounty =(select count(*) from  areastatistical where statisticalDate=curdate() and area_id= @County);


if @isexitcommunity=0 then
		
	INSERT INTO areastatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`)
			VALUES
				(@countyofcityarea,@countyoutcityarea,'Community',@manTotalarea,@otherprovincesarea,@provinceoutcityarea,curdate(),1,@womanTotalarea,@community);

elseif @isexitcommunity>0 THEN
	UPDATE areastatistical SET `countyOfCity` = `countyOfCity`+@countyofcityarea,
		`countyOutCity` = `countyOutCity`+@countyoutcityarea,`manTotal` = `manTotal`+@manTotalarea,`otherProvinces` = `otherProvinces`+@otherprovincesarea,`provinceOutCity` = provinceOutCity+@provinceoutcityarea,
		`total` = total+1,`womanTotal` = `womanTotal`+@womanTotalarea
	WHERE `area_id` = @community and statisticalDate =curdate() ;
end if;

if @isexitTownshipStreet=0 THEN
			INSERT INTO areastatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`)
			VALUES
				(@countyofcityarea,@countyoutcityarea,'TownshipStreet',@manTotalarea,@otherprovincesarea,@provinceoutcityarea,curdate(),1,@womanTotalarea,@TownshipStreet);
elseif @isexitTownshipStreet>0 THEN
	UPDATE areastatistical SET `countyOfCity` = countyOfCity+@countyofcityarea,
		`countyOutCity` = countyOutCity+@countyoutcityarea,`manTotal` = manTotal+@manTotalarea,`otherProvinces` = otherProvinces+@otherprovincesarea,`provinceOutCity` = provinceOutCity+@provinceoutcityarea,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotalarea
	WHERE `area_id` = @TownshipStreet and statisticalDate =curdate() ;
end if;

if @isexitCounty=0 then
	INSERT INTO areastatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`)
			VALUES
				(@countyofcityarea,@countyoutcityarea,'County',@manTotalarea,@otherprovincesarea,@provinceoutcityarea,curdate(),1,@womanTotalarea,@County);

elseif @isexitCounty>0 then
	UPDATE areastatistical SET `countyOfCity` = countyOfCity+@countyofcityarea,
		`countyOutCity` = countyOutCity+@countyoutcityarea,`manTotal` = manTotal+@manTotalarea,`otherProvinces` = otherProvinces+@otherprovincesarea,`provinceOutCity` = provinceOutCity+@provinceoutcityarea,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotalarea
	WHERE `area_id` = @County and statisticalDate =curdate() ;
end if;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `proc_contraceptiveStatistical`
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_contraceptiveStatistical`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_contraceptiveStatistical`(in getmedicinerecordId int)
begin

declare contraceptive int;
declare	community int;
declare	TownshipStreet int;
declare	County int;
#判断是否存在存在update不存在insert
declare isexitcommunity int;
declare isexitTownshipStreet int;
declare isexitCounty int;
#判断性别
declare	manTotal1 int;
declare	womanTotal1 int;
#判断
declare	countyofcity1 int;
declare	countyoutcity1 int;
declare provinceoutcity1 int;
declare	otherprovinces1 int;

set @contraceptive = (select contraceptive_id from getmedicinerecord where id =getmedicinerecordId);
set @manTotal1= (select count(*) from getmedicinerecord where id =getmedicinerecordId and sex ='男');
set @womanTotal1= (select count(*) from getmedicinerecord where id =getmedicinerecordId and sex ='女');
set @countyofcity1 = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本市本县');
set @countyoutcity1 = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本市外县');
set @provinceoutcity1 = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='本省外市');
set @otherprovinces1 = (select count(*) from getmedicinerecord where id =getmedicinerecordId and turnoverSituation='外省市');

set @community = (select area_id from MachineryEquipment where  id =(select machineryEquipment_id from getmedicinerecord where  id =getmedicinerecordId));
set @TownshipStreet= (select parentArea_id from area where id= @community);
set @County=(select parentArea_id from area where id= @TownshipStreet);

set @isexitcommunity =(select count(*) from  contraceptivestatistical where statisticalDate=curdate() and area_id=@community and contraceptive_id =@contraceptive);
set @isexitTownshipStreet =(select count(*) from  contraceptivestatistical where statisticalDate=curdate() and area_id=@TownshipStreet and contraceptive_id =@contraceptive);
set @isexitCounty =(select count(*) from  contraceptivestatistical where statisticalDate=curdate() and area_id=@County and contraceptive_id =@contraceptive);



if @isexitcommunity=0 then
		
	INSERT INTO contraceptivestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`contraceptive_id`)
			VALUES
				(@countyofcity1,@countyoutcity1,'Community',@manTotal1,@otherprovinces1,@provinceoutcity1,curdate(),1,@womanTotal1,@community,@contraceptive);
			
elseif @isexitcommunity>0 then

	UPDATE contraceptivestatistical SET `countyOfCity` = `countyOfCity`+@countyofcity1,
		`countyOutCity` = `countyOutCity`+@countyoutcity1,`manTotal` = `manTotal`+@manTotal1,`otherProvinces` = `otherProvinces`+@otherprovinces1,`provinceOutCity` = provinceOutCity+@provinceoutcity1,
		`total` = total+1,`womanTotal` = `womanTotal`+@womanTotal1
	WHERE `area_id` = @community and statisticalDate =curdate() and contraceptive_id=@contraceptive;

end if;

if @isexitTownshipStreet=0 then

	INSERT INTO contraceptivestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`contraceptive_id`)
			VALUES
				(@countyofcity1,@countyoutcity1,'TownshipStreet',@manTotal1,@otherprovinces1,@provinceoutcity1,curdate(),1,@womanTotal1,@TownshipStreet,@contraceptive);
elseif @isexitTownshipStreet>0 then
		UPDATE contraceptivestatistical SET `countyOfCity` = countyOfCity+@countyofcity1,
		`countyOutCity` = countyOutCity+@countyoutcity1,`manTotal` = manTotal+@manTotal1,`otherProvinces` = otherProvinces+@otherprovinces1,`provinceOutCity` = provinceOutCity+@provinceoutcity1,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotal1
	WHERE `area_id` = @TownshipStreet and statisticalDate =curdate() and contraceptive_id=@contraceptive;
end if;

if @isexitCounty=0 then
	INSERT INTO contraceptivestatistical
				(`countyOfCity`,`countyOutCity`,`level`,`manTotal`,`otherProvinces`,`provinceOutCity`,`statisticalDate`,`total`,`womanTotal`,`area_id`,`contraceptive_id`)
			VALUES
				(@countyofcity1,@countyoutcity1,'County',@manTotal1,@otherprovinces1,@provinceoutcity1,curdate(),1,@womanTotal1,@County,@contraceptive);
elseif @isexitCounty>0 then
	UPDATE contraceptivestatistical SET `countyOfCity` = countyOfCity+@countyofcity1,
		`countyOutCity` = countyOutCity+@countyoutcity1,`manTotal` = manTotal+@manTotal1,`otherProvinces` = otherProvinces+@otherprovinces1,`provinceOutCity` = provinceOutCity+@provinceoutcity1,
		`total` = total+1,`womanTotal` = womanTotal+@womanTotal1
	WHERE `area_id` = @County and statisticalDate =curdate() and contraceptive_id=@contraceptive;
end if;

end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `statistical`;
DELIMITER ;;
CREATE TRIGGER `statistical` AFTER INSERT ON `getmedicinerecord` FOR EACH ROW begin  
	
	call proc_areaStatistical(new.id); 
	call proc_ageStatistical(new.id,new.age); 
	call proc_contraceptiveStatistical(new.id); 
end
;;
DELIMITER ;