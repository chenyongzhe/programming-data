# 事务

---
## 什么是事务

事务（Transaction）是一个对数据库执行工作单元。事务（Transaction）是以逻辑顺序完成的工作单位或序列，可以是由用户手动操作完成，也可以是由某种数据库程序自动完成。使用事务处理（transaction processing），通过确保成批的SQL操作要么完全执行，要么完全不执行，来维护数据库的完整性。

可以把许多的 SQLite 查询联合成一组，把所有这些放在一起作为事务的一部分进行执行。

---
## 事务的属性

事务（Transaction）具有以下四个标准属性，通常根据首字母缩写为 ACID：

*   **原子性（Atomicity）：**确保工作单位内的所有操作都成功完成，否则，事务会在出现故障时终止，之前的操作也会回滚到以前的状态。
*   **一致性（Consistency)：**确保数据库在成功提交的事务上正确地改变状态。
*   **隔离性（Isolation）：**使事务操作相互独立和透明。
*   **持久性（Durability）：**确保已提交事务的结果或效果在系统发生故障的情况下仍然存在。

---
## 事务控制

使用下面的命令来控制事务：

*   **BEGIN TRANSACTION**：开始事务处理。可以简化为BEGIN
*   **COMMIT**：保存更改，或者可以使用 **END TRANSACTION** 命令。
*   **ROLLBACK**：回滚所做的更改。

事务控制命令只与 DML 命令 INSERT、UPDATE 和 DELETE 一起使用。他们不能在创建表或删除表时使用，因为这些操作在数据库中是自动提交的。

---
## 事务与性能

SQLite 默认会为每个插入、更新操作创建一个事务，并且在每次插入、更新后立即提交。如果我们插入 1000 条数据，那么这个过程就会反复执行 1000 次，而如果我们显式地开启事务，那么事务操作只有一次，1000 插入都在内存中进行，最后提交才会进行 I/O 操作，这大大提升了数据库操作性能。