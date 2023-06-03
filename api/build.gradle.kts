

plugins {

}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
// https://mvnrepository.com/artifact/org.springframework.security/spring-security-oauth2-client
    implementation("org.springframework.security:spring-security-oauth2-client:6.0.1")

    // https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-s3
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.261")

    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // https://mvnrepository.com/artifact/org.aspectj/aspectjweaver
    runtimeOnly("org.aspectj:aspectjweaver:1.9.9.1")


    // jwt 라이브러리 추가
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.security:spring-security-test")
    implementation(kotlin("stdlib"))

    //querydsl 추가
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-core:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-kotlin-codegen:5.0.0")
}
repositories {
    mavenCentral()
}

//querydsl 추가
val querydslDir = "$buildDir/generated/querydsl"

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}