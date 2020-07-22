/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

/**
 * @author Clinton Begin
 * mybatis定义了三种执行器，用来执行sql语句
 * SIMPLE: 默认执行器，对每条sql进行预编译->设置参数->执行等操作
 * REUSE: 执行器会重用预处理语句
 * BATCH: 批量处理器，对相同sql进行一次预编译，然后设置参数，最后统一执行操作
 *
 * 对于单挑sql执行, 不同的执行器没有太大的差异,
 * 当选择批量执行器时, 纵使在获取sqlSession时, 设置了自动提交事务, 也需要手动提交事务
 * 在做批量操作时, 使用批量执行器, 性能会有很大的提升.
 * SIMPLE 每次插入操作, 都会执行编译, 设置参数, 执行sql操作.
 * REUSE 只有第一次插入操作, 执行了sql编译步骤, 对其它插入操作执行了设置参数, 执行sql的操作.
 * BATCH 只对第一次插入操作执行了sql编译操作, 对其它插入操作仅执行了设置参数操作, 最后统一执行.
 * 参考：https://blog.csdn.net/zongf0504/article/details/100104029
 */
public enum ExecutorType {
  SIMPLE, REUSE, BATCH
}
