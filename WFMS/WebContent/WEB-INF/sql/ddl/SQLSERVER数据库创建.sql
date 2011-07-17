IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'wfms')
	DROP DATABASE [wfms]
GO

CREATE DATABASE [wfms]  ON (NAME = N'wfms_Data', FILENAME = N'e:\DATA\MSSQL\wfms_Data.MDF' , SIZE = 5, FILEGROWTH = 10%) LOG ON (NAME = N'wfms_Log', FILENAME = N'e:\DATA\MSSQL\wfms_Log.LDF' , SIZE = 5, FILEGROWTH = 10%)
 COLLATE Chinese_PRC_CI_AS
GO

exec sp_dboption N'wfms', N'autoclose', N'false'
GO

exec sp_dboption N'wfms', N'bulkcopy', N'false'
GO

exec sp_dboption N'wfms', N'trunc. log', N'false'
GO

exec sp_dboption N'wfms', N'torn page detection', N'true'
GO

exec sp_dboption N'wfms', N'read only', N'false'
GO

exec sp_dboption N'wfms', N'dbo use', N'false'
GO

exec sp_dboption N'wfms', N'single', N'false'
GO

exec sp_dboption N'wfms', N'autoshrink', N'false'
GO

exec sp_dboption N'wfms', N'ANSI null default', N'false'
GO

exec sp_dboption N'wfms', N'recursive triggers', N'false'
GO

exec sp_dboption N'wfms', N'ANSI nulls', N'false'
GO

exec sp_dboption N'wfms', N'concat null yields null', N'false'
GO

exec sp_dboption N'wfms', N'cursor close on commit', N'false'
GO

exec sp_dboption N'wfms', N'default to local cursor', N'false'
GO

exec sp_dboption N'wfms', N'quoted identifier', N'false'
GO

exec sp_dboption N'wfms', N'ANSI warnings', N'false'
GO

exec sp_dboption N'wfms', N'auto create statistics', N'true'
GO

exec sp_dboption N'wfms', N'auto update statistics', N'true'
GO

if( ( (@@microsoftversion / power(2, 24) = 8) and (@@microsoftversion & 0xffff >= 724) ) or ( (@@microsoftversion / power(2, 24) = 7) and (@@microsoftversion & 0xffff >= 1082) ) )
	exec sp_dboption N'wfms', N'db chaining', N'false'
GO

use [wfms]
GO


----------  创建表

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[DEP_GENINF]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[DEP_GENINF]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MEM_GENINF]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MEM_GENINF]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MEM_ROL]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MEM_ROL]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MEM_MODULE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MEM_MODULE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MODULE_GENINF]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MODULE_GENINF]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ROLE_MODULE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ROLE_MODULE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ROL_GENINF]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ROL_GENINF]
GO

---  部门表
CREATE TABLE [dbo].[DEP_GENINF] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[DEPTNAME] [varchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[PARENTID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MEMO] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

---- 人员表
CREATE TABLE [dbo].[MEM_GENINF] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[LOGINID] [varchar] (16) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[LOGINPWD] [varchar] (16) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[PHONE] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[EMAIL] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[OFFICEPHONE] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[WORKPLACE] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[FAX] [char] (1) COLLATE Chinese_PRC_CI_AS NULL ,
	[BRITHDAY] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[BRITHPLACE] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[MOBILEPHONE] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[HOMEPHONE] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[ADDRESS] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[REGISTEREDADDRESS] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[GENDER] [char] (1) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MARRIAGE] [char] (1) COLLATE Chinese_PRC_CI_AS NULL ,
	[DENIEDLOGIN] [char] (1) COLLATE Chinese_PRC_CI_AS NULL ,
	[LASTLOGINTIME] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[USERNAME] [varchar] (16) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[ROLID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MEMO] [varchar] (128) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

---- 人员与功能模块对应表
CREATE TABLE [dbo].[MEM_MODULE] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MEMID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MODULEID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[GRANTTYPE] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL 
) ON [PRIMARY]
GO

----  员工与职务关联
CREATE TABLE [dbo].[MEM_ROLE] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MEMID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[ROLID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL 
) ON [PRIMARY]
GO

----   功能模块表
CREATE TABLE [dbo].[MODULE_GENINF] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[PARENTID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[MODULENAME] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[PHONETICIZE] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[FORWARDPAGE] [varchar] (128) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[CREATETIME] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[DESCRIPTION] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[MEMO] [varchar] (128) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

-----  职务与功能模块对应表
CREATE TABLE [dbo].[ROLE_MODULE] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[ROLEID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MODULEID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[GRANTTYPE] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

----   职务表
CREATE TABLE [dbo].[ROL_GENINF] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[ROLNAME] [varchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[DEPT] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[CREATETIME] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[MEM0] [char] (128) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[DEP_GENINF] ADD 
	CONSTRAINT [PK_DEP_GENINF] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[MEM_GENINF] ADD 
	CONSTRAINT [PK_MEM_GENINF] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[MEM_ROL] ADD 
	CONSTRAINT [PK_MEM_ROL] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[MEM_MODULE] ADD 
	CONSTRAINT [PK_PER_MEMMODULE] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[MODULE_GENINF] ADD 
	CONSTRAINT [PK_MODULE_GENINF] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[ROLE_MODULE] ADD 
	CONSTRAINT [PK_ROLE_MODULE] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[ROL_GENINF] ADD 
	CONSTRAINT [PK_ROL_GENINF] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

