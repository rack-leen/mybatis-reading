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
 * @author Eduardo Macarron
 * mybatis缓存作用
 * 在同一个缓存域中两次执行相同的sql语句，在第一次访问数据库后将数据写入缓存，当缓存还存在时，就不用重新访问数据库，只需要从缓存中拿去数据
 * mybatis本地缓存级别
 * SESSION : 一级缓存，作用域为一个mybatis会话，在一个mybatis会话中执行的所有语句都共享一个缓存
 * STATEMENT : 二级缓存，作用域是一个在同一个namespace域下的mapper文件,多个Session共享
 */
public enum LocalCacheScope {
  SESSION,STATEMENT
}
