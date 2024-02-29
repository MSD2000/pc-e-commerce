--------------------------------------------------------
--  File created - четвъртък-февруари-29-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Type ADDRESS_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."ADDRESS_T" AS OBJECT 
(
  Country         NVARCHAR2(16),
  District        NVARCHAR2(16),
  PopulatedPlace  NVARCHAR2(16),
  ZIPCode         NVARCHAR2(8),
  FullLocation    NVARCHAR2(128)
)

/
--------------------------------------------------------
--  DDL for Type CATEGORY_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."CATEGORY_T" AS OBJECT 
( 
    CategoryID   NUMBER(10),
    CategoryName VARCHAR2(16)
)

/
--------------------------------------------------------
--  DDL for Type CPU_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."CPU_T" UNDER PRODUCT_T
( 
    Cores NUMBER(3),
    Threads NUMBER(3),
    MinGHz NUMBER(3),
    MaxGHz NUMBER(3),
    TransistorsNM NUMBER(3),
    CacheMemory NUMBER(3),
    TDP NUMBER(3),
    SocketType NVARCHAR2(8),
    IGPU NVARCHAR2(8)
);

/
--------------------------------------------------------
--  DDL for Type CUSTOMER_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."CUSTOMER_T" UNDER USER_T
( 
  PhoneNumber nvarchar2(16),
  Address ADDRESS_T,
  CustomerPoints NUMBER(6,0)
)

/
--------------------------------------------------------
--  DDL for Type EMPLOYEE_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."EMPLOYEE_T" UNDER USER_T
( 
  EmployeeRole nvarchar2(16),
  HireDate nvarchar2(16)
)

/
--------------------------------------------------------
--  DDL for Type FINANCIAL_OBLIGATION_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."FINANCIAL_OBLIGATION_T" AS OBJECT 
( 
  ObligationID NUMBER(10),
  PaymentMethod nvarchar2(8),
  PaymentStatus nvarchar2(8),
  ExpectedAmount number(10,2),
  PayedAmount number(10,2),
  PayedOn nvarchar2(16)
)

/
--------------------------------------------------------
--  DDL for Type KEYBOARD_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."KEYBOARD_T" UNDER PRODUCT_T
(
    Layout NVARCHAR2(16),
    SwitchType NVARCHAR2(16),
    FormFactor NVARCHAR2(16),
    Connectivity NVARCHAR2(16)
)

/
--------------------------------------------------------
--  DDL for Type MANUFACTURER_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."MANUFACTURER_T" AS OBJECT 
( 
  ManufacturerID NUMBER(10),
  ManufacturerName NVARCHAR2(64),
  Country NVARCHAR2(32)
)

/
--------------------------------------------------------
--  DDL for Type MONITOR_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."MONITOR_T" UNDER PRODUCT_T
(
    ScreenSize NUMBER(3),
    Resolution NVARCHAR2(16),
    RefreshRate NUMBER(3),
    PanelType NVARCHAR2(16)
)

/
--------------------------------------------------------
--  DDL for Type MOTHERBOARD_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."MOTHERBOARD_T" UNDER PRODUCT_T
(
    FormFactor NVARCHAR2(8),
    SocketType NVARCHAR2(8),
    Chipset NVARCHAR2(8),
    RAMSlots NUMBER(2),
    RAMType NVARCHAR2(8),
    IO NVARCHAR2(32),
    Interfaces NVARCHAR2(32)
)

/
--------------------------------------------------------
--  DDL for Type ORDER_PRODUCT_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."ORDER_PRODUCT_T" AS OBJECT 
( 
    OrderID NUMBER(10),
    ProductID NUMBER(10),
    OrderedQuantity Number(4),
    Subtotal NUMBER(8,2)
)

/
--------------------------------------------------------
--  DDL for Type ORDER_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."ORDER_T" AS OBJECT 
( 
  OrderID NUMBER(10),
  OrderStatus nvarchar2(8),
  OrderedQuantity number(8),
  TotalPrice number(10,2),
  OrderedOn nvarchar2(16),
  Customer USER_T,
  PromoCode PROMOCODE_T,
  FinancialObligation FINANCIAL_OBLIGATION_T
)

/
--------------------------------------------------------
--  DDL for Type PRODUCT_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."PRODUCT_T" AS OBJECT 
( 
    ProductID NUMBER(10),
    ProductName NVARCHAR2(64),
    ModelName NVARCHAR2(64),
    Manufacturer MANUFACTURER_T,
    Price NUMBER(10,2),
    ProductCategory CATEGORY_T,
    AddedOn NVARCHAR2(16)
)
NOT FINAL;

/
--------------------------------------------------------
--  DDL for Type PROMOCODE_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."PROMOCODE_T" AS OBJECT 
( 
  PromoCodeID NUMBER(10),
  PromoCodeName NVARCHAR2(64),
  DiscountPercentage NUMBER(3),
  StartDate NVARCHAR2(16),
  EndDate NVARCHAR2(16)
)

/
--------------------------------------------------------
--  DDL for Type USER_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##EX23_MARTIN_COURSETASK"."USER_T" AS OBJECT 
(
  UserID NUMBER(10),
  UserNames nvarchar2(64),
  Email nvarchar2(64),
  UserPassword nvarchar2(64),
  CreatedOn nvarchar2(16)
)
NOT FINAL;

/
