## Framework API Automation Test Menggunakan RestAssured dan Web Automation Test Menggunakan Selenium
### [Java, Cucumber, Gradle]

###
### Tujuan Project
Tujuan dari project ini adalah melakukan pengujian api automation menggunakan framework **RestAssured** dan pengujian web automation menggunakan framework **Selenium**.
Bahasa pemrograman yang digunakan adalah java dengan gradle sebagai tools manajemen dependensi serta integrasi Cucumber untuk membangun skenario pengujian berbasis Behavior-Driven Development (BDD).

### Persiapan dan Konfigurasi Project
Berikut adalah langkah yang dilakukan dalam menyiapkan project,
1. Install Java Development Kit (JDK) dan pastikan JDK terbaru sudah terpasang di sistem.
2. Install Gradle pada sistem. Pada project ini menggunakan Gradle Wrapper.
3. Buat proyek Gradle baru untuk proyek testing UI web. Hal ini dapat dilakukan menggunakan perintah `gradle init` untuk membuat proyek Gradle baru.
4. Tambahkan dependensi RestAssured, Selenium WebDriver, dan Cucumber ke file build.gradle. Pada project ini, dependensi yang digunakan adalah sebagai berikut:
```
dependencies {
    testImplementation group: 'io.rest-assured', name: "rest-assured", version: '4.5.1'
    implementation group: 'org.json', name: 'json', version: '20230227'
    testImplementation group: 'io.rest-assured', name: 'json-schema-validator', version: '4.5.1'

    implementation 'org.seleniumhq.selenium:selenium-java:4.9.0'
    implementation 'io.github.bonigarcia:webdrivermanager:5.3.2'

    implementation 'io.cucumber:cucumber-java:7.11.2'
    testImplementation 'io.cucumber:cucumber-junit:7.11.2'

    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}
```
5. Buat file *.feature yang berisi skenario pengujian. 
6. Buat direktori "stepdef" untuk menampung file step definition yang sesuai dengan setiap skenario dalam file *.feature.
7. Implementasikan page object model untuk memisahkan kode pengujian dari kode UI yang memungkinkan pengujian menjadi lebih mudah dan teratur. Dalam POM, setiap halaman di situs web diwakili oleh sebuah objek yang terdiri dari elemen UI dan metode-metode yang menjalankan action pada elemen-elemen tersebut.
8. Buat cucumber task pada file build.gradle, contohnya dapat dilihat sebagai berikut:
```
def tags = (findProperty('tags') == null) ? 'not @exclude' : findProperty('tags') + ' and not @exclude'

task cucumber() {
    description("Running Cucumber Test")
    dependsOn assemble, compileTestJava
    doLast {
        javaexec {
            main = "io.cucumber.core.cli.Main"
            classpath = configurations.cucumberRuntime + sourceSets.main.output + sourceSets.test.output
            args = [
                    '--plugin', 'html:reports/index.html',
                    '--plugin', 'pretty',
                    '--glue', 'com.fajarb',
                    '--tags', "${tags}",
                    'src/test/resources'
            ]
        }
    }
}
```
9. Script pada langkah nomor 8 digunakan jika ingin menjalankan task secara keseluruhan ataupun dengan tag tertentu yang ditentukan melalui parameter pada CLI. Jika ingin membuat task yang terpisah antara task api testing dan web testing, maka dapat dibuat dua task berikut pada file `gradle.build`,
```
task web() {
    description("Running Cucumber for Web UI Test")
    dependsOn assemble, compileTestJava
    doLast {
        javaexec {
            main = "io.cucumber.core.cli.Main"
            classpath = configurations.cucumberRuntime + sourceSets.main.output + sourceSets.test.output
            args = [
                    '--plugin', 'html:reports/report-web.html',
                    '--plugin', 'json:reports/report-web.json',
                    '--plugin', 'pretty',
                    '--glue', 'com.fajarb',
                    '--tags', "@web",
                    'src/test/resources/web'
            ]
        }
    }
}

task api() {
    description("Running Cucumber for API Test")
    dependsOn assemble, compileTestJava
    doLast {
        javaexec {
            main = "io.cucumber.core.cli.Main"
            classpath = configurations.cucumberRuntime + sourceSets.main.output + sourceSets.test.output
            args = [
                    '--plugin', 'html:reports/report-api.html',
                    '--plugin', 'json:reports/report-api.json',
                    '--plugin', 'pretty',
                    '--glue', 'com.fajarb',
                    '--tags', "@api",
                    'src/test/resources/api'
            ]
        }
    }
}
```
10. Jalankan test.

### Cara Menjalankan Pengujian
Akses pada terminal lalu jalankan pengujian menggunakan command gradle.

Jika menggunakan script pada langkah nomor 8, maka gunakan perintah berikut,
```zsh
./gradlew cucumber
```

Jika menggunakan script pada langkah nomor 9, maka gunakan perintah berikut,
```zsh
# untuk task api
./gradlew api

# untuk task web
./gradlew web
```