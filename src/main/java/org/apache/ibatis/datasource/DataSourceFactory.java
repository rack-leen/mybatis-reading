/**
 *    Copyright 2009-2020 the original author or authors.
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
package org.apache.ibatis.datasource;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * 数据源工厂接口，用于对外提供
 *  对数据源操作有池化，非池化和jndi
 *    池化: 创建一个连接池，我们使用数据源就从连接池中拿
 *    非池化: 每次都要创建一个连接
 *    jndi:
 * @author Clinton Begin
 */
public interface DataSourceFactory {

  /**
   * 为工厂设置数据源需要的属性
   * @param props 配置属性
   */
  void setProperties(Properties props);

  /**
   * 工厂生产数据源
   * @return
   */
  DataSource getDataSource();

}
