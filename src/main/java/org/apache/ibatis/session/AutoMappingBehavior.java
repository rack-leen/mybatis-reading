/**
 *    Copyright 2009-2019 the original author or authors.
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
 * Specifies if and how MyBatis should automatically map columns to fields/properties.
 *
 * @author Eduardo Macarron
 *
 * 指定mybatis是否应该和怎样自动映射指定列到字段或者属性
 *
 * NONE ： 禁止自动映射
 * PARTIAL : 只会自动映射没有定义嵌套结果集映射的结果集(只有一个结果集)
 * FULL : 自动映射任意复杂的结果集(当多个嵌套的结果集(也就是我们定义的实体类)的字段不能有重复的)
 * 参考： https://www.cnblogs.com/TheViper/p/4480765.html
 */
public enum AutoMappingBehavior {

  /**
   * Disables auto-mapping.
   */
  NONE,

  /**
   * Will only auto-map results with no nested result mappings defined inside.
   */
  PARTIAL,

  /**
   * Will auto-map result mappings of any complexity (containing nested or otherwise).
   */
  FULL
}
