USE [master]
GO
/****** Object:  Database [VEMAYBAY]    Script Date: 3/1/2024 10:10:50 AM ******/
CREATE DATABASE [VEMAYBAY]
GO
USE [VEMAYBAY]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 3/1/2024 10:10:50 AM ******/
CREATE TABLE [dbo].[Account](
	[USERNAME] [nvarchar](10) NOT NULL,
	[PASSWORD] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[USERNAME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ticket]    Script Date: 3/1/2024 10:10:51 AM ******/
CREATE TABLE [dbo].[Ticket](
	[ID] [int] NOT NULL,
	[SOLD] [bit] NULL,
	[USERNAME] [nvarchar](10) NULL,
 CONSTRAINT [PK_Ticket] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Account] ([USERNAME], [PASSWORD]) VALUES (N'danh', N'123')
INSERT [dbo].[Account] ([USERNAME], [PASSWORD]) VALUES (N'hoa', N'123')
INSERT [dbo].[Account] ([USERNAME], [PASSWORD]) VALUES (N'hoang', N'123')
INSERT [dbo].[Account] ([USERNAME], [PASSWORD]) VALUES (N'huy', N'123')
GO
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (1, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (2, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (3, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (4, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (5, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (6, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (7, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (8, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (9, 0, NULL)
INSERT [dbo].[Ticket] ([ID], [SOLD], [USERNAME]) VALUES (10, 0, NULL)
GO
ALTER TABLE [dbo].[Ticket]  WITH CHECK ADD  CONSTRAINT [FK_Ticket_Account] FOREIGN KEY([USERNAME])
REFERENCES [dbo].[Account] ([USERNAME])
GO
ALTER TABLE [dbo].[Ticket] CHECK CONSTRAINT [FK_Ticket_Account]
GO
USE [master]
GO
ALTER DATABASE [VEMAYBAY] SET  READ_WRITE 
GO
