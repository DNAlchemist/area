buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath "org.akhikhl.gretty:gretty:1.2.4"
    }
}

repositories {
    jcenter()
}

apply plugin: 'groovy'

dependencies {
    testCompile "org.codehaus.groovy:groovy-all:2.4.5"
    testCompile group: 'org.spockframework', name: 'spock-core', version: '1.0-groovy-2.4'
    testCompile group: 'org.akhikhl.gretty', name: 'gretty-spock', version: '1.4.0'
}

test {
    exclude '**/*IT.*'
}

task integrationTest( type: Test )
