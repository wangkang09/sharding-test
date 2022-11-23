#######----------------------------encrypt 数据库


create table global_tx_status
(
    id               bigint unsigned auto_increment comment '主键'
        primary key,
    global_tx_id     varchar(64)  default ''                not null comment '全局事务id（协调者生成）',
    uuid             varchar(64)  default ''                not null comment '全局事务唯一id（客户端生成）',
    insert_time      datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    last_update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remark           varchar(200) default ''                not null comment '备注',
    tx_status        tinyint      default 1                 not null comment '事务状态，1表示事务成功',
    sharding_value   varchar(50)  default ''                null,
    constraint uk_uuid
        unique (uuid)
)
    comment '分支事务表状态表。一般只记录成功的事务就可以了' collate = utf8mb4_bin;

create index idx_global_tx_id
    on global_tx_status (global_tx_id);

create index idx_tbt_last_update_time
    on global_tx_status (last_update_time);
	
create table rs_order_1
(
    id          bigint unsigned              not null
        primary key,
    member_id   bigint unsigned              not null,
    billno      varchar(100)     default ''  not null,
    STATUS      varchar(10)      default ''  not null,
    is_del      tinyint unsigned default '0' not null,
    email       varchar(100)     default ''  not null,
    email_plain varchar(100)     default ''  not null,
    email_hash  varchar(100)     default ''  not null
);

create table rs_order_3
(
    id          bigint unsigned              not null
        primary key,
    member_id   bigint unsigned              not null,
    billno      varchar(100)     default ''  not null,
    STATUS      varchar(10)      default ''  not null,
    is_del      tinyint unsigned default '0' not null,
    email       varchar(100)     default ''  not null,
    email_plain varchar(100)     default ''  not null,
    email_hash  varchar(100)     default ''  not null
);


#######----------------------------test 数据库

create table global_tx_status
(
    id               bigint unsigned auto_increment comment '主键'
        primary key,
    global_tx_id     varchar(64)  default ''                not null comment '全局事务id（协调者生成）',
    uuid             varchar(64)  default ''                not null comment '全局事务唯一id（客户端生成）',
    insert_time      datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    last_update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remark           varchar(200) default ''                not null comment '备注',
    tx_status        tinyint      default 1                 not null comment '事务状态，1表示事务成功',
    sharding_value   varchar(50)  default ''                null,
    constraint uk_uuid
        unique (uuid)
)
    comment '分支事务表状态表。一般只记录成功的事务就可以了' collate = utf8mb4_bin;

create index idx_global_tx_id
    on global_tx_status (global_tx_id);

create index idx_tbt_last_update_time
    on global_tx_status (last_update_time);
	
create table rs_order_0
(
    id          bigint unsigned              not null
        primary key,
    member_id   bigint unsigned              not null,
    billno      varchar(100)     default ''  not null,
    STATUS      varchar(10)      default ''  not null,
    is_del      tinyint unsigned default '0' not null,
    email       varchar(100)     default ''  not null,
    email_plain varchar(100)     default ''  not null,
    email_hash  varchar(100)     default ''  not null
);

create table rs_order_2
(
    id          bigint unsigned              not null
        primary key,
    member_id   bigint unsigned              not null,
    billno      varchar(100)     default ''  not null,
    STATUS      varchar(10)      default ''  not null,
    is_del      tinyint unsigned default '0' not null,
    email       varchar(100)     default ''  not null,
    email_plain varchar(100)     default ''  not null,
    email_hash  varchar(100)     default ''  not null
);

	