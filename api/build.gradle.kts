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


    // jwt 라이브러리 추가
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.security:spring-security-test")
    implementation(kotlin("stdlib"))
}
repositories {
    mavenCentral()
}

