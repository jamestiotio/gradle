/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gradlebuild

plugins {
    java
}

val validateTaskName = "validatePlugins"
val reportFileName = "task-properties/report.txt"

val validateTask = tasks.register<ValidatePlugins>(validateTaskName)
validateTask {
    configureValidateTask()
}
tasks.named("codeQuality") {
    dependsOn(validateTask)
}
tasks.withType<Test>().configureEach {
    shouldRunAfter(validateTask)
}

fun ValidatePlugins.configureValidateTask() {
    val main = project.sourceSets.main.get()
    classes.from(main.output)
    classpath.from(main.runtimeClasspath)
    outputFile.set(project.reporting.baseDirectory.file(reportFileName))
    enableStricterValidation.set(true)
}
