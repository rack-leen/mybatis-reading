/**
 *    Copyright 2009-2017 the original author or authors.
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
package org.apache.ibatis.builder.annotation;

import java.lang.reflect.Method;

/**
 * 方法解析器
 * @author Eduardo Macarron
 */
public class MethodResolver {
  /**
   * 注解构建器
   */
  private final MapperAnnotationBuilder annotationBuilder;
  /**
   * 方法属性，用来将mapper接口中的方法反射为实体类方法
   */
  private final Method method;

  public MethodResolver(MapperAnnotationBuilder annotationBuilder, Method method) {
    this.annotationBuilder = annotationBuilder;
    this.method = method;
  }

  /**
   * 解析方法注解，将注解解析为sql语句
   */
  public void resolve() {
    annotationBuilder.parseStatement(method);
  }

}
