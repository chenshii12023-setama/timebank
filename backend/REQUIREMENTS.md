# 诚朴时间银行需求规格说明书 (Spring Boot + React 版)

## 一、项目背景与目标

### 1.1 项目背景

在社区生活、校园互助与公益服务场景中，大量真实需求与个人技能未被有效连接。主要痛点包括金钱交易门槛高、公益行为缺乏持续激励、个体时间价值难以量化。

“时间银行”通过**以时间为计价单位**（Time Coin），将互助与志愿行为价值化、制度化，构建低成本、高信任的社区协作网络。

### 1.2 项目目标

构建一个前后端分离的“时间币”互助平台，实现：

- **价值量化**：用户时间/技能 -> 时间币。
- **供需连接**：任务发布与接单撮合。
- **安全保障**：基于 Spring Security 的认证体系与 MySQL 事务机制保障资产安全。
- **激励循环**：公益任务与信用评价体系闭环。

------



## 二、产品定位与技术栈

### 2.1 产品定位

- **类型**：B/S 架构社区互助平台
- **核心模式**：C2C 任务协作 + 虚拟货币结算
- **部署形态**：Web 端 SPA（单页应用） + RESTful API 服务端

### 2.2 技术栈选型

- **前端**：React 18 + TypeScript + Ant Design (UI组件库) + Axios
- **后端**：Java (JDK 17/21) + Spring Boot 3.x
- **数据库**：MySQL 8.0
- **中间件**：Redis (可选，用于缓存/Token管理), WebSocket (实时消息)
- **文件存储**：本地存储 或 对象存储 (OSS/MinIO)

### 2.3 用户角色

1. **普通用户**：需求方（发单扣币）/ 服务方（接单赚币）。
2. **志愿者**：承接官方公益任务。
3. **管理员**：后台管理系统用户，负责审核与仲裁。

------



## 三、核心业务流程

1. **注册认证**：用户注册 -> 实名/校内认证（可选） -> 初始化账户。
2. **任务流转**：发布需求（冻结资金） -> 浏览接单 -> 线下/线上执行。
3. **结算验收**：服务提交 -> 需求方确认 -> **系统解冻并转账**。
4. **评价闭环**：双向评分 -> 更新信用分 -> 结束。
5. **异常处理**：超时/取消/纠纷 -> 资金回退或介入仲裁。

------



## 四、功能模块详细需求

### 4.1 用户与认证模块 (Auth & User)

#### 4.1.1 功能描述

基于 Spring Security + JWT 实现无状态认证体系。

#### 4.1.2 功能点

- **注册/登录**：账号密码登录，JWT 颁发与校验。
- **个人中心**：修改头像、昵称、手机号。
- **技能管理**：标签式管理（如：Java、修电脑、拿快递），存为 JSON 或关联表。
- **资产概览**：查看余额、冻结资金、信用分。

#### 4.1.3 数据结构 (User Entity)

- id: Long (主键)
- username: String (唯一)
- password: String (BCrypt加密)
- balance: Integer (可用时间币)
- frozen_balance: Integer (交易冻结资金)
- credit_score: Integer (默认100)
- role: String (ROLE_USER, ROLE_ADMIN)

------



### 4.2 任务大厅与发布模块 (Task Market)

#### 4.2.1 功能描述

任务信息的发布、展示与检索。

#### 4.2.2 功能点

- **发布任务**：
  - 填写标题、详情、截止时间、悬赏金额。
  - **核心逻辑**：发布时校验余额充足，并**开启数据库事务**扣除可用余额至冻结余额。
- **任务大厅**：
  - 分页查询 (PageHelper / Spring Data JPA Pageable)。
  - 多维筛选：按金额、发布时间、技能分类。
  - 搜索：关键字模糊查询。

#### 4.2.3 数据结构 (Task Entity)

- id: Long
- publisher_id: Long
- title: String
- price: Integer
- status: Enum (PENDING, ACCEPTED, IN_PROGRESS, SUBMITTED, COMPLETED, CANCELLED)

------



### 4.3 订单与流转模块 (Order Workflow)

#### 4.3.1 功能描述

管理任务从接单到完成的全生命周期。

#### 4.3.2 功能点

- **接单**：
  - 乐观锁控制（防止多人抢单）。
  - 校验接单人非发布人。
- **状态流转**：
  - SUBMITTED：服务方点击完成。
  - COMPLETED：需求方确认验收（触发资金结算）。
- **取消任务**：
  - 无人接单时：无损取消，解冻资金。
  - 过程中取消：根据规则判定是否扣除信用分。

------



### 4.4 资产与交易模块 (Wallet & Transaction)

#### 4.4.1 功能描述

系统的核心账本，确保资金安全。

#### 4.4.2 功能点

- **流水记录**：任何余额变动（发布锁单、结算转账、充值、提现）必须写入流水表。
- **资金结算**：
  - 使用 @Transactional 注解保证原子性。
  - 验收成功：Publisher.frozen -= price, Worker.balance += price。
- **防篡改**：前端仅展示，所有计算逻辑在后端 Service 层完成。

#### 4.4.3 数据结构 (TransactionRecord Entity)

- id: Long
- user_id: Long (变动账户)
- related_user_id: Long (交易对手)
- amount: Integer (+/-)
- type: String (PUBLISH_LOCK, TASK_INCOME, REFUND)
- task_id: Long

------



### 4.5 评价与信用模块 (Review)

#### 4.5.1 功能描述

建立社区信任机制。

#### 4.5.2 功能点

- **双向评价**：任务状态为 COMPLETED 后，双方可互评。
- **信用分计算**：
  - 初始 100 分。
  - 好评 +1，差评 -5，违约 -10。
  - 信用分 < 60 禁止接单。

------



### 4.6 消息与通知模块 (Notification)

#### 4.6.1 功能描述

解决即时通讯与系统提醒。

#### 4.6.2 技术实现

- **WebSocket**：实现任务详情页内的实时聊天。
- **轮询/SSE**：通知栏消息（"有人接了你的单"）。

------



### 4.7 争议仲裁模块 (Arbitration)

#### 4.7.1 功能描述

处理交易纠纷。

#### 4.7.2 功能点

- **申请仲裁**：上传图片凭证（存入 OSS，数据库存 URL）。
- **管理员后台**：查看举证，判定资金流向（强制结算给服务方 或 退回给发布方）。

------



## 五、系统架构设计

### 5.1 总体架构

采用前后端分离架构，通过 RESTful API 进行交互。

codeCode

```
[Client: Browser/Mobile] 
       | (HTTPS + JWT)
       v
[Nginx / API Gateway] (Optional)
       |
[Backend: Spring Boot Application]
   |-- Controller (Web Layer)
   |-- Service (Business Logic & Transactions)
   |-- Repository (JPA/MyBatis)
       |
[Database: MySQL 8.0]
```

### 5.2 数据库设计 (ER简述)

- **Users**: 用户基础信息、账户余额、信用分。
- **Tasks**: 任务详情、状态、关联的用户ID。
- **Transactions**: 资金流水账本。
- **Reviews**: 评价记录。
- **Arbitrations**: 仲裁工单及凭证。
- **Messages**: 聊天记录（可考虑 MongoDB 或 MySQL 分表）。

------



## 六、API 接口规范

所有接口返回统一 JSON 格式：

codeJSON

```
{
  "code": 200,    // 业务状态码
  "msg": "success", // 提示信息
  "data": { ... }   // 业务数据
}
```

### 核心接口示例

1. **Auth**: POST /api/auth/login
2. **Task**:
   - POST /api/tasks (发布)
   - GET /api/tasks?page=1&category=study (列表)
   - POST /api/tasks/{id}/accept (接单)
   - POST /api/tasks/{id}/confirm (验收结算)
3. **Wallet**: GET /api/wallet/transactions

------



## 七、开发阶段规划

### Phase 1: 基础设施与 MVP (最小可行性产品)

- [后端] 搭建 Spring Boot，配置 MySQL, Swagger, Security。
- [后端] 完成 User, Task, Transaction 表设计与 CRUD。
- [后端] 实现核心事务：发布扣款、验收转账。
- [前端] 搭建 React + Antd，实现登录、任务大厅、详情页、个人中心。

### Phase 2: 信用与交互

- [后端] 评价系统逻辑，信用分计算 Job。
- [全栈] 图片上传功能（集成 OSS/MinIO）。
- [全栈] WebSocket 简易聊天室。

### Phase 3: 运营与治理

- [前端] 开发管理员后台 (Admin Dashboard)。
- [后端] 仲裁逻辑与数据统计接口。