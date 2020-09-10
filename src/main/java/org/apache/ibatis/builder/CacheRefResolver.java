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
package org.apache.ibatis.builder;

import org.apache.ibatis.cache.Cache;

/**
 * 缓存引用解析器
 * 使用mapper构建助手来解析
 * @author Clinton Begin
 */
public class CacheRefResolver {
  /**
   * mapper构建助手，用于解析缓存引用
   */
  private final MapperBuilderAssistant assistant;
  /**
   * 缓存引用命名空间
   */
  private final String cacheRefNamespace;

  public CacheRefResolver(MapperBuilderAssistant assistant, String cacheRefNamespace) {
    this.assistant = assistant;
    this.cacheRefNamespace = cacheRefNamespace;
  }

  /**
   * 解析缓存引用
   * @return
   */
  public Cache resolveCacheRef() {
    return assistant.useCacheRef(cacheRefNamespace);
  }
}
