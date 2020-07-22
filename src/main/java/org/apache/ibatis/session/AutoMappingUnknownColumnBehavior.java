/**
 *    Copyright 2009-2018 the original author or authors.
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

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * Specify the behavior when detects an unknown column (or unknown property type) of automatic mapping target.
 * 指明当检测到一个自动映射目标未知列(或者是未知属性类型)这种行为需要那种级别的日志，默认是不打印日志
 * @since 3.4.0
 * @author Kazuki Shimizu
 */
public enum AutoMappingUnknownColumnBehavior {

  /**
   * Do nothing (Default).
   * 不打印日志
   */
  NONE {
    @Override
    public void doAction(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType) {
      // do nothing
    }
  },

  /**
   * Output warning log.
   * 输出警告日志
   * Note: The log level of {@code 'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior'} must be set to {@code WARN}.
   */
  WARNING {
    @Override
    public void doAction(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType) {
      LogHolder.log.warn(buildMessage(mappedStatement, columnName, property, propertyType));
    }
  },

  /**
   * Fail mapping.
   * 输出错误日志，直接报错
   * Note: throw {@link SqlSessionException}.
   */
  FAILING {
    @Override
    public void doAction(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType) {
      throw new SqlSessionException(buildMessage(mappedStatement, columnName, property, propertyType));
    }
  };

  /**
   * 当检测到自动映射目标有未知列时需要执行的动作
   * Perform the action when detects an unknown column (or unknown property type) of automatic mapping target.
   * @param mappedStatement current mapped statement 一个sql语句在mybatis中的对象
   * @param columnName column name for mapping target 当前映射列名
   * @param propertyName property name for mapping target 当前映射属性名
   * @param propertyType property type for mapping target (If this argument is not null, {@link org.apache.ibatis.type.TypeHandler} for property type is not registered)
   *                     当前映射对象的属性类型
     */
  public abstract void doAction(MappedStatement mappedStatement, String columnName, String propertyName, Class<?> propertyType);

  /**
   * build error message.
   * 构建错误信息，用在FAILING级别上
   */
  private static String buildMessage(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType) {
    return new StringBuilder("Unknown column is detected on '")
      .append(mappedStatement.getId())
      .append("' auto-mapping. Mapping parameters are ")
      .append("[")
      .append("columnName=").append(columnName)
      .append(",").append("propertyName=").append(property)
      .append(",").append("propertyType=").append(propertyType != null ? propertyType.getName() : null)
      .append("]")
      .toString();
  }

  /**
   * 打印警告日志的日志实例，用在WARNING级别上
   */
  private static class LogHolder {
    private static final Log log = LogFactory.getLog(AutoMappingUnknownColumnBehavior.class);
  }

}
