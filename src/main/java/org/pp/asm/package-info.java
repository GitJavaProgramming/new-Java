/**
 * 参考
 * maven pom--见项目pom
 * ASM 3.0
 * https://www.ibm.com/developerworks/cn/java/j-lo-asm30/
 * ASM 4.0 之后
 * https://www.iteye.com/blog/yunshen0909-2219310
 *
 * 本项目版本问题 https://mvnrepository.com/artifact/org.ow2.asm
 * asm core版本7.3.1       Last Release on Jan 10, 2020
 * 由于没有使用特定类加载器，系统类加载器优先加载asm core覆盖all中的class 使用 SystemClassLoader
 * asm-all合成版本为5.2    Last Release on Jul 17, 2017
 */
package org.pp.asm;