IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'wfms')
	DROP DATABASE [wfms]
GO

CREATE DATABASE [wfms]  ON (NAME = N'wfms_Data', FILENAME = N'd:\Program Files\Microsoft SQL Server\MSSQL\data\wfms_Data.MDF' , SIZE = 1, FILEGROWTH = 10%) LOG ON (NAME = N'wfms_Log', FILENAME = N'd:\Program Files\Microsoft SQL Server\MSSQL\data\wfms_Log.LDF' , SIZE = 1, FILEGROWTH = 10%)
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

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_DEP_GENINF_MEM_GENINF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[DEP_GENINF] DROP CONSTRAINT FK_DEP_GENINF_MEM_GENINF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_MEM_MODULE_MEM_GENINF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MEM_MODULE] DROP CONSTRAINT FK_MEM_MODULE_MEM_GENINF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_MEM_ROLE_MEM_GENINF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MEM_ROLE] DROP CONSTRAINT FK_MEM_ROLE_MEM_GENINF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_MEM_MODULE_MODULE_GENINF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MEM_MODULE] DROP CONSTRAINT FK_MEM_MODULE_MODULE_GENINF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_ROLE_MODULE_MODULE_GENINF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[ROLE_MODULE] DROP CONSTRAINT FK_ROLE_MODULE_MODULE_GENINF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_MEM_GENINF_ROLE_GENINF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MEM_GENINF] DROP CONSTRAINT FK_MEM_GENINF_ROLE_GENINF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_MEM_ROLE_ROLE_GENINF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MEM_ROLE] DROP CONSTRAINT FK_MEM_ROLE_ROLE_GENINF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_ROLE_MODULE_ROLE_GENINF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[ROLE_MODULE] DROP CONSTRAINT FK_ROLE_MODULE_ROLE_GENINF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[DEP_GENINF]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[DEP_GENINF]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MEM_GENINF]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MEM_GENINF]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MEM_MODULE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MEM_MODULE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MEM_ROLE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MEM_ROLE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MODULE_GENINF]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MODULE_GENINF]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ROLE_GENINF]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ROLE_GENINF]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ROLE_MODULE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ROLE_MODULE]
GO

----  部门信息表
CREATE TABLE [dbo].[DEP_GENINF] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[DEPTNAME] [varchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[PARENTID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MEM_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[MEMO] [varchar] (128) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

----  人员表
CREATE TABLE [dbo].[MEM_GENINF] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[LOGINID] [varchar] (16) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[LOGINPWD] [varchar] (16) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[PHONE] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[EMAIL] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[OFFICEPHONE] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[WORKPLACE] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[FAX] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[BRITHDAY] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[BRITHPLACE] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[MOBILEPHONE] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[HOMEPHONE] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[ADDRESS] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[GENDER] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MARRIAGE] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[DENIEDLOGIN] [char] (1) COLLATE Chinese_PRC_CI_AS NULL ,
	[LASTLOGINTIME] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[USERNAME] [varchar] (16) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[ROLE_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[IDENTITYID] [varchar] (18) COLLATE Chinese_PRC_CI_AS NULL ,
	[MEMO] [varchar] (128) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

----   用户和功能模块表
CREATE TABLE [dbo].[MEM_MODULE] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MEM_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MODULE_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[GRANTTYPE] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL 
) ON [PRIMARY]
GO

----  职务用户表
CREATE TABLE [dbo].[MEM_ROLE] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MEM_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[ROLE_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL 
) ON [PRIMARY]
GO

----  功能模块表
CREATE TABLE [dbo].[MODULE_GENINF] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[PARENTID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[MODULENAME] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[PHONETICIZE] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[FORWARDPAGE] [varchar] (128) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[CREATETIME] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[MODULETYPE] [char] (1) COLLATE Chinese_PRC_CI_AS NULL ,
	[DESCRIPTION] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[MEMO] [varchar] (128) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

----  职务表
CREATE TABLE [dbo].[ROLE_GENINF] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[ROLENAME] [varchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[DEPT_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[CREATETIME] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[MEM0] [char] (128) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

----  职务和功能模块表
CREATE TABLE [dbo].[ROLE_MODULE] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[ROLE_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MODULE_ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[GRANTTYPE] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL 
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

ALTER TABLE [dbo].[MEM_MODULE] ADD 
	CONSTRAINT [PK_PER_MEMMODULE] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[MEM_ROLE] ADD 
	CONSTRAINT [PK_MEM_ROL] PRIMARY KEY  CLUSTERED 
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

ALTER TABLE [dbo].[ROLE_GENINF] ADD 
	CONSTRAINT [PK_ROL_GENINF] PRIMARY KEY  CLUSTERED 
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

ALTER TABLE [dbo].[DEP_GENINF] ADD 
	CONSTRAINT [FK_DEP_GENINF_MEM_GENINF] FOREIGN KEY 
	(
		[MEM_ID]
	) REFERENCES [dbo].[MEM_GENINF] (
		[ID]
	)
GO

ALTER TABLE [dbo].[MEM_GENINF] ADD 
	CONSTRAINT [FK_MEM_GENINF_ROLE_GENINF] FOREIGN KEY 
	(
		[ROLE_ID]
	) REFERENCES [dbo].[ROLE_GENINF] (
		[ID]
	) ON UPDATE CASCADE 
GO

ALTER TABLE [dbo].[MEM_MODULE] ADD 
	CONSTRAINT [FK_MEM_MODULE_MEM_GENINF] FOREIGN KEY 
	(
		[MEM_ID]
	) REFERENCES [dbo].[MEM_GENINF] (
		[ID]
	),
	CONSTRAINT [FK_MEM_MODULE_MODULE_GENINF] FOREIGN KEY 
	(
		[MODULE_ID]
	) REFERENCES [dbo].[MODULE_GENINF] (
		[ID]
	)
GO

ALTER TABLE [dbo].[MEM_ROLE] ADD 
	CONSTRAINT [FK_MEM_ROLE_MEM_GENINF] FOREIGN KEY 
	(
		[MEM_ID]
	) REFERENCES [dbo].[MEM_GENINF] (
		[ID]
	),
	CONSTRAINT [FK_MEM_ROLE_ROLE_GENINF] FOREIGN KEY 
	(
		[ROLE_ID]
	) REFERENCES [dbo].[ROLE_GENINF] (
		[ID]
	)
GO

ALTER TABLE [dbo].[ROLE_MODULE] ADD 
	CONSTRAINT [FK_ROLE_MODULE_MODULE_GENINF] FOREIGN KEY 
	(
		[MODULE_ID]
	) REFERENCES [dbo].[MODULE_GENINF] (
		[ID]
	),
	CONSTRAINT [FK_ROLE_MODULE_ROLE_GENINF] FOREIGN KEY 
	(
		[ROLE_ID]
	) REFERENCES [dbo].[ROLE_GENINF] (
		[ID]
	)
GO

