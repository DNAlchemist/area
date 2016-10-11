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
    testCompile group: 'org.spockframework', name: 'spock-core', version: '1.1-groovy-2.4-rc-2'
}

test {
    exclude '**/*IT.*'
}

task integrationTest( type: Test )
