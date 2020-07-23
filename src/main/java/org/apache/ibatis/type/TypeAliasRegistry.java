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
package org.apache.ibatis.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.io.Resources;

/**
 * 类型别名注册器
 * @author Clinton Begin
 */
public class TypeAliasRegistry {

  /**
   * mybatis全局维持一个以别名为key，类型对象为value的Map，由registerAlias方法调用，将需要设置别名的类型注册进typeAliases
   */
  private final Map<String, Class<?>> typeAliases = new HashMap<>();

  /**
   * 注册的别名带下划线是基本数据类型注册的别名，不带下划线的是引用类型注册的别名,这些别名是mybatis需要的数据类型别名，由构造器默认注册进typeAliases
   */
  public TypeAliasRegistry() {
    registerAlias("string", String.class);

    registerAlias("byte", Byte.class);
    registerAlias("long", Long.class);
    registerAlias("short", Short.class);
    registerAlias("int", Integer.class);
    registerAlias("integer", Integer.class);
    registerAlias("double", Double.class);
    registerAlias("float", Float.class);
    registerAlias("boolean", Boolean.class);

    registerAlias("byte[]", Byte[].class);
    registerAlias("long[]", Long[].class);
    registerAlias("short[]", Short[].class);
    registerAlias("int[]", Integer[].class);
    registerAlias("integer[]", Integer[].class);
    registerAlias("double[]", Double[].class);
    registerAlias("float[]", Float[].class);
    registerAlias("boolean[]", Boolean[].class);

    registerAlias("_byte", byte.class);
    registerAlias("_long", long.class);
    registerAlias("_short", short.class);
    registerAlias("_int", int.class);
    registerAlias("_integer", int.class);
    registerAlias("_double", double.class);
    registerAlias("_float", float.class);
    registerAlias("_boolean", boolean.class);

    registerAlias("_byte[]", byte[].class);
    registerAlias("_long[]", long[].class);
    registerAlias("_short[]", short[].class);
    registerAlias("_int[]", int[].class);
    registerAlias("_integer[]", int[].class);
    registerAlias("_double[]", double[].class);
    registerAlias("_float[]", float[].class);
    registerAlias("_boolean[]", boolean[].class);

    registerAlias("date", Date.class);
    registerAlias("decimal", BigDecimal.class);
    registerAlias("bigdecimal", BigDecimal.class);
    registerAlias("biginteger", BigInteger.class);
    registerAlias("object", Object.class);

    registerAlias("date[]", Date[].class);
    registerAlias("decimal[]", BigDecimal[].class);
    registerAlias("bigdecimal[]", BigDecimal[].class);
    registerAlias("biginteger[]", BigInteger[].class);
    registerAlias("object[]", Object[].class);

    registerAlias("map", Map.class);
    registerAlias("hashmap", HashMap.class);
    registerAlias("list", List.class);
    registerAlias("arraylist", ArrayList.class);
    registerAlias("collection", Collection.class);
    registerAlias("iterator", Iterator.class);

    registerAlias("ResultSet", ResultSet.class);
  }

  /*********************************************************************
   * 为指定类注册别名
   *********************************************************************/

  @SuppressWarnings("unchecked")
  // throws class cast exception as well if types cannot be assigned
  /**
   *  1. 只传入key
   * 解析别名,通过别名key从别名池中取出对应的类型对象
   * 输入的string可以是别名(比如string)，也可以是完整的类型名(比如java.lang.String)
   */
  public <T> Class<T> resolveAlias(String string) {
    try {
      // 如果需要解析的别名为空
      if (string == null) {
        return null;
      }
      // issue #748 将所有别名字母转为英文小写
      String key = string.toLowerCase(Locale.ENGLISH);
      Class<T> value;
      // 如果别名池里包含了该需要解析的别名的key,就将别名代表的类型对象取出
      // 如果别名池中没有相应的key,就通过输入的string反射获取类型对象
      if (typeAliases.containsKey(key)) {
        value = (Class<T>) typeAliases.get(key);
      } else {
        value = (Class<T>) Resources.classForName(string);
      }
      return value;
    } catch (ClassNotFoundException e) {
      throw new TypeException("Could not resolve type alias '" + string + "'.  Cause: " + e, e);
    }
  }

  /**
   * 2. 这个方法只传入value(类型对象)
   * 这个传入的类型对象作为别名Map的value
   * 这个别名map的key可以有两种
   *  1. 通过Class类提供的getSimpleName方法获取类型对象的简单类型名为别名
   *  2. 另一种是在实体类上加上Alias注解，以这个注解的值为别名
   * 如果注解值为空(没有加上注解)就默认使用第一种
   * @param type
   */
  public void registerAlias(Class<?> type) {
    String alias = type.getSimpleName();
    Alias aliasAnnotation = type.getAnnotation(Alias.class);
    if (aliasAnnotation != null) {
      alias = aliasAnnotation.value();
    }
    registerAlias(alias, type);
  }

  /**
   * 3. 将别名key和类型对象value都传入
   * @param alias 别名
   * @param value 类型对象
   */
  public void registerAlias(String alias, Class<?> value) {
    if (alias == null) {
      throw new TypeException("The parameter alias cannot be null");
    }
    // issue #748
    String key = alias.toLowerCase(Locale.ENGLISH);
    if (typeAliases.containsKey(key) && typeAliases.get(key) != null && !typeAliases.get(key).equals(value)) {
      throw new TypeException("The alias '" + alias + "' is already mapped to the value '" + typeAliases.get(key).getName() + "'.");
    }
    typeAliases.put(key, value);
  }

  /**
   * 4. 将别名key和类型对象名传入
   * 我们需要通过类型对象名反射为类型对象，再设置进map
   * @param alias 别名
   * @param value 类型对象名
   */
  public void registerAlias(String alias, String value) {
    try {
      registerAlias(alias, Resources.classForName(value));
    } catch (ClassNotFoundException e) {
      throw new TypeException("Error registering type alias " + alias + " for " + value + ". Cause: " + e, e);
    }
  }

  /*********************************************************************
   * 扫描一个包名下的所有类,将这些类都注册为别名
   *********************************************************************/

  /**
   * A. 传入一个包名和一个超类的类型对象
   * 这个包下的所有实体类都有相同的超类类型对象
   * @param packageName
   * @param superType
   */
  public void registerAliases(String packageName, Class<?> superType) {
    // mybatis实现的类解析器工具类
    ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
    // 以superType为基准类型，如果packageName下的类都是这个基准类型对象下的对象就将对象存储到一个全局HashSet中
    resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
    // 从全局HashSet中获取set到新的局部set中
    Set<Class<? extends Class<?>>> typeSet = resolverUtil.getClasses();
    for (Class<?> type : typeSet) {
      // 忽视内联类,匿名类和接口 Ignore inner classes and interfaces (including package-info.java)
      // Skip also inner classes. See issue #6
      if (!type.isAnonymousClass() && !type.isInterface() && !type.isMemberClass()) {
        // 调用上面的方法2
        registerAlias(type);
      }
    }
  }

  /**
   * B. 只需要传入一个包名，类型对象，默认为Object,其他的由A方法实现
   * @param packageName
   */
  public void registerAliases(String packageName) {
    registerAliases(packageName, Object.class);
  }

  /**
   * Gets the type aliases.
   * 获取mybatis所有注册的别名map
   * @return the type aliases
   * @since 3.2.2
   */
  public Map<String, Class<?>> getTypeAliases() {
    return Collections.unmodifiableMap(typeAliases);
  }

}
