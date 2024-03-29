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

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ZCGL_CPXX]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ZCGL_CPXX]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ZCGL_KHWH]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ZCGL_KHWH]
GO

CREATE TABLE [dbo].[ZCGL_CPXX] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[CPMC] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[SCCJ] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[CPDH] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[KCSL] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ZCGL_KHWH] (
	[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[KHMC] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[KHDH] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[YHZH] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[XXDZ] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[QY] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[GSFZRID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[GSFZRMC] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[KHFR] [varchar] (16) COLLATE Chinese_PRC_CI_AS NULL ,
	[KHLX] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	[MEMO] [varchar] (128) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ZCGL_CPXX] ADD 
	CONSTRAINT [PK_ZCGL_CPXX] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[ZCGL_KHWH] ADD 
	CONSTRAINT [PK_ZCGL_KHWH] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

